package com.daniel.modyo.web.pagination;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;


@Component
public class PaginationComponent {
    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";
    public static final String DEFAULT_LIMIT = "10";
    public static final String DEFAULT_OFFSET = "0";
    public static final String NEXT = "next";
    public static final String PREVIOUS = "previous";

    public void setPaginationHeaders(int limit, int offset, ServerHttpResponse response) {
        response.getHeaders().set(LIMIT, String.valueOf(limit));
        response.getHeaders().set(OFFSET, String.valueOf(offset));
        response.getHeaders().set(NEXT, UriComponentsBuilder.newInstance().queryParam(OFFSET, offset + limit).queryParam(LIMIT, limit).build().toUriString());
        int previousOffset = Math.max(offset - limit, 0);
        if (previousOffset > 0) {
            response.getHeaders().set(PREVIOUS, UriComponentsBuilder.newInstance().queryParam(OFFSET, previousOffset).queryParam(LIMIT, limit).toUriString());
        }
    }
}
