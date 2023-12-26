package com.api.controller;

import com.api.dto.response.NotificationResponseDto;
import com.api.service.NotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Miguel Castro
 */
@RestController
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Notifications")
@RequestMapping("api/v1/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/list")
    public ResponseEntity<List<NotificationResponseDto>> list() {
        List<NotificationResponseDto> notificationResponseDtoList = notificationService.findByUserId().stream()
                .map(notification -> {
                    NotificationResponseDto notificationResponseDto = NotificationResponseDto.convertEntityForNotificationDto(notification);
                    Link selfLink = linkTo(methodOn(NotificationController.class).list()).withSelfRel();
                    notificationResponseDto.add(selfLink);
                    return notificationResponseDto;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(notificationResponseDtoList, HttpStatus.OK);
    }
}
