package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.repository.entity.GroupSubscribtion;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.TelegramUser;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import com.github.Naroru.JavaRushTelegramBot.service.TelegramUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("unit test for command GetGroupListCommand")
class GetGroupListCommandTest {


    @Test
    public void shouldProperlySendMessage()
    {
        //given
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        SendMessageService sendMessageService = Mockito.mock(SendMessageService.class);

        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);

        List<GroupSubscribtion> groupsSub = List.of(
                new GroupSubscribtion(1, "g1", 0, null),
                new GroupSubscribtion(2, "g2", 0, null)
        );

        TelegramUser user = new TelegramUser("1", true, groupsSub);
        when(telegramUserService.findByChatId((user.getChatId()))).thenReturn(Optional.of(user));

        when(update.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(Long.valueOf(user.getChatId()));

        GetGroupListCommand command = new GetGroupListCommand(sendMessageService, telegramUserService);

        String collectedGroups = GetGroupListCommand.MESSAGE_TEXT +
                user.getGroups()
                        .stream()
                        .map(group -> String.format("%s, ID = %d\n", group.getTitle(), group.getId()))
                        .collect(Collectors.joining());

        //when
        command.execute(update);

        //then
        verify(sendMessageService).sendMessage(user.getChatId(), collectedGroups);
    }
}