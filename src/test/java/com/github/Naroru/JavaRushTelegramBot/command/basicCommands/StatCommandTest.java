package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.command.Command;

import static com.github.Naroru.JavaRushTelegramBot.command.basicCommands.StatCommand.STAT_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;

class StatCommandTest extends AbstractCommandTest{

    @Override
    String getCommandMessage() {
        return String.format(STAT_MESSAGE, 0);
    }

    @Override
    Command getCommand() {
        return new StatCommand(sendMessageService,telegramUserService);
    }
}