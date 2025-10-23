package com.sesametrees.ds;

import com.alibaba.cloud.circuitbreaker.sentinel.SentinelCircuitBreakerFactory;
import com.alibaba.cloud.circuitbreaker.sentinel.SentinelConfigBuilder;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.sesametrees.ds.config.JsonFlowRuleListConverter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@SpringBootApplication
public class SpringAiSesametreesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiSesametreesApplication.class, args);
    }

    @Bean
    public ChatClient openAiChatClient(OpenAiChatModel chatModel) {
        return ChatClient.create(chatModel);
    }

    @Bean
    public Converter myConverter() {
        return new JsonFlowRuleListConverter();
    }

    @Bean
    public Customizer<SentinelCircuitBreakerFactory> defaultConfig() {
        return factory -> {
            factory.configureDefault(
                    id -> new SentinelConfigBuilder().resourceName(id)
                            .rules(Collections.singletonList(new DegradeRule(id)
                                    .setGrade(RuleConstant.DEGRADE_GRADE_RT).setCount(100)
                                    .setTimeWindow(10)))
                            .build());
        };
    }
}
