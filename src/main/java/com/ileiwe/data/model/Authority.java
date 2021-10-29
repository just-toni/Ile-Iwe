package com.ileiwe.data.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@RequiredArgsConstructor
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    private Role authority;
    @ManyToOne
    private LearningParty user;

    public Authority(Role role){
        this.authority = role;
    }

}
