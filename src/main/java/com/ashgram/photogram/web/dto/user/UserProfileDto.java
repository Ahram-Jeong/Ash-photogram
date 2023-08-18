package com.ashgram.photogram.web.dto.user;

import com.ashgram.photogram.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private boolean pageOwnerState;
    private int imageCount;
    private User user;
}
