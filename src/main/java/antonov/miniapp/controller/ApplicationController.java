package antonov.miniapp.controller;

import antonov.miniapp.dto.ApplicationDto;
import antonov.miniapp.dto.ApplicationRequestDto;
import antonov.miniapp.entity.Application;
import antonov.miniapp.mapper.ApplicationMapper;
import antonov.miniapp.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicationMapper applicationMapper;

    public ApplicationController(ApplicationService applicationService, ApplicationMapper applicationMapper) {
        this.applicationService = applicationService;
        this.applicationMapper = applicationMapper;
    }

    @PostMapping
    public ResponseEntity<ApplicationDto> createApplication(
            @RequestParam String applicationType,
            @RequestParam(required = false) String description) {
        log.info("Creating new application with type={} and description={}", applicationType, description);
        ApplicationRequestDto requestDto = new ApplicationRequestDto(applicationType, description);
        Application application = applicationMapper.toEntity(requestDto);
        Application savedApplication = applicationService.saveApplication(application);
        return new ResponseEntity<>(applicationMapper.toDto(savedApplication), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ApplicationDto>> getAllApplications() {
        log.info("Fetching all applications");
        List<Application> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applicationMapper.toDtoList(applications));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> getApplicationById(@PathVariable Long id) {
        log.info("Fetching application with id: {}", id);
        Optional<Application> application = applicationService.getApplicationById(id);
        return application.map(app -> ResponseEntity.ok(applicationMapper.toDto(app)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationDto> updateApplication(
            @PathVariable Long id,
            @RequestParam String applicationType,
            @RequestParam(required = false) String description) {
        log.info("Updating application with id: {}", id);
        Optional<Application> optionalApplication = applicationService.getApplicationById(id);
        if (optionalApplication.isPresent()) {
            ApplicationRequestDto requestDto = new ApplicationRequestDto(applicationType, description);
            Application existingApplication = optionalApplication.get();
            applicationMapper.updateEntityFromDto(requestDto, existingApplication);
            Application updatedApplication = applicationService.updateApplication(existingApplication);
            return ResponseEntity.ok(applicationMapper.toDto(updatedApplication));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        log.info("Deleting application with id: {}", id);
        Optional<Application> application = applicationService.getApplicationById(id);
        if (application.isPresent()) {
            applicationService.deleteApplication(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
