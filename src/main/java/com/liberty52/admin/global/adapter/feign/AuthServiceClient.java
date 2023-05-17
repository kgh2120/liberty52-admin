package com.liberty52.admin.global.adapter.feign;

import com.liberty52.admin.global.adapter.feign.dto.*;
import com.liberty52.admin.service.controller.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@FeignClient(value = "auth", primary = false)
public interface AuthServiceClient {

  @PostMapping(value = "/login")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<AdminLoginResponseDto> login(AdminLoginRequestDto dto);

  @GetMapping(value = "/my")
  AuthProfileDto getAuthProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId);

  @PostMapping("/info")
  Map<String, AuthClientDataResponse> retrieveAuthData(@RequestBody Set<String> ids);

  @PutMapping("/questionReplies/{questionReplyId}")
  void questionReplyModify(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String adminId,
      @RequestHeader("LB-Role") String role,
      @Validated @RequestBody QuestionReplyModifyRequestDto dto,
      @PathVariable String questionReplyId);

  @DeleteMapping("/questionReplies/{questionReplyId}")
  @ResponseStatus(HttpStatus.OK)
  void deleteQuestionReply(@RequestHeader(HttpHeaders.AUTHORIZATION) String adminId,
      @RequestHeader("LB-Role") String role, @PathVariable String questionReplyId);

  @PostMapping("/questionReplies")
  @ResponseStatus(HttpStatus.CREATED)
  void createQuestionReply(@RequestHeader(HttpHeaders.AUTHORIZATION) String adminId,
      @RequestHeader("LB-Role") String role,
      @Validated @RequestBody QuestionReplyCreateRequestDto dto);

  @GetMapping("/all-questions")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<AdminQuestionRetrieveResponse> retrieveAllQuestions(
      @RequestHeader("LB-Role") String role,
      @RequestParam(value = "page", defaultValue = "0") int pageNumber,
      @RequestParam(value = "size", defaultValue = "10") int size);

  @GetMapping("/all-questions/{questionId}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<QuestionDetailResponseDto> retrieveQuestionDetail(
      @RequestHeader("LB-Role") String role, @PathVariable("questionId") String questionId);

  @PutMapping("/notices/{noticeId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
      void noticeModify(@RequestHeader("LB-Role") String role,
      @PathVariable String noticeId,
      @Validated @RequestBody NoticeModifyRequestDto dto);

  @GetMapping("/customer-info")
  @ResponseStatus(HttpStatus.OK)
  CustomerInfoListResponseDto customerInfoListByAdmin(@RequestHeader("LB-Role") String role,
                                                      Pageable pageable);

  @PostMapping("/notices")
  @ResponseStatus(HttpStatus.CREATED)
  public void createNotice(@RequestHeader("LB-Role") String role,
      @Validated @RequestBody NoticeCreateRequestDto dto);

  @GetMapping("/admin/notices")
  @ResponseStatus(HttpStatus.OK)
  NoticeRetrieveResponse retrieveNotices(@RequestHeader("LB-Role") String role,
      Pageable pageable);

  @GetMapping("/admin/notices/{noticeId}")
  @ResponseStatus(HttpStatus.OK)
  NoticeDetailRetrieveResponse retrieveNoticeDetail(@RequestHeader("LB-Role") String role,
      @PathVariable String noticeId);

  @DeleteMapping("/notices/{noticeId}")
  void deleteNotice(@RequestHeader("LB-Role") String role, @PathVariable String noticeId);
}
