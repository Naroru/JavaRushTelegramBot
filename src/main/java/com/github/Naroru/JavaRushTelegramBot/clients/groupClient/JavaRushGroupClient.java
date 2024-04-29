package com.github.Naroru.JavaRushTelegramBot.clients.groupClient;

import com.github.Naroru.JavaRushTelegramBot.clients.dto.*;

import java.util.List;

public interface JavaRushGroupClient{

 List<GroupInfo> getGroupList(GroupRequestArgs requestArgs);

 List<GroupDiscussionInfo> getGroupDiscussionList(GroupRequestArgs requestArgs);

 Integer getGroupCount(GroupCountRequestArgs requestArgs);

 GroupDiscussionInfo getGroupByID(int id);

 int getLastPostID(int groupID);
}
