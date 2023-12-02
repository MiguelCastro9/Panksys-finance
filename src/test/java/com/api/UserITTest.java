package com.api;

import com.api.dto.request.UserRequestDto;
import com.api.enums.RoleEnum;
import com.api.model.UserModel;
import com.api.service.UserService;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

/**
 *
 * @author Miguel Castro
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserITTest {

    @Autowired
    UserService userService;

    @Autowired
    TestRestTemplate testRestTemplate;

    @BeforeEach
    void cleanDatabase() {
        userService.deleteAll();
    }

    @Test
    void savePostTest() {
        UserRequestDto userRequestDto = new UserRequestDto("miguel castro", LocalDate.now(), "miguel@email.com", "miguelmiguel", "miguelmiguel", RoleEnum.USER);
        ResponseEntity<UserRequestDto> requestTemplate = testRestTemplate.postForEntity("/api/v1/auth/singup", userRequestDto, UserRequestDto.class);
        Assertions.assertNotNull(requestTemplate);
    }

    @Test
    void updatePutTest() {
        UserRequestDto userRequestDto1 = new UserRequestDto("miguel castro", LocalDate.now(), "miguel@email.com", "miguelmiguel", "miguelmiguel", RoleEnum.USER);
        UserModel userModel = userService.singup(userRequestDto1.convertUserDtoForEntity());
        Long userId = userModel.getId();
        Optional<UserModel> existingUserBeforeUpdate = userService.find(userId);
        Assertions.assertTrue(existingUserBeforeUpdate.isPresent());
        UserRequestDto userRequestDto2 = new UserRequestDto("miguel updated", LocalDate.now(), "miguel@email.com", "miguelmiguel", "miguelmiguel", RoleEnum.USER);
        ResponseEntity<Void> responseTemplate = testRestTemplate.exchange(
                "/api/v1/user/update/" + userId,
                HttpMethod.PUT,
                new HttpEntity<>(userRequestDto2),
                Void.class
        );
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseTemplate.getStatusCode());
    }
}
