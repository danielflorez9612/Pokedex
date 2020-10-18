package com.daniel.modyo.controller;

import com.daniel.modyo.pokeapi.mapper.PokeApiMapper;
import com.daniel.modyo.service.PokeApiService;
import com.daniel.modyo.service.PokemonService;
import com.daniel.modyo.web.dto.Pokemon;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    @Setter(onMethod_=@Autowired)
    private PokemonService pokemonService;

    @GetMapping
    public Flux<Pokemon> getAllPokemon() {
        return null;
    }

    @GetMapping("/{name}")
    public Mono<Pokemon> getPokemon(@PathVariable("name") String name) {
        return pokemonService.getPokemon(name);
    }
}
