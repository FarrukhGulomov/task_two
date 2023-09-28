package uz.pdp.online.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.exception.ExceptionMessage;
import uz.pdp.online.payload.UserDto;
import uz.pdp.online.service.UserService;

@RestController
@Validated
@RequestMapping("/api/users")
public class UserController extends ExceptionMessage {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto){
        return userService.register(userDto);
    }
    @GetMapping
    public ResponseEntity<?> getUserPage(@RequestParam Integer page){
        return userService.getUsersByPage(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@PathVariable Long id,@Valid @RequestBody UserDto userDto){
        return userService.editUser(id,userDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}
