package tsihala.aidesigner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AiDesignerApplicationTests {

    @Test
    void applicationClassLoads() {
        assertEquals("AiDesignerApplication", AiDesignerApplication.class.getSimpleName());
    }
}
