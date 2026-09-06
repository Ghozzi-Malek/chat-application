package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import com.example.demo.service.RedisReceiver;
@Configuration 
public class RedisConfig {

    @Bean 
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,MessageListenerAdapter listenerAdapter){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic(redisChannel()));
        return container;
    }

    @Bean 
    public MessageListenerAdapter listenerAdapter(RedisReceiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage");
    }
    @Bean 
    public StringRedisTemplate template(RedisConnectionFactory connectionFactory){
        return new StringRedisTemplate(connectionFactory);
    }
    @Bean 
    public String redisChannel(){
        return "exampleapp";
    }
}
