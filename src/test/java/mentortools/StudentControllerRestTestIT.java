package mentortools;

import mentortools.student.CreateStudentCommand;
import mentortools.student.StudentDto;
import mentortools.trainingclass.CreateTrainingClassCommand;
import mentortools.trainingclass.TrainingClassDto;
import mentortools.trainingclass.UpdateTrainingClassByNameCommand;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerRestTestIT {


    @Autowired
    TestRestTemplate template;

  @Autowired
    Flyway flyway;

    @BeforeEach
    public void init() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void testCreate() {
        StudentDto student = template.postForObject("/api/mentortools/students",
                new CreateStudentCommand("Jack Sparrow", "jack_sparrow@carib.com", "jacksparrow", "ok"), StudentDto.class);
        assertEquals("Jack Sparrow", student.getName());

    }

    @Test
    void testList() {
        template.postForObject("/api/mentortools/students",
                new CreateStudentCommand("Jack Sparrow", "jack_sparrow@carib.com", "jacksparrow", "ok"), StudentDto.class);
        template.postForObject("/api/mentortools/students",
                new CreateStudentCommand("Mary Martial", "mary_martial@mortal.com", "marymartial", "ok"), StudentDto.class);
        template.postForObject("/api/mentortools/students",
                new CreateStudentCommand("Liu King", "liu_king@mortal.com", "liuking", "ok"), StudentDto.class);
        List<StudentDto> result = template.exchange(
                "/api/mentortools/students",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StudentDto>>() {
                }).getBody();
        assertEquals(3, result.size());
    }


    @Test
    void testUpdate() {
        template.postForObject("/api/mentortools/students",
                new CreateStudentCommand("Jack Sparrow", "jack_sparrow@carib.com", "jacksparrow", "ok"), StudentDto.class);
        template.put("/api/mentortools/students/1/name", new UpdateTrainingClassByNameCommand("Jack Sparrow captain"));
        StudentDto trainingClass = template.getForObject("/api/mentortools/students/1", StudentDto.class);
        assertEquals("Jack Sparrow captain", trainingClass.getName());
    }

    @Test
    void testDelete() {
        template.postForObject("/api/mentortools/students",
                new CreateStudentCommand("Jack Sparrow", "jack_sparrow@carib.com", "jacksparrow", "ok"), StudentDto.class);
        template.delete("/api/mentortools/students/1");
        Problem result = template.getForObject("/api/mentortools/students/1", Problem.class);
        assertEquals(URI.create("student/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }

    @Test
    void testValid() {
        Problem result = template.postForObject("/api/mentortools/students",
                new CreateStudentCommand("", "jack_sparrow@carib.com", "jacksparrow", "ok"), Problem.class);
        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }
}
