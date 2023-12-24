package com.api;

import com.api.dto.request.SigninRequestDto;
import com.api.dto.request.SimpleFinanceInstallmentRequestDto;
import com.api.dto.request.SimpleFinanceRequestDto;
import com.api.dto.request.UserRequestDto;
import com.api.dto.response.JWTResponseDto;
import com.api.dto.response.SimpleFinanceInstallmentResponseDto;
import com.api.enums.FormPaymentEnum;
import com.api.enums.StatusPaymentEnum;
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
public class SimpleFinanceInstallmentITTest {
 
    @Autowired
    TestRestTemplate restTemplate;
    
    
    @Test
    void updatePutTest() {
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
        SimpleFinanceRequestDto simpleFinanceRequestDto1
                = new SimpleFinanceRequestDto("Notebook", 5000, FormPaymentEnum.CREDIT, LocalDate.now(), 5, "Mac OS M1");
        ResponseEntity<Void> restTemplate3 = restTemplate.postForEntity(
                "/api/v1/simple-finance/save",
                new HttpEntity<>(simpleFinanceRequestDto1, headers), Void.class);
        Assertions.assertNotNull(restTemplate3);
        Assertions.assertEquals(HttpStatus.CREATED, restTemplate3.getStatusCode());
        SimpleFinanceInstallmentRequestDto simpleFinanceInstallmentRequestDto = new SimpleFinanceInstallmentRequestDto
        (StatusPaymentEnum.PAID);
        ResponseEntity<Void> restTemplate4 = restTemplate.exchange(
                "/api/v1/simple-finance-installment/update/2",
                HttpMethod.PUT,
                new HttpEntity<>(simpleFinanceInstallmentRequestDto, headers),
                Void.class
        );
        Assertions.assertNotNull(restTemplate4);
        Assertions.assertEquals(HttpStatus.OK, restTemplate4.getStatusCode());
        ResponseEntity<MessageException> restTemplate5 = restTemplate.exchange(
                "/api/v1/simple-finance-installment/update/6",
                HttpMethod.PUT,
                new HttpEntity<>(simpleFinanceInstallmentRequestDto, headers),
                MessageException.class
        );
        Assertions.assertNotNull(restTemplate5);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, restTemplate5.getStatusCode());
        Assertions.assertEquals("Simple finance installment don't exists.", restTemplate5.getBody().getMessage_user());
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
        SimpleFinanceRequestDto simpleFinanceRequestDto1
                = new SimpleFinanceRequestDto("Notebook", 5000, FormPaymentEnum.CREDIT, LocalDate.now(), 5, "Mac OS M1");
        ResponseEntity<Void> restTemplate3 = restTemplate.postForEntity(
                "/api/v1/simple-finance/save",
                new HttpEntity<>(simpleFinanceRequestDto1, headers), Void.class);
        Assertions.assertNotNull(restTemplate3);
        Assertions.assertEquals(HttpStatus.CREATED, restTemplate3.getStatusCode());
        ResponseEntity<List<SimpleFinanceInstallmentResponseDto>> restTemplate4 = restTemplate.exchange(
                "/api/v1/simple-finance-installment/list/1",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<SimpleFinanceInstallmentResponseDto>>() {
        }
        );
        Assertions.assertNotNull(restTemplate4);
        Assertions.assertEquals(HttpStatus.OK, restTemplate4.getStatusCode());
        List<SimpleFinanceInstallmentResponseDto> simpleFinanceInstallments = restTemplate4.getBody();
        Assertions.assertNotNull(simpleFinanceInstallments);
        
        ResponseEntity<MessageException> restTemplate5 = restTemplate.exchange(
                "/api/v1/simple-finance-installment/list/6",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                MessageException.class
        );
        Assertions.assertNotNull(restTemplate5);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, restTemplate5.getStatusCode());
        Assertions.assertEquals("Simple finance installment don't exists.", restTemplate5.getBody().getMessage_user());
    }
}
