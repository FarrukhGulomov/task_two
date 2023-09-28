package uz.pdp.online.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.online.entity.User;
import uz.pdp.online.payload.UserDto;
import uz.pdp.online.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {


    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // register user
    public ResponseEntity<?> register(UserDto userDto) {
        boolean existsByEmail = userRepository.existsByEmail(userDto.getEmail());
        if(existsByEmail) return ResponseEntity.status(409)
                .body("This email is already exist!");
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return ResponseEntity.status(202)
                .body("User is successfully added!");
    }

// get all users by page
    public ResponseEntity<?> getUsersByPage(Integer page){
        int size=10;
        Pageable pageable= PageRequest.of(page,size);
        Page<User> userPage = userRepository.findAll(pageable);
        return ResponseEntity.status(202)
                .body(userPage);
    }

    // get user by id
    public ResponseEntity<?> getUser(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) return ResponseEntity.status(404)
                .body("User is not found by this id!");
        return ResponseEntity.status(201)
                .body(optionalUser.get());
    }

    // edit user
    public ResponseEntity<?> editUser(Long id,UserDto userDto){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) return ResponseEntity.status(404)
                .body("This user is not found!");

        boolean existsByEmailAndIdNot = userRepository.existsByEmailAndIdNot(userDto.getEmail(), id);
        if(existsByEmailAndIdNot) return ResponseEntity.status(404)
                .body("This email is already exist!");
        User user = optionalUser.get();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return ResponseEntity.status(202)
                .body("User is successfully edited!");
    }

    //delete user

    public ResponseEntity<?> deleteUser(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) return ResponseEntity.status(404)
                .body("User is not found!");

        boolean existsByUserInResult = userRepository.existsByUserInResult(id);
        if(existsByUserInResult) return ResponseEntity.status(404)
                .body("You can not delete this user for relationship!");

        userRepository.deleteById(id);
        return ResponseEntity.status(202)
                .body("User is successfully deleted!");
    }
}
