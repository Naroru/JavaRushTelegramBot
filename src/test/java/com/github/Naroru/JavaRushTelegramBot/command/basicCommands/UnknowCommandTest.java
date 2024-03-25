package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.command.Command;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Unit testing for UnknownCommand")
class UnknowCommandTest extends AbstractCommandTest{

    @Override
     String getCommandMessage() {
        return UnknowCommand.UNKNOW_MESSAGE;
    }

    @Override
     Command getCommand() {
        return new UnknowCommand(sendMessageService);
    }
}