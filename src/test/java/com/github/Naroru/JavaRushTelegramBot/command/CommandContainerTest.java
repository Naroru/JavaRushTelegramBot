package com.github.Naroru.JavaRushTelegramBot.command;

import com.github.Naroru.JavaRushTelegramBot.command.basicCommands.UnknowCommand;
import com.github.Naroru.JavaRushTelegramBot.clients.groupClient.JavaRushGroupClient;
import com.github.Naroru.JavaRushTelegramBot.service.GroupSubsciptionService;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import com.github.Naroru.JavaRushTelegramBot.service.StatiscticService;
import com.github.Naroru.JavaRushTelegramBot.service.TelegramUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CommandContainer unit-testing")
@ExtendWith(MockitoExtension.class)
class CommandContainerTest {

    @Mock
    private SendMessageService sendMessageService;

    @Mock
    private TelegramUserService telegramUserService;

    @Mock
    private JavaRushGroupClient javaRushGroupClient;

    @Mock
    private GroupSubsciptionService groupSubsciptionService;

    @Mock
    private StatiscticService statiscticService;

    private CommandContainer commandContainer;

    @BeforeEach
    public void init()
    {
        commandContainer = new CommandContainer(sendMessageService, telegramUserService, javaRushGroupClient,groupSubsciptionService,statiscticService, List.of("username"));
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
                            Command command = commandContainer.getCommand(commandName,"username");
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
        Command command = commandContainer.getCommand(commandName, "username");

        //then

        assertEquals(UnknowCommand.class, command.getClass());
    }


}