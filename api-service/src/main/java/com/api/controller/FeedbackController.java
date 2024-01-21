package com.api.controller;

import com.api.dto.request.FeedbackRequestDto;
import com.api.dto.response.FeedbackResponseDto;
import com.api.model.FeedbackModel;
import com.api.service.FeedbackService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Miguel Castro
 */
@RestController
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Feedbacks")
@RequestMapping("api/v1/feedback")
public class FeedbackController {
    
    @Autowired
    private FeedbackService feedbackService;
    
    
    @PostMapping("/save")
    public ResponseEntity<FeedbackResponseDto> save(@Valid @RequestBody FeedbackRequestDto feedbackRequestDto) {
        FeedbackModel feedbackModel = feedbackService.save(feedbackRequestDto.convertFeedbackRequestDtoForEntity());
        FeedbackResponseDto feedbackResponseDto = FeedbackResponseDto.convertEntityForFeedbackResponseDto(feedbackModel);
        feedbackResponseDto.add(linkTo(methodOn(FeedbackController.class).save(feedbackRequestDto)).withSelfRel());
        return new ResponseEntity<>(feedbackResponseDto, HttpStatus.CREATED);
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<FeedbackResponseDto>> findAllFeedbacks() {
        List<FeedbackResponseDto> feedbackResponseDtoList = feedbackService.findAllFeedbacks().stream()
                .map(feedback -> {
                    FeedbackResponseDto feedbackResponseDto = FeedbackResponseDto.convertEntityForFeedbackResponseDto(feedback);
                    Link selfLink = linkTo(methodOn(FeedbackController.class).findAllFeedbacks()).withSelfRel();
                    feedbackResponseDto.add(selfLink);
                    return feedbackResponseDto;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(feedbackResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/find/{totalStars}")
    public ResponseEntity<List<FeedbackResponseDto>> filterFeedbackByStars(@PathVariable Integer totalStars) {
        List<FeedbackResponseDto> feedbackResponseDtoList = feedbackService.filterFeedbackByStars(totalStars).stream()
                .map(feedback -> {
                    FeedbackResponseDto feedbackResponseDto = FeedbackResponseDto.convertEntityForFeedbackResponseDto(feedback);
                    Link selfLink = linkTo(methodOn(FeedbackController.class).filterFeedbackByStars(totalStars)).withSelfRel();
                    feedbackResponseDto.add(selfLink);
                    return feedbackResponseDto;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(feedbackResponseDtoList, HttpStatus.OK);
    }
    
    @GetMapping("/my-feedbacks")
    public ResponseEntity<List<FeedbackResponseDto>> findAllMyFeedbacks() {
        List<FeedbackResponseDto> feedbackResponseDtoList = feedbackService.findAllMyFeedbacks().stream()
                .map(feedback -> {
                    FeedbackResponseDto feedbackResponseDto = FeedbackResponseDto.convertEntityForFeedbackResponseDto(feedback);
                    Link selfLink = linkTo(methodOn(FeedbackController.class).findAllMyFeedbacks()).withSelfRel();
                    feedbackResponseDto.add(selfLink);
                    return feedbackResponseDto;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(feedbackResponseDtoList, HttpStatus.OK);
    }
    
    @DeleteMapping("/disabled/{id}")
    public ResponseEntity<FeedbackResponseDto> disabled(@PathVariable Long id) {
        FeedbackModel feedbackModel = feedbackService.disabled(id);
        FeedbackResponseDto feedbackResponseDto = FeedbackResponseDto.convertEntityForFeedbackResponseDto(feedbackModel);
        feedbackResponseDto.add(linkTo(methodOn(FeedbackController.class).disabled(id)).withSelfRel());
        return new ResponseEntity("Feedback [" + feedbackResponseDto.getId() + "] " + feedbackResponseDto.getName() + " disabled with success.", HttpStatus.OK);
    }
}
