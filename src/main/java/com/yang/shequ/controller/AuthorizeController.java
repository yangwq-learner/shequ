package com.yang.shequ.controller;

import com.yang.shequ.dto.AccessTokenDTO;
import com.yang.shequ.dto.GithubUser;
import com.yang.shequ.mapper.UserMapper;
import com.yang.shequ.model.User;
import com.yang.shequ.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * created by ywq on 2020/04/24
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper usermapper;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secrect}")
    private String clientSecrect;

    @Value("${github.refirect.uri}")
    private String refirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")  String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecrect);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(refirectUri);
        accessTokenDTO.setState(state);
        String accessToken =  githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
//        System.out.println(githubUser.getName());
        if(githubUser != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            usermapper.insert(user);
            response.addCookie(new Cookie("token",token));
//            request.getSession().setAttribute("user", githubUser);
            return "redirect:/";
            //登录成功， 写cookie 和 session
        }else{
            return "redirect:/";
            //登录失败，重新登录
        }
    }
}
