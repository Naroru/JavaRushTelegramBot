package com.github.Naroru.JavaRushTelegramBot.job;

import com.github.Naroru.JavaRushTelegramBot.service.FindNewArtcileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@Slf4j
public class FindNewArticleJob {

    private final FindNewArtcileService findNewArtcileService;

    public FindNewArticleJob(FindNewArtcileService findNewArtcileService) {
        this.findNewArtcileService = findNewArtcileService;
    }

    @Scheduled(fixedRateString = "${bot.recountNewArticleFixedRate}")
    public void findNewArticle()
    {
        LocalDateTime start = LocalDateTime.now();
        log.info("Find new article job started.");

        findNewArtcileService.findNewArticle();

        LocalDateTime end = LocalDateTime.now();
        log.info(String.format("Find new article job finished during %d",
                end.toEpochSecond(ZoneOffset.UTC) - start.toEpochSecond(ZoneOffset.UTC)));

    }

}
