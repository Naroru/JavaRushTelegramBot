package com.github.Naroru.JavaRushTelegramBot.clients.postClient;

import com.github.Naroru.JavaRushTelegramBot.clients.dto.PostInfo;

import java.util.List;

public interface JavaRushPostClient {

    List<PostInfo> findNewPosts(Integer groupId, Integer lastPostId);

}
