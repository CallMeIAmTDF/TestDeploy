package com.huce.edu.security;

import com.huce.edu.entities.AdminsEntity;
import com.huce.edu.entities.UserEntity;
import com.huce.edu.repositories.AdminsRepo;
import com.huce.edu.repositories.UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
@Service
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private UserAccountRepo userAccountRepo;

    @Autowired
    private AdminsRepo adminsRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userAccountRepo.findByEmail(email);

        if(user.isEmpty()) {
            Optional<AdminsEntity> admin = adminsRepo.findByEmail(email);
            return admin.map(UserInfoUserDetails::new)
                    .orElseThrow(() -> new UsernameNotFoundException("Admin not found " + email));
        }

        return user.map(UserInfoUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found " + email));
    }
}
