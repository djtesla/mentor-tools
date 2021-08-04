package mentortools.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentCommand {

    @NotBlank
    @Length(max = 255)
    private String name;
    private String email;
    private String gitHubUserName;
    private String comment;

    public CreateStudentCommand(String name, String email, String comment) {
        this.name = name;
        this.email = email;
        this.comment = comment;
    }


}
