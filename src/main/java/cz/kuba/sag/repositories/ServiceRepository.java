package cz.kuba.sag.repositories;

import cz.kuba.sag.models.SasService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<SasService, Integer> {

    SasService findByPrefix(String prefix);
}
