package uz.pdp.online.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
    @NotNull(message = "{userDto.email.notEmpty}")
    private String email;

    @NotNull(message = "{userDto.password.notEmpty}")
    private String password;


}
