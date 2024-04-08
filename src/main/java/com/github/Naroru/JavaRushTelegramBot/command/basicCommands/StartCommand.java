package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.command.Command;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.TelegramUser;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import com.github.Naroru.JavaRushTelegramBot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;


public class StartCommand implements Command {

   private final SendMessageService sendMessageService;
   private final TelegramUserService telegramUserService;


    public final static String START_MESSAGE = "Привет. Я Javarush Telegram Bot. Я помогу тебе быть в курсе последних \n"
            +"статей тех авторов, котрые тебе интересны. Я еще маленький и только учусь.";


    public StartCommand(SendMessageService sendMessageService, TelegramUserService telegramUserService) {
        this.sendMessageService = sendMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {

        String chatID = update.getMessage().getChatId().toString();

        telegramUserService.findByChatId(chatID).ifPresentOrElse(
                telegramUser -> {
                    telegramUser.setActive(true);
                    telegramUserService.save(telegramUser);
                },
                () -> {
                    TelegramUser telegramUser = new TelegramUser(chatID, true);
                    telegramUserService.save(telegramUser);
                }
        );
        sendMessageService.sendMessage(chatID, START_MESSAGE);
    }
}
