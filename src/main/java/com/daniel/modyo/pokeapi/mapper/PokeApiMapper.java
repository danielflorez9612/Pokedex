package com.daniel.modyo.pokeapi.mapper;

import com.daniel.modyo.pokeapi.dto.PokeApiAbilityItem;
import com.daniel.modyo.pokeapi.dto.PokeApiNamedResource;
import com.daniel.modyo.pokeapi.dto.PokeApiTypeItem;
import com.daniel.modyo.pokeapi.dto.Pokemon;
import com.daniel.modyo.pokeapi.dto.evolution.ChainLink;
import com.daniel.modyo.pokeapi.dto.evolution.EvolutionChain;
import com.daniel.modyo.pokeapi.dto.species.FlavorText;
import com.daniel.modyo.pokeapi.dto.species.PokemonSpecies;
import com.daniel.modyo.pokeapi.mapper.bo.PokemonDetailBO;
import com.daniel.modyo.web.dto.PokemonDto;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PokeApiMapper {
    private static List<ChainLink> getEvolutionChainFor(PokeApiNamedResource species, ChainLink evolutionChain) {
        if (evolutionChain.getSpecies().getName().equals(species.getName())) {
            return evolutionChain.getEvolvesTo();
        } else {
            return evolutionChain.getEvolvesTo().stream()
                    .map(chainLink -> getEvolutionChainFor(species, chainLink))
                    .filter(chainLinks -> !chainLinks.isEmpty())
                    .findFirst()
                    .orElse(Collections.emptyList())
                    ;
        }
    }

    private static List<String> getAbilities(Pokemon pokemon) {
        return pokemon.getAbilities().stream()
                .map(PokeApiAbilityItem::getAbility)
                .map(PokeApiNamedResource::getName)
                .collect(Collectors.toList());
    }

    private static String getTypes(Pokemon pokemon) {
        return pokemon.getTypes().stream()
                .map(PokeApiTypeItem::getType)
                .map(PokeApiNamedResource::getName)
                .collect(Collectors.joining("/"));
    }

    private static List<String> getEvolutions(PokeApiNamedResource species, EvolutionChain evolutionChain) {
        return getEvolutionChainFor(species, evolutionChain.getChain()).stream()
                .map(ChainLink::getSpecies)
                .map(PokeApiNamedResource::getName)
                .collect(Collectors.toList());
    }

    private static String getDescription(PokemonSpecies species) {
        return species.getFlavorTextEntries().stream()
                .filter(flavorText -> flavorText.getLanguage().getName().equals("en"))
                .findFirst()
                .map(FlavorText::getFlavorText)
                .orElse("No description.");
    }

    public PokemonDto map(PokemonDetailBO pokemonBO) {
        return PokemonDto.builder()
                .id(pokemonBO.getPokemon().getId())
                .name(pokemonBO.getPokemon().getName())
                .weight(pokemonBO.getPokemon().getWeight())
                .abilities(getAbilities(pokemonBO.getPokemon()))
                .type(getTypes(pokemonBO.getPokemon()))
                .image(pokemonBO.getPokemon().getSprites().getOther().getOfficialArtwork().getFrontDefault())
                .description(getDescription(pokemonBO.getPokemonSpecies()))
                .evolutions(getEvolutions(pokemonBO.getPokemon().getSpecies(), pokemonBO.getEvolutionChain()))
                .build();
    }

    public PokemonDto mapListItem(Pokemon pokemon) {
        return PokemonDto.builder()
                .id(pokemon.getId())
                .name(pokemon.getName())
                .weight(pokemon.getWeight())
                .type(getTypes(pokemon))
                .abilities(getAbilities(pokemon))
                .image(pokemon.getSprites().getOther().getOfficialArtwork().getFrontDefault())
                .build();
    }
}
