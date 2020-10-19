package com.daniel.modyo.pokeapi.mapper;

import com.daniel.modyo.pokeapi.dto.PokeApiAbilityItem;
import com.daniel.modyo.pokeapi.dto.PokeApiNamedResource;
import com.daniel.modyo.pokeapi.dto.PokeApiTypeItem;
import com.daniel.modyo.pokeapi.dto.evolution.ChainLink;
import com.daniel.modyo.pokeapi.dto.species.FlavorText;
import com.daniel.modyo.pokeapi.mapper.bo.PokemonDetailBO;
import com.daniel.modyo.web.dto.Pokemon;
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

    public Pokemon map(PokemonDetailBO pokemonBO) {
        return Pokemon.builder()
                .id(pokemonBO.getPokemon().getId())
                .name(pokemonBO.getPokemon().getName())
                .weight(pokemonBO.getPokemon().getWeight())
                .abilities(
                        pokemonBO.getPokemon().getAbilities().stream()
                                .map(PokeApiAbilityItem::getAbility)
                                .map(PokeApiNamedResource::getName)
                                .collect(Collectors.toList())
                )
                .type(
                        pokemonBO.getPokemon().getTypes().stream()
                                .map(PokeApiTypeItem::getType)
                                .map(PokeApiNamedResource::getName)
                                .collect(Collectors.joining("/"))
                )
                .image(pokemonBO.getPokemon().getSprites().getOther().getOfficialArtwork().getFrontDefault())
                .description(
                        pokemonBO.getPokemonSpecies().getFlavorTextEntries().stream()
                                .filter(flavorText -> flavorText.getLanguage().getName().equals("en"))
                                .findFirst()
                                .map(FlavorText::getFlavorText)
                                .orElse("No description.")
                )
                .evolutions(
                        getEvolutionChainFor(pokemonBO.getPokemon().getSpecies(), pokemonBO.getEvolutionChain().getChain()).stream()
                                .map(ChainLink::getSpecies)
                                .map(PokeApiNamedResource::getName)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public Pokemon mapListItem(com.daniel.modyo.pokeapi.dto.Pokemon pokemon) {
        return Pokemon.builder()
                .id(pokemon.getId())
                .name(pokemon.getName())
                .weight(pokemon.getWeight())
                .type(
                        pokemon.getTypes().stream()
                                .map(PokeApiTypeItem::getType)
                                .map(PokeApiNamedResource::getName)
                                .collect(Collectors.joining("/"))
                )
                .abilities(
                        pokemon.getAbilities().stream()
                                .map(PokeApiAbilityItem::getAbility)
                                .map(PokeApiNamedResource::getName)
                                .collect(Collectors.toList())
                )
                .image(pokemon.getSprites().getOther().getOfficialArtwork().getFrontDefault())
                .build();
    }
}
