package com.daniel.pokedex.repository.mapper.bo;

import com.daniel.pokedex.repository.entity.Pokemon;
import com.daniel.pokedex.repository.entity.species.PokemonSpecies;
import com.daniel.pokedex.repository.entity.evolution.EvolutionChain;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PokemonDetailBO {
    private Pokemon pokemon;
    private PokemonSpecies pokemonSpecies;
    private EvolutionChain evolutionChain;
}
