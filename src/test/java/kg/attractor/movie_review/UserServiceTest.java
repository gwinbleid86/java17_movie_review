package kg.attractor.movie_review;

import kg.attractor.movie_review.model.User;
import kg.attractor.movie_review.repository.RoleRepository;
import kg.attractor.movie_review.repository.UserRepository;
import kg.attractor.movie_review.service.AuthUserDetailsService;
import kg.attractor.movie_review.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private AuthUserDetailsService service;

    @Test
    void testGetAllUsers_ReturnListUsers() {
        List<User> fakeUsers = new ArrayList<>();
        fakeUsers.add(new User());
        fakeUsers.add(new User());
        fakeUsers.add(new User());
        fakeUsers.add(new User());
        fakeUsers.add(new User());
        when(userRepository.findAll()).thenReturn(fakeUsers);

        List<User> result = userService.getAllUsers();

        assertEquals(fakeUsers, result);

        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

//    @Test
//    void testRegisterExistingUser() {
//        UserDto userDto = new UserDto();
//        userDto.setEmail("qwe@qwe.qwe");
//
//        when(userRepository.getByEmail(userDto.getEmail())).thenReturn(Optional.of(new User()));
//
//        assertThrows(IllegalArgumentException.class, () -> userService.createUser(userDto));
//    }
}
