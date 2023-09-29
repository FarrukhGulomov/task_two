package uz.pdp.online.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.payload.TaskDto;
import uz.pdp.online.service.TaskService;

@RestController
@Validated
@RequestMapping(("/api/tasks"))
public class TaskController {
    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<?> addTask(@Valid @RequestBody TaskDto taskDto){
        return taskService.addTask(taskDto);
    }
    @GetMapping
    public ResponseEntity<?> getTaskPage(@RequestParam Integer page){
        return taskService.getCodePage(page);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id){
        return taskService.getTask(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editTask(@PathVariable Long id,@Valid @RequestBody TaskDto taskDto){
        return taskService.editTask(id,taskDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id){
        return taskService.deleteTask(id);
    }
}
