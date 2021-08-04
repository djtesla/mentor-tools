package mentortools.student;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/api/mentortools/students")
public class StudentController {

    private StudentService studentService;

    @GetMapping
    public List<StudentDto> listAllStudents() {
        return studentService.listAllStudents();
    }

    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable("id") long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDto createStudent(@RequestBody @Valid CreateStudentCommand command) {
        return studentService.createStudent(command);
    }

    @PutMapping("/{id}/name")
    public StudentDto updateStudentByName(@PathVariable("id") long id, @RequestBody @Valid UpdateStudentByNameCommand command) {
        return studentService.updateStudentByName(id, command);
    }

    @PutMapping("/{id}/email")
    public StudentDto updateStudentByEmail(@PathVariable("id") long id, @RequestBody @Valid UpdateStudentByEmailCommand command) {
        return studentService.updateStudentByEmail(id, command);
    }

    @PutMapping("/{id}/githubusername")
    public StudentDto updateStudentByGitHubUserName(@PathVariable("id") long id, @RequestBody @Valid UpdateStudentByGitHubUserNameCommand command) {
        return studentService.updateStudentByGitHubUserName(id, command);
    }

    @PutMapping("/{id}/comment")
    public StudentDto updateStudentByComment(@PathVariable("id") long id, @RequestBody @Valid UpdateStudentByCommentCommand command) {
        return studentService.updateStudentByComment(id, command);
    }


    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable("id") long id) {
        studentService.deleteStudentById(id);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Problem> handleXNotFound(EntityNotFoundException enfe) {
        Problem problem = Problem.builder()
                .withType(URI.create("student/not-found"))
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
                .withType(URI.create("student/not-valid"))
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
