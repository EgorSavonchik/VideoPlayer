package org.kursach.controller;

import org.kursach.DTO.UserDTO;
import org.kursach.model.Role;
import org.kursach.model.User;
import org.kursach.model.Video;
import org.kursach.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/user")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAll(
            Pageable pageable)
    {
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(
        @PathVariable("id") Integer id)
    {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<User> addUser(
            @RequestPart("userDTO") UserDTO userDTO)
    {
        ModelMapper modelMapper = new ModelMapper();

        User user = modelMapper.map(userDTO, User.class);
        user.setVideos(new ArrayList<Video>());
        user.setRole(Role.USER);

        userService.add(user);

        return ResponseEntity.ok(user);
    }
}
