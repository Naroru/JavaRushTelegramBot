package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.command.Command;
import com.github.Naroru.JavaRushTelegramBot.command.CommandName;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.GroupSubscribtion;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.TelegramUser;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import com.github.Naroru.JavaRushTelegramBot.service.TelegramUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("unit-testing command DeleteGroupSubCommand")
class DeleteGroupSubCommandTest {

    private SendMessageService sendMessageService;
    private TelegramUserService telegramUserService;
    private TelegramUser user;
    private final static Long CHAT_ID =  123L;

    private Command command;


    @BeforeEach
    public void init()
    {
        sendMessageService = Mockito.mock(SendMessageService.class);
        telegramUserService = Mockito.mock(TelegramUserService.class);

        user = new TelegramUser();
        user.setChatId(CHAT_ID.toString());

        when(telegramUserService.findByChatId(CHAT_ID.toString())).thenReturn(Optional.ofNullable(user));

        command = new DeleteGroupSubCommand(sendMessageService,telegramUserService);

    }

    @DisplayName("Should send a message with info that there are not any subscription")
    @Test
    public void shouldSendMessageWithNoActiveSub()
    {

        //given

        Update update = AbstractCommandTest.preparedUpdate(CHAT_ID, CommandName.DELETE_GROUP_SUB.getCommandName());

        user.setGroups(new ArrayList<>());

        when(telegramUserService.findByChatId(CHAT_ID.toString()))
                .thenReturn(Optional.of(user));

        String expectedMessage = String.format("""
                        Для удаления подписки используй команду %s ID_группы.

                        Список твоих активных групп:
                        
                        У вас нет активных подписок""",CommandName.DELETE_GROUP_SUB.getCommandName());

        //when
        command.execute(update);

        //then
        verify(sendMessageService,only()).sendMessage(CHAT_ID.toString(),expectedMessage);

    }

    @Test
    public void shouldProperlySendMessageWithUserSubscription()
    {
        //given

        Update update = AbstractCommandTest.preparedUpdate(CHAT_ID,CommandName.DELETE_GROUP_SUB.getCommandName());

        GroupSubscribtion g1 = new GroupSubscribtion();
        g1.setTitle("g1");
        g1.setId(1);

        GroupSubscribtion g2 = new GroupSubscribtion();
        g1.setTitle("g2");
        g1.setId(2);
        user.setGroups(List.of(g1, g2));

        when(telegramUserService.findByChatId(CHAT_ID.toString()))
                .thenReturn(Optional.of(user));

        String allGroups = user.getGroups().stream()
                .map(group -> String.format("%s, ID - %d \n", group.getTitle(),group.getId()))
                .collect(Collectors.joining());

         String expectedMessage = String.format("""
                        Для удаления подписки используй команду %s ID_группы.

                        Список твоих активных групп:
                        
                        %s""",CommandName.DELETE_GROUP_SUB.getCommandName(),allGroups);

        //when
        command.execute(update);

        //then
        verify(sendMessageService,only()).sendMessage(CHAT_ID.toString(),expectedMessage);

    }

    @Test
    public void shouldSendMessageGroupIDIncorrect() {

        //given
        String incorrectID = "asd";
        Update update = AbstractCommandTest.preparedUpdate(CHAT_ID,
                String.format("%s %s",CommandName.DELETE_GROUP_SUB.getCommandName(), incorrectID));

        //when
        command.execute(update);

        //then
        verify(sendMessageService,only()).sendMessage(CHAT_ID.toString(), "Указанный ID группы некорректный. ID может содержать только цифры");
    }

    @Test
    public void shouldSendMessageGroupNotFound() {

        //given
        int id = 11111;
        Update update = AbstractCommandTest.preparedUpdate(CHAT_ID,
                String.format("%s %d", CommandName.DELETE_GROUP_SUB.getCommandName(), id));

        GroupSubscribtion g1 = new GroupSubscribtion();
        g1.setTitle("g1");
        g1.setId(1);

        user.setGroups(List.of(g1));

        String userGroups = user.getGroups().stream()
                .map(group -> String.format("%s, ID - %d \n", group.getTitle(),group.getId()))
                .collect(Collectors.joining());

        String expectedMessage =  String.format("Указанная группа не найдена, проверьте правильность ID, " +
                "ваши текущие подписки на группы: \n" +
                "%s", userGroups);
        //when
        command.execute(update);

        //then
        verify(sendMessageService, only()).sendMessage(CHAT_ID.toString(), expectedMessage);

    }

    @Test
    public void ShouldProperlyDeleteGroupSubscription()
    {
        //given
        int gr1_ID = 1;
        int gr2_ID = 2;

        Update update = AbstractCommandTest.preparedUpdate(CHAT_ID,
                String.format("%s %d", CommandName.DELETE_GROUP_SUB.getCommandName(), gr1_ID));

        GroupSubscribtion g1 = new GroupSubscribtion();
        g1.setId(gr1_ID);
        g1.setTitle("g1");

        GroupSubscribtion g2 = new GroupSubscribtion();
        g2.setId(gr2_ID);
        g2.setTitle("g2");
        user.setGroups(new ArrayList<>(Arrays.asList(g1, g2)));

        //when
        command.execute(update);

        //then
        verify(sendMessageService,only()).sendMessage(CHAT_ID.toString(), "Подписка удалена!");
        verify(telegramUserService,atMostOnce()).save(user);

        assertFalse(user.getGroups().stream()
                .anyMatch(groupSubscribtion ->
                        groupSubscribtion.getId() == gr1_ID));

        assertTrue(user.getGroups().stream()
                .anyMatch(groupSubscribtion ->
                        groupSubscribtion.getId() == gr2_ID));


    }


}