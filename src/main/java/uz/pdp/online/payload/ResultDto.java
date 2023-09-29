package uz.pdp.online.payload;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.pdp.online.entity.Task;
import uz.pdp.online.entity.User;

@Data
public class ResultDto {

    @NotNull(message = "{resultDto.userId.notEmpty}")
    private Long userId;

    @NotNull(message = "{resultDto.taskId.notEmpty}")
    private Long taskId;

    @NotNull(message = "{resultDto.responseResult.notEmpty}")
    private String responseResult;

   @NotNull(message = "{resultDto.isCorrect.notEmpty}")
    private Boolean isCorrect;
}

