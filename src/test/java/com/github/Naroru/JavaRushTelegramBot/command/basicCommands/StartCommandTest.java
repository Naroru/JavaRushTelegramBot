package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.command.Command;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Unit testing for StartCommand")
class StartCommandTest extends AbstractCommandTest{

    @Override
     String getCommandMessage() {
        return StartCommand.START_MESSAGE;
    }

    @Override
     Command getCommand() {
        return new StartCommand(sendMessageService, telegramUserService);
    }
}