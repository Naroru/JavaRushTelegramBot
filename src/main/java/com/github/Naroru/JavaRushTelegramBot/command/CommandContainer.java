package com.github.Naroru.JavaRushTelegramBot.command;

import com.github.Naroru.JavaRushTelegramBot.command.basicCommands.*;
import com.github.Naroru.JavaRushTelegramBot.javarushclient.JavaRushGroupClient;
import com.github.Naroru.JavaRushTelegramBot.javarushclient.JavaRushGroupClientImp;
import com.github.Naroru.JavaRushTelegramBot.service.GroupSubsciptionService;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import com.github.Naroru.JavaRushTelegramBot.service.TelegramUserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandContainer {

    private final Map<String, Command> commands;
    private final Command unknownCommand;

   public CommandContainer (SendMessageService sendMessageService,
                            TelegramUserService telegramUserService,
                            JavaRushGroupClient javaRushGroupClient,
                            GroupSubsciptionService groupSubsciptionService)
   {

       commands = new HashMap<>();
       commands.put(CommandName.START.getCommandName(), new StartCommand(sendMessageService, telegramUserService));
       commands.put(CommandName.STOP.getCommandName(), new StopCommand(sendMessageService, telegramUserService));
       commands.put(CommandName.NO.getCommandName(), new NoCommand(sendMessageService));
       commands.put(CommandName.HELP.getCommandName(), new HelpCommand(sendMessageService));
       commands.put(CommandName.STAT.getCommandName(), new StatCommand(sendMessageService, telegramUserService));
       commands.put(CommandName.ADD_GROUP_SUB.getCommandName(), new AddGroupSubCommand(sendMessageService, javaRushGroupClient, groupSubsciptionService));

       unknownCommand = new UnknowCommand(sendMessageService);
   }

    public Command getCommand(String commandIdentifier) {

        Optional<Command> command = Optional.ofNullable(commands.get(commandIdentifier));
        return command.orElse(unknownCommand);
    }

}
