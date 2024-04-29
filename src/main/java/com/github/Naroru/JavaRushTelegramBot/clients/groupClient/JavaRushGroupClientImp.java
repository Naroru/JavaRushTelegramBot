package com.github.Naroru.JavaRushTelegramBot.clients.groupClient;

import com.github.Naroru.JavaRushTelegramBot.clients.dto.GroupCountRequestArgs;
import com.github.Naroru.JavaRushTelegramBot.clients.dto.GroupDiscussionInfo;
import com.github.Naroru.JavaRushTelegramBot.clients.dto.GroupInfo;
import com.github.Naroru.JavaRushTelegramBot.clients.dto.GroupRequestArgs;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class JavaRushGroupClientImp implements JavaRushGroupClient{


    RestTemplate restTemplate = new RestTemplate();

    private final String javarushAPIGroupPath;
    private final String javarushAPIPostPath;


    public JavaRushGroupClientImp(@Value("${javarush.api.path}") String javarushApiPath) {
        javarushAPIGroupPath = javarushApiPath + "/groups";
        javarushAPIPostPath = javarushApiPath + "/posts";
    }

    @Override
    public List<GroupInfo> getGroupList(GroupRequestArgs requestArgs) {

        return Unirest.get(javarushAPIGroupPath)
                .queryString(requestArgs.populateQueries())
                .asObject(new GenericType<List<GroupInfo>>() {
                })
                .getBody();

    }



    @Override
    public List<GroupDiscussionInfo> getGroupDiscussionList(GroupRequestArgs requestArgs) {

        return Unirest.get(javarushAPIGroupPath)
                .queryString(requestArgs.populateQueries())
                .asObject(new GenericType<List<GroupDiscussionInfo>>() {
                })
                .getBody();
    }



    @Override
    public Integer getGroupCount(GroupCountRequestArgs requestArgs) {


        return Unirest.get(String.format("%s%s",javarushAPIGroupPath,"/count"))
                .queryString(requestArgs.populateQueries())
                .asObject(Integer.class)
                .getBody();

    }



    @Override
    public GroupDiscussionInfo getGroupByID(int id) {

        return Unirest.get(String.format("%s/group%d", javarushAPIGroupPath, id))
                .asObject(GroupDiscussionInfo.class)
                .getBody();

    }

    @Override
    public int getLastPostID(int groupID) {

        return Unirest.get(javarushAPIPostPath)
                .queryString("groupKid", groupID)
                .queryString("order", "NEW")
                .queryString("limit", 1)
                .asObject(Integer.class)
                .getBody();

    }
}
