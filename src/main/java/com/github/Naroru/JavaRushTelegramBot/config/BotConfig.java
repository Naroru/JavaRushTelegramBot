package com.github.Naroru.JavaRushTelegramBot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {


    @Value("${bot.token}")
    private String token = "";

    @Bean
    public String getToken()
    {
        return token;
    }
}
