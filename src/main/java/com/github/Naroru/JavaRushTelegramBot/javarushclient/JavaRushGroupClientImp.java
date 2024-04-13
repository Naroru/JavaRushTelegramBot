package com.github.Naroru.JavaRushTelegramBot.javarushclient;

import com.github.Naroru.JavaRushTelegramBot.javarushclient.dto.GroupCountRequestArgs;
import com.github.Naroru.JavaRushTelegramBot.javarushclient.dto.GroupDiscussionInfo;
import com.github.Naroru.JavaRushTelegramBot.javarushclient.dto.GroupInfo;
import com.github.Naroru.JavaRushTelegramBot.javarushclient.dto.GroupRequestArgs;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class JavaRushGroupClientImp implements JavaRushGroupClient{


    RestTemplate restTemplate = new RestTemplate();

    private final String javarushAPIGroupPath;

    public JavaRushGroupClientImp(@Value("${javarush.api.path}") String javarushApiPath) {
        javarushAPIGroupPath = javarushApiPath + "/groups";
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


        return Unirest.get("https://javarush.com/api/1.0/rest/groups/count")
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
}
