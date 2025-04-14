package cz.kuba.hub.repositories;

import cz.kuba.hub.models.Service;
import org.springframework.data.repository.CrudRepository;

public interface ServiceRepository extends CrudRepository<Service, Integer> {
}
