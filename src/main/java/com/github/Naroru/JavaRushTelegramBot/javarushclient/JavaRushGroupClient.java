package com.github.Naroru.JavaRushTelegramBot.javarushclient;

import com.github.Naroru.JavaRushTelegramBot.javarushclient.dto.GroupCountRequestArgs;
import com.github.Naroru.JavaRushTelegramBot.javarushclient.dto.GroupDiscussionInfo;
import com.github.Naroru.JavaRushTelegramBot.javarushclient.dto.GroupInfo;
import com.github.Naroru.JavaRushTelegramBot.javarushclient.dto.GroupRequestArgs;

import java.util.List;

public interface JavaRushGroupClient{

 List<GroupInfo> getGroupList(GroupRequestArgs requestArgs);

 List<GroupDiscussionInfo> getGroupDiscussionList(GroupRequestArgs requestArgs);

 Integer getGroupCount(GroupCountRequestArgs requestArgs);

 GroupDiscussionInfo getGroupByID(int id);
}
