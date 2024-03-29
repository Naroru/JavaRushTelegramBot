package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.command.Command;

import static org.junit.jupiter.api.Assertions.*;

class StatCommandTest extends AbstractCommandTest{

    @Override
    String getCommandMessage() {
        return StatCommand.STAT_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StatCommand(sendMessageService,telegramUserService);
    }
}