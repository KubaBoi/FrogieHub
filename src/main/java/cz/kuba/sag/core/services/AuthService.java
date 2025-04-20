package cz.kuba.sag.core.services;

import cz.kuba.sag.data.models.Account;
import cz.kuba.sag.data.repositories.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;

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
     * @param username User's login name
     * @param password Raw password
     * @return New account
     */
    public Account createUser(String username, String password) {
        log.info("Creating user {}", username);
        Account account = new Account();
        account.setUserName(username);
        account.setPasswordHash(passwordEncoder.encode(password));
        account = accountRepository.save(account);
        return account;
    }

    /**
     * Authenticate account via password
     *
     * @param username User's login name
     * @param password Raw password
     * @return Authenticated account
     */
    public Account login(String username, String password) throws AccountNotFoundException {
        Account account = accountRepository.findByUserName(username);
        if (account == null) {
            log.error("Account not found");
            throw new AccountNotFoundException("Account not found");
        }
        if (!passwordEncoder.matches(password, account.getPasswordHash())) {
            log.error("Wrong password");
            throw new AccountNotFoundException("Wrong password");
        }
        return account;
    }

    public String createJwt(Account account) {

    }
}
