package cz.kuba.sag.data.mappers;

import cz.kuba.sag.data.models.entities.SasService;
import cz.kuba.sag.data.models.dtos.ServiceDTO;

public class ServiceMapper {

    static SasService toEntity(ServiceDTO service) {
        return new SasService()
                .setId(service.getId())
                .setPrefix(service.getPrefix())
                .setName(service.getName())
                .setDescription(service.getDescription())
                .setPort(service.getPort())
                .setDriverType(service.getDriverType());
    }

    static ServiceDTO toDto(SasService service) {
        return new ServiceDTO()
                .setId(service.getId())
                .setPrefix(service.getPrefix())
                .setName(service.getName())
                .setDescription(service.getDescription())
                .setPort(service.getPort())
                .setDriverType(service.getDriverType());
    }
}
