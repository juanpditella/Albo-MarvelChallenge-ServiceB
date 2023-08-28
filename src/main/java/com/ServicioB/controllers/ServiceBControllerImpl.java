package com.ServicioB.controllers;


import com.ServicioB.entities.response_entities.HeroeInteractResponse;
import com.ServicioB.exceptions.HeroNotFoundException;
import com.ServicioB.exceptions.LastTimeNotFoundException;
import com.ServicioB.services.ServiceBService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/marvel/characters")
public class ServiceBControllerImpl implements ServiceBController {
    private ServiceBService serviceBService;
    @Value("${heroes.endpoints}")
    private String heroesEndpoints;

    public ServiceBControllerImpl (ServiceBService serviceBService){
        this.serviceBService=serviceBService;
    }

    @GetMapping("/{character}")
    public ResponseEntity<HeroeInteractResponse> getCollaborators(@PathVariable String character) {
        HeroeInteractResponse response = new HeroeInteractResponse();
        String realName = this.getRealNameForEndpoint(character);

        try {
            response = this.serviceBService.getInteractionsForHero(realName);
            response.setMessage("El servicio fue ejecutado correctamente.");
        } catch (HeroNotFoundException hnf) {
            response.setMessage("El valor de la ruta definida no corresponde con ningun heroe cargado en nuestra base de datos.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (LastTimeNotFoundException ltnf) {
            response.setMessage("No existe una sincronizacion de datos valida en nuestra base de datos.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    private String getRealNameForEndpoint(String endpoint) {
        for (String endpointInfo : heroesEndpoints.split(",")) {
            String[] parts = endpointInfo.split("=");
            if (parts.length == 2 && parts[0].equals(endpoint)) {
                return parts[1];
            }
        }
        return "Unknown Hero";
    }
}

