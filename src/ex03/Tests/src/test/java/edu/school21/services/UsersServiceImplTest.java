package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.numbers.IllegalNumberException;
import edu.school21.repositories.UsersRepository;
import edu.school21.service.UsersServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UsersServiceImplTest {

    @Test
    public void testAuthorizedTrueLoginPassword() throws AlreadyAuthenticatedException {
        UsersRepository mockUsersRepository = Mockito.mock(UsersRepository.class);
        User user = new User(1, "abalonef", "12345", false);
        Mockito.when(mockUsersRepository.findByLogin("abalonef")).thenReturn(user);
        UsersServiceImpl usersService = new UsersServiceImpl(mockUsersRepository);
        boolean result = usersService.authenticate("abalonef", "12345");

        assertEquals(result, true);
    }

    @Test
    public void testAuthorizedFailLogin() throws AlreadyAuthenticatedException {
        UsersRepository mockUsersRepository = Mockito.mock(UsersRepository.class);
        User user = null;
        Mockito.when(mockUsersRepository.findByLogin("abalonef")).thenReturn(user);
        UsersServiceImpl usersService = new UsersServiceImpl(mockUsersRepository);
        assertEquals(usersService.authenticate("abalonefff", "12345"), false);
    }

    @Test
    public void testAuthorizedFailPassword() throws AlreadyAuthenticatedException {
        UsersRepository mockUsersRepository = Mockito.mock(UsersRepository.class);
        User user = new User(1, "abalonef", "123456666", false);
        Mockito.when(mockUsersRepository.findByLogin("abalonef")).thenReturn(user);
        UsersServiceImpl usersService = new UsersServiceImpl(mockUsersRepository);

        assertEquals(usersService.authenticate("abalonef", "12345"), false);
    }

    @Test
    public void testAuthorizedTrueAuth() {
        UsersRepository mockUsersRepository = Mockito.mock(UsersRepository.class);
        User user = new User(1, "abalonef", "12345", true);
        Mockito.when(mockUsersRepository.findByLogin("abalonef")).thenReturn(user);
        UsersServiceImpl usersService = new UsersServiceImpl(mockUsersRepository);

        AlreadyAuthenticatedException thrown = Assertions.assertThrows(AlreadyAuthenticatedException.class, () -> {
            usersService.authenticate("abalonef", "12345");
        });

        Assertions.assertEquals("User authorized!", thrown.getMessage());
    }
}
