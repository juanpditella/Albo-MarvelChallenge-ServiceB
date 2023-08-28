package com.ServicioB.services;

import com.ServicioB.entities.repository_entities.Interaction;
import com.ServicioB.entities.repository_entities.LastSyncInfo;
import com.ServicioB.entities.repository_entities.SuperHero;
import com.ServicioB.entities.response_entities.ComicCharacter;
import com.ServicioB.entities.response_entities.HeroeInteractResponse;
import com.ServicioB.exceptions.HeroNotFoundException;
import com.ServicioB.exceptions.LastTimeNotFoundException;
import com.ServicioB.repository.InteractionRepository;
import com.ServicioB.repository.LastSyncInfoRepository;
import com.ServicioB.repository.SuperHeroRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceBServiceImpl implements ServiceBService {

    private SuperHeroRepository superHeroRepository;
    private LastSyncInfoRepository lastSyncInfoRepository;
    private InteractionRepository interactionRepository;

    public ServiceBServiceImpl(SuperHeroRepository superHeroRepository, LastSyncInfoRepository lastSyncInfoRepository, InteractionRepository interactionRepository){
        this.superHeroRepository=superHeroRepository;
        this.lastSyncInfoRepository=lastSyncInfoRepository;
        this.interactionRepository=interactionRepository;
    }
    public HeroeInteractResponse getInteractionsForHero(String superHeroName) throws LastTimeNotFoundException, HeroNotFoundException {
        SuperHero superHero=this.superHeroRepository.findByName(superHeroName);
        if (superHero==null){
            throw new HeroNotFoundException("El heroe "+superHeroName+ " no se encuenta en la base de datos.");
        }
        else {
            List<Interaction> interactions = interactionRepository.findInteractionsByHeroId(superHero.getId());
            HeroeInteractResponse heroInteractResponse = new HeroeInteractResponse();
            heroInteractResponse.setLast_sync("Fecha de la última sincronización"); // Configura la fecha adecuada

            Map<String, List<String>> characterComicsMap = new HashMap<>();
            for (Interaction interaction : interactions) {
                String characterName = interaction.getInteractionHero();
                String comicName = interaction.getComicName();

                characterComicsMap.computeIfAbsent(characterName, k -> new ArrayList<>()).add(comicName);
            }

            List<ComicCharacter> comicCharacters = new ArrayList<>();
            for (Map.Entry<String, List<String>> entry : characterComicsMap.entrySet()) {
                ComicCharacter comicCharacter = new ComicCharacter();
                comicCharacter.setCharacter(entry.getKey());
                comicCharacter.setComics(entry.getValue());
                comicCharacters.add(comicCharacter);
            }

            heroInteractResponse.setCharacters(comicCharacters);
            try{
                LocalDateTime lastSyncDateTime = this.getLastSyncDateTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String formattedDateTime = lastSyncDateTime.format(formatter);

                String message = String.format("Fecha de la última sincronización: %s", formattedDateTime);
                heroInteractResponse.setLast_sync(message);
            }
            catch (LastTimeNotFoundException ltnf){
                throw new LastTimeNotFoundException(ltnf.getMessage());
            }

            return heroInteractResponse;
        }
    }

    public LocalDateTime getLastSyncDateTime() throws LastTimeNotFoundException {
        LastSyncInfo lastSyncInfo = lastSyncInfoRepository.findTopByOrderByLastSyncDateTimeDesc();

        if (lastSyncInfo != null) {
            return lastSyncInfo.getLastSyncDateTime();
        }

        throw new LastTimeNotFoundException("No existen datos de sincronizacion en la base.");
    }
}
