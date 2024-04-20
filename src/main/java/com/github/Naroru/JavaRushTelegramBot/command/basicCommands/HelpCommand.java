package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.command.Command;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.Naroru.JavaRushTelegramBot.command.CommandName.*;

public class HelpCommand implements Command {

    private final SendMessageService sendMessageService;

    public static final String HELP_MESSAGE = String.format("""
                    ✨<b>Доступные команды</b>✨

                    <b>Начать\\закончить работу с ботом</b>
                    %s - начать работу со мной
                    %s - приостановить работу со мной
                    %s - подписаться на группу
                    %s - посмотреть список своих подписок
                    %s - получить статистику по активным пользователям

                    %s - получить помощь в работе со мной
                    """,

            START.getCommandName(),
            STOP.getCommandName(),
            ADD_GROUP_SUB.getCommandName(),
            GET_GROUP_LIST.getCommandName(),
            STAT.getCommandName(),
            HELP.getCommandName());

    public HelpCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        sendMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}
