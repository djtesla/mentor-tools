package mentortools.trainingclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CreateTrainingClassCommand {

    @NotBlank
    @Length(max = 255)
    String name;
    LocalDate dateOfStart;

    
    LocalDate dateOfFinish;
}
