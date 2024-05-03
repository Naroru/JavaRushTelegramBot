package com.github.Naroru.JavaRushTelegramBot.job;

import com.github.Naroru.JavaRushTelegramBot.service.FindNewArtcileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@Slf4j
public class FindNewPostJob {

    private final FindNewArtcileService findNewPostService;

    public FindNewPostJob(FindNewArtcileService findNewArtcileService) {
        this.findNewPostService = findNewArtcileService;
    }

    @Scheduled(fixedRateString = "${bot.recountNewPostsFixedRate}")
    public void findNewPost()
    {
        LocalDateTime start = LocalDateTime.now();
        log.info("Find new post job started.");

        findNewPostService.findNewPost();

        LocalDateTime end = LocalDateTime.now();
        log.info(String.format("Find new post job finished during %d",
                end.toEpochSecond(ZoneOffset.UTC) - start.toEpochSecond(ZoneOffset.UTC)));

    }

}
