package com.github.Naroru.JavaRushTelegramBot.command;

public enum CommandName {

    START("/start"),
    STOP("/stop"),
    NO(""),
    STAT("/stat"),
    ADD_GROUP_SUB("/addgroupsub"),
    GET_GROUP_LIST("/getgrouplist"),
    HELP("/help");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

      public String getCommandName() {
        return commandName;
    }

}
