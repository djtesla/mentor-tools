package mentortools.trainingclass;

import lombok.AllArgsConstructor;
import mentortools.EntityNotFoundException;
import mentortools.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/mentortools/trainingclasses")
public class TrainingClassController {

    private TrainingClassService trainingClassService;

    @GetMapping
    public List<TrainingClassDto> listAllTrainingClasses() {
        return trainingClassService.listAllTrainingClasses();
    }

    @GetMapping("/{id}")
    public TrainingClassDto getTrainingClassById(@PathVariable("id") long id) {
        return trainingClassService.getTrainingClassById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingClassDto createTrainingClass(@RequestBody @Valid CreateTrainingClassCommand command) {
        return trainingClassService.createTrainingClass(command);
    }

    @PutMapping("/{id}/name")
    public TrainingClassDto updateTrainingClassByName(@PathVariable("id") long id, @RequestBody @Valid UpdateTrainingClassByNameCommand command) {
        return trainingClassService.updateTrainingClassByName(id, command);
    }

    @PutMapping("/{id}/start")
    public TrainingClassDto updateTrainingClassByDateOfStart(@PathVariable("id") long id, @RequestBody @Valid UpdateTrainingClassByDateOfStartCommand command) {
        return trainingClassService.updateTrainingClassByDateOfStart(id, command);
    }

    @PutMapping("/{id}/finish")
    public TrainingClassDto updateTrainingClassByDateOfFinish(@PathVariable("id") long id, @RequestBody @Valid UpdateTrainingClassByDateOfFinishCommand command) {
        return trainingClassService.updateTrainingClassByDateOfFinish(id, command);
    }

    @DeleteMapping("/{id}")
    public void deleteTrainingClassById(@PathVariable("id") long id) {
        trainingClassService.deleteTrainingClassById(id);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Problem> handleTrainingClassNotFound(EntityNotFoundException enfe) {
        Problem problem = Problem.builder()
                .withType(URI.create("training-class/not-found"))
                .withStatus(Status.NOT_FOUND)
                .withTitle("Not found")
                .withDetail(enfe.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Problem> handleNotValidException(MethodArgumentNotValidException mae) {

        List<Violation> violations = mae.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new Violation(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        Problem problem = Problem.builder()
                .withType(URI.create("trainingclass/not-valid"))
                .withTitle(("Validation error"))
                .withStatus(Status.BAD_REQUEST)
                .withDetail(mae.getMessage())
                .with("violations", violations)
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problem);
    }
}
