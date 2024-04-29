package com.github.Naroru.JavaRushTelegramBot.clients.dto;

import lombok.Data;

@Data
public class UserDiscussionInfo {

    private boolean isBookmarked;

    private Integer lastTime;

    private Integer newCommentsCount;

}
