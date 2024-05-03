package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.command.Command;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.TelegramUser;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import com.github.Naroru.JavaRushTelegramBot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.stream.Collectors;

public class GetGroupListCommand implements Command {

    public static  final String MESSAGE_TEXT = "Вот список всех твоих подписок: \n\n";
    private final SendMessageService sendMessageService;
    private final TelegramUserService telegramUserService;

    public GetGroupListCommand(SendMessageService sendMessageService, TelegramUserService telegramUserService) {
        this.sendMessageService = sendMessageService;
        this.telegramUserService = telegramUserService;
    }


    @Override
    public void execute(Update update) {

        Long chatID = update.getMessage().getChatId();
        //todo make handling exception
        TelegramUser user = telegramUserService.findByChatId(chatID)
                .orElseThrow(NotFoundException::new);

        String groupList = user.getGroups()
                .stream()
                .map(group -> String.format("%s, ID = %d\n",group.getTitle(), group.getId()))
                .collect(Collectors.joining());

        String message = String.format(MESSAGE_TEXT +"%s",groupList);

        sendMessageService.sendMessage(chatID, message);
    }
}
