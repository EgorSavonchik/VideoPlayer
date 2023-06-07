package org.kursach.controller;

import jakarta.validation.Valid;
import org.kursach.DTO.CommentDTO;
import org.kursach.DTO.ResponseObject;
import org.kursach.model.Comment;
import org.kursach.service.CommentService;
import org.kursach.service.UserService;
import org.kursach.service.VideoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/comment")
public class CommentController
{
    private final CommentService commentService;
    private final VideoService videoService;
    private final UserService userService;

    public CommentController(CommentService commentService, VideoService videoService, UserService userService)
    {
        this.commentService = commentService;
        this.videoService = videoService;
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Page<Comment>> getCommentsByUser(
            @PathVariable Integer id, Pageable pageable)
    {
        return ResponseEntity.ok(commentService.findByUserId(id, pageable));
    }

    @GetMapping("/video/{id}")
    public ResponseEntity<Page<Comment>> getCommentsByVideo(
            @PathVariable Integer id, Pageable pageable)
    {
        return ResponseEntity.ok(commentService.findByVideoId(id, pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<Comment> createComment(
            @Valid @RequestPart("commentDTO") CommentDTO commentDTO)
    {
        Comment comment = convertToComment(new Comment(), commentDTO);
        commentService.add(comment);

        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseObject> removeCommentById(
            @PathVariable("id") Integer id)
    {
        commentService.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "success", "Comment removed"));
    }

    @GetMapping("{id}")
    public ResponseEntity<Comment> getCommentById(
            @PathVariable("id") Integer id)
    {
        return ResponseEntity.ok(commentService.getById(id));
    }

    @GetMapping()
    public ResponseEntity<Page<Comment>> getAllComment(
            Pageable pageable)
    {
        return ResponseEntity.ok(commentService.getAll(pageable));
    }

    @PatchMapping("{id}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable("id") Integer id,
            @Valid @RequestPart("newCommentDTO") CommentDTO commentDTO)
    {
        Comment comment = commentService.getById(id);

        convertToComment(comment, commentDTO);
        commentService.update(comment);

        return ResponseEntity.ok(comment);
    }

    private Comment convertToComment(Comment comment, CommentDTO commentDTO)
    {
        comment.setCommentText(commentDTO.getCommentText());
        comment.setOwnerUser(userService.findById(commentDTO.getUserId()));
        comment.setOwnerVideo(videoService.findById(commentDTO.getVideoId()));
        comment.setDateOfCreate(LocalDateTime.now());

        return comment;
    }
}
