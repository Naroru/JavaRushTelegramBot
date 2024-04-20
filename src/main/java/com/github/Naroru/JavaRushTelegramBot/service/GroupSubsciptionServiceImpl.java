package com.github.Naroru.JavaRushTelegramBot.service;

import com.github.Naroru.JavaRushTelegramBot.javarushclient.dto.GroupDiscussionInfo;
import com.github.Naroru.JavaRushTelegramBot.repository.GroupSubscriptionRepository;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.GroupSubscribtion;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.TelegramUser;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
@Service
public class GroupSubsciptionServiceImpl implements GroupSubsciptionService {

    private final TelegramUserService telegramUserService;
    private final GroupSubscriptionRepository groupRepository;

    public GroupSubsciptionServiceImpl(TelegramUserService telegramUserService, GroupSubscriptionRepository groupRepository) {
        this.telegramUserService = telegramUserService;
        this.groupRepository = groupRepository;
    }


    @Override
    public GroupSubscribtion save(String chatId, GroupDiscussionInfo info) {
        //не уверен что здесь нужны какие то проверки, что пользователь существует или что
        //если группа существует тогда обновляем
        //TODO add exception handling
        TelegramUser user = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);

        GroupSubscribtion groupSubscribtion;
        Optional<GroupSubscribtion> groupFromDB = groupRepository.findById(info.getId());

        if (groupFromDB.isPresent()) {

            groupSubscribtion = groupFromDB.get();

            Optional<TelegramUser> first = groupSubscribtion.getUsers().stream()
                    .filter(user1 -> user1.getChatId().equalsIgnoreCase(chatId))
                    .findFirst();

            if (first.isEmpty())
                groupSubscribtion.addUser(user);
        } else {

            groupSubscribtion = new GroupSubscribtion();
            groupSubscribtion.setTitle(info.getTitle());
            groupSubscribtion.setId(info.getId());
            groupSubscribtion.setUsers(List.of(user));

        }

        return groupRepository.save(groupSubscribtion);


    }
}
