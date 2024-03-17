package com.github.Naroru.JavaRushTelegramBot.command;

import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class NoCommand implements Command {

    private final SendMessageService sendMessageService;

    private final String NO_MESSAGE = "Я поддерживаю команды, начинающиеся со слеша(/).\n"
            + "Чтобы посмотреть список команд введите /help";

    public NoCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }


    @Override
    public void execute(Update update) {
        sendMessageService.sendMessage(update.getMessage().getChatId().toString(), NO_MESSAGE);
    }
}
