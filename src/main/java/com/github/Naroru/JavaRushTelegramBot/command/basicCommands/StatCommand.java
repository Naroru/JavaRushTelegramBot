package com.github.Naroru.JavaRushTelegramBot.command.basicCommands;

import com.github.Naroru.JavaRushTelegramBot.annotation.AdminCommand;
import com.github.Naroru.JavaRushTelegramBot.command.Command;
import com.github.Naroru.JavaRushTelegramBot.dto.StatDto;
import com.github.Naroru.JavaRushTelegramBot.service.SendMessageService;
import com.github.Naroru.JavaRushTelegramBot.service.StatiscticService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

@AdminCommand
public class StatCommand implements Command {

   private final SendMessageService sendMessageService;
   private final StatiscticService statiscticService;


    public final static String STAT_MESSAGE =
            """
                    Javarush Telegram Bot использует %d активных пользователей
                    Неактивных пользователей %d человек,
                    Среднее количество подписок у пользователя %s

                    Детализация по группам:\
                    %s""";

    public StatCommand(SendMessageService sendMessageService, StatiscticService statiscticService) {
        this.sendMessageService = sendMessageService;
        this.statiscticService = statiscticService;
    }


    @Override
    public void execute(Update update) {

        StatDto statistic = statiscticService.getStatisctic();

        String message = String.format(STAT_MESSAGE,
                statistic.getActiveUsers(),
                statistic.getInactiveUsers(),
                statistic.getAverageGroupCountByUser(),
                getGroupStatInfo(statistic));

        sendMessageService.sendMessage(update.getMessage().getChatId().toString(), message);

    }

    private String getGroupStatInfo(StatDto statistic)
    {
        return statistic.getGroupStat().stream()
                .map(stat -> String.format("%s, ID = %d: %d подписчиков \n", stat.title(), stat.id(), stat.activeUserNumber()))
                .collect(Collectors.joining());
    }
}
