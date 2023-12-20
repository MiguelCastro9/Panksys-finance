package com.api;

import com.api.dto.request.SigninRequestDto;
import com.api.dto.request.UserRequestDto;
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
    void signinPostTest() {
        UserRequestDto userRequestDto = new UserRequestDto("miguel castro", LocalDate.now(), "miguel@email.com", "miguelmiguel", "miguelmiguel");
        ResponseEntity<UserRequestDto> restTemplate1 = testRestTemplate.postForEntity("/api/v1/auth/singup", userRequestDto, UserRequestDto.class);
        Assertions.assertNotNull(restTemplate1);
        Assertions.assertNotNull(restTemplate1.getBody());
        Assertions.assertEquals(HttpStatus.CREATED, restTemplate1.getStatusCode());
        SigninRequestDto signinRequestDto = new SigninRequestDto(userRequestDto.getEmail(), userRequestDto.getPassword());
        ResponseEntity<SigninRequestDto> restTemplate2 = testRestTemplate.postForEntity("/api/v1/auth/signin", signinRequestDto, SigninRequestDto.class);
        Assertions.assertNotNull(restTemplate2);
        Assertions.assertEquals(HttpStatus.OK, restTemplate2.getStatusCode());
        Assertions.assertNotNull(restTemplate2.getBody());
    }

    @Test
    void singupPostTest() {
        UserRequestDto userRequestDto = new UserRequestDto("miguel castro", LocalDate.now(), "miguel@email.com", "miguelmiguel", "miguelmiguel");
        Assertions.assertEquals(userRequestDto.getPassword(), userRequestDto.getPassword_repeated());
        ResponseEntity<UserRequestDto> restTemplate = testRestTemplate.postForEntity("/api/v1/auth/singup", userRequestDto, UserRequestDto.class);
        Assertions.assertNotNull(restTemplate);
    }

    @Test
    void updatePutTest() {
        UserRequestDto userRequestDto1 = new UserRequestDto("miguel castro", LocalDate.now(), "miguel@email.com", "miguelmiguel", "miguelmiguel");
        Assertions.assertEquals(userRequestDto1.getPassword(), userRequestDto1.getPassword_repeated());
        UserModel builder = userService.singup(userRequestDto1.convertUserDtoForEntity());
        Long userId = builder.getId();
        Optional<UserModel> existingUserBeforeUpdate = userService.find(userId);
        Assertions.assertTrue(existingUserBeforeUpdate.isPresent());
        UserRequestDto userRequestDto2 = new UserRequestDto("miguel updated", LocalDate.now(), "miguel@email.com", "miguelmiguel", "miguelmiguel");
        Assertions.assertEquals(userRequestDto2.getPassword(), userRequestDto2.getPassword_repeated());
        ResponseEntity<Void> restTemplate = testRestTemplate.exchange(
                "/api/v1/user/update/" + userId,
                HttpMethod.PUT,
                new HttpEntity<>(userRequestDto2),
                Void.class
        );
        Assertions.assertNotNull(restTemplate);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, restTemplate.getStatusCode());
    }
    
    @Test
    void disabledPutTest() {
        UserRequestDto userRequestDto = new UserRequestDto("miguel castro", LocalDate.now(), "miguel@email.com", "miguelmiguel", "miguelmiguel");
        Assertions.assertEquals(userRequestDto.getPassword(), userRequestDto.getPassword_repeated());
        UserModel builder = userService.singup(userRequestDto.convertUserDtoForEntity());
        Long userId = builder.getId();
        Optional<UserModel> existingUserBeforeUpdate = userService.find(userId);
        Assertions.assertTrue(existingUserBeforeUpdate.isPresent());
        ResponseEntity<Void> restTemplate = testRestTemplate.exchange(
                "/api/v1/user/disabled/" + userId,
                HttpMethod.PUT,
                new HttpEntity<>(userRequestDto),
                Void.class
        );
        Assertions.assertNotNull(restTemplate);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, restTemplate.getStatusCode());
    }
}
