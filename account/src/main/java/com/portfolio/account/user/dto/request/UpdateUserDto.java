package com.portfolio.account.user.dto.request;

import com.portfolio.core.helpers.validators.ValidEnum;
import com.portfolio.account.user.entity.UserStatus;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateUserDto {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private MultipartFile userImage;
    private String image;

    @ValidEnum(enumClass = UserStatus.class, message = "Please provide a valid status")
    private UserStatus status;

}
