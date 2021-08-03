package mentortools.trainingclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingClassDto {

    Long id;
    String name;
    LocalDate dateOfStart;
    LocalDate dateOfFinish;
}
