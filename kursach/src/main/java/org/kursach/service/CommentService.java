package org.kursach.service;




import org.kursach.model.Comment;
import org.kursach.model.User;
import org.kursach.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService
{
    private final VideoService videoService;
    private final CommentRepository commentRepository;

    @Autowired
    CommentService(VideoService videoService, CommentRepository commentRepository)
    {
        this.videoService = videoService;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public void add(Comment comment)
    {
        commentRepository.save(comment);
    }

    @Transactional
    public void update(Comment comment)
    {
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteById(int id)
    {
        commentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Comment> findAll()
    {
        return commentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Comment> findByUserId(Integer id, Pageable pageable)
    {
        return commentRepository.findAllByUserId(id, pageable); //!!!
    }

    @Transactional(readOnly = true)
    public Page<Comment> findByVideoId(Integer id, Pageable pageable)
    {
        return commentRepository.findAllByVideoId(id, pageable); //!!!
    }

    @Transactional(readOnly = true)
    public Page<Comment> getAll(Pageable pageable)
    {
        return commentRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public User getAuthor(Comment comment)
    {
        return videoService.findById(comment.getCommentId()).getAuthor();
    }

    @Transactional(readOnly = true)
    public Comment getById(Integer id)
    {
        return commentRepository.findById(id).orElse(null);
    }
}
