package org.kursach.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.kursach.DTO.ResponseObject;
import org.kursach.DTO.VideoDTO;
import org.kursach.model.Comment;
import org.kursach.model.User;
import org.kursach.model.Video;
import org.kursach.service.UserService;
import org.kursach.service.VideoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.clickntap.vimeo.*;

@RestController
@RequestMapping("/api/video")
public class VideoController
{
    private final VideoService videoService;
    private final UserService userService;

    @Autowired
    public VideoController(VideoService videoService, UserService userService)
    {
        this.videoService = videoService;
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<Page<Video>> getAllVideo(
            @RequestParam(name = "authorId",required = false) Integer authorId, Pageable pageable)
    {
        return ResponseEntity.ok(videoService.findAll(authorId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideoById(@PathVariable Integer id) throws VimeoException, IOException
    {
        return ResponseEntity.ok(videoService.findById(id));
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Video> uploadVideo(
            @Valid @RequestPart("videoDTO") VideoDTO videoDTO,
            @RequestPart(value = "video") MultipartFile videoFile) throws IOException, VimeoException
    {
        Video video = userService.uploadVideo(videoFile, videoDTO);

        videoService.add(video);
        return ResponseEntity.ok(video);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> removeVideoById(@PathVariable Integer id) throws VimeoException, IOException
    {
        Vimeo vimeo = new Vimeo("46e4217f1a78bb0dd7d21fc3559ff4b3");

        vimeo.removeVideo(videoService.findById(id).getVideoEndPoint());
        videoService.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "success", "Video removed"));
    }

    @PatchMapping("{id}")
    public ResponseEntity<Video> updateVideo(@Valid @RequestPart("newVideo") VideoDTO videoDTO, @PathVariable Integer id)
    {
        Video video = videoService.findById(id);

        video.setName(videoDTO.getName());
        video.setDescription(videoDTO.getDescription());

        videoService.update(video);
        return ResponseEntity.ok(video);
    }
}
