package cz.kuba.sag.data.mappers;

import cz.kuba.sag.data.models.entities.SasAccount;
import cz.kuba.sag.data.models.dtos.AccountDTO;

public class AccountMapper {

    public static SasAccount toEntity(AccountDTO account) {
        return new SasAccount()
                .setId(account.getId())
                .setUserName(account.getUserName());
    }

    public static AccountDTO toDTO(SasAccount account) {
        return new AccountDTO()
                .setId(account.getId())
                .setUserName(account.getUserName());
    }
}
