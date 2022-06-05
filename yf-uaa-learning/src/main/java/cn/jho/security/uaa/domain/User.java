package cn.jho.security.uaa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * @author JHO xu-jihong@qq.com
 * @date 2022-05-03 0:58
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Data
@Entity(name = "uaa_users")
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80, unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @Column(name = "password_hash", length = 80, nullable = false)
    private String password;

    @Column(length = 225, unique = true, nullable = false)
    private String email;

    @Column(length = 11, unique = true, nullable = false)
    private String mobile;

    @Column(length = 50)
    private String name;

    @Column(nullable = false)
    private boolean enabled;

    @Column(name = "account_non_expired", nullable = false)
    private boolean accountNonExpired;

    @Column(name = "account_non_locked", nullable = false)
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired", nullable = false)
    private boolean credentialsNonExpired;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "uaa_users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Role> authorities;

}
