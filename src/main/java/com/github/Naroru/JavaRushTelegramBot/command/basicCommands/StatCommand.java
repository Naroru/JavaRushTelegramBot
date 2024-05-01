package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.annotation.AdminCommand;
import com.github.Naroru.JavaRushTelegramBot.command.Command;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.TelegramUser;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import com.github.Naroru.JavaRushTelegramBot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@AdminCommand
public class StatCommand implements Command {

    private final SendMessageService sendMessageService;
    private final TelegramUserService  telegramUserService;
    public final static String STAT_MESSAGE = "Javarush Telegram Bot использует %s человек.";

    public StatCommand(SendMessageService sendMessageService, TelegramUserService telegramUserService) {
        this.sendMessageService = sendMessageService;
        this.telegramUserService = telegramUserService;
    }


    @Override
    public void execute(Update update) {

        int activeUserCount = telegramUserService.retrieveAllActiveUsers().size();

        sendMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format(STAT_MESSAGE, activeUserCount));

    }

}
