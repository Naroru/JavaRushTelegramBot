package com.github.Naroru.JavaRushTelegramBot.service;

import com.github.Naroru.JavaRushTelegramBot.javarushclient.dto.GroupDiscussionInfo;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.GroupSubscribtion;

import java.util.Optional;

public interface GroupSubsciptionService {

    GroupSubscribtion save(String chatId, GroupDiscussionInfo info );

    GroupSubscribtion save(GroupSubscribtion groupSubscribtion);

    Optional<GroupSubscribtion> findByID(int id);
}
