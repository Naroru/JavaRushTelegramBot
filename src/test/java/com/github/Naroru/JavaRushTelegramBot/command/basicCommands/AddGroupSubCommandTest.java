package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.clients.dto.GroupDiscussionInfo;
import com.github.Naroru.JavaRushTelegramBot.clients.dto.GroupInfo;
import com.github.Naroru.JavaRushTelegramBot.clients.dto.GroupRequestArgs;
import com.github.Naroru.JavaRushTelegramBot.clients.groupClient.JavaRushGroupClient;
import com.github.Naroru.JavaRushTelegramBot.command.Command;
import com.github.Naroru.JavaRushTelegramBot.command.CommandName;
import com.github.Naroru.JavaRushTelegramBot.service.GroupSubsciptionService;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.Naroru.JavaRushTelegramBot.command.CommandName.ADD_GROUP_SUB;
import static com.github.Naroru.JavaRushTelegramBot.command.basicCommands.AddGroupSubCommand.GROUP_NOT_FOUND_MESSAGE;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@DisplayName("unit-testing for AddGroupSubCommand")
class AddGroupSubCommandTest {

    @Mock
    private SendMessageService sendMessageService;

    @Mock
    private JavaRushGroupClient javaRushGroupClient;

    @Mock
    private GroupSubsciptionService groupSubsciptionService;

    @Mock
    private Update update;

    @Mock
    private Message message;

    private final Long chatID = 123L;

    private Command command;

    @BeforeEach
    public void init()
    {
        openMocks(this);

        when(update.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(Long.valueOf(chatID));

        command = new AddGroupSubCommand(sendMessageService, javaRushGroupClient, groupSubsciptionService);
    }

    @Test
    public void ShouldSendAllGroupsWithID()
    {
        //given
        List<GroupInfo> groups = List.of(
                createGroupInfo(1),
                createGroupInfo(2)
        );

        when(message.getText()).thenReturn(CommandName.ADD_GROUP_SUB.getCommandName());
        when(javaRushGroupClient.getGroupList(GroupRequestArgs.builder().build())).thenReturn(groups);

        String groupsAndID = groups.stream()
                .map(groupInfo -> String.format("%s: %d\n", groupInfo.getTitle(), groupInfo.getId()))
                .collect(Collectors.joining());

        String messageText = String.format("""
                Для подписи на определенную группу следует использовать команду:

                 <b>%s ID_группы</b>

                Список доступных групп и ID:
                %s""", ADD_GROUP_SUB.getCommandName(), groupsAndID);

        //when
        command.execute(update);

        //then
        verify(sendMessageService).sendMessage(chatID,messageText);

    }

    @Test
    public void shouldSendMessageGroupNotFound()
    {
        //given
        int unknownGroupID = 1;

        when(message.getText()).thenReturn(
                String.format("%s %d",
                        CommandName.ADD_GROUP_SUB.getCommandName(), unknownGroupID)
        );

        when(javaRushGroupClient.getGroupByID(unknownGroupID)).thenReturn(null);

        //when
        command.execute(update);

        //then
        verify(sendMessageService).sendMessage(chatID, GROUP_NOT_FOUND_MESSAGE);
    }


    @Test
    public void shouldSendMessageGroupIDIncorrect()
    {
        //given
        String incorrectID = "123f";
        when(message.getText()).thenReturn(CommandName.ADD_GROUP_SUB.getCommandName()+" "+incorrectID);

        String message = String.format("Указанный ID = %s не является ID существующей группы. Используйте команду " +
                "%s для получения списка существующих ID", incorrectID, ADD_GROUP_SUB.getCommandName());

        //when
        command.execute(update);

        //then
        verify(sendMessageService).sendMessage(chatID,message);

    }

    @Test
    public void shouldSuccessfullyMakeSubscription()
    {

        //given
        int correctID = 10;
        when(message.getText()).thenReturn(CommandName.ADD_GROUP_SUB.getCommandName()+" "+correctID);
        when(javaRushGroupClient.getGroupByID(correctID)).thenReturn(null);

        GroupDiscussionInfo groupDiscussionInfo = new GroupDiscussionInfo();
        groupDiscussionInfo.setId(correctID);

        when(javaRushGroupClient.getGroupByID(correctID)).thenReturn(groupDiscussionInfo);

        //when
        command.execute(update);

        //then
        verify(groupSubsciptionService).save(chatID,groupDiscussionInfo);
        verify(sendMessageService).sendMessage(chatID, "Подписка оформлена!");

    }


    private GroupInfo createGroupInfo(int id)
    {
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setId(id);
        groupInfo.setTitle(String.format("g%d",id));
        return groupInfo;

    }


}