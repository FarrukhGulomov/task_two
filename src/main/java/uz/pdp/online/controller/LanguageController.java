package uz.pdp.online.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.exception.ExceptionMessage;
import uz.pdp.online.payload.LanguageDto;
import uz.pdp.online.repository.LanguageRepository;
import uz.pdp.online.service.LanguageService;

@RestController
@Validated
@RequestMapping("/api/languages")
public class LanguageController extends ExceptionMessage {

    LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @PostMapping
    public ResponseEntity<?> addLanguage(@Valid @RequestBody LanguageDto languageDto){
        return languageService.addLanguage(languageDto);
    }

    @GetMapping
    public ResponseEntity<?> getLanguagePage(@RequestParam Integer page){
        return languageService.getLanguagePage(page);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getLanguage(@PathVariable Long id){
        return languageService.getLanguage(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editLanguage(@PathVariable Long id,@Valid @RequestBody LanguageDto languageDto){
        return languageService.editLanguage(id,languageDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLanguage(@PathVariable Long id){
        return languageService.deleteLanguage(id);
    }

}
