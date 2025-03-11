package com.eazycodes.gatewayserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;



@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    @Autowired
    FilterUtility filterUtility;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeader = exchange.getRequest().getHeaders();
        if (isCorrelationIdPresent(requestHeader)){
            logger.debug("eazyBank-correlation-id found in RequestTraceFilter : {}",
                    filterUtility.getCorrelationId(requestHeader));
        }else {
            String correlationId  = generateCorrelationId();
            exchange = filterUtility.setCorrelationId(exchange, correlationId);
            logger.debug("eazyBank-correlation-id generated in RequestTraceFilter : {}", correlationId);
        }
        return chain.filter(exchange);
    }

    public boolean isCorrelationIdPresent(HttpHeaders header){
        if (filterUtility.getCorrelationId(header) != null){
            return true;
        }else {
            return false;
        }
    }

    public String generateCorrelationId(){
        return java.util.UUID.randomUUID().toString();
    }
}
