package cz.kuba.sag.data.repositories;

import cz.kuba.sag.data.models.entities.SasService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServiceRepository extends JpaRepository<SasService, UUID> {

    SasService findByPrefix(String prefix);
}
