package com.github.Naroru.JavaRushTelegramBot.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class StatDto {

    private final int activeUsers;
    private final int inactiveUsers;
    private final List<GroupStatDto> groupStat;
    private final double averageGroupCountByUser;

    public StatDto(int activeUsers, int inactiveUsers, List<GroupStatDto> groupStat, double averageGroupCountByUser) {
        this.activeUsers = activeUsers;
        this.inactiveUsers = inactiveUsers;
        this.groupStat = groupStat;
        this.averageGroupCountByUser = averageGroupCountByUser;
    }
}
