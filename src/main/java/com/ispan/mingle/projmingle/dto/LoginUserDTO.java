package com.ispan.mingle.projmingle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserDTO {

    private String userId;

    private String username;

    private Boolean isAuthenticated;
    
}
