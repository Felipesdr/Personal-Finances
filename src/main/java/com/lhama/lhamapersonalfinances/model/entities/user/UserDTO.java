package com.lhama.lhamapersonalfinances.model.entities.user;

public record UserDTO(Integer id, String name, String email, String cpf) {

    public UserDTO(User user){
        this(user.getIdUser(), user.getName(), user.getEmail(), user.getCpf());
    }
}
