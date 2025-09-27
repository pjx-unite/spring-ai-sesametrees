package com.sesametrees.ds.controller;

import com.sesametrees.ds.dao.Stability;
import com.sesametrees.ds.server.StabilityClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeepSeekController {

    private final ChatClient chatClient;
    @Autowired
    private StabilityClient stabilityClient;

    public DeepSeekController(@Qualifier("openAiChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping("/text2ImageV2")
    public Stability stabilityUltra(@RequestParam String message) {
        System.out.println(message);
        String trans = "An 18-year-old prohibited from viewing image.";
        try {
            trans = chatClient.prompt()
                    .user(message + " Translate this sentence into English.")
                    .call()
                    .content();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(trans);
        String urlImagePath = stabilityClient.getMessageUltra(trans).block();
        System.out.println(urlImagePath);
        Stability stability = new Stability();
        stability.setPrompt(message);
        stability.setUrl(urlImagePath);
        return stability;
    }
}
