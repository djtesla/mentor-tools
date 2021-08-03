package mentortools.trainingclass;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UpdateDateOfFinishCommand {

    LocalDate dateOfFinish;

}


