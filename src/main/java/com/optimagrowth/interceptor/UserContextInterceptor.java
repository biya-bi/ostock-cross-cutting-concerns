package com.optimagrowth.interceptor;

import static com.optimagrowth.http.HeaderNames.AUTH_TOKEN;
import static com.optimagrowth.http.HeaderNames.CORRELATION_ID;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.optimagrowth.context.UserContext;
import com.optimagrowth.context.UserContextHolder;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class UserContextInterceptor implements ClientHttpRequestInterceptor, RequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        var headers = request.getHeaders();
        var context = UserContextHolder.getContext();

        forwardHeaders(headers, context);

        return execution.execute(request, body);
    }

    @Override
    public void apply(RequestTemplate template) {
        var context = UserContextHolder.getContext();

        apply(template, context);
    }

    protected void forwardHeaders(HttpHeaders headers, UserContext context) {
        headers.add(CORRELATION_ID, context.getCorrelationId());
        headers.add(AUTH_TOKEN, context.getAuthToken());
    }

    protected void apply(RequestTemplate template, UserContext context) {
        template.header(CORRELATION_ID, context.getCorrelationId());
        template.header(AUTH_TOKEN, context.getAuthToken());
    }

}