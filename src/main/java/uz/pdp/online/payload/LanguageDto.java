package uz.pdp.online.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LanguageDto {

    @NotNull(message = "{languageDto.name.notEmpty}")
    private String name;
}
