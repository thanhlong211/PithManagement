package com.swd.ccp.services_implementors;

import com.swd.ccp.DTO.response_models.LogoutResponse;
import com.swd.ccp.models.entity_models.Account;
import com.swd.ccp.models.entity_models.Token;

import com.swd.ccp.repositories.AccountRepo;
import com.swd.ccp.repositories.TokenRepo;
import com.swd.ccp.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;

    private final TokenRepo tokenRepo;
    @Override
    public String getAccessToken(Integer accountID) {
        Account account = accountRepo.findById(accountID).orElse(null);
        if(account != null){
            Token accessToken = tokenRepo.findByAccount_IdAndStatusAndType(accountID, 1, "access").orElse(null);
            return accessToken != null ? accessToken.getToken() : null;
        }
        return null;
    }

    @Override
    public String getRefreshToken(Integer accountID) {
        Account account = accountRepo.findById(accountID).orElse(null);
        if(account != null){
            Token refreshToken = tokenRepo.findByAccount_IdAndStatusAndType(accountID, 1, "refresh").orElse(null);
            return refreshToken != null ? refreshToken.getToken() : null;
        }
        return null;
    }

    @Override
    public Account getCurrentLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return accountRepo.findByEmail(authentication.getName()).orElse(null);
    }

    @Override
    public LogoutResponse logout() {
        Account account = getCurrentLoggedUser();
        if(account != null){
            Token accessToken = tokenRepo.findByAccount_IdAndStatusAndType(account.getId(), 1, "access").orElse(null);
            Token refreshToken = tokenRepo.findByAccount_IdAndStatusAndType(account.getId(), 1, "refresh").orElse(null);
            if(accessToken != null){
                accessToken.setStatus(0);
                tokenRepo.save(accessToken);
            }
            if(refreshToken != null){
                refreshToken.setStatus(0);
                tokenRepo.save(refreshToken);
            }
            return LogoutResponse.builder().status(true).message("Logout successfully").build();
        }
        return LogoutResponse.builder().status(false).message("Ooh! Something happen").build();
    }
}
