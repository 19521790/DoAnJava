package com.server.service;

import com.server.entity.Playlist;
import com.server.entity.User;
import com.server.exception.UserException;
import com.server.repository.PlaylistTemplate;
import com.server.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.service.drive.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service

public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaylistTemplate playlistRepository;

    @Autowired
    private GoogleDriveService driveService;

    public boolean addUser(MultipartFile file, String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(json, User.class);
        if (userRepository.existsByNameOrEmail(user.getName(), user.getEmail())) {
            return false;
        }

        String userImgFolderId = "1ZtqcTpapCismmefalDItN5rxb9UDB5-R";
        user.setImage(driveService.uploadFile(user.getName(), file, "audio/mpeg", userImgFolderId).getId());
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        userRepository.save(user);
        return true;
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

    public void addPlaylistToUser(String idUser, String idPlaylist) throws UserException{
        User user = userRepository.findById(idUser).orElse(null);

        if(user!=null){
            userRepository.addPlaylistToUser(idUser,idPlaylist);
        }else{
            throw new UserException(UserException.NotFoundException(idUser));
        }
    }

    public void deleteUser(String id) throws UserException {
        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            driveService.deleteFile(id);
            userRepository.deleteById(id);
        } else {
            throw new UserException(UserException.NotFoundException(id));
        }
    }
}
