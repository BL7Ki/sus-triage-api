package com.tech.sus_triage_api.errors;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.List;
import static org.mockito.Mockito.*;

class CustomExceptionHandlerTest {

    @Mock
    private HttpServletRequest request;

    private CustomExceptionHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new CustomExceptionHandler();
        when(request.getServletPath()).thenReturn("/api/test");
        when(request.getRequestURI()).thenReturn("/api/test");
    }

    @Test
    void summerException_deveRetornarErroView() {
        SummerException ex = new SummerException("Erro de negócio");
        ErroView erro = handler.SummerException(ex, request);
        assertEquals("Erro de negócio", erro.getMessage());
        assertEquals("BAD_REQUEST", erro.getError());
        assertEquals("/api/test", erro.getPath());
    }

    @Test
    void summerNotFoundException_deveRetornarErroView() {
        SummerNotFoundException ex = new SummerNotFoundException("Não encontrado");
        ErroView erro = handler.SummerNotFoundException(ex, request);
        assertEquals("Não encontrado", erro.getMessage());
        assertEquals("NOT_FOUND", erro.getError());
        assertEquals("/api/test", erro.getPath());
    }

    @Test
    void userDomainException_deveRetornarErroView() {
        UserDomainException ex = new UserDomainException("Erro de domínio");
        ErroView erro = handler.SummerParseException(ex, request);
        assertEquals("Erro de domínio", erro.getMessage());
        assertEquals("BAD_REQUEST", erro.getError());
        assertEquals("/api/test", erro.getPath());
    }

    @Test
    void handleGenericException_deveRetornarErroView() {
        Exception ex = new Exception("Erro genérico");
        ErroView erro = handler.handleGenericException(ex, request);
        assertEquals("Erro genérico", erro.getMessage());
        assertEquals("INTERNAL_SERVER_ERROR", erro.getError());
        assertEquals("/api/test", erro.getPath());
    }

    @Test
    void handleValidationExceptions_deveRetornarErroViewComMensagens() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("object", "campo1", "mensagem1");
        FieldError fieldError2 = new FieldError("object", "campo2", "mensagem2");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ErroView erro = handler.handleValidationExceptions(ex, request);
        assertTrue(erro.getMessage().contains("campo1: mensagem1"));
        assertTrue(erro.getMessage().contains("campo2: mensagem2"));
        assertEquals("BAD_REQUEST", erro.getError());
        assertEquals("/api/test", erro.getPath());
    }
}