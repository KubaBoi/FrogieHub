package cz.kuba.sag.core.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.kuba.sag.data.models.dtos.LoginResponseDTO;
import cz.kuba.sag.data.models.entities.SasAccount;
import cz.kuba.sag.data.mappers.AccountMapper;
import cz.kuba.sag.data.models.dtos.AccountDTO;
import cz.kuba.sag.data.models.dtos.LoginRequestDTO;
import cz.kuba.sag.data.repositories.AccountRepository;
import cz.kuba.sag.utils.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public AuthService(PasswordEncoder passwordEncoder,
                       AccountRepository accountRepository) {
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }

    /**
     * Creates account with authentication via password
     *
     * @param credentials User's credentials
     * @return New account
     */
    public AccountDTO createUser(LoginRequestDTO credentials) {
        log.info("Creating user {}", credentials.getUsername());
        SasAccount account = new SasAccount();
        account.setUserName(credentials.getUsername());
        account.setPasswordHash(passwordEncoder.encode(credentials.getPassword()));
        return AccountMapper.toDTO(accountRepository.save(account));
    }

    /**
     * Authenticate account via password
     *
     * @param credentials User's credentials
     * @return Authenticated account
     */
    public LoginResponseDTO login(LoginRequestDTO credentials) throws AccountNotFoundException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        SasAccount account = accountRepository.findByUserName(credentials.getUsername());
        if (account == null) {
            log.error("Account not found");
            throw new AccountNotFoundException("Account not found");
        }
        if (!passwordEncoder.matches(credentials.getPassword(), account.getPasswordHash())) {
            log.error("Wrong password");
            throw new AccountNotFoundException("Wrong password");
        }

        AccountDTO accountDTO = AccountMapper.toDTO(account);
        Instant now = Instant.now();
        String jwt = new Jwt()
                .payload(new Jwt.Payload()
                        .issuer("SAG")
                        .subject(accountDTO.getId().toString())
                        .expiration(now.plus(1, ChronoUnit.HOURS))
                        .issued(now))
                .build("123456789123456789123456798123456789");

        return new LoginResponseDTO(jwt, accountDTO);
    }
}
