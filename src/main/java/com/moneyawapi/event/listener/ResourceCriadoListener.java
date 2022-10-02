package com.moneyawapi.event.listener;

import com.moneyawapi.event.ResourceCriadoEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class ResourceCriadoListener implements ApplicationListener<ResourceCriadoEvent> {

    @Override
    public void onApplicationEvent(ResourceCriadoEvent event) {

        HttpServletResponse response = event.getResponse();
        Long codigo = event.getCodigo();

        adicionarHeaderLocation(response, codigo);
    }

    private static void adicionarHeaderLocation(HttpServletResponse response, Long codigo) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(codigo).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }
}
