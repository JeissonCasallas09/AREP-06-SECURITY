package edu.eci.arep.secureapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arep.secureapp.model.User;
import edu.eci.arep.secureapp.service.UserService;

@CrossOrigin(origins = "https://securityfrontarep.duckdns.org", allowCredentials = "true")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Registrar usuario
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.CREATED); // 201 Created
        } catch (Exception e) {
            return new ResponseEntity<>("Error al registrar el usuario", HttpStatus.BAD_REQUEST); // 400 Bad Request
        }
    }

    @PostMapping("/verification")
    public ResponseEntity<String> authenticate(@RequestBody User user) {
        if (userService.verification(user.getUsername(), user.getPassword())) {
            return ResponseEntity.ok("Autenticado exitosamente"); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas"); // 401 Unauthorized
        }
    }
}

