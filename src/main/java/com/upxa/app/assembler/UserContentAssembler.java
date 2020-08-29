package com.upxa.app.assembler;

import com.upxa.app.domain.entity.UserContent;
import com.upxa.app.dto.UserContentDTO;

public class UserContentAssembler {
    public UserContent toDomainObject(UserContentDTO userContentDTO){
        UserContent userContentDomain = new UserContent();
        userContentDomain.setFiles(userContentDTO.getFiles());
        return userContentDomain;
    }

    public UserContentDTO fromDomainObject(UserContent userContentDomain){
        UserContentDTO userContentDTO = new UserContentDTO();
        userContentDTO.setFiles(userContentDomain.getFiles());
        userContentDTO.setContentId(userContentDomain.getContentId());
        return userContentDTO;
    }
}
