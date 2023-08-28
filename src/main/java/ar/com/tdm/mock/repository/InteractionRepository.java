package ar.com.tdm.mock.repository;

import ar.com.tdm.mock.entities.repository_entities.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long> {

    @Query("SELECT i FROM Interaction i WHERE i.hero.id = :heroId")
    List<Interaction> findInteractionsByHeroId(Long heroId);
}
