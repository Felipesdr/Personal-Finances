package com.lhama.lhamapersonalfinances.model.entities.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;
    @NotNull @NotBlank @Column(length = 512)
    private String name;
    @NotNull @NotBlank @Column(length = 512) @Email
    private String email;
    @NotNull @NotBlank @Column(length = 512)
    private String password;
    @NotNull @NotNull @NotBlank @Column(length = 11, unique = true)
    private String cpf;
    @NotNull @Enumerated(EnumType.STRING)
    private UserRole role;

    public User() {
    }

    public User(Integer idUser, String name, String email, String password, String cpf, UserRole role) {
        this.idUser = idUser;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.role = role;
    }

    public User(UserRegisterDTO userRegisterDTO){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.name = userRegisterDTO.name();
        this.email = userRegisterDTO.email();
        this.password = encoder.encode(userRegisterDTO.password());
        this.cpf = userRegisterDTO.cpf();
        this.role = UserRole.USER;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getIdUser(), user.getIdUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUser());
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}
