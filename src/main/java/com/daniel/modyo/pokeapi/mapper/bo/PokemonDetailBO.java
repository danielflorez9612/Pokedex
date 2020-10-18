package com.daniel.modyo.pokeapi.mapper.bo;

import com.daniel.modyo.pokeapi.dto.Pokemon;
import com.daniel.modyo.pokeapi.dto.species.PokemonSpecies;
import com.daniel.modyo.pokeapi.dto.evolution.EvolutionChain;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PokemonDetailBO {
    private Pokemon pokemon;
    private PokemonSpecies pokemonSpecies;
    private EvolutionChain evolutionChain;
}
