package tsihala.aidesigner.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tsihala.aidesigner.design.services.DesignGenerationService;

@RestController
@RequestMapping("/receiver")
public class ReceiverController {

    private static final Logger log = LoggerFactory.getLogger(ReceiverController.class);

    private final DesignGenerationService designGenerationService;

    public ReceiverController(DesignGenerationService designGenerationService) {
        this.designGenerationService = designGenerationService;
    }

    @GetMapping(value = "/chat", produces = MediaType.TEXT_HTML_VALUE)
    public String chat() {
        long startedAt = System.nanoTime();
        log.info("HTTP GET /receiver/chat started");
        try {
            String html = designGenerationService.generate();
            log.info("HTTP GET /receiver/chat succeeded in {} ms", elapsedMs(startedAt));
            return html;
        }
        catch (RuntimeException e) {
            log.error("HTTP GET /receiver/chat failed in {} ms", elapsedMs(startedAt), e);
            throw e;
        }
    }

    private long elapsedMs(long startedAtNano) {
        return (System.nanoTime() - startedAtNano) / 1_000_000;
    }
}
