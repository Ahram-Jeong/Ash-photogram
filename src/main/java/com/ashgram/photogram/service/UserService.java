package com.ashgram.photogram.service;

import com.ashgram.photogram.domain.subscribe.SubscribeRepository;
import com.ashgram.photogram.domain.user.User;
import com.ashgram.photogram.domain.user.UserRepository;
import com.ashgram.photogram.handler.ex.CustomApiException;
import com.ashgram.photogram.handler.ex.CustomException;
import com.ashgram.photogram.handler.ex.CustomValidationApiException;
import com.ashgram.photogram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // ******************** 프로필 ********************
    @Transactional(readOnly = true) // 읽기 전용으로, 변경 감지를 하지 않음
    public UserProfileDto showUser(long pageUserId, long principalId) {
        UserProfileDto userProfileDto = new UserProfileDto();

        User userEntity = userRepository.findById(pageUserId).orElseThrow(()->{ // id 값이 없으면 예외 발생
            throw new CustomException("요청하신 프로필이 존재하지 않습니다.");
        });

        userProfileDto.setUser(userEntity);
        userProfileDto.setPageOwnerState(pageUserId == principalId); // true or false
        userProfileDto.setImageCount(userEntity.getImages().size());
        // 구독 정보 추가
        int subscribeState = subscribeRepository.mSubscribeState(pageUserId, principalId);
        long subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);
        userProfileDto.setSubscribeState(subscribeState == 1);
        userProfileDto.setSubscribeCount(subscribeCount);

        // profile 페이지의 image 좋아요 수 출력 로직 추가 (userProfileDto가 아닌 userEntity 내부를 수정하면 됨)
        userEntity.getImages().forEach((image) -> { // userEntity 내부의 images
            image.setLikeCount(image.getLikes().size()); // 각 image 내 likeCount
        });

        return userProfileDto;
    }

    // ******************** 회원 정보 수정 ********************
    @Transactional
    public User modify(long id, User user) {
        // 1. 영속화
        // Optional 처리: .get()대신 .orElseThrow()를 사용하는 것이 바람직
        User userEntity = userRepository.findById(id).orElseThrow(() -> { // id 값이 없으면 예외 발생
            return new CustomValidationApiException("찾을 수 없는 ID입니다.");
        });
        // 2. 영속화 된 obj 수정
        userEntity.setName(user.getName());
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        userEntity.setPassword(encPassword);

        userEntity.setWebsite(user.getWebsite());
        userEntity.setBio(user.getBio());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());
        return userEntity;
    }

    // ******************** 프로필 사진 변경 ********************
    @Value("${file.path}") // application.yml > file > path
    private String uploadFolder;
    @Transactional
    public User updateProfileImage(long principalId, MultipartFile profileImageFile) {
        UUID uuid = UUID.randomUUID(); // 범용 고유 식별자, Universally Unique IDentifier
        String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        // 통신, I/O -> 예외가 발생할 수 있음
        try {
            Files.write(imageFilePath, profileImageFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        User userEntity = userRepository.findById(principalId).orElseThrow(() -> {
            throw new CustomApiException("없는 사용자 입니다.");
        });
        userEntity.setProfileImageUrl(imageFileName);

        return userEntity;
    }
}
