package tsihala.aidesigner.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receiver")
public class ReceiverController {

    private final ChatClient chatClient;

    public ReceiverController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @GetMapping(value = "/chat", produces = "text/plain")
    public String chat() {
        UserMessage um = UserMessage.builder()
                .text("Explain what do you see in this picture?")
                .media(new Media(MimeTypeUtils.IMAGE_PNG, new ClassPathResource("images/img_1.png")))
                .build();

        return chatClient
                .prompt(new Prompt(um))
                .call()
                .content();
    }
}
