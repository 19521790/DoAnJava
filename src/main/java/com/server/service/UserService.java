package com.server.service;

import com.server.entity.Album;
import com.server.entity.Artist;
import com.server.entity.Playlist;
import com.server.entity.User;
import com.server.exception.AlbumException;
import com.server.exception.ArtistException;
import com.server.exception.FileFormatException;
import com.server.exception.UserException;
import com.server.repository.AlbumRepository;
import com.server.repository.ArtistRepository;
import com.server.repository.PlaylistRepository;
import com.server.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.service.data.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service

public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private DataService dataService;

    @Autowired
    private PlaylistService playlistService;

    public String addUser(MultipartFile image, String json) throws ConstraintViolationException, IOException, FileFormatException, UserException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(json, User.class);
        if (userRepository.existsByNameOrEmail(user.getName(), user.getEmail())) {
            throw new UserException(UserException.UserAlreadyExist());
        }

        user.setImage(dataService.storeData(image, ".jpg"));
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        String idUser = userRepository.save(user).getId();

        //create like and download playlist at the same time
        playlistService.addLikeAndDownloadPlaylist(idUser);
        return idUser;
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

    public void deleteUser(String id) throws UserException, FileFormatException {
        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            dataService.deleteData(id);
            userRepository.deleteById(id);
        } else {
            throw new UserException(UserException.NotFoundException(id));
        }
    }

    public void addLastListenSong(String idUser, String idSong) throws UserException {
        User user = userRepository.findById(idUser).orElse(null);

        if (user != null) {
            List<String> idLastListenSong = user.getIdLastListenSongs();
            userRepository.addLastListenSong(idUser, idSong);
        } else {
            throw new UserException(UserException.NotFoundException(idUser));
        }
    }

    public User getLastListenSong(String idUser) throws UserException {
        User user = userRepository.findById(idUser).orElse(null);

        if (user != null) {
            return userRepository.getLastListenSong(idUser);
        } else {
            throw new UserException(UserException.NotFoundException(idUser));
        }
    }

    public void followArtist(String idUser, String idArtist) throws UserException, ArtistException {
        User user = userRepository.findById(idUser).orElse(null);
        Artist artist = artistRepository.findById(idArtist).orElse(null);

        if (user == null) {
            throw new UserException(UserException.NotFoundException(idUser));
        } else if (artist == null) {
            throw new ArtistException(ArtistException.NotFoundException(idArtist));
        } else {
            userRepository.followArtist(idUser, idArtist);
        }
    }

    public void saveAlbum(String idUser, String idAlbum) throws UserException, AlbumException {
        User user = userRepository.findById(idUser).orElse(null);
        Album album = albumRepository.findById(idAlbum).orElse(null);

        if (user == null) {
            throw new UserException(UserException.NotFoundException(idUser));
        } else if (album == null) {
            throw new AlbumException(AlbumException.NotFoundException(idAlbum));
        } else {
            userRepository.saveAlbum(idUser, idAlbum);
        }
    }

    public void unfollowArtist(String idUser, String idArtist) throws UserException, ArtistException {
        User user = userRepository.findById(idUser).orElse(null);
        Artist artist = artistRepository.findById(idArtist).orElse(null);

        if (user == null) {
            throw new UserException(UserException.NotFoundException(idUser));
        } else if (artist == null) {
            throw new ArtistException(ArtistException.NotFoundException(idArtist));
        } else {
            userRepository.unfollowArtist(idUser, idArtist);
        }
    }

    public void removeAlbum(String idUser, String idAlbum) throws UserException, AlbumException {
        User user = userRepository.findById(idUser).orElse(null);
        Album album = albumRepository.findById(idAlbum).orElse(null);

        if (user == null) {
            throw new UserException(UserException.NotFoundException(idUser));
        } else if (album == null) {
            throw new AlbumException(AlbumException.NotFoundException(idAlbum));
        } else {
            userRepository.removeAlbum(idUser, idAlbum);
        }
    }

    public List<Playlist> findPlaylistFromUser(String idUser) throws UserException {
        User user = userRepository.findById(idUser).orElse(null);

        if(user==null){
            throw new UserException(UserException.NotFoundException(idUser));
        }else{
            return playlistRepository.findPlaylistFromUser(idUser);
        }
    }

    //    public User registerNewUserAccount(UserDto userDto) throws UserException{
//        if (emailExist(userDto.getEmail())) {
//            throw new UserException("There is an account with that email address: "
//                    + userDto.getEmail());
//        }
//    }
    private boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
