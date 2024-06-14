package com.optimagrowth.interceptor;

import static com.optimagrowth.http.HeaderNames.CORRELATION_ID;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.util.function.BiConsumer;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.optimagrowth.context.UserContextHolder;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class UserContextInterceptor implements ClientHttpRequestInterceptor, RequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        var headers = request.getHeaders();

        applyContext(headers::add);

        return execution.execute(request, body);
    }

    @Override
    public void apply(RequestTemplate template) {
        applyContext(template::header);
    }

    private void applyContext(BiConsumer<String, String> consumer) {
        var context = UserContextHolder.getContext();

        consumer.accept(CORRELATION_ID, context.getCorrelationId());
        consumer.accept(AUTHORIZATION, context.getAuthToken());
    }

}