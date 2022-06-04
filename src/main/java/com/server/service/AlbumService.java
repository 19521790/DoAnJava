package com.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.entity.Album;
import com.server.exception.AlbumException;
import com.server.repository.AlbumRepository;
import com.server.service.drive.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private GoogleDriveService driveService;

    public Album addAlbum(String albumString, MultipartFile image) throws ConstraintViolationException,AlbumException,JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Album album = objectMapper.readValue(albumString,Album.class);

        String albumImgFolderId = "1RPmGzNV-xQ1n3F53tYAlq1P4_40hBxZ7";

        Album albumOptional = albumRepository.findByName(album.getName());

        if(albumOptional==null){
            album.setImage(driveService.uploadFile(image,"image/jpeg",albumImgFolderId).getId());
            return albumRepository.save(album);
        }else if(albumOptional.getName().equals(album.getName())&&albumOptional.getImage().equals(album.getImage())){
            throw new AlbumException(AlbumException.AlbumAlreadyExist());
        }else{
            return albumRepository.save(album);
        }
    }

    public Album findAlbumById(String id){
        return albumRepository.findById(id).orElse(null);
    }

    public List<Album> findAllAlbums(){
        return  albumRepository.findAll();
    }

    public Album updateAlbum(Album album){
        return albumRepository.save(album);
    }

    public String deleteAlbum(String id){
        albumRepository.deleteById(id);
        return ("Album has been deleted: "+id);
    }
}
