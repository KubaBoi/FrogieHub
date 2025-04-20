package cz.kuba.sag.core.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.kuba.sag.core.services.AuthService;
import cz.kuba.sag.data.models.dtos.AccountDTO;
import cz.kuba.sag.data.models.dtos.CredentialsDTO;
import cz.kuba.sag.data.models.dtos.LoginResponseDTO;
import cz.kuba.sag.utils.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody CredentialsDTO credentials) {
        LoginResponseDTO account;
        try {
            account = authService.login(credentials);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (NoSuchAlgorithmException | InvalidKeyException | JsonProcessingException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @PutMapping
    @RequestMapping("/register")
    public ResponseEntity<AccountDTO> register(@RequestBody CredentialsDTO credentials) {
        AccountDTO account = authService.createUser(credentials);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }
}
