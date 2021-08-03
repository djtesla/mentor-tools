package mentortools.trainingclass;

import lombok.AllArgsConstructor;
import mentortools.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TrainingClassService {

    private ModelMapper modelMapper;
    private TrainingClassRepository trainingClassRepository;

    public List<TrainingClassDto> listAllTrainingClasses() {
        List<TrainingClass> trainingClasses =  trainingClassRepository.findAll();
        return trainingClasses.stream().map(trainingClass -> modelMapper.map(trainingClass, TrainingClassDto.class)).collect(Collectors.toList());
    }

    public TrainingClassDto getTrainingClassById(long id) {
        TrainingClass trainingClass = trainingClassRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("trainingClass cannot be found by id " + id));
        return modelMapper.map(trainingClass, TrainingClassDto.class);
    }

    public TrainingClassDto createTrainingClass(CreateTrainingClassCommand command) {
        TrainingClass trainingClass = new TrainingClass(command.getName(), command.getDateOfStart(), command.getDateOfFinish());
        trainingClassRepository.save(trainingClass);
        return modelMapper.map(trainingClass, TrainingClassDto.class);
    }

    @Transactional
    public TrainingClassDto updateTrainingClassByName(long id, UpdateNameCommand command) {
        TrainingClass trainingClass = trainingClassRepository.findById(id).orElseThrow(()->new EntityNotFoundException("TrainingClass cannot be found by id " + id));
        trainingClass.setName(command.getName());
        return modelMapper.map(trainingClass, TrainingClassDto.class);
    }

    @Transactional
    public TrainingClassDto updateTrainingClassByDateOfStart(long id, UpdateDateOfStartCommand command) {
        TrainingClass trainingClass = trainingClassRepository.findById(id).orElseThrow(()->new EntityNotFoundException("TrainingClass cannot be found by id " + id));
        trainingClass.setDateOfStart(command.getDateOfStart());
        return modelMapper.map(trainingClass, TrainingClassDto.class);
    }

    @Transactional
    public TrainingClassDto updateTrainingClassByDateOfFinish(long id, UpdateDateOfFinishCommand command) {
        TrainingClass trainingClass = trainingClassRepository.findById(id).orElseThrow(()->new EntityNotFoundException("TrainingClass cannot be found by id " + id));
        trainingClass.setDateOfFinish(command.getDateOfFinish());
        return modelMapper.map(trainingClass, TrainingClassDto.class);
    }

    @Transactional
    public void deleteTrainingClassById(long id) {
        TrainingClass trainingClass = trainingClassRepository.findById(id).orElseThrow(()->new EntityNotFoundException("TrainingClass cannot be found by id " + id));
        trainingClassRepository.delete(trainingClass);
    }


}
