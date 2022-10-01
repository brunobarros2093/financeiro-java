package com.moneyawapi.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String mensagem = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
        String msgDev = ex.getCause().toString();
        return handleExceptionInternal(ex, new Erro(mensagem, msgDev), headers, HttpStatus.BAD_REQUEST, request);
    }

    public static class Erro {


        private String mensagem;
        private String errorMsg;

        public Erro(String mensagem, String errorMsg) {
            this.mensagem = mensagem;
            this.errorMsg = errorMsg;
        }

        public String getMensagem() {
            return mensagem;
        }

        public void setMensagem(String mensagem) {
            this.mensagem = mensagem;
        }

        public String getErrorCause() {
            return errorMsg;
        }

        public void setErrorCause(String errorMsg) {
            this.errorMsg = errorMsg;
        }
    }
}
