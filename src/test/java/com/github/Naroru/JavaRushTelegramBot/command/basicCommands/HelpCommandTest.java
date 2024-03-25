package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.command.Command;
import org.junit.jupiter.api.DisplayName;


@DisplayName("Unit-testing HelpCommand")
class HelpCommandTest extends AbstractCommandTest{


    @Override
     String getCommandMessage() {
        return HelpCommand.HELP_MESSAGE;
    }

    @Override
     Command getCommand() {
        return new HelpCommand(sendMessageService);
    }
}