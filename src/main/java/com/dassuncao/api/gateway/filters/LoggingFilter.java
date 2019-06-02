package com.dassuncao.api.gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.WebClientWriteResponseFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.CLIENT_RESPONSE_ATTR;

@Slf4j
@Configuration
public class LoggingFilter {

    @Bean
    public GlobalFilter filter() {
        return (exchange, chain) -> {
            loggingRequest(exchange);
            return chain.filter(exchange).then(loggingResponse(exchange));
        };
    }

    private void loggingRequest(final ServerWebExchange exchange) {
        defaultLoggingParameters(exchange);
    }

    private Mono<Void> loggingResponse(final ServerWebExchange exchange) {
        return Mono.fromRunnable(() -> defaultLoggingParameters(exchange));
    }

    private void defaultLoggingParameters(final ServerWebExchange exchange) {
        final var gatewayRequestUrl = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        final var gatewayRoute = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        final var clientResponse = exchange.getAttribute(ServerWebExchangeUtils.CLIENT_RESPONSE_ATTR);
        final var clientResponseHeader = exchange.getAttribute(ServerWebExchangeUtils.CLIENT_RESPONSE_HEADER_NAMES);
        log.info("gatewayRequestUrl={}, clientResponseHeader={}, gatewayRoute={}, clientResponse={}",
                gatewayRequestUrl, clientResponseHeader, gatewayRoute, clientResponse);
    }
}