package com.upxa.app.service;

import com.upxa.app.assembler.UserContentAssembler;
import com.upxa.app.domain.entity.UserContent;
import com.upxa.app.domain.entity.repository.IUserContentRepository;
import com.upxa.app.dto.UserContentDTO;
import com.upxa.app.dto.response.UserContentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserContentService {
    @Autowired
    IUserContentRepository userContentRepository;

    public UserContentResponse save(UserContentDTO userContentDTO){
        UserContentResponse userContentResponse=new UserContentResponse();
        UserContentAssembler userContentAssembler = new UserContentAssembler();
        UserContent userContentDomain=userContentAssembler.toDomainObject(userContentDTO);

        userContentRepository.save(userContentDomain);
        userContentResponse.setUserContentDTO(userContentAssembler.fromDomainObject(userContentDomain));
        return userContentResponse;
    }

    public UserContentResponse read(String contentId){
        UserContentResponse userContentResponse=new UserContentResponse();
        UserContentAssembler userContentAssembler = new UserContentAssembler();
        UserContentDTO userContentDTO=null;

        Optional<UserContent> userContentOptional=userContentRepository.findById(contentId);
        if(userContentOptional.isPresent()){
            UserContent userContentDomain=userContentOptional.get();
            userContentDTO=userContentAssembler.fromDomainObject(userContentDomain);
        }
        userContentResponse.setUserContentDTO(userContentDTO);
        return userContentResponse;
    }
}

