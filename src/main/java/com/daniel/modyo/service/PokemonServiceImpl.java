package com.daniel.modyo.service;

import com.daniel.modyo.pokeapi.mapper.PokeApiMapper;
import com.daniel.modyo.pokeapi.mapper.bo.PokemonBO;
import com.daniel.modyo.web.dto.Pokemon;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PokemonServiceImpl implements PokemonService {

    @Setter(onMethod_ = @Autowired)
    private PokeApiService pokeApiService;

    @Setter(onMethod_ = @Autowired)
    private PokeApiMapper pokeApiMapper;

    @Override
    public List<Pokemon> getAllPokemon() {
        return null;
    }

    @Override
    public Mono<Pokemon> getPokemon(String name) {
        return pokeApiService.getPokemon(name)
                .flatMap(pokemon -> pokeApiService.getSpecies(pokemon.getSpecies().getName())
                        .flatMap(pokemonSpecies -> pokeApiService.getEvolutionChain(pokemonSpecies.getEvolutionChain())
                                .map(evolutionChain -> new PokemonBO(pokemon, pokemonSpecies, evolutionChain))))
                .map(pokeApiMapper::map)
                ;
    }
}
