package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.command.Command;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.TelegramUser;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import com.github.Naroru.JavaRushTelegramBot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StopCommand implements Command {

    public static final  String STOP_MESSAGE = "Деактивировал все ваши подписки \uD83D\uDE1F.";
    private final SendMessageService sendMessageService;
    private final TelegramUserService telegramUserService;


    public StopCommand(SendMessageService sendMessageService, TelegramUserService telegramUserService) {
        this.sendMessageService = sendMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {

        String chatID = update.getMessage().getChatId().toString();

        telegramUserService.findByChatId(chatID)
                        .ifPresent(
                                telegramUser -> {
                                    telegramUser.setActive(false);
                                    telegramUserService.save(telegramUser);
                                }
                        );
        sendMessageService.sendMessage(chatID,STOP_MESSAGE);
    }
}
