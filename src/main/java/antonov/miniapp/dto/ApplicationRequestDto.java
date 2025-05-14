package antonov.miniapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for creating or updating Application
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApplicationRequestDto {
    private String applicationType;
    private String description;
}