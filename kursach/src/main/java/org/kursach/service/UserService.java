package org.kursach.service;

import com.clickntap.vimeo.*;
import org.kursach.DTO.VideoDTO;
import org.kursach.model.*;
import org.kursach.model.User;
import org.kursach.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService
{
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Transactional
    public void add(User user)
    {
        userRepository.save(user);
    }

    @Transactional
    public void update(User user)
    {
        userRepository.save(user);
    }

    @Transactional
    public void deleteById(int id)
    {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable)
    {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public User findById(int id)
    {
        return userRepository.findById(id).orElse(null); //!!!
    }

    @Transactional(readOnly = true)
    public User findByName(String name)
    {
        return userRepository.getUserByName(name).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByName(username);
    }

    public Video uploadVideo(MultipartFile videoFile, VideoDTO videoDTO) throws IOException, VimeoException
    {
        File destinationFile = new File("C:\\Users\\HP\\Desktop\\clone\\VideoPlayer\\kursach\\src\\main\\resources\\temp\\" +
                videoFile.getOriginalFilename());

        // Сохраните содержимое видео в файл
        videoFile.transferTo(destinationFile);

        Video video = new Video();
        video.setName(videoDTO.getName());
        video.setDescription(videoDTO.getDescription());
        video.setAuthor(this.findById(videoDTO.getUserId()));
        video.setComments(new ArrayList<Comment>());

        Vimeo vimeo = new Vimeo("46e4217f1a78bb0dd7d21fc3559ff4b3");

        //add a video
        String videoEndPoint = vimeo.addVideo(destinationFile);
        video.setVideoLink("https://player.vimeo.com/video/" + videoEndPoint.substring(8));
        video.setVideoEndPoint(videoEndPoint);

        //edit video
        String name = video.getName();
        String desc = video.getDescription();
        String license = ""; //see Vimeo API Documentation
        String privacyView = "disable"; //see Vimeo API Documentation
        String privacyEmbed = "whitelist"; //see Vimeo API Documentation
        boolean reviewLink = false;
        vimeo.updateVideoMetadata(videoEndPoint, name, desc, license, privacyView, privacyEmbed, reviewLink);

        vimeo.addVideoPrivacyDomain(videoEndPoint, "clickntap.com");

        destinationFile.delete();

        return video;
    }
}
