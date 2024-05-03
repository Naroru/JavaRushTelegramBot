package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.command.Command;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UnknowCommand implements Command {

    public static final   String UNKNOW_MESSAGE = "Данная команда не распознана. Используйте /help для получения списка" +
            " доступных команд";
    private final SendMessageService sendMessageService;

    public UnknowCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {

        sendMessageService.sendMessage(update.getMessage().getChatId(), UNKNOW_MESSAGE);

    }
}
