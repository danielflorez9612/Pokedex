package com.daniel.modyo.controller;

import com.daniel.modyo.service.PokemonService;
import com.daniel.modyo.web.dto.PokemonDto;
import com.daniel.modyo.web.pagination.PaginationComponent;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    @Setter(onMethod_=@Autowired)
    private PokemonService pokemonService;

    @Setter(onMethod_=@Autowired)
    private PaginationComponent paginationComponent;

    @GetMapping
    public Flux<PokemonDto> getAllPokemon(
            @RequestParam(name = PaginationComponent.LIMIT, defaultValue = PaginationComponent.DEFAULT_LIMIT) int limit,
            @RequestParam(name = PaginationComponent.OFFSET, defaultValue = PaginationComponent.DEFAULT_OFFSET) int offset,
            ServerHttpResponse response
    ) {
        paginationComponent.setPaginationHeaders(limit, offset, response);
        return pokemonService.getAllPokemon(limit, offset).sort(Comparator.comparing(PokemonDto::getId));
    }

    @GetMapping("/{name}")
    public Mono<PokemonDto> getPokemon(@PathVariable("name") String name) {
        return pokemonService.getPokemon(name);
    }
}
