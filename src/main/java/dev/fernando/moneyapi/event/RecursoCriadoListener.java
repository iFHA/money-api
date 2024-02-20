package dev.fernando.moneyapi.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class RecursoCriadoListener {
    @EventListener
    public void recursoCriadoHandler(RecursoCriadoEvent recursoCriado){
        var uri = ServletUriComponentsBuilder
        .fromCurrentRequestUri()
        .path("/{id}")
        .buildAndExpand(recursoCriado.getId())
        .toUri();
        recursoCriado.getResponse().setHeader("Location", uri.toASCIIString());
    }
}
