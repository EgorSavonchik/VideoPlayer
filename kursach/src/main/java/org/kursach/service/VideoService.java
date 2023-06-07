package org.kursach.service;


import org.kursach.model.Video;
import org.kursach.repository.VideoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VideoService
{
    private VideoRepository userRepository;

    public VideoService(VideoRepository videoRepository)
    {
        this.userRepository = videoRepository;
    }

    @Transactional
    public void add(Video user)
    {
        userRepository.save(user);
    }

    @Transactional
    public void update(Video user)
    {
        userRepository.save(user);
    }

    @Transactional
    public void deleteById(int id)
    {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Video> findAll(Integer authorId, Pageable pageable)
    {
        return userRepository.findAllByAuthorId(authorId, pageable);
    }

    @Transactional(readOnly = true)
    public Video findById(int id)
    {
        return userRepository.findById(id).orElse(null); //!!!
    }
}
