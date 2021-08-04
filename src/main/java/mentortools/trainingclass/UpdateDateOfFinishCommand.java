package mentortools.trainingclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDateOfFinishCommand {

    LocalDate dateOfFinish;

}



