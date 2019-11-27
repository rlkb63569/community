package com.baizhi.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baizhi.dto.AccessTokenDto;
import com.baizhi.dto.GithubUser;
import okhttp3.*;

import java.io.IOException;

public class GitHubProvider {

    public static String getAccessToken(AccessTokenDto accessTokenDto) throws IOException {

        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSONObject.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        request.header("application/json;charset=utf-8");
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String access_token1 = string.split("&")[0];
            String access_token=access_token1.split("=")[1];
            return access_token;
        }

    }

    public static GithubUser getUser(String accessToken) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        request.header("application/json");
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
