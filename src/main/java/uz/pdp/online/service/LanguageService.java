package uz.pdp.online.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.online.entity.Language;
import uz.pdp.online.payload.LanguageDto;
import uz.pdp.online.repository.LanguageRepository;

import java.util.Optional;

@Service
public class LanguageService {
    LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }
    // add language
    public ResponseEntity<?> addLanguage(LanguageDto languageDto){
        boolean existsByName = languageRepository.existsByName(languageDto.getName());
        if(existsByName) return ResponseEntity.status(404)
                .body("This language is already exist!");

        Language language=new Language();
        language.setName(languageDto.getName());
        languageRepository.save(language);
        return ResponseEntity.status(202)
                .body("Language is successfully added!");
    }

    // getLanguagePage
    public ResponseEntity<?> getLanguagePage(Integer page){
        int size=10;
        Pageable pageable= PageRequest.of(page,size);
        Page<Language> languagePage = languageRepository.findAll(pageable);
        return ResponseEntity.status(202).body(languagePage);
    }

    // get language by id
    public ResponseEntity<?> getLanguage(Long id){
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if(optionalLanguage.isEmpty()) return ResponseEntity.status(404)
                .body("Language is not found!");
        return ResponseEntity.status(202).body(optionalLanguage.get());
    }

    // edit language
    public ResponseEntity<?> editLanguage(Long id,LanguageDto languageDto){
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if(optionalLanguage.isEmpty()) return ResponseEntity.status(404)
                .body("Language is not found!");
        boolean existsByNameAndIdNot = languageRepository.existsByNameAndIdNot(languageDto.getName(), id);
        if(existsByNameAndIdNot) return ResponseEntity.status(404).body("This language is already exist!");
        Language language = optionalLanguage.get();
        language.setName(languageDto.getName());
        languageRepository.save(language);
        return ResponseEntity.status(202).body("Language is successfully edited!");
    }

    // delete language
    public ResponseEntity<?> deleteLanguage(Long id){
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if(optionalLanguage.isEmpty()) return ResponseEntity.status(404)
                .body("Language is not found by this id");
        boolean existLanguageInTask = languageRepository.existLanguageInTask(id);
        if(existLanguageInTask) return ResponseEntity.status(404)
                .body("You can  not delete this language for relationship!");

        languageRepository.deleteById(id);
        return ResponseEntity.status(202)
                .body("Language is successfully deleted!");
    }

}
