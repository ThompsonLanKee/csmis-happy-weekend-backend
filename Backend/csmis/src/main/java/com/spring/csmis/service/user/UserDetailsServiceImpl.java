package com.spring.csmis.service.user;

import com.spring.csmis.entity.UserEntity;
import com.spring.csmis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identityNo) throws UsernameNotFoundException {
        // Retrieve the user by their identity number (username)
        UserEntity user = userRepository.findByIdentityNo(identityNo)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with identity number: " + identityNo));

        // Map user role directly to authorities (SimpleGrantedAuthority)
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));

        // Return an instance of UserDetailsImpl with required fields
        return new UserDetailsImpl(
                user.getId(),
                user.getEmployee().getStaffID(),  // Assuming this is the 'identityNo' for login
                user.getPassword(),  // User password encoded
                authorities  // Authorities (roles)
        );
    }
}
