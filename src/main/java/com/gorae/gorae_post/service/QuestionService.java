package com.gorae.gorae_post.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gorae.gorae_post.common.exception.NotFound;
import com.gorae.gorae_post.domain.dto.question.*;
import com.gorae.gorae_post.domain.entity.Question;
import com.gorae.gorae_post.domain.entity.UserInfo;
import com.gorae.gorae_post.domain.dto.user.UserInfoDto;
import com.gorae.gorae_post.domain.repository.QuestionRepository;
import com.gorae.gorae_post.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Transactional
    public Map<String, Object> mapContent(String contentJson) throws JsonProcessingException {
        // 저장된 String block 을 역직렬화 하는 함수
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(contentJson, new TypeReference<>() {
        });
    }

    @Transactional
    public Long create(QuestionCreateDto questionCreateDto, String userId) throws AccessDeniedException {
        Question question = questionCreateDto.toEntity(userId);
        UserInfo userInfo = userRepository.findById(userId)
                .orElseThrow(() -> new AccessDeniedException("인증되지 않았습니다."));
        questionRepository.save(question);
        return question.getId();
    }

    @Transactional
    public void update(QuestionUpdateDto questionUpdateDto, String userId) throws AccessDeniedException, JsonProcessingException {
        Long questionId = questionUpdateDto.getQuestionId();
        ObjectMapper mapper = new ObjectMapper();
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFound("존재하지 않는 글입니다."));
        if (!question.getUserId().equals(userId)) {
            throw new AccessDeniedException("본인 글만 수정이 가능합니다.");
        }

        question.setTitle(questionUpdateDto.getTitle());
        question.setContentJson(mapper.writeValueAsString(questionUpdateDto.getContent()));
        question.setUpdateAt(LocalDateTime.now());
        questionRepository.save(question);
    }

    @Transactional
    public void delete(Long questionId, String userId) throws AccessDeniedException {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFound("존재하지 않는 글입니다."));
        if (!question.getUserId().equals(userId)) {
            throw new AccessDeniedException("본인 글만 삭제가 가능합니다.");
        }
        if (!question.getCommentList().isEmpty()) {
            throw new AccessDeniedException("답변이 달린 글은 삭제가 불가능합니다.");
        }
        question.setDisplay(false);
        questionRepository.save(question);
    }

    private QuestionOverviewDto convertOverviewDto(Question question, String keyword) throws JsonProcessingException {
        String userId = question.getUserId();

        // display True 인 question 만 인자로 받기 때문에 예외가 발생하지는 않는다.
        UserInfo userInfo = userRepository.findById(userId)
                .orElseThrow(() -> new NotFound(""));

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(userInfo.getUserId())
                .userName(userInfo.getUserName())
                .profileImgUrl(userInfo.getProfileImgUrl())
                .userBadge(userInfo.getUserBadge())
                .likeBadge(userInfo.getLikeBadge())
                .build();

        // Question Entity class 인스턴스를 질문 미리보기 타입으로 변경해주는 함수
        return QuestionOverviewDto.builder()
                .keyword(keyword == null ? "" : keyword)
                .questionId(question.getId())
                .userInfoDto(userInfoDto)
                .title(question.getTitle())
                .previewContent(mapContent(question.getContentJson()))
                .writer(question.getUserId())
                .build();
    }

//    private List<CommentDto> convertToCommentDtoList(Question question, UserInfoDto userInfoDto) {
//        List<Comment> commentList = question.getCommentList();
//        List<CommentDto> commentDtoList = new ArrayList<>();
//        for (Comment comment : commentList) {
//            CommentDto dto = CommentDto.builder()
//                    .commentId(comment.getId())
//                    .commentContent(comment.getCommentContent())
//                    .updateAt(comment.getUpdateAt())
//                    .likeCount(comment.getLikeCount())
//                    .adopt(comment.isAdopt())
//                    .userInfoDto(userInfoDto)
//                    .build();
//            commentDtoList.add(dto);
//        }
//        return commentDtoList;
//    }

    @Transactional
    public QuestionListDto overview(int page, int size, String keyword, String sort, String order) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, sort));

        Page<Question> questionPage = questionRepository.findByDisplayTrue(pageable);

        List<QuestionOverviewDto> overviewDtos = questionPage.getContent()
                .stream()
                .map(question -> {
                    try {
                        return convertOverviewDto(question, keyword);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        QuestionListDto dto = new QuestionListDto(
                overviewDtos,
                page,
                size,
                questionPage.getTotalPages(),
                questionPage.getTotalElements()
        );
        return dto;
    }

    @Transactional
    public QuestionListDto search(int page, int size, String keyword, String sort, String order) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, sort));

        Page<Question> questionPage = questionRepository.findByDisplayTrueAndTitleContainingOrContentJsonContaining(keyword, keyword, pageable);

        List<QuestionOverviewDto> overviewDtos = questionPage.getContent()
                .stream()
                .map(question -> {
                    try {
                        return convertOverviewDto(question, keyword);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        QuestionListDto dto = new QuestionListDto(
                overviewDtos,
                page,
                size,
                questionPage.getTotalPages(),
                questionPage.getTotalElements()
        );
        return dto;
    }

    @Transactional
    public QuestionDetailDto detail(Long questionId, String userId) throws JsonProcessingException {
        // DB에 존재하지 않는 questionId 조회시
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFound("존재하지 않거나 삭제된 글입니다."));

        // DB에 남아있지만 display 가 False 인 questionId 조회시
        if (!question.getDisplay()) {
            throw new NotFound("존재하지 않거나 삭제된 글입니다.");
        }

        // 유저 정보 조회
        UserInfo userInfo = userRepository.findById(question.getUserId())
                .orElseThrow(() -> new NotFound("존재하지 않거나 삭제된 글입니다."));

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(userInfo.getUserId())
                .userName(userInfo.getUserName())
                .profileImgUrl(userInfo.getProfileImgUrl())
                .userBadge(userInfo.getUserBadge())
                .likeBadge(userInfo.getLikeBadge())
                .build();

        // viewCount 증가
        // TODO : 본인글 조회시 viewCount 증가하지 않도록 하는 로직
        Integer viewCount = question.getViewCount();
        question.setViewCount(viewCount + 1);
        questionRepository.save(question);

        boolean author = false;
        if (userId != null && userId.equals(userInfoDto.getUserId())) {
            author = true;
        }

        return QuestionDetailDto.builder()
                .questionId(question.getId())
                .title(question.getTitle())
                .detailContent(mapContent(question.getContentJson()))
                .userInfo(userInfoDto)
                .updateAt(question.getUpdateAt())
                .isAuthor(author)
                .viewCount(question.getViewCount())
                .build();
    }

    @Transactional
    public MyQuestionListDto myDetail(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        UserInfo userInfo = userRepository.findById(userId)
                .orElseThrow(() -> new NotFound("로그인이 필요한 서비스입니다."));
        Page<Question> myPage = questionRepository.findByUserIdAndDisplayTrue(userInfo.getUserId(), pageable);

        List<MyQuestionDto> myQuestionList = myPage.getContent()
                .stream()
                .map(
                        question -> {
                            try{
                                return MyQuestionDto.builder()
                                        .title(question.getTitle())
                                        .questionId(question.getId())
                                        .build();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                ).toList();
        MyQuestionListDto myQuestion = new MyQuestionListDto(
                myQuestionList,
                page,
                size,
                myPage.getTotalPages(),
                myPage.getTotalElements()
        );
        return myQuestion;
    }

}
