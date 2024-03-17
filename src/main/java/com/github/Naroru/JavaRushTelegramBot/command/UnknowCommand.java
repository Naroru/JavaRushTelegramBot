package com.github.Naroru.JavaRushTelegramBot.command;

import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UnknowCommand implements Command{

    private final  String UNKNOW_MESSAGE = "Данная команда не распознана. Используйте /help для получения списка" +
            " доступных команд";
    private final SendMessageService sendMessageService;

    public UnknowCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {

        sendMessageService.sendMessage(update.getMessage().getChatId().toString(), UNKNOW_MESSAGE);

    }
}
