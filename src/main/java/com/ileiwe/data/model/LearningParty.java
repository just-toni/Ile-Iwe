package com.ileiwe.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningParty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    @NotBlank @NotNull
    private String email;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @CreationTimestamp
    private LocalDateTime dateCreated;
    @JsonIgnore
    private boolean enabled;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Authority> authorities;
    @JsonIgnore
    private String token;

    public LearningParty (String email, String password, Authority authority) {
        this.email = email;
        this.password = password;
        addAuthority(authority);
        this.enabled = false;
    }

    private void addAuthority(Authority authority){
        if(this.authorities == null){
            this.authorities = new ArrayList<>();
        }
        this.authorities.add(authority);
    }

}
