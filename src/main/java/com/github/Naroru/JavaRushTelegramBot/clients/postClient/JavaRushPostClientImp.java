package com.github.Naroru.JavaRushTelegramBot.clients.postClient;

import com.github.Naroru.JavaRushTelegramBot.clients.dto.PostInfo;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JavaRushPostClientImp implements JavaRushPostClient{

    private final String javarushAPIPostPath;

    public JavaRushPostClientImp(@Value("${javarush.api.path}") String javarushAPIPostPath) {
        this.javarushAPIPostPath = javarushAPIPostPath +"/posts";
    }


    @Override
    public List<PostInfo> findNewPosts(Integer groupId, Integer lastPostId) {

        List<PostInfo>  lastPosts = Unirest.get(javarushAPIPostPath)
                .queryString("order", "NEW")
                .queryString("groupKid", groupId)
                .queryString("limit", 10)
                .asObject(new GenericType<List<PostInfo>>() {
                })
                .getBody();

        List<PostInfo> newPosts = new ArrayList<>();

        for(PostInfo post : lastPosts)
        {
            if(lastPostId == post.getId())
                return newPosts;

            newPosts.add(post);
        }

        return newPosts;

    }

}
