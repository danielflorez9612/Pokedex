package com.daniel.modyo.service;

import com.daniel.modyo.pokeapi.dto.PokeApiNamedResource;
import com.daniel.modyo.pokeapi.dto.PokeApiPokemonList;
import com.daniel.modyo.pokeapi.mapper.PokeApiMapper;
import com.daniel.modyo.pokeapi.mapper.bo.PokemonDetailBO;
import com.daniel.modyo.web.dto.Pokemon;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PokemonServiceImpl implements PokemonService {

    @Setter(onMethod_ = @Autowired)
    private PokeApiService pokeApiService;

    @Setter(onMethod_ = @Autowired)
    private PokeApiMapper pokeApiMapper;

    @Override
    public Flux<Pokemon> getAllPokemon(int limit, int offset) {
        return pokeApiService.getAllPokemon(limit, offset)
                .map(PokeApiPokemonList::getResults)
                .flatMapMany(Flux::fromIterable)
                .map(PokeApiNamedResource::getName)
                .flatMap(pokeApiService::getPokemon)
                .map(pokeApiMapper::mapListItem);
    }

    @Override
    public Mono<Pokemon> getPokemon(String name) {
        return pokeApiService.getPokemon(name)
                .flatMap(pokemon -> pokeApiService.getSpecies(pokemon.getSpecies().getName())
                        .flatMap(pokemonSpecies -> pokeApiService.getEvolutionChain(pokemonSpecies.getEvolutionChain())
                                .map(evolutionChain -> new PokemonDetailBO(pokemon, pokemonSpecies, evolutionChain))))
                .map(pokeApiMapper::map)
                ;
    }
}
