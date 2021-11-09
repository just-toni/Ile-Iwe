package com.ileiwe.data.security.user;

import com.ileiwe.data.model.Authority;
import com.ileiwe.data.model.LearningParty;
import com.ileiwe.data.repository.LearningPartyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class LearningPartyServiceImpl implements UserDetailsService {
    @Autowired
    private LearningPartyRepository learningPartyRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.info("Username request --> {}", email);
        LearningParty user =
                learningPartyRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException
                    ("User with email does not exists");
        }
        return new User(user.getEmail(), user.getPassword(),
                getAuthorities(user.getAuthorities()));
    }

    private List<SimpleGrantedAuthority> getAuthorities
            (List<Authority> authorities){
        return  authorities
                .stream()
                .map(authority -> {
                    return new SimpleGrantedAuthority
                            (authority.getAuthority().name());
                }).collect(Collectors.toList());
    }
}
