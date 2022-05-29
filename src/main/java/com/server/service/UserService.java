package com.server.service;

import com.server.entity.Playlist;
import com.server.entity.User;
import com.server.repository.PlaylistRepository;
import com.server.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Service

public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    public User addUser(User user) {
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        user.setUpdatedAt(new Date(System.currentTimeMillis()));
        return userRepository.save(user);
    }

    public User findUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findByNameAndPassword(String name, String password) {
        return userRepository.findByNameAndPassword(name, password);
    }

    public User findByNameAndEmail(String name, String email) {
        return userRepository.findByNameAndEmail(name, email);

    }



    public void updateUserPassword(User user) {
        userRepository.save(user);

    }

    public boolean createUser(MultipartFile file, String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(json, User.class);
        if (userRepository.existsByNameOrEmail(user.getName(), user.getEmail())) {
            return false;
        }

        Path filepath = Paths.get(System.getProperty("user.dir") + "\\src\\main\\java\\com\\server\\assets", user.getName() + ".png");
        file.transferTo(filepath);
        user.setImage("http://localhost:8080/image/" + user.getName() + ".png");
        userRepository.save(user);
        return true;
    }

    public String addPlaylistToUser(String idUser, String idPlaylist) {
        User user = userRepository.findById(idUser).get();
        Playlist playlist = playlistRepository.findById(idPlaylist).get();
        System.out.println(user);
        System.out.println(playlist);
        if (playlist != null) {
            user.addPlaylistToUser(idPlaylist);
            return "Succeeded in adding playlist " + idPlaylist + " to user " + idUser;
        } else {
            return "Failed to add playlist to user";
        }
    }
}
