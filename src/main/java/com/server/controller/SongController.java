package com.server.controller;

import com.server.entity.Song;
import com.server.exception.FileFormatException;
import com.server.exception.SongException;
import com.server.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping("/addSong")
    public ResponseEntity addSong(
            @RequestPart("song") String songString, @RequestPart("file") MultipartFile file
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(songService.addSong(songString, file));
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (SongException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        } catch (FileFormatException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    @GetMapping("/findSongById/{id}")
    public ResponseEntity findSongById(@PathVariable String id) throws SongException, IOException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(songService.findSongById(id));
        } catch (SongException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (FileFormatException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    @GetMapping("/findAllSongs")
    public ResponseEntity findAllSongs() {
        List<Song> songs = songService.findAllSongs();
        return ResponseEntity.status(songs.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(songs);
    }

    @PutMapping("/updateSong")
    public ResponseEntity updateSong(@RequestPart("song") String songString
            , @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(songService.updateSong(songString, file));
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (SongException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException | FileFormatException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }

    }

    @DeleteMapping("/deleteSong/{id}")
    public ResponseEntity deleteSong(@PathVariable String id) {
        try {
            songService.deleteSong(id);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully delete song with id: " + id);
        } catch (SongException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (FileFormatException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

//    @GetMapping("/audios")
//    public Mono<ResponseEntity<byte[]>> streamAudio() {
//        System.out.println("Has been call streamAudio");
//        return Mono.just(getContent("C:\\Users\\nguye\\Desktop\\DoAnJava\\server\\src\\main\\resources\\data\\jean.m4a", null, "audio"));
//    }
//
//    private ResponseEntity<byte[]> getContent(String fileName, String range, String contentTypePrefix) {
//        long rangeStart = 0;
//        long rangeEnd;
//        byte[] data;
//        Long fileSize;
//        String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
//        try {
//            fileSize = Optional.ofNullable(fileName)
//                    .map(file -> Paths.get(fileName))
//                    .map(this::sizeFromFile)
//                    .orElse(0L);
//            if (range == null) {
//                return ResponseEntity.status(HttpStatus.OK)
//                        .header("Content-Type", contentTypePrefix+"/" + fileType)
//                        .header("Content-Length", String.valueOf(fileSize))
//                        .body(readByteRange(fileName, rangeStart, fileSize - 1));
//            }
//            String[] ranges = range.split("-");
//            rangeStart = Long.parseLong(ranges[0].substring(6));
//            if (ranges.length > 1) {
//                rangeEnd = Long.parseLong(ranges[1]);
//            } else {
//                rangeEnd = fileSize - 1;
//            }
//            if (fileSize < rangeEnd) {
//                rangeEnd = fileSize - 1;
//            }
//            data = readByteRange(fileName, rangeStart, rangeEnd);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//        String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
//        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
//                .header("Content-Type", contentTypePrefix + "/" + fileType)
//                .header("Accept-Ranges", "bytes")
//                .header("Content-Length", contentLength)
//                .header("Content-Range", "bytes" + " " + rangeStart + "-" + rangeEnd + "/" + fileSize)
//                .body(data);
//    }
//
//    public static final int BYTE_RANGE = 128;
//
//    public byte[] readByteRange(String filename, long start, long end) throws IOException {
//        Path path = Paths.get(filename);
//        System.out.println(path);
//        try (InputStream inputStream = (Files.newInputStream(path));
//             ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream()) {
//            byte[] data = new byte[BYTE_RANGE];
//            int nRead;
//            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
//                bufferedOutputStream.write(data, 0, nRead);
//            }
//            bufferedOutputStream.flush();
//            byte[] result = new byte[(int) (end - start) + 1];
//            System.arraycopy(bufferedOutputStream.toByteArray(), (int) start, result, 0, result.length);
//            return result;
//        }
//    }
//
//    private Long sizeFromFile(Path path) {
//        try {
//            return Files.size(path);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        return 0L;
//    }
}

