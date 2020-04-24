package com.yang.shequ.controller;

import com.yang.shequ.dto.AccessTokenDTO;
import com.yang.shequ.dto.GithubUser;
import com.yang.shequ.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * created by ywq on 2020/04/24
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secrect}")
    private String clientSecrect;

    @Value("${github.refirect.uri}")
    private String refirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")  String code,
                           @RequestParam(name="state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecrect);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(refirectUri);
        accessTokenDTO.setState(state);
        String accessToken =  githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser.getName());
        return "index";
    }
}
