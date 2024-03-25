package com.github.Naroru.JavaRushTelegramBot.command;

import com.github.Naroru.JavaRushTelegramBot.command.basicCommands.UnknowCommand;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CommandContainer unit-testing")
@ExtendWith(MockitoExtension.class)
class CommandContainerTest {

    @Mock
    private SendMessageService sendMessageService;

    private CommandContainer commandContainer;

    @BeforeEach
    public void init()
    {
        commandContainer = new CommandContainer(sendMessageService);
    }

    @Test
    public void allCommandsExists()
    {
        //when-then
        Arrays.stream(CommandName.values())
                .forEach(
                        value ->
                        {
                            String commandName = value.getCommandName();
                            Command command = commandContainer.getCommand(commandName);
                            assertNotEquals(UnknowCommand.class, command.getClass());
                        }
                );
    }

    @Test
    public void ShouldBeUnknownCommand()
    {
        //given
        String commandName = "dfgdfg";

        //when
        Command command = commandContainer.getCommand(commandName);

        //then

        assertEquals(UnknowCommand.class, command.getClass());
    }


}