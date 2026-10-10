package com.ecom_app.user.controller;

import com.ecom_app.user.dto.UserRequest;
import com.ecom_app.user.dto.UserResponse;
import com.ecom_app.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

   @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        return new ResponseEntity<>( userService.fetchAllUsers() , HttpStatus.OK);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String userId) {

       if(  userService.fetchUsers(userId) != null){

           return new ResponseEntity<>(userService.fetchUsers(userId), HttpStatus.OK);
       }
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest user) {
        return new  ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody UserRequest user) {

       boolean update = userService.updateUser(id, user);
       if(update){
           return new ResponseEntity<>("User Updated Successfully",HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
