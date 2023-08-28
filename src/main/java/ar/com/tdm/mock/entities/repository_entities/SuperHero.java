package ar.com.tdm.mock.entities.repository_entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "SuperHero")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuperHero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "hero") // Corregir el atributo "hero" aquí
    private List<Interaction> collaborations;
}


