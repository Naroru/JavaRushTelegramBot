package com.github.Naroru.JavaRushTelegramBot.command;

import com.github.Naroru.JavaRushTelegramBot.service.SendMessageServiceImp;

import java.util.Map;

public class CommandContainer {

    private Map<String, Command> commands;

   public CommandContainer ()
   {
       commands.put("/start", new StartCommand(new SendMessageServiceImp()))
   }

}
