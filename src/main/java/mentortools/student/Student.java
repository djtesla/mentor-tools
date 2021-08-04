package mentortools.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.trainingclass.TrainingClass;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @Column(name = "github_username")
    private String gitHubUserName;
    private String comment;

    @ManyToMany(mappedBy = "students")
    private List<TrainingClass> trainingClasses;

    public Student(String name, String email, String gitHubUserName, String comment) {
        this.name = name;
        this.email = email;
        this.gitHubUserName = gitHubUserName;
        this.comment = comment;
    }
}
