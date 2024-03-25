package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.command.Command;
import com.github.Naroru.JavaRushTelegramBot.command.basicCommands.AbstractCommandTest;
import com.github.Naroru.JavaRushTelegramBot.command.basicCommands.NoCommand;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Unit testing for NoCommand")
class NoCommandTest extends AbstractCommandTest {


    @Override
     String getCommandMessage() {
        return NoCommand.NO_MESSAGE;
    }

    @Override
     Command getCommand() {
        return new NoCommand(sendMessageService);
    }
}