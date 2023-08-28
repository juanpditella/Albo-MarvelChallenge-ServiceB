package com.ServicioB.entities.response_entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeroeInteractResponse {
    private String last_sync;
    private List<ComicCharacter> characters;
    private String message;
}
