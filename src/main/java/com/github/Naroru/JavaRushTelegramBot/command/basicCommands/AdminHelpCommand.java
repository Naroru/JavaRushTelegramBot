package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.command.Command;
import com.github.Naroru.JavaRushTelegramBot.command.CommandName;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AdminHelpCommand implements Command {

    private final SendMessageService sendMessageService;
    public static final String ADMIN_HELP_MESSAGE = String.format("""
                        Список админских команд:

                        %s  - получить статистику по количеству пользователей\s
                        """
            , CommandName.STAT.getCommandName());
    public AdminHelpCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {



        sendMessageService.sendMessage(update.getMessage().getChatId(), ADMIN_HELP_MESSAGE);

    }
}
