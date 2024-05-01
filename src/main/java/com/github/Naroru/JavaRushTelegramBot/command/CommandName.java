package com.github.Naroru.JavaRushTelegramBot.command;

public enum CommandName {

    START("/start"),
    STOP("/stop"),
    NO(""),
    STAT("/stat"),
    ADD_GROUP_SUB("/addgroupsub"),
    GET_GROUP_LIST("/getgrouplist"),
    DELETE_GROUP_SUB("/deletegroupsub"),
    HELP("/help"),
    ADMIN_HELP("/ahelp");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

      public String getCommandName() {
        return commandName;
    }

}
