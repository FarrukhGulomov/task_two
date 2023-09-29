package uz.pdp.online.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.online.entity.Result;
import uz.pdp.online.entity.Task;
import uz.pdp.online.entity.User;
import uz.pdp.online.payload.ResultDto;
import uz.pdp.online.repository.LanguageRepository;
import uz.pdp.online.repository.ResultRepository;
import uz.pdp.online.repository.TaskRepository;
import uz.pdp.online.repository.UserRepository;

import java.util.Optional;

@Service
public class ResultService {
    ResultRepository resultRepository;
    TaskRepository taskRepository;
    UserRepository userRepository;

    public ResultService(ResultRepository resultRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.resultRepository = resultRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // add result
    public ResponseEntity<?> addResult(ResultDto resultDto) {
        Result result = new Result();
        boolean existsByResponseResult = resultRepository.existsByResponseResult(resultDto.getResponseResult());
        if (existsByResponseResult) return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                .body("This responseResult is already exist!");

        Optional<Task> optionalTask = taskRepository.findById(resultDto.getTaskId());
        if (optionalTask.isEmpty()) return ResponseEntity.status(404).body("Task is not found!");

        Optional<User> optionalUser = userRepository.findById(resultDto.getUserId());
        if (optionalUser.isEmpty()) return ResponseEntity.status(404).body("User is not found!");

        result.setResponseResult(resultDto.getResponseResult());
        result.setUser(optionalUser.get());
        result.setTask(optionalTask.get());
        result.setCorrect(resultDto.getIsCorrect());
        resultRepository.save(result);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Result is successfully added!");

    }

    // get resultPage
    public ResponseEntity<?> getResultPage(Integer page){
        int size=10;
        Pageable pageable= PageRequest.of(page,size);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resultRepository.findAll(pageable));
    }
    //get result by id
    public ResponseEntity<?> getResult(Long id){
        Optional<Result> optionalResult = resultRepository.findById(id);
        if(optionalResult.isEmpty()) return ResponseEntity.status(404).body("Result is not found!");

        return ResponseEntity.status(202).body(optionalResult.get());
    }

    // edit Result
    public ResponseEntity<?> editResult(Long id,ResultDto resultDto){
        Optional<Result> optionalResult = resultRepository.findById(id);
        if(optionalResult.isEmpty()) return ResponseEntity.status(404).body("Result is not found!");
        boolean existsByResponseResultAndIdNot = resultRepository.existsByResponseResultAndIdNot(resultDto.getResponseResult(), id);
        if(existsByResponseResultAndIdNot) return ResponseEntity.status(404).body("Already exist!");

        Result result = optionalResult.get();
        Optional<Task> optionalTask = taskRepository.findById(resultDto.getTaskId());

        Optional<User> optionalUser = userRepository.findById(resultDto.getUserId());
        if(optionalUser.isEmpty() || optionalTask.isEmpty() ) return ResponseEntity.status(404).body("not found!");
        result.setCorrect(resultDto.getIsCorrect());
        result.setResponseResult(resultDto.getResponseResult());
        result.setUser(optionalUser.get());
        result.setTask(optionalTask.get());
        resultRepository.save(result);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfully edited!");
    }

    //delete result
    public ResponseEntity<?> deleteResult(Long id){
        Optional<Result> optionalResult = resultRepository.findById(id);
        if(optionalResult.isEmpty())return  ResponseEntity.status(404).body("not found!");

        resultRepository.deleteById(id);
        return ResponseEntity.status(202).body("Successfully deleted!");
    }

}
