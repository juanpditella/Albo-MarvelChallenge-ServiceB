package ar.com.tdm.mock.services;

import ar.com.tdm.mock.entities.response_entities.HeroeInteractResponse;
import ar.com.tdm.mock.exceptions.HeroNotFoundException;
import ar.com.tdm.mock.exceptions.LastTimeNotFoundException;


import java.util.List;

public interface ServiceBService {
    public HeroeInteractResponse getInteractionsForHero(String superHeroName) throws LastTimeNotFoundException, HeroNotFoundException;
}
