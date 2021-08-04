package mentortools.trainingclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.student.Student;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingClassDto {

    Long id;
    String name;
    LocalDate dateOfStart;
    LocalDate dateOfFinish;
    private List<Student> students;

    public TrainingClassDto(Long id, String name, LocalDate dateOfStart, LocalDate dateOfFinish) {
        this.id = id;
        this.name = name;
        this.dateOfStart = dateOfStart;
        this.dateOfFinish = dateOfFinish;
    }
}
