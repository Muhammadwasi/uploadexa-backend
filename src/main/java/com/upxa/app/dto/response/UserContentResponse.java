package com.upxa.app.dto.response;

import com.upxa.app.dto.UserContentDTO;

public class UserContentResponse extends Response {
    private UserContentDTO userContentDTO;

    public UserContentDTO getUserContentDTO() {
        return userContentDTO;
    }

    public void setUserContentDTO(UserContentDTO userContentDTO) {
        this.userContentDTO = userContentDTO;
    }
}
