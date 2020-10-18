package com.daniel.modyo.web.pagination;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

@Component
public class PaginationComponent {
    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";
    public static final String DEFAULT_LIMIT = "10";
    public static final String DEFAULT_OFFSET = "0";
    public static final String NEXT = "next";
    public static final String PREVIOUS = "previous";

    public void setPaginationHeaders(int limit, int offset, HttpServletResponse response) {
        response.setHeader(LIMIT, String.valueOf(limit));
        response.setHeader(OFFSET, String.valueOf(offset));
        response.setHeader(NEXT, UriComponentsBuilder.newInstance().queryParam(OFFSET, offset + limit).queryParam(LIMIT, limit).build().toUriString());
        response.setHeader(PREVIOUS, UriComponentsBuilder.newInstance().queryParam(OFFSET, Math.max(offset - limit, 0)).queryParam(LIMIT, limit).toUriString());
    }
}
