package org.kursach.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.kursach.DTO.ResponseObject;
import org.kursach.DTO.UserDTO;
import org.kursach.model.Role;
import org.kursach.model.User;
import org.kursach.repository.JwtTokenRepository;
import org.kursach.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenRepository jwtTokenRepository;

    public AuthController(@Qualifier("userService") UserService userService, JwtTokenRepository jwtTokenRepository) {
        this.userService = userService;
        this.jwtTokenRepository = jwtTokenRepository;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<User> getAuthUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        User user = (principal instanceof User) ? (User) principal : null;

        return ResponseEntity.ok(Objects.nonNull(user) ? this.userService.findByName(user.getName()) : null);
    }

    @PostMapping(path = "/registration")
    public ResponseEntity<User> registerUser(@Valid @RequestPart("userDTO") UserDTO userDTO)
    {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userDTO, User.class);
        user.setVideos(new ArrayList<>());
        user.setRole(Role.USER);

        userService.add(user);

        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<ResponseObject> userLogout(HttpServletResponse response)
    {
        jwtTokenRepository.clearToken(response);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "success", "Logout"));
    }
}
