package com.lhama.lhamapersonalfinances.repositorys;

import com.lhama.lhamapersonalfinances.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
