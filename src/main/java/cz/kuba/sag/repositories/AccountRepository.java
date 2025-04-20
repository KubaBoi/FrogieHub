package cz.kuba.sag.repositories;

import cz.kuba.sag.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    Account findByUserName(String username);
}
