package com.github.Naroru.JavaRushTelegramBot.service;

import com.github.Naroru.JavaRushTelegramBot.bot.JavarushBot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Unit-level testing for SendMessageService")
@ExtendWith(MockitoExtension.class)
class SendMessageServiceImpTest {

    private SendMessageService sendMessageService;

    @Mock
    private JavarushBot javarushBot;

    @BeforeEach
    public void init()
    {
        sendMessageService = new SendMessageServiceImp(javarushBot);
    }
@Test
    public void sendMessageShouldBeCorrect() throws TelegramApiException {
    //given
    String chatID = "123";
    String message = "test message";

    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatID);
    sendMessage.setText(message);
    sendMessage.enableHtml(true);

    //when
    sendMessageService.sendMessage(chatID, message);

    //then
    verify(javarushBot,only()).execute(sendMessage);

}




}