package mentortools.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.trainingclass.TrainingClass;

import javax.persistence.ManyToMany;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private Long id;
    private String name;
    private String email;
    private String gitHubUserName;
    private String comment;
    private List<TrainingClass> trainingClasses;

    public StudentDto(Long id, String name, String email, String gitHubUserName, String comment) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gitHubUserName = gitHubUserName;
        this.comment = comment;
    }
}
