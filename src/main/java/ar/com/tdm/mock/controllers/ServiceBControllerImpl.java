package ar.com.tdm.mock.controllers;

import ar.com.tdm.mock.entities.response_entities.HeroeInteractResponse;
import ar.com.tdm.mock.exceptions.HeroNotFoundException;
import ar.com.tdm.mock.exceptions.LastTimeNotFoundException;
import ar.com.tdm.mock.services.ServiceBService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/marvel/characters")
public class ServiceBControllerImpl implements ServiceBController {
    private ServiceBService serviceBService;

    public ServiceBControllerImpl (ServiceBService serviceBService){
        this.serviceBService=serviceBService;
    }

    @GetMapping("/{character}")
    public ResponseEntity<HeroeInteractResponse> getCollaborators(@PathVariable String character) {
        HeroeInteractResponse response = new HeroeInteractResponse();
        try {
            response = this.serviceBService.getInteractionsForHero(character);
            response.setMessage("El servicio fue ejecutado correctamente.");
        } catch (HeroNotFoundException hnf) {
            response.setMessage("El heroe "+character+ " no es un heroe presente en nuestra base de datos.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (LastTimeNotFoundException ltnf) {
            response.setMessage("No existe una sincronizacion de datos valida en nuestra base de datos.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }
}

