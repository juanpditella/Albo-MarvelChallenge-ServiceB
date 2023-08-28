package com.ServicioB.repository;


import com.ServicioB.entities.repository_entities.LastSyncInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastSyncInfoRepository extends JpaRepository<LastSyncInfo, Long> {
    LastSyncInfo findTopByOrderByLastSyncDateTimeDesc();
}

