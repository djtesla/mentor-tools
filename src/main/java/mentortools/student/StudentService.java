package mentortools.student;

import lombok.AllArgsConstructor;
import mentortools.EntityNotFoundException;
import mentortools.trainingclass.TrainingClass;
import mentortools.trainingclass.TrainingClassDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentService {

    private ModelMapper modelMapper;
    private StudentRepository studentRepository;


    public List<StudentDto> listAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(trainingClass -> modelMapper.map(students, StudentDto.class)).collect(Collectors.toList());
    }

    public StudentDto getStudentById(long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("student cannot be found by id " + id));
        return modelMapper.map(student, StudentDto.class);
    }

    public StudentDto createStudent(CreateStudentCommand command) {
        Student student = new Student(command.getName(), command.getEmail(), command.getGitHubUserName(), command.getComment());
        studentRepository.save(student);
        return modelMapper.map(student, StudentDto.class);
    }

    @Transactional
    public StudentDto updateStudentByName(long id, UpdateStudentByNameCommand command) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("student cannot be found by id " + id));
        student.setName(command.getName());
        return modelMapper.map(student, StudentDto.class);
    }

    @Transactional
    public StudentDto updateStudentByEmail(long id, UpdateStudentByEmailCommand command) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("student cannot be found by id " + id));
        student.setEmail(command.getEmail());
        return modelMapper.map(student, StudentDto.class);
    }

    @Transactional
    public StudentDto updateStudentByGitHubUserName(long id, UpdateStudentByGitHubUserNameCommand command) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("student cannot be found by id " + id));
        student.setGitHubUserName(command.getGitHubUserName());
        return modelMapper.map(student, StudentDto.class);
    }

    @Transactional
    public StudentDto updateStudentByComment(long id, UpdateStudentByCommentCommand command) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("student cannot be found by id " + id));
        student.setComment(command.getComment());
        return modelMapper.map(student, StudentDto.class);
    }

    @Transactional
    public void deleteStudentById(long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("student cannot be found by id " + id));
        studentRepository.delete(student);
    }


}
