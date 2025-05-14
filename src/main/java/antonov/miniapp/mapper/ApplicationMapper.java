package antonov.miniapp.mapper;

import antonov.miniapp.dto.ApplicationDto;
import antonov.miniapp.dto.ApplicationRequestDto;
import antonov.miniapp.entity.Application;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class to convert between Application entity and DTOs
 */
@Component
public class ApplicationMapper {

    /**
     * Convert Application entity to ApplicationDto
     */
    public ApplicationDto toDto(Application application) {
        if (application == null) {
            return null;
        }

        ApplicationDto dto = new ApplicationDto();
        dto.setId(application.getId());
        dto.setApplicationType(application.getApplicationType());
        dto.setDescription(application.getDescription());
        dto.setCreatedAt(application.getCreatedAt());

        return dto;
    }

    /**
     * Convert ApplicationDto to Application entity
     */
    public Application toEntity(ApplicationDto dto) {
        if (dto == null) {
            return null;
        }

        Application application = new Application();
        application.setId(dto.getId());
        application.setApplicationType(dto.getApplicationType());
        application.setDescription(dto.getDescription());
        application.setCreatedAt(dto.getCreatedAt());

        return application;
    }

    /**
     * Convert ApplicationRequestDto to Application entity
     */
    public Application toEntity(ApplicationRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Application application = new Application();
        application.setApplicationType(dto.getApplicationType());
        application.setDescription(dto.getDescription());

        return application;
    }

    /**
     * Update Application entity from ApplicationRequestDto
     */
    public void updateEntityFromDto(ApplicationRequestDto dto, Application application) {
        if (dto == null || application == null) {
            return;
        }

        application.setApplicationType(dto.getApplicationType());
        application.setDescription(dto.getDescription());
    }

    /**
     * Convert list of Application entities to list of ApplicationDtos
     */
    public List<ApplicationDto> toDtoList(List<Application> applications) {
        return applications.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
