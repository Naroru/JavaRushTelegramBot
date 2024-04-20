package com.github.Naroru.JavaRushTelegramBot.service;

import com.github.Naroru.JavaRushTelegramBot.javarushclient.dto.GroupDiscussionInfo;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.GroupSubscribtion;

public interface GroupSubsciptionService {

    GroupSubscribtion save(String chatId, GroupDiscussionInfo info );

}
