package com.ruoyi.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);
    private static final String START_TIME = "startTime";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(START_TIME, System.currentTimeMillis());

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            long startTime = exchange.getAttribute(START_TIME);
            long endTime = System.currentTimeMillis();
            long duration = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);
            HttpStatus status = exchange.getResponse().getStatusCode();

            String requestPath = exchange.getRequest().getPath().toString();
            String method = exchange.getRequest().getMethodValue();
            String query = exchange.getRequest().getQueryParams().toString();

            log.info("Request: {} {} {} - {}s - Status: {}", method, requestPath, query, duration, status);
        }));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
