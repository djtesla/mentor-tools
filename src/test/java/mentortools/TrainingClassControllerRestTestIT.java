package mentortools;

import mentortools.trainingclass.CreateTrainingClassCommand;
import mentortools.trainingclass.TrainingClassDto;
import mentortools.trainingclass.UpdateNameCommand;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrainingClassControllerRestTestIT {


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
        TrainingClassDto trainingClass = template.postForObject("/api/mentortools/trainingclasses",
                new CreateTrainingClassCommand("Java kezdő", LocalDate.of(2021, 6, 1), LocalDate.of(2021, 10, 1)), TrainingClassDto.class);
     assertEquals("Java kezdő", trainingClass.getName());

    }

    @Test
    void testList() {
      template.postForObject("/api/mentortools/trainingclasses",
                new CreateTrainingClassCommand("Java kezdő", LocalDate.of(2021, 6, 1), LocalDate.of(2021, 10, 1)), TrainingClassDto.class);
        template.postForObject("/api/mentortools/trainingclasses",
                new CreateTrainingClassCommand("JavaScript kezdő", LocalDate.of(2021, 7, 1), LocalDate.of(2021, 11, 1)), TrainingClassDto.class);
        template.postForObject("/api/mentortools/trainingclasses",
                new CreateTrainingClassCommand("C++ kezdő", LocalDate.of(2021, 8, 1), LocalDate.of(2021, 12, 1)), TrainingClassDto.class);
        List<TrainingClassDto> result = template.exchange(
                "/api/mentortools/trainingclasses",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TrainingClassDto>>() {
                }).getBody();
        assertEquals(3, result.size());
    }


    @Test
    void testUpdate() {
        template.postForObject("/api/mentortools/trainingclasses",
                new CreateTrainingClassCommand("Java kezdő", LocalDate.of(2021, 6, 1), LocalDate.of(2021, 10, 1)), TrainingClassDto.class);
        template.put("/api/mentortools/trainingclasses/1/name", new UpdateNameCommand("Java backend kezdő"));
        TrainingClassDto trainingClass = template.getForObject("/api/mentortools/trainingclasses/1",TrainingClassDto.class);
        assertEquals("Java backend kezdő", trainingClass.getName());
    }

    @Test
    void testDelete() {
        template.postForObject("/api/mentortools/trainingclasses",
                new CreateTrainingClassCommand("Java kezdő", LocalDate.of(2021, 6, 1), LocalDate.of(2021, 10, 1)), TrainingClassDto.class);
        template.delete("/api/mentortools/trainingclasses/1");
        Problem result = template.getForObject("/api/mentortools/trainingclasses/1", Problem.class);
        assertEquals(URI.create("training-class/not-found"),result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }

    @Test
    void testValid() {
        Problem result = template.postForObject("/api/mentortools/trainingclasses",
                new CreateTrainingClassCommand("", LocalDate.of(2021, 6, 1), LocalDate.of(2021, 10, 1)), Problem.class);
        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }
}
