package com.api;

import com.api.dto.request.FeedbackRequestDto;
import com.api.dto.request.SigninRequestDto;
import com.api.dto.request.UserRequestDto;
import com.api.dto.response.FeedbackResponseDto;
import com.api.dto.response.JWTResponseDto;
import com.api.exception.MessageException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class FeedbackITTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void savePostTest() {
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
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(restTemplate2.getBody().getToken());
        FeedbackRequestDto feedbackRequestDto1 = new FeedbackRequestDto(5, "Very good!", "The panksys finance system help me lot.");
        ResponseEntity<Void> restTemplate3 = restTemplate.exchange(
                "/api/v1/feedback/save",
                HttpMethod.POST,
                new HttpEntity<>(feedbackRequestDto1, headers),
                Void.class
        );
        Assertions.assertNotNull(restTemplate3);
        Assertions.assertEquals(HttpStatus.CREATED, restTemplate3.getStatusCode());
    }

    @Test
    void listGetTest() {
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
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(restTemplate2.getBody().getToken());
        FeedbackRequestDto feedbackRequestDto1 = new FeedbackRequestDto(5, "Very good!", "The panksys finance system help me lot.");
        ResponseEntity<Void> restTemplate3 = restTemplate.exchange(
                "/api/v1/feedback/save",
                HttpMethod.POST,
                new HttpEntity<>(feedbackRequestDto1, headers),
                Void.class
        );
        Assertions.assertNotNull(restTemplate3);
        Assertions.assertEquals(HttpStatus.CREATED, restTemplate3.getStatusCode());
        ResponseEntity<List<FeedbackResponseDto>> restTemplate5 = restTemplate.exchange(
                "/api/v1/feedback/list",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<FeedbackResponseDto>>() {
        }
        );
        Assertions.assertNotNull(restTemplate5);
        Assertions.assertEquals(HttpStatus.OK, restTemplate5.getStatusCode());
        List<FeedbackResponseDto> feedbacks = restTemplate5.getBody();
        Assertions.assertNotNull(feedbacks);
    }

    @Test
    void findGetTest() {
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
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(restTemplate2.getBody().getToken());
        FeedbackRequestDto feedbackRequestDto1 = new FeedbackRequestDto(5, "Very good!", "The panksys finance system help me lot.");
        ResponseEntity<Void> restTemplate3 = restTemplate.exchange(
                "/api/v1/feedback/save",
                HttpMethod.POST,
                new HttpEntity<>(feedbackRequestDto1, headers),
                Void.class
        );
        Assertions.assertNotNull(restTemplate3);
        Assertions.assertEquals(HttpStatus.CREATED, restTemplate3.getStatusCode());
        ResponseEntity<List<FeedbackResponseDto>> restTemplate5 = restTemplate.exchange(
                "/api/v1/feedback/find/5",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<FeedbackResponseDto>>() {
        }
        );
        Assertions.assertNotNull(restTemplate5);
        Assertions.assertEquals(HttpStatus.OK, restTemplate5.getStatusCode());
        List<FeedbackResponseDto> feedbacks = restTemplate5.getBody();
        Assertions.assertNotNull(feedbacks);
    }
    
    @Test
    void myFeedbacksGetTest() {
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
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(restTemplate2.getBody().getToken());
        
        
        FeedbackRequestDto feedbackRequestDto1 = new FeedbackRequestDto(5, "Very good!", "The panksys finance system help me lot.");
        ResponseEntity<Void> restTemplate3 = restTemplate.exchange(
                "/api/v1/feedback/save",
                HttpMethod.POST,
                new HttpEntity<>(feedbackRequestDto1, headers),
                Void.class
        );
        Assertions.assertNotNull(restTemplate3);
        Assertions.assertEquals(HttpStatus.CREATED, restTemplate3.getStatusCode());
        
        
        ResponseEntity<List<FeedbackResponseDto>> restTemplate5 = restTemplate.exchange(
                "/api/v1/feedback/my-feedbacks",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<FeedbackResponseDto>>() {
        }
        );
        Assertions.assertNotNull(restTemplate5);
        Assertions.assertEquals(HttpStatus.OK, restTemplate5.getStatusCode());
        List<FeedbackResponseDto> feedbacks = restTemplate5.getBody();
        Assertions.assertNotNull(feedbacks);
    }
    
    @Test
    void disabledDeleteTest() {
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
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(restTemplate2.getBody().getToken());
        
        
        FeedbackRequestDto feedbackRequestDto1 = new FeedbackRequestDto(5, "Very good!", "The panksys finance system help me lot.");
        ResponseEntity<Void> restTemplate3 = restTemplate.exchange(
                "/api/v1/feedback/save",
                HttpMethod.POST,
                new HttpEntity<>(feedbackRequestDto1, headers),
                Void.class
        );
        Assertions.assertNotNull(restTemplate3);
        Assertions.assertEquals(HttpStatus.CREATED, restTemplate3.getStatusCode());
        System.out.println("aquiii");
        System.out.println(feedbackRequestDto1);
        
        ResponseEntity<Void> restTemplate5 = restTemplate.exchange(
                "/api/v1/feedback/disabled/1",
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                Void.class
        );
        Assertions.assertNotNull(restTemplate5);
        System.out.println(restTemplate5);
        Assertions.assertEquals(HttpStatus.OK, restTemplate5.getStatusCode());
        ResponseEntity<MessageException> restTemplate6 = restTemplate.exchange(
                "/api/v1/feedback/disabled/2",
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                MessageException.class
        );
        Assertions.assertNotNull(restTemplate5);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, restTemplate6.getStatusCode());
        Assertions.assertEquals("Feedback don't exists.", restTemplate6.getBody().getMessage_user());
    }
}
