package uz.pdp.online.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.exception.ExceptionMessage;
import uz.pdp.online.payload.ResultDto;
import uz.pdp.online.service.ResultService;

@RestController
@Validated
@RequestMapping("/api/results")
public class ResultController extends ExceptionMessage {
    ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }
    @PostMapping
    public ResponseEntity<?> addResult(@Valid @RequestBody ResultDto resultDto){
        return resultService.addResult(resultDto);
    }
    @GetMapping
    public ResponseEntity<?> getResulPage(@RequestParam Integer page){
        return resultService.getResultPage(page);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getResult(@PathVariable Long id){
        return resultService.getResult(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editResult(@PathVariable Long id,@Valid @RequestBody ResultDto resultDto){
        return resultService.editResult(id,resultDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResult(@PathVariable Long id){
        return resultService.deleteResult(id);
    }
}
