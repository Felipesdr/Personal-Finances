package com.lhama.lhamapersonalfinances.model.services;

import com.lhama.lhamapersonalfinances.model.entities.user.User;
import com.lhama.lhamapersonalfinances.model.entities.user.UserRegisterDTO;
import com.lhama.lhamapersonalfinances.model.repositorys.UserRepository;
import com.lhama.lhamapersonalfinances.model.util.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public User registerUser(UserRegisterDTO userRegisterData) {
        if (userRepository.findByEmail(userRegisterData.email()) != null) return null;

        User newUser = userRepository.save(new User(userRegisterData));

        String to = newUser.getEmail();
        String subject = "Olá! Bem vindo ao Lhama Personal Finances!";
        String body = "Olá "
                + newUser.getName()
                + " Você acaba de dar o primeiro passo para garantir sua saúde financeira! Bem vindo ao melhor aplicativo de gestão de finanças pessoais do mercado.";

        emailService.sendEmail(to, subject, body);

        return newUser;
    }

    public void changePassword(String newPassword, Long idUser){
        User user = userRepository.getReferenceById(idUser);
        user.changePassword(newPassword);
        userRepository.save(user);
    }
}
