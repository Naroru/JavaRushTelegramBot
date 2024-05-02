package com.github.Naroru.JavaRushTelegramBot.command;

import com.github.Naroru.JavaRushTelegramBot.annotation.AdminCommand;
import com.github.Naroru.JavaRushTelegramBot.command.basicCommands.*;
import com.github.Naroru.JavaRushTelegramBot.clients.groupClient.JavaRushGroupClient;
import com.github.Naroru.JavaRushTelegramBot.service.GroupSubsciptionService;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import com.github.Naroru.JavaRushTelegramBot.service.StatiscticService;
import com.github.Naroru.JavaRushTelegramBot.service.TelegramUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.nonNull;

public class CommandContainer {

    private final Map<String, Command> commands;
    private final Command unknownCommand;
    private final List<String> admins;


   public CommandContainer (SendMessageService sendMessageService,
                            TelegramUserService telegramUserService,
                            JavaRushGroupClient javaRushGroupClient,
                            GroupSubsciptionService groupSubsciptionService,
                            StatiscticService statiscticService,
                            List<String> admins)
   {

       commands = new HashMap<>();
       commands.put(CommandName.START.getCommandName(), new StartCommand(sendMessageService, telegramUserService));
       commands.put(CommandName.STOP.getCommandName(), new StopCommand(sendMessageService, telegramUserService));
       commands.put(CommandName.NO.getCommandName(), new NoCommand(sendMessageService));
       commands.put(CommandName.HELP.getCommandName(), new HelpCommand(sendMessageService));
       commands.put(CommandName.STAT.getCommandName(), new StatCommand(sendMessageService, statiscticService));
       commands.put(CommandName.ADD_GROUP_SUB.getCommandName(), new AddGroupSubCommand(sendMessageService, javaRushGroupClient, groupSubsciptionService));
       commands.put(CommandName.GET_GROUP_LIST.getCommandName(), new GetGroupListCommand(sendMessageService, telegramUserService));
       commands.put(CommandName.DELETE_GROUP_SUB.getCommandName(), new DeleteGroupSubCommand(sendMessageService, telegramUserService));
       commands.put(CommandName.ADMIN_HELP.getCommandName(), new AdminHelpCommand(sendMessageService));

       unknownCommand = new UnknowCommand(sendMessageService);

       this.admins = admins;
   }

    public Command getCommand(String commandIdentifier, String username) {

        Command command = Optional.ofNullable(commands.get(commandIdentifier))
                .orElse(unknownCommand);

        if (adminCommand(command)) {
            if (admins.contains(username))
                return command;
            else
                return unknownCommand;
        } else
            return command;

    }

    private boolean adminCommand(Command command) {

       return nonNull(command.getClass().getAnnotation(AdminCommand.class));
    }


}
