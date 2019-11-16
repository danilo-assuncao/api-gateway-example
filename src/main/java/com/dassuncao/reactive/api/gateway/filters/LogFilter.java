package com.dassuncao.reactive.api.gateway.filters;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;

@Slf4j
@Component
public class LogFilter implements GatewayFilterFactory<LogFilter.Config> {

    @Override
    public GatewayFilter apply(final LogFilter.Config config) {
        return (exchange, chain) -> {
            logRequest(exchange);
            return chain.filter(exchange)
                    .then(logResponse(exchange));
        };
    }

    private void logRequest(final ServerWebExchange exchange) {
        defaultLogParameters(exchange);
    }

    private Mono<Void> logResponse(final ServerWebExchange exchange) {
        return Mono.fromRunnable(() -> defaultLogParameters(exchange));
    }

    private void defaultLogParameters(final ServerWebExchange exchange) {
        final var gatewayRequestUrl = exchange.getAttribute(GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        final var gatewayRoute = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
        final var clientResponse = exchange.getAttribute(CLIENT_RESPONSE_ATTR);
        final var clientResponseHeader = exchange.getAttribute(CLIENT_RESPONSE_HEADER_NAMES);
        log.info("gatewayRequestUrl={}, clientResponseHeader={}, gatewayRoute={}, clientResponse={}",
                gatewayRequestUrl, clientResponseHeader, gatewayRoute, clientResponse);
    }

    @Override
    public Class<LogFilter.Config> getConfigClass() {
        return LogFilter.Config.class;
    }

    @Override
    public LogFilter.Config newConfig() {
        return new Config(this.name());
    }

    @Value
    static class Config {
        private String configName;
    }
}