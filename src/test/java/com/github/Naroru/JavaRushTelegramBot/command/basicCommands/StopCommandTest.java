package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.command.Command;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Unit testing for StopCommand")
class StopCommandTest extends AbstractCommandTest{

    @Override
     String getCommandMessage() {
        return StopCommand.STOP_MESSAGE;
    }

    @Override
     Command getCommand() {
        return new StopCommand(sendMessageService, telegramUserService);
    }
}