package dev.fernando.moneyapi.event;

import jakarta.servlet.http.HttpServletResponse;

public class RecursoCriadoEvent {

    private static final long serialVersionUID = 1L;

    private Long id;
    private HttpServletResponse response;

    public RecursoCriadoEvent(Long id, HttpServletResponse response) {
        this.id = id;
        this.response = response;
    }

    public Long getId() {
        return id;
    }
    
    public HttpServletResponse getResponse() {
        return this.response;
    }
}
