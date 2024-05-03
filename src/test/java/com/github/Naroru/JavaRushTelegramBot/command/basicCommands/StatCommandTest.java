package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;


import com.github.Naroru.JavaRushTelegramBot.dto.GroupStatDto;
import com.github.Naroru.JavaRushTelegramBot.dto.StatDto;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import com.github.Naroru.JavaRushTelegramBot.service.StatiscticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.Naroru.JavaRushTelegramBot.command.basicCommands.AbstractCommandTest.preparedUpdate;
import static com.github.Naroru.JavaRushTelegramBot.command.basicCommands.StatCommand.STAT_MESSAGE;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Unit test of StatCommand class")
class StatCommandTest {

    @Mock
    private SendMessageService sendMessageService;

    @Mock

    private StatCommand statCommand;

    private StatiscticService statiscticService;

    @BeforeEach
    public void init() {
        sendMessageService = Mockito.mock(SendMessageService.class);
        statiscticService = Mockito.mock(StatiscticService.class);
        statCommand = new StatCommand(sendMessageService, statiscticService);

    }

    @Test
    public void shouldSendProperMessage()
    {
        //given
        Long chatId = 1234567L;
        GroupStatDto groupStatDto = new GroupStatDto(1,"g1", 10);
        StatDto statistic = new StatDto(10,5, List.of(groupStatDto),10);

        when(statiscticService.getStatisctic()).thenReturn(statistic);

        String excpectedMessage = String.format(STAT_MESSAGE,statistic.getActiveUsers(),
                statistic.getInactiveUsers(),
                statistic.getAverageGroupCountByUser(),
                statistic.getGroupStat().stream()
                        .map(stat -> String.format("%s, ID = %d: %d подписчиков \n", stat.title(), stat.id(), stat.activeUserNumber()))
                        .collect(Collectors.joining())
        );

        //when
        statCommand.execute(preparedUpdate(chatId,STAT_MESSAGE));

        verify(sendMessageService).sendMessage(chatId,excpectedMessage);
    }



}