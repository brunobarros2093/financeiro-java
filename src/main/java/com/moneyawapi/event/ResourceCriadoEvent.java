package com.moneyawapi.event;

import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

public class ResourceCriadoEvent extends ApplicationEvent {

    private HttpServletResponse response;
    private Long codigo;


    public ResourceCriadoEvent(Object source, HttpServletResponse response, Long codigo) {
        super(source);
        this.response = response;
        this.codigo = codigo;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Long getCodigo() {
        return codigo;
    }
}
