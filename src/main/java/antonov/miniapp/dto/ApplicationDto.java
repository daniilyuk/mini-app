package antonov.miniapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Application entity
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApplicationDto {
    private Long id;
    private String applicationType;
    private String description;
    private LocalDateTime createdAt;
}