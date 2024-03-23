package com.github.Naroru.JavaRushTelegramBot.command;

import com.github.Naroru.JavaRushTelegramBot.command.basicCommands.HelpCommand;
import com.github.Naroru.JavaRushTelegramBot.command.basicCommands.StartCommand;
import com.github.Naroru.JavaRushTelegramBot.command.basicCommands.StopCommand;
import com.github.Naroru.JavaRushTelegramBot.command.basicCommands.UnknowCommand;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class CommandContainer {
    private final Map<String, Command> commands;
    private final Command unknownCommand;
   public CommandContainer (SendMessageService sendMessageService)
   {
       commands = new HashMap<>();
       commands.put(CommandName.START.getCommandName(), new StartCommand(sendMessageService));
       commands.put(CommandName.STOP.getCommandName(), new StopCommand(sendMessageService));
       commands.put(CommandName.NO.getCommandName(), new NoCommand(sendMessageService));
       commands.put(CommandName.HELP.getCommandName(), new HelpCommand(sendMessageService));

       unknownCommand = new UnknowCommand(sendMessageService);
   }

    public Command getCommand(String commandIdentifier) {

        Optional<Command> command = Optional.ofNullable(commands.get(commandIdentifier));
        return command.orElse(unknownCommand);
    }

}
