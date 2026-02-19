package tsihala.aidesigner.design.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.content.Media;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class DesignGenerationService {

    private static final Logger log = LoggerFactory.getLogger(DesignGenerationService.class);

    private final ChatClient chatClient;

    public DesignGenerationService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String generate() {
        long startedAt = System.nanoTime();
        log.info("Design generation started");

        ClassPathResource imageResource = new ClassPathResource("images/img_1.png");
        String prompt = """
                You are a Senior UI/UX Designer and HTML/CSS integrator.
                Transform the attached tri-fold depliant draft image into a polished HTML/CSS design.
                Keep the structure consistent with the source design and improve visual quality.
                
                Rules:
                - Return only raw HTML with internal CSS.
                - No markdown fences.
                - Include panel wrappers with data-panel-id: RECTO_P1, RECTO_P2, RECTO_P3, VERSO_P1, VERSO_P2, VERSO_P3.
                - Keep French language in content.
                - Do not add interactions: no hover effects, no animations, no transitions, no JavaScript behavior.
                - Respect A4 landscape print size for a tri-fold depliant (297mm x 210mm overall, 3 equal vertical panels per side).
                """;

        String html = chatClient.prompt()
                .user(user -> user
                        .text(prompt)
                        .media(new Media(MimeTypeUtils.IMAGE_PNG, imageResource)))
                .call()
                .content();

        log.info("Design generation completed in {} ms", elapsedMs(startedAt));
        return html;
    }

    private long elapsedMs(long startedAtNano) {
        return (System.nanoTime() - startedAtNano) / 1_000_000;
    }
}
