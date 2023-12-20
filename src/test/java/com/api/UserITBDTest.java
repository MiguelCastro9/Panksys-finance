package com.api;

import com.api.dto.request.UserRequestDto;
import com.api.dto.response.UserResponseDto;
import com.api.model.UserModel;
import com.api.service.UserService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

/**
 *
 * @author Miguel Castro
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserITBDTest {

    @Autowired
    UserService userService;

    private List<UserResponseDto> userResponseDto;

    @BeforeEach
    void cleanDatabase() {
        userService.deleteAll();
    }

    @Test
    void singupTest() {
        UserRequestDto userRequestDto = new UserRequestDto("miguel castro", LocalDate.now(), "miguel@email.com", "miguelmiguel", "miguelmiguel");
        Assertions.assertEquals(userRequestDto.getPassword(), userRequestDto.getPassword_repeated());
        userService.singup(userRequestDto.convertUserDtoForEntity());
        userResponseDto = userService.list().stream().map(user
                -> UserResponseDto.convertEntityForUserDto(user))
                .collect(Collectors.toList());
        Integer size = userResponseDto.size();
        Assertions.assertEquals(1, size);
    }

    @Test
    void updateTest() {
        UserRequestDto userRequestDto1 = new UserRequestDto("miguel castro", LocalDate.now(), "miguel@email.com", "miguelmiguel", "miguelmiguel");
        Assertions.assertEquals(userRequestDto1.getPassword(), userRequestDto1.getPassword_repeated());
        UserModel builder = userService.singup(userRequestDto1.convertUserDtoForEntity());
        Long userId = builder.getId();
        Optional<UserModel> existingUserBeforeUpdate = userService.find(userId);
        Assertions.assertTrue(existingUserBeforeUpdate.isPresent());
        UserRequestDto userRequestDto2 = new UserRequestDto("miguel updated", LocalDate.now(), "miguel@email.com", "miguelmiguel", "miguelmiguel");
        Assertions.assertEquals(userRequestDto2.getPassword(), userRequestDto2.getPassword_repeated());
        userService.update(userId, userRequestDto2.convertUserUpdateDtoForEntity());
        Optional<UserModel> existingUserAfterUpdate = userService.find(userId);
        Assertions.assertTrue(existingUserAfterUpdate.isPresent());
        Assertions.assertEquals("miguel updated", existingUserAfterUpdate.get().getName());
    }
}
