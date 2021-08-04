package mentortools.trainingclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.student.Student;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "training_classes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    LocalDate dateOfStart;

    LocalDate dateOfFinish;

    @ManyToMany
    @JoinTable(name = "trainingclass_student",
            joinColumns = @JoinColumn(name = "trainingclass_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    List<Student> students;

    public TrainingClass(String name, LocalDate dateOfStart, LocalDate dateOfFinish) {
        this.name = name;
        this.dateOfStart = dateOfStart;
        this.dateOfFinish = dateOfFinish;
    }
}
