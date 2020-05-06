package com.yang.shequ.provider;

import com.alibaba.fastjson.JSON;
import com.yang.shequ.dto.AccessTokenDTO;
import com.yang.shequ.dto.GithubUser;
import com.yang.shequ.model.User;
import okhttp3.*;
import org.springframework.stereotype.Component;


import java.io.IOException;

/**
 * Created by ywq on 2020/04/24
 */
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO),mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string =  response.body().string();
            String token = string.split("&")[0].split("=")[1];
            System.out.println(string);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        GithubUser  githubUser = new GithubUser();
        githubUser.setId((long) 999);
        githubUser.setName("小强");
        githubUser.setBio(accessToken);
        return githubUser;

//        OkHttpClient client = new OkHttpClient();
//        String url = "https://api.github.com/user?access_token=" + accessToken;
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        try {
//            Response response = client.newCall(request).execute();
//            String string =  response.body().string();
//            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
//            return githubUser;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return  null;
    }
}
