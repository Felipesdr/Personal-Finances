package com.lhama.lhamapersonalfinances.entities.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;
    @NotNull(message = "O nome não pode ser nulo")
    @NotBlank(message = "O nome não pode estar vazio")
    @Column(length = 512)
    private String name;
    @NotNull(message = "O email não pode ser nulo")
    @NotBlank(message = "O email não pode estar vazio")
    @Column(length = 512)
    @Email
    private String email;
    @NotNull(message = "A senha não pode ser nula")
    @NotBlank(message = "A senha não pode estar vazia")
    @Column(length = 512)
    private String password;
    @NotNull
    @NotNull(message = "A senha não pode ser nula")
    @NotBlank(message = "A senha não pode estar vazia")
    @Column(length = 11, unique = true)
    private String cpf;

    public User() {
    }

    public User(Integer idUser, String name, String email, String password, String cpf) {
        this.idUser = idUser;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
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

    public String getPassword() {
        return password;
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
}
