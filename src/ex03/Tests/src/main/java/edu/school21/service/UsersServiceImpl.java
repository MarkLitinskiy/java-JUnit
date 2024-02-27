package edu.school21.service;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

import java.sql.Connection;

public class UsersServiceImpl implements UsersRepository {
    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public User findByLogin(String login) {
        return usersRepository.findByLogin(login);
    }

    @Override
    public void update(User user) {

    }

    public boolean authenticate(String login, String password) throws AlreadyAuthenticatedException {
        User user = findByLogin(login);
        if (user == null)
            return false;
        if (user.isSuccessStatus()){
            throw new AlreadyAuthenticatedException("User authorized!");
        } else if (user.getPassword().equals(password) ) {
            user.setSuccessStatus(true);
            update(user);
            return true;
        } else {
            return false;
        }
    }


}
