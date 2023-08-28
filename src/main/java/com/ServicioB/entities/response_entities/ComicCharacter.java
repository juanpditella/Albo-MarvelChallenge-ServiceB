package com.ServicioB.entities.response_entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComicCharacter {
    private String character;
    private List<String> Comics;
}