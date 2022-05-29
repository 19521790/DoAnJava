package com.server.controller;

import com.server.entity.User;
import com.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/findAllUsers")
    public ResponseEntity findAllUsers() {
        List<User> users = userService.findAllUsers();
        if (users.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users available");
        }
    }

    @GetMapping("/findUserById/{id}")
    public ResponseEntity<User> findUserById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserById(id));
    }

    @GetMapping("/findUserByNameAndPassword/{name}/{password}")
    public User findUserByNameAndPassword(@PathVariable String name, @PathVariable String password) {
        return userService.findByNameAndPassword(name, password);
    }

    @GetMapping("/findUserByNameAndEmail/{name}/{email}")
    public User findUserByNameAndEmail(@PathVariable String name, @PathVariable String email) {
        return userService.findByNameAndEmail(name, email);
    }

    @PostMapping("/updatePassword")
    public void updateUserPassword(@RequestBody User user) {
        userService.updateUserPassword(user);
    }

    @PostMapping("/createUser")
    public boolean createUser(@RequestParam("file") MultipartFile file, @RequestParam("json") String json) throws IOException {
        return userService.createUser(file, json);
    }

    @PutMapping("/addPlaylistToUser")
    public ResponseEntity addPlaylistToUser(@RequestParam String idUser, @RequestParam String idPlaylist){
        return ResponseEntity.status(HttpStatus.OK).body(userService.addPlaylistToUser(idUser,idPlaylist));
    }
}
