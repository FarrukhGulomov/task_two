package uz.pdp.online.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.pdp.online.entity.Language;

@Data
public class TaskDto {

    @NotNull(message = "{taskDto.languageId.notEmpty}")
    private Long languageId;

    @NotNull(message = "{taskDto.code.notEmpty}")
    private String code;

    @NotNull(message = "{taskDto.description.notEmpty}")
    private String description;

    private String solution;
}
