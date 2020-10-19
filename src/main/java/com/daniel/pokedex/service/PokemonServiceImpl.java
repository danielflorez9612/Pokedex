package com.daniel.pokedex.service;

import com.daniel.pokedex.repository.entity.NamedApiResource;
import com.daniel.pokedex.repository.entity.PokemonList;
import com.daniel.pokedex.repository.mapper.PokeApiMapper;
import com.daniel.pokedex.repository.mapper.bo.PokemonDetailBO;
import com.daniel.pokedex.repository.PokemonRepository;
import com.daniel.pokedex.web.dto.PokemonDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PokemonServiceImpl implements PokemonService {

    @Setter(onMethod_ = @Autowired)
    private PokemonRepository pokemonRepository;

    @Setter(onMethod_ = @Autowired)
    private PokeApiMapper pokeApiMapper;

    @Override
    public Flux<PokemonDto> getAllPokemon(int limit, int offset) {
        return pokemonRepository.getAllPokemon(limit, offset)
                .map(PokemonList::getResults)
                .flatMapMany(Flux::fromIterable)
                .map(NamedApiResource::getName)
                .flatMap(name -> pokemonRepository.getPokemon(name).onErrorResume(e-> Mono.empty()))
                .map(pokeApiMapper::mapListItem);
    }

    @Override
    public Mono<PokemonDto> getPokemon(String name) {
        return pokemonRepository.getPokemon(name)
                .flatMap(pokemon -> pokemonRepository.getSpecies(pokemon.getSpecies().getName())
                        .flatMap(pokemonSpecies -> pokemonRepository.getEvolutionChain(pokemonSpecies.getEvolutionChain())
                                .map(evolutionChain -> new PokemonDetailBO(pokemon, pokemonSpecies, evolutionChain))))
                .map(pokeApiMapper::map)
                ;
    }
}
