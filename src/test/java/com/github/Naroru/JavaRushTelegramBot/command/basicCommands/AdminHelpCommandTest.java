package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.command.Command;

import static org.junit.jupiter.api.Assertions.*;

class AdminHelpCommandTest extends AbstractCommandTest{


    @Override
    String getCommandMessage() {
        return AdminHelpCommand.ADMIN_HELP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new AdminHelpCommand(sendMessageService);
    }

}