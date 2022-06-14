package com.server.controller;

import com.server.entity.User;
import com.server.entity.dto.UserDto;
import com.server.exception.AlbumException;
import com.server.exception.ArtistException;
import com.server.exception.FileFormatException;
import com.server.exception.UserException;
import com.server.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity addUser(@RequestPart("image") MultipartFile image, @RequestPart("user") String userString) {
        try {
            String idUser = userService.addUser(image, userString);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully created user " + idUser);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IOException | FileFormatException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
    public ResponseEntity<User> findUserById(@PathVariable String id) {
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

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity deleteUser(@PathVariable String idUser) {
        try {
            userService.deleteUser(idUser);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully delete user with id " + idUser);
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (FileFormatException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    @PutMapping("/addLastListenSong")
    public ResponseEntity addLastListenSong(@RequestParam("idUser") String idUser, @RequestParam("idSong") String idSong) {
        try {
            userService.addLastListenSong(idUser, idSong);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully add last listen song " + idSong + " to user " + idUser);
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/getLastListenSong/{idUser}")
    public ResponseEntity getLastListenSong(@PathVariable String idUser) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getLastListenSong(idUser));
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PutMapping("/followArtist")
    public ResponseEntity unfollowArtist(@RequestParam("idUser") String idUser, @RequestParam("idArtist") String idArtist) {
        try {
            userService.followArtist(idUser, idArtist);
            return ResponseEntity.status(HttpStatus.OK).body("User " + idUser + " has followed artist " + idArtist);
        } catch (ArtistException | UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/saveAlbum")
    public ResponseEntity saveAlbum(@RequestParam("idUser") String idUser, @RequestParam("idAlbum") String idAlbum) {
        try {
            userService.saveAlbum(idUser, idAlbum);
            return ResponseEntity.status(HttpStatus.OK).body("User " + idUser + " has save album " + idAlbum);
        } catch (AlbumException | UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/unfollowArtist")
    public ResponseEntity followArtist(@RequestParam("idUser") String idUser, @RequestParam("idArtist") String idArtist) {
        try {
            userService.unfollowArtist(idUser, idArtist);
            return ResponseEntity.status(HttpStatus.OK).body("User " + idUser + " has unfollowed artist " + idArtist);
        } catch (ArtistException | UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/removeAlbum")
    public ResponseEntity removeAlbum(@RequestParam("idUser") String idUser, @RequestParam("idAlbum") String idAlbum) {
        try {
            userService.removeAlbum(idUser, idAlbum);
            return ResponseEntity.status(HttpStatus.OK).body("User " + idUser + " has remove album " + idAlbum);
        } catch (AlbumException | UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/findPlaylistFromUser")
    public ResponseEntity findPlaylistFromUser(@RequestParam("idUser") String idUser) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.findPlaylistFromUser(idUser));
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
    }

    @GetMapping("/findSavedAlbumFromUser")
    public ResponseEntity findSavedAlbumFromUser(@RequestParam("idUser") String idUser) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.findSavedAlbumFromUser(idUser));
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
    }

    @GetMapping("/findFollowedArtistFromUser")
    public ResponseEntity findFollowedArtistFromUser(@RequestParam("idUser") String idUser) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.findFollowedArtistFromUser(idUser));
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
    }
//    @PostMapping("/registration")
//    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto,
//                                            HttpServletRequest request,
//                                            Error errors){
//        try {
//            User registered = userService.registerNewUserAccount(userDto);
//        } catch (UserException e) {
//            ModelAndView mav = new ModelAndView();
//            mav.addObject("message", "An account for that username/email already exists.");
//            return mav;
//        }
//    }
}
