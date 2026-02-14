package com.tech.sus_triage_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTest {
    @Test
    void customOpenAPIReturnsOpenAPIWithInfo() {
        SwaggerConfig config = new SwaggerConfig();
        OpenAPI openAPI = config.customOpenAPI();
        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("sus-triage-api", openAPI.getInfo().getTitle());
        assertEquals("1.0.0", openAPI.getInfo().getVersion());
        assertEquals("Documentação da API com Swagger", openAPI.getInfo().getDescription());
    }
}
