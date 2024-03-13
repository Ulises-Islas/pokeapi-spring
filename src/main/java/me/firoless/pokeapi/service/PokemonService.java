package me.firoless.pokeapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import me.firoless.pokeapi.entity.Pokemon;
import me.firoless.pokeapi.entity.PokemonAbility;
import me.firoless.pokeapi.repository.IAbilityRepository;
import me.firoless.pokeapi.repository.IPokemonAbilityRepository;
import me.firoless.pokeapi.repository.IPokemonRepository;

@Service
public class PokemonService {
    @Autowired
    private IPokemonRepository pokemonRepository;
    @Autowired
    private IAbilityRepository abilityRepository;
    @Autowired
    private IPokemonAbilityRepository pokemonAbilityRepository;

    @Transactional(readOnly = true)
    public List<Pokemon> findAll() {
        return pokemonRepository.findAll();
    }

    @Transactional
    public Pokemon searchAndSave(String name) {
        String uri = "https://pokeapi.co/api/v2/pokemon/" + name;
        RestTemplate restTemplate = new RestTemplate();
        Pokemon result = restTemplate.getForObject(uri, Pokemon.class);
        if (pokemonRepository.findById(result.getId()).isPresent()) {
            result = pokemonRepository.findById(result.id).get();
        } else {
            pokemonRepository.save(result);
            for (PokemonAbility ability : result.getAbilities()) {
                if (abilityRepository.findById(ability.getAbility().name).isPresent()) {
                    ability.setAbility(abilityRepository.findById(ability.getAbility().name).get());
                } else {
                    ability.setAbility(abilityRepository.save(ability.getAbility()));
                }
                if (pokemonAbilityRepository.findByPokemonAndAbility(result.getId(), ability.getAbility().getName()) == null) {
                    ability.setPokemon(result);
                    ability.setAbility(ability.getAbility());
                    pokemonAbilityRepository.save(ability);
                } else {
                    ability = pokemonAbilityRepository.findByPokemonAndAbility(result.getId(), ability.getAbility().getName());
                }
            }
        }
        return result;
    }
}
