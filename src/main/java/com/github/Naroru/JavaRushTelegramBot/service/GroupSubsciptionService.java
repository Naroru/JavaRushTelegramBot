package com.github.Naroru.JavaRushTelegramBot.service;

import com.github.Naroru.JavaRushTelegramBot.clients.dto.GroupDiscussionInfo;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.GroupSubscribtion;

import java.util.List;
import java.util.Optional;

public interface GroupSubsciptionService {

    GroupSubscribtion save(Long chatId, GroupDiscussionInfo info );

    GroupSubscribtion save(GroupSubscribtion groupSubscribtion);

    Optional<GroupSubscribtion> findByID(int id);

    List<GroupSubscribtion> findAll();
}
