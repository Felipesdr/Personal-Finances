package com.lhama.lhamapersonalfinances.model.repositorys;

import com.lhama.lhamapersonalfinances.model.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Integer> {
    UserDetails findByEmail(String email);
}
