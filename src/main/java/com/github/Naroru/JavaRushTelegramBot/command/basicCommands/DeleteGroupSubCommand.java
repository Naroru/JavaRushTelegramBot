package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.command.Command;
import com.github.Naroru.JavaRushTelegramBot.command.CommandName;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.GroupSubscribtion;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.TelegramUser;
import com.github.Naroru.JavaRushTelegramBot.service.GroupSubsciptionService;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import com.github.Naroru.JavaRushTelegramBot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class DeleteGroupSubCommand implements Command {

    private final SendMessageService sendMessageService;
    private final TelegramUserService telegramUserService;
    private final GroupSubsciptionService groupSubsciptionService;


    public DeleteGroupSubCommand(SendMessageService sendMessageService, TelegramUserService telegramUserService, GroupSubsciptionService groupSubsciptionService) {
        this.sendMessageService = sendMessageService;
        this.telegramUserService = telegramUserService;
        this.groupSubsciptionService = groupSubsciptionService;
    }

    @Override
    public void execute(Update update) {

        String commandMessage = update.getMessage().getText();
        String chatID = update.getMessage().getChatId().toString();
        TelegramUser user = telegramUserService.findByChatId(chatID).orElseThrow(NotFoundException::new);

        if (commandMessage.equalsIgnoreCase(CommandName.DELETE_GROUP_SUB.getCommandName())) {
            sendMessageAllGroupSubs(chatID,user);
            return;
        }

        String groupID = commandMessage.split(" ")[1];

        if (isNumeric(groupID)) {

            Optional<GroupSubscribtion> optionalGroupSub = groupSubsciptionService.findByID(Integer.parseInt(groupID));

            if (optionalGroupSub.isPresent()) {

                GroupSubscribtion groupSubscribtion = optionalGroupSub.get();

     /*           groupSubscribtion.getUsers().remove(user);
                groupSubsciptionService.save(groupSubscribtion);
*/

                user.getGroups().remove(groupSubscribtion);
                telegramUserService.save(user);

                sendMessageService.sendMessage(chatID, "Подписка удалена!");
            }
            else
                sendMessageGroupSubIDNotFound(chatID,user);

        } else
            sendMessageService.sendMessage(chatID, "Указанный ID группы некорректный. ID может содержать только цифры");


    }

    private void sendMessageAllGroupSubs(String chatID, TelegramUser user)
    {
        //todo it looks similar GetGroupListCommand()
        String message = String.format("""
                        Для удаления подписки используй команду %s ID_группы.

                        Список твоих активных групп:
                        
                        %s""",
                CommandName.DELETE_GROUP_SUB.getCommandName(),
                getUserActiveGroupSubs(user));

        sendMessageService.sendMessage(chatID,message);
    }

    private void sendMessageGroupSubIDNotFound(String chatID, TelegramUser user) {

        String message =  String.format("Указанная группа не найдена, проверьте правильность ID, " +
                "ваши текущие подписки на группы: \n" +
                "%s", getUserActiveGroupSubs(user));

        sendMessageService.sendMessage(chatID, message);

    }

    private  String getUserActiveGroupSubs(TelegramUser user)
    {

        List<GroupSubscribtion> groups = user.getGroups();

        if (groups.isEmpty())
            return "У вас нет активных подписок";
        else
            return groups.stream()
                    .map(group -> String.format("%s, ID - %d \n", group.getTitle(),group.getId()))
                    .collect(Collectors.joining());

    }
}
