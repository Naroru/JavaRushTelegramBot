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

@DisplayName("Unit-testing for CommandContainer")
@ExtendWith(MockitoExtension.class)
class CommandContainerTest {

   /* @Mock
    SendMessageService sendMessageService;

    private CommandContainer commandContainer;

    @BeforeEach
    public void init()
    {
        commandContainer = new CommandContainer(sendMessageService);
    }

    @Test
    public void shouldGetAllTheExistingCommands() {

        //when-then
        Arrays.stream(CommandName.values())
                .forEach(
                        commandName ->
                        {
                            Command command = commandContainer.getCommand(commandName.getCommandName());
                            Assertions.assertNotEquals(UnknowCommand.class,  command.getClass());
                        }

                );
    }

    @Test
    public void shouldReturnUnknownCommand()
    {
        //given
        String unknownCommandName = "/dfsdfsd";

        //when
        Command command = commandContainer.getCommand(unknownCommandName);

        //then
        assertEquals(UnknowCommand.class, command.getClass());
    }*/
}