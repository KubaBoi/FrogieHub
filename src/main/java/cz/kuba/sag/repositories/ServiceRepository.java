package cz.kuba.sag.repositories;

import cz.kuba.sag.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
}
