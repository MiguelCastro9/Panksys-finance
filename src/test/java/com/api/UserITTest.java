package com.api;

import com.api.dto.request.SigninRequestDto;
import com.api.dto.request.UserRequestDto;
import com.api.dto.response.JWTResponseDto;
import com.api.exception.MessageException;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

/**
 *
 * @author Miguel Castro
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class UserITTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void singupPostTest() {
        UserRequestDto userRequestDto1 = new UserRequestDto("panksys finance", LocalDate.now(), "panksysfinance@email.com", "panksysfinance", "panksysfinance");
        Assertions.assertEquals(userRequestDto1.getPassword(), userRequestDto1.getPassword_repeated());
        ResponseEntity<UserRequestDto> restTemplate1 = restTemplate.postForEntity("/api/v1/auth/singup", userRequestDto1, UserRequestDto.class);
        Assertions.assertNotNull(restTemplate1);
        UserRequestDto userRequestDto2 = new UserRequestDto("panksys finance", LocalDate.now(), "panksysfinance@email.com", "panksysfinance", "panksysfinance");
        Assertions.assertEquals(userRequestDto2.getPassword(), userRequestDto2.getPassword_repeated());
        ResponseEntity<MessageException> restTemplate2 = restTemplate.postForEntity("/api/v1/auth/singup", userRequestDto2, MessageException.class);
        Assertions.assertNotNull(restTemplate2);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, restTemplate2.getStatusCode());
        Assertions.assertEquals("User with email " + userRequestDto2.getEmail() + " already exists", restTemplate2.getBody().getMessage_user());
    }

    @Test
    void signinPostTest() {
        UserRequestDto userRequestDto = new UserRequestDto("panksys finance", LocalDate.now(), "panksysfinance@email.com", "panksysfinance", "panksysfinance");
        ResponseEntity<UserRequestDto> restTemplate1 = restTemplate.postForEntity("/api/v1/auth/singup", userRequestDto, UserRequestDto.class);
        Assertions.assertNotNull(restTemplate1);
        Assertions.assertNotNull(restTemplate1.getBody());
        Assertions.assertEquals(HttpStatus.CREATED, restTemplate1.getStatusCode());
        SigninRequestDto signinRequestDto = new SigninRequestDto(userRequestDto.getEmail(), userRequestDto.getPassword());
        ResponseEntity<JWTResponseDto> restTemplate2 = restTemplate.postForEntity("/api/v1/auth/signin", signinRequestDto, JWTResponseDto.class);
        Assertions.assertNotNull(restTemplate2);
        Assertions.assertEquals(HttpStatus.OK, restTemplate2.getStatusCode());
        Assertions.assertNotNull(restTemplate2.getBody());
        Assertions.assertNotNull(restTemplate2.getBody().getToken());
    }

    @Test
    void updatePutTest() {
        UserRequestDto userRequestDto1 = new UserRequestDto("panksys finance", LocalDate.now(), "panksysfinance@email.com", "panksysfinance", "panksysfinance");
        Assertions.assertEquals(userRequestDto1.getPassword(), userRequestDto1.getPassword_repeated());
        ResponseEntity<UserRequestDto> restTemplate1 = restTemplate.postForEntity("/api/v1/auth/singup", userRequestDto1, UserRequestDto.class);
        Assertions.assertNotNull(restTemplate1);
        Assertions.assertNotNull(restTemplate1.getBody());
        Assertions.assertEquals(HttpStatus.CREATED, restTemplate1.getStatusCode());
        SigninRequestDto signinRequestDto = new SigninRequestDto(userRequestDto1.getEmail(), userRequestDto1.getPassword());
        ResponseEntity<JWTResponseDto> restTemplate2 = restTemplate.postForEntity("/api/v1/auth/signin", signinRequestDto, JWTResponseDto.class);
        Assertions.assertNotNull(restTemplate2);
        Assertions.assertEquals(HttpStatus.OK, restTemplate2.getStatusCode());
        Assertions.assertNotNull(restTemplate2.getBody());
        Assertions.assertNotNull(restTemplate2.getBody().getToken());
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(restTemplate2.getBody().getToken());
        UserRequestDto userRequestDto2 = new UserRequestDto("panksys finance updated", LocalDate.now(), "panksysfinance@email.com", "panksysfinance", "panksysfinance");
        Assertions.assertEquals(userRequestDto2.getPassword(), userRequestDto2.getPassword_repeated());
        ResponseEntity<Void> restTemplate3 = restTemplate.exchange(
                "/api/v1/user/update/1",
                HttpMethod.PUT,
                new HttpEntity<>(userRequestDto2, headers),
                Void.class
        );
        Assertions.assertNotNull(restTemplate3);
        Assertions.assertEquals(HttpStatus.OK, restTemplate3.getStatusCode());
        ResponseEntity<MessageException> restTemplate4 = restTemplate.exchange(
                "/api/v1/user/update/2",
                HttpMethod.PUT,
                new HttpEntity<>(userRequestDto2, headers),
                MessageException.class
        );
        Assertions.assertNotNull(restTemplate4);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, restTemplate4.getStatusCode());
        Assertions.assertEquals("You are not allowed to update other users.", restTemplate4.getBody().getMessage_user());
    }

    @Test
    void disabledPutTest() {
        UserRequestDto userRequestDto1 = new UserRequestDto("panksys finance", LocalDate.now(), "panksysfinance@email.com", "panksysfinance", "panksysfinance");
        Assertions.assertEquals(userRequestDto1.getPassword(), userRequestDto1.getPassword_repeated());
        ResponseEntity<UserRequestDto> restTemplate1 = restTemplate.postForEntity("/api/v1/auth/singup", userRequestDto1, UserRequestDto.class);
        Assertions.assertNotNull(restTemplate1);
        Assertions.assertNotNull(restTemplate1.getBody());
        Assertions.assertEquals(HttpStatus.CREATED, restTemplate1.getStatusCode());
        SigninRequestDto signinRequestDto = new SigninRequestDto(userRequestDto1.getEmail(), userRequestDto1.getPassword());
        ResponseEntity<JWTResponseDto> restTemplate2 = restTemplate.postForEntity("/api/v1/auth/signin", signinRequestDto, JWTResponseDto.class);
        Assertions.assertNotNull(restTemplate2);
        Assertions.assertEquals(HttpStatus.OK, restTemplate2.getStatusCode());
        Assertions.assertNotNull(restTemplate2.getBody());
        Assertions.assertNotNull(restTemplate2.getBody().getToken());
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(restTemplate2.getBody().getToken());
        ResponseEntity<Void> restTemplate3 = restTemplate.exchange(
                "/api/v1/user/disabled/1",
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                Void.class
        );
        Assertions.assertNotNull(restTemplate3);
        Assertions.assertEquals(HttpStatus.OK, restTemplate3.getStatusCode());
        ResponseEntity<MessageException> restTemplate4 = restTemplate.exchange(
                "/api/v1/user/disabled/2",
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                MessageException.class
        );
        Assertions.assertNotNull(restTemplate4);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, restTemplate4.getStatusCode());
        Assertions.assertEquals("Your user is disabled.", restTemplate4.getBody().getMessage_user());
    }
}
