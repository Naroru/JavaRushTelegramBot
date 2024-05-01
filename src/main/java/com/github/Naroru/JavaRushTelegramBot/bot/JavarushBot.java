package com.github.Naroru.JavaRushTelegramBot.bot;

import com.github.Naroru.JavaRushTelegramBot.command.Command;
import com.github.Naroru.JavaRushTelegramBot.command.CommandContainer;
import com.github.Naroru.JavaRushTelegramBot.command.CommandName;
import com.github.Naroru.JavaRushTelegramBot.clients.groupClient.JavaRushGroupClient;
import com.github.Naroru.JavaRushTelegramBot.service.GroupSubsciptionService;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageServiceImp;
import com.github.Naroru.JavaRushTelegramBot.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class JavarushBot extends TelegramLongPollingBot {

    private final String COMMAND_PREFIX = "/";
    private final CommandContainer commandContainer;


    @Value("${bot.username}")
    private String name;


    public JavarushBot(String botToken,
                       TelegramUserService telegramUserService,
                       JavaRushGroupClient javaRushGroupClient,
                       GroupSubsciptionService groupSubsciptionService,
                       @Value("#{'${bot.admins}'.split(',')}") List<String> admins) {

        super(botToken);
        this.commandContainer = new CommandContainer(
                new SendMessageServiceImp(this),
                telegramUserService,
                javaRushGroupClient,
                groupSubsciptionService,
                admins);

    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()) {

            String message = update.getMessage().getText().trim();
            String username = update.getMessage().getFrom().getUserName();

            if(message.startsWith(COMMAND_PREFIX)) {

                String commandIdentifier = message.split(" ")[0].toLowerCase();
                Command command = commandContainer.getCommand(commandIdentifier, username);
                command.execute(update);

            }
            else
                commandContainer.getCommand(CommandName.NO.getCommandName(),username)
                        .execute(update);
        }
    }


}
