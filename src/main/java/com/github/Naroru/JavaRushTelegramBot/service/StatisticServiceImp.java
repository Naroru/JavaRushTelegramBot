package com.github.Naroru.JavaRushTelegramBot.service;

import com.github.Naroru.JavaRushTelegramBot.dto.GroupStatDto;
import com.github.Naroru.JavaRushTelegramBot.dto.StatDto;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.TelegramUser;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class StatisticServiceImp implements StatiscticService{

    private final TelegramUserService userService;
    private final GroupSubsciptionService subsciptionService;

    public StatisticServiceImp(TelegramUserService userService, GroupSubsciptionService subsciptionService) {
        this.userService = userService;
        this.subsciptionService = subsciptionService;
    }


    @Override
    public StatDto getStatisctic() {

        List<TelegramUser> activeUsers = userService.findAllActiveUsers();
        List<TelegramUser> inactiveUsers = userService.findAllInactiveUsers();

        List<GroupStatDto> groupStat = subsciptionService.findAll()
                .stream()
                .filter(subscribtion -> !isEmpty(subscribtion.getUsers()))
                .map(groupSubscribtion ->
                        new GroupStatDto(groupSubscribtion.getId(), groupSubscribtion.getTitle(), groupSubscribtion.getUsers().size()))
                .toList();

        double averageGroupCountBuUser = (double) activeUsers.stream()
                .mapToInt(user -> user.getGroups().size())
                .sum() / activeUsers.size();

        return new StatDto(activeUsers.size(), inactiveUsers.size(), groupStat, averageGroupCountBuUser);

    }


}
