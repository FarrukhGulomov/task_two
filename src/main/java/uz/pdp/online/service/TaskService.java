package uz.pdp.online.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.online.entity.Language;
import uz.pdp.online.entity.Task;
import uz.pdp.online.payload.TaskDto;
import uz.pdp.online.repository.LanguageRepository;
import uz.pdp.online.repository.TaskRepository;

import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/tasks")

public class TaskService {


    TaskRepository taskRepository;
    LanguageRepository languageRepository;

    public TaskService(TaskRepository taskRepository, LanguageRepository languageRepository) {
        this.taskRepository = taskRepository;
        this.languageRepository = languageRepository;
    }

    // add task
    public ResponseEntity<?> addTask(TaskDto taskDto) {

        Optional<Language> optionalLanguage = languageRepository.findById(taskDto.getLanguageId());
        if(optionalLanguage.isEmpty()) return ResponseEntity.status(404)
                .body("Language is not found!");

        boolean existsByCode = taskRepository.existsByCode(taskDto.getCode());
        if(existsByCode) return ResponseEntity.status(404).body("This code is already exist!");
        Language language = optionalLanguage.get();
        Task task=new Task();
        task.setCode(taskDto.getCode());
        task.setDescription(taskDto.getDescription());
        task.setSolution(taskDto.getSolution());
        task.setLanguage(language);
        taskRepository.save(task);
        return ResponseEntity.status(202)
                .body("Task is successfully added!");
    }
    //get CodePage
    public ResponseEntity<?> getCodePage(Integer page){
        int size=10;
            Pageable pageable= PageRequest.of(page,size);
        Page<Task> taskPage = taskRepository.findAll(pageable);
        if(taskPage.hasContent()) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(taskPage);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Page not found!");

    }
    // get task by id
    public ResponseEntity<?> getTask(Long id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isEmpty()) return ResponseEntity.status(404)
                .body("This id is not found!");

        return ResponseEntity.status(202)
                .body(optionalTask.get());
    }

    // edit task
    public ResponseEntity<?> editTask(Long id,TaskDto taskDto){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Task is not found!");


        boolean existsTaskByCodeAndIdNot = taskRepository.existsTaskByCodeAndIdNot(taskDto.getCode(), id);
        if(existsTaskByCodeAndIdNot) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("This code is already exists");
        Task task = optionalTask.get();
        Optional<Language> optionalLanguage = languageRepository.findById(taskDto.getLanguageId());
        if(optionalLanguage.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Language is not found!");
        Language language = optionalLanguage.get();
        task.setLanguage(language);
        task.setCode(taskDto.getCode());
        task.setSolution(taskDto.getSolution());
        task.setDescription(taskDto.getDescription());
        taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Task is successfully edited!");

    }

    //delete task
    public ResponseEntity<?> deleteTask(Long id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("This id is not found!");
        boolean existsTaskInResult = taskRepository.existsTaskInResult(id);
        if(existsTaskInResult) return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("You can not delete this task for relationship!");

        taskRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Task is successfully deleted!");
    }

}
