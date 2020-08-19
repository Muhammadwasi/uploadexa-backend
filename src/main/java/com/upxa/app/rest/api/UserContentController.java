package com.upxa.app.rest.api;

import com.upxa.app.dto.UserContentDTO;
import com.upxa.app.dto.response.Response;
import com.upxa.app.service.UserContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/storage")
public class UserContentController {
    private UserContentService userContentService;

    @Autowired
    public UserContentController(UserContentService userContentService){
        this.userContentService=userContentService;
    }
    @RequestMapping(value = "/saveUserContent",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = {RequestMethod.POST}
    )
    public Response save(@RequestBody UserContentDTO userContentDTO){
        System.out.println(userContentDTO);
        Response userContentResponse=this.userContentService.save(userContentDTO);
        return userContentResponse;
    }

    @RequestMapping(value = "/getUserContent/{contentId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = {RequestMethod.GET}
    )
    public Response read(@PathVariable String contentId){
        System.out.println(contentId);
        Response userContentResponse=this.userContentService.read(contentId);
        return userContentResponse;
    }
}

