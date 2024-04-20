package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.command.Command;
import com.github.Naroru.JavaRushTelegramBot.javarushclient.JavaRushGroupClient;
import com.github.Naroru.JavaRushTelegramBot.javarushclient.dto.GroupDiscussionInfo;
import com.github.Naroru.JavaRushTelegramBot.javarushclient.dto.GroupInfo;
import com.github.Naroru.JavaRushTelegramBot.javarushclient.dto.GroupRequestArgs;
import com.github.Naroru.JavaRushTelegramBot.service.GroupSubsciptionService;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.Naroru.JavaRushTelegramBot.command.CommandName.ADD_GROUP_SUB;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNumeric;


public class AddGroupSubCommand implements Command {


    private final SendMessageService sendMessageService;
    private final JavaRushGroupClient javaRushGroupClient;
    private final GroupSubsciptionService groupSubsciptionService;


    public static final String GROUP_NOT_FOUND_MESSAGE = "Указанная группа не найдена. Проверьте правильность ID";

    public AddGroupSubCommand(SendMessageService sendMessageService, JavaRushGroupClient javaRushGroupClient, GroupSubsciptionService groupSubsciptionService) {
        this.sendMessageService = sendMessageService;
        this.javaRushGroupClient = javaRushGroupClient;
        this.groupSubsciptionService = groupSubsciptionService;
    }


    @Override
    public void execute(Update update) {

        String message = update.getMessage().getText();
        String chatID = update.getMessage().getChatId().toString();

        if (message.equalsIgnoreCase(ADD_GROUP_SUB.getCommandName())) {
            sendAllGroupsWithID(chatID);
            return;
        }

        String groupID = message.split(" ")[1];

        if (isNumeric(groupID)) {

            GroupDiscussionInfo groupDiscussionInfo = javaRushGroupClient.getGroupByID(Integer.parseInt(groupID));

            if (isNull(groupDiscussionInfo))
                sendMessageGroupNotFound(chatID);
            else
                makeSubscription(groupDiscussionInfo, chatID);

        } else
            sendMessageIDIsNotCorrect(groupID, chatID);
    }


    private void sendMessageGroupNotFound(String chatID) {

        sendMessageService.sendMessage(chatID, GROUP_NOT_FOUND_MESSAGE);
    }

    private void makeSubscription(GroupDiscussionInfo groupDiscussionInfo, String chatID) {

        groupSubsciptionService.save(chatID, groupDiscussionInfo);
        sendMessageService.sendMessage(chatID, "Подписка оформлена!");
    }

    private void sendAllGroupsWithID(String chatID) {

        List<GroupInfo> groups = javaRushGroupClient.getGroupList(GroupRequestArgs.builder().build());

        String groupsAndID = groups.stream()
                .map(groupInfo -> String.format("%s: %d\n", groupInfo.getTitle(), groupInfo.getId()))
                .collect(Collectors.joining());

//todo возможно подобные сообщения стоит вынести в публичные статические функции, чтобы не дублировать текст в тестах

        String messageText = String.format("""
                Для подписи на определенную группу следует использовать команду:

                 <b>/addgroupsub ID_группы</b>

                Список доступных групп и ID:
                %s""", groupsAndID);


        sendMessageService.sendMessage(chatID, messageText);
    }

    private void sendMessageIDIsNotCorrect(String groupID, String chatID) {
        //todo возможно подобные сообщения стоит вынести в публичные статические функции, чтобы не дублировать текст в тестах
        String message = String.format("Указанный ID = %s не является ID существующей группы. Используйте команду " +
                "%s для получения списка существующих ID", groupID, ADD_GROUP_SUB.getCommandName());

        sendMessageService.sendMessage(chatID, message);

    }


}
