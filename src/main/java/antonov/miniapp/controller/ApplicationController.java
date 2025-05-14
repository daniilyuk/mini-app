package antonov.miniapp.controller;

import antonov.miniapp.dto.ApplicationDto;
import antonov.miniapp.dto.ApplicationRequestDto;
import antonov.miniapp.entity.Application;
import antonov.miniapp.mapper.ApplicationMapper;
import antonov.miniapp.service.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    private final ApplicationService applicationService;
    private final ApplicationMapper applicationMapper;

    public ApplicationController(ApplicationService applicationService, ApplicationMapper applicationMapper) {
        this.applicationService = applicationService;
        this.applicationMapper = applicationMapper;
    }

    @PostMapping
    public ResponseEntity<ApplicationDto> createApplication(@RequestBody ApplicationRequestDto requestDto) {
        logger.info("Creating new application: {}", requestDto);
        Application application = applicationMapper.toEntity(requestDto);
        Application savedApplication = applicationService.saveApplication(application);
        return new ResponseEntity<>(applicationMapper.toDto(savedApplication), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ApplicationDto>> getAllApplications() {
        logger.info("Fetching all applications");
        List<Application> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applicationMapper.toDtoList(applications));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> getApplicationById(@PathVariable Long id) {
        logger.info("Fetching application with id: {}", id);
        Optional<Application> application = applicationService.getApplicationById(id);
        return application.map(app -> ResponseEntity.ok(applicationMapper.toDto(app)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationDto> updateApplication(@PathVariable Long id, @RequestBody ApplicationRequestDto requestDto) {
        logger.info("Updating application with id: {}", id);
        Optional<Application> optionalApplication = applicationService.getApplicationById(id);
        if (optionalApplication.isPresent()) {
            Application existingApplication = optionalApplication.get();
            applicationMapper.updateEntityFromDto(requestDto, existingApplication);
            Application updatedApplication = applicationService.updateApplication(existingApplication);
            return ResponseEntity.ok(applicationMapper.toDto(updatedApplication));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        logger.info("Deleting application with id: {}", id);
        Optional<Application> application = applicationService.getApplicationById(id);
        if (application.isPresent()) {
            applicationService.deleteApplication(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
