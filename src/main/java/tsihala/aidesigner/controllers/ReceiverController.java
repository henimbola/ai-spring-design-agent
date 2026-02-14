package tsihala.aidesigner.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.AdvisorParams;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/receiver")
public class ReceiverController {

    private final ChatClient chatClient;

    public ReceiverController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @GetMapping(value = "/chat", produces = MediaType.TEXT_HTML_VALUE)
    public String chat() {
        /*
        *                         You are an HTML and CSS integrator.
                        Your task is to transform the media you're receiving as an image into a HTML and CSS page.
                        You can consider the entry image as a design you have to implement.

                        Your response should be only the html/css content. Nothing else.
                        *
                        *
                        *
                        *
                        * "
                        You are a graphic designer and we are giving you an artistic direction with the content on it.
                        But we want you to be creative and think outside the box.

                        Your tasks :
                        1. Describe what you see.
                        2. Think about how you would use that image to create a flyer with that.

                        Your only response should be a prompt to ask an web integrator agent to implement your new ideas.
                        In that prompt, force the agent to only return the HTML and CSS content (without triple quotes).
        * */

        UserMessage um = UserMessage.builder()
                .text("""
                        **Role:** You are a Senior Visual Instructional Designer and Design Architect. Your mission is to eliminate "blank page syndrome" by generating high-fidelity visual blueprints for educational and corporate documents (Flyers, Booklets, PPTs).
                        
                        **Objective:**
                        **Role:** You are a Senior UI/UX Designer specialized in Instructional Design. Your expertise lies in taking "rough drafts" and transforming them into professional, high-fidelity visual layouts while strictly preserving the original information structure.
                        
                        **Objective:**
                        Produce a single, improved version of the user's provided layout in HTML/CSS. [cite_start]The goal is to keep the layout's core structure (where elements are placed)  but significantly enhance the visual aesthetics.
                        
                        **Key Enhancement Areas:**
                        1. **Typography:** Replace standard fonts with modern, Canva-compatible pairings (one for titles, one for body text).
                        2. [cite_start]**Color Palette:** Suggest a new, cohesive color scheme that fits the target audience.
                        3. **Spacing & White Space:** Optimize margins, padding, and line height to improve readability and reduce cognitive load.
                        4. **Iconography:** Integrate modern icon placeholders (using FontAwesome or SVG placeholders) to replace rough sketches or text-based icons.
                        5. **Visual Hierarchy:** Use font weights and color contrasts to guide the eye toward the most important information.
                        
                        **Output Requirements:**
                        - **Format:** A single standalone raw HTML file with internal CSS.
                        - ** Don't put the html on a triple quote. Output the raw html
                        - [cite_start]**Dimensions:** Match the size of the input draft exactly (e.g., A4 or specific pixels).
                        - [cite_start]**Content:** Use the actual text provided in the input files (Possibility 1, 2, or 3).
                        - **Style:** Professional, clean, and polished.
                        
                        **Instruction:** Do not reinvent the layout. [cite_start]If there is a header at the top and three columns at the bottom in the draft, keep that structure. Simply make it look like it was designed by a professional agency.
                        """)
                .media(new Media(MimeTypeUtils.IMAGE_PNG, new ClassPathResource("images/img_1.png")))
                .build();

        return chatClient
                .prompt(new Prompt(um))
                .call()
                .content();
    }
}
