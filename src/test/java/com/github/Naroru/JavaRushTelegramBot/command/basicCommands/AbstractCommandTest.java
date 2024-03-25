package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.bot.JavarushBot;
import com.github.Naroru.JavaRushTelegramBot.command.Command;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 abstract class AbstractCommandTest {

     abstract String getCommandMessage();

     abstract Command getCommand();

    @Mock
    Update update;

    @Mock
    JavarushBot javarushBot;

    public SendMessageService sendMessageService;


    @Test
    public void CommandShouldExecuteProperly() throws TelegramApiException {
       //given
        long chatID = 1231232342313L;

        sendMessageService = new SendMessageServiceImp(javarushBot);

        Update update =  Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);

        when(update.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(chatID);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatID));
        sendMessage.setText(getCommandMessage());
        sendMessage.enableHtml(true);

        //when
        getCommand().execute(update);

        //then
        verify(javarushBot).execute(sendMessage);


    }


}
