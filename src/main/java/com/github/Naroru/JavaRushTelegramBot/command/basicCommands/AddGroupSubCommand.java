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

import static com.github.Naroru.JavaRushTelegramBot.command.CommandName.ADD_GROUP_SUB;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class AddGroupSubCommand implements Command {

    private final SendMessageService sendMessageService;
    private final JavaRushGroupClient javaRushGroupClient;
    private final GroupSubsciptionService groupSubsciptionService;

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

            if (isNumeric(groupID))
                makeSubscription(groupID, chatID);
            else
                sendMessageIDIsNotCorrect(groupID, chatID);


    }

    private void makeSubscription(String groupID, String chatID) {

        GroupDiscussionInfo groupDiscussionInfo = javaRushGroupClient.getGroupByID(Integer.parseInt(groupID));

        groupSubsciptionService.save(chatID, groupDiscussionInfo);

        String message = "Подписка оформлена!";

        sendMessageService.sendMessage(chatID, message);

    }

    private void sendAllGroupsWithID(String chatID) {


        StringBuilder messageText = new StringBuilder("Для подписи на определенную группу следует использовать "
                + "команду:\n\n <b>/addgroupsub ID_группы</b>\n\n"
                + "Список доступных групп и ID:\n");

        List<GroupInfo> groups = javaRushGroupClient.getGroupList(GroupRequestArgs.builder().build());

        for (GroupInfo groupInfo : groups) {
            messageText.append(groupInfo.getTitle())
                    .append(": ")
                    .append(groupInfo.getId())
                    .append("\n");
        }

        sendMessageService.sendMessage(chatID, messageText.toString());
    }

    private void sendMessageIDIsNotCorrect(String groupID, String chatID) {
        String message = String.format("Указанный ID = %s не является ID существующей группы. Используйте команду " +
                "%s для получения списка сущесвующих ID", groupID, ADD_GROUP_SUB.getCommandName());

        sendMessageService.sendMessage(chatID, message);

    }


}
