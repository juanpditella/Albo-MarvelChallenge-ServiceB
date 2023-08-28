package com.ServicioB.services;



import com.ServicioB.entities.response_entities.HeroeInteractResponse;
import com.ServicioB.exceptions.HeroNotFoundException;
import com.ServicioB.exceptions.LastTimeNotFoundException;

import java.util.List;

public interface ServiceBService {
    public HeroeInteractResponse getInteractionsForHero(String superHeroName) throws LastTimeNotFoundException, HeroNotFoundException;
}
