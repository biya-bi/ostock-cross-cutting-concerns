package com.optimagrowth.config;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.optimagrowth.context.UserContextHolder;
import com.optimagrowth.service.MessageService;

@Configuration
public class MessageConfig {

    @Bean
    public MessageSource messageSource() {
        var messageSource = new ResourceBundleMessageSource();
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return messageSource;
    }

    @Bean
    public MessageService messageService(MessageSource messageSource) {
        return new MessageServiceImpl(messageSource);
    }

    private static class MessageServiceImpl implements MessageService {
        private final MessageSource messageSource;

        public MessageServiceImpl(MessageSource messageSource) {
            this.messageSource = messageSource;
        }

        @Override
        public String getMessage(String key, Object... args) {
            var locale = getLocale();
            return messageSource.getMessage(key, args, locale);
        }

        private Locale getLocale() {
            var language = UserContextHolder.getContext().getLanguage();
            if (language != null) {
                return Locale.forLanguageTag(language);
            }
            return Locale.ENGLISH;
        }
    }

}
