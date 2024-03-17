package com.github.Naroru.JavaRushTelegramBot.command;

import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StopCommand implements  Command{

   private  final String STOP_MESSAGE = "Деактивировал все ваши подписки \uD83D\uDE1F.";
    private final SendMessageService sendMessageService;

    public StopCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        sendMessageService.sendMessage(update.getMessage().getChatId().toString(),STOP_MESSAGE);
    }
}
