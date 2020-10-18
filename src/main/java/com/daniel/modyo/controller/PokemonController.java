package com.daniel.modyo.controller;

import com.daniel.modyo.service.PokemonService;
import com.daniel.modyo.web.dto.Pokemon;
import com.daniel.modyo.web.pagination.PaginationComponent;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    @Setter(onMethod_=@Autowired)
    private PokemonService pokemonService;

    @Setter(onMethod_=@Autowired)
    private PaginationComponent paginationComponent;

    @GetMapping
    public Flux<Pokemon> getAllPokemon(
            @RequestParam(name = PaginationComponent.LIMIT, defaultValue = PaginationComponent.DEFAULT_LIMIT) int limit,
            @RequestParam(name = PaginationComponent.OFFSET, defaultValue = PaginationComponent.DEFAULT_OFFSET) int offset,
            HttpServletResponse response
    ) {
        paginationComponent.setPaginationHeaders(limit, offset, response);
        return pokemonService.getAllPokemon(limit, offset).sort(Comparator.comparing(Pokemon::getId));
    }

    @GetMapping("/{name}")
    public Mono<Pokemon> getPokemon(@PathVariable("name") String name) {
        return pokemonService.getPokemon(name);
    }
}
