package mentortools.trainingclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

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

    public TrainingClass(String name, LocalDate dateOfStart, LocalDate dateOfFinish) {
        this.name = name;
        this.dateOfStart = dateOfStart;
        this.dateOfFinish = dateOfFinish;
    }
}
