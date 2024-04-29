package com.github.Naroru.JavaRushTelegramBot.clients.postClient;

import com.github.Naroru.JavaRushTelegramBot.clients.dto.PostInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("integration test for JavaRushPostClient")
class JavaRushPostClientImpIT {

    private final JavaRushPostClient postClient = new JavaRushPostClientImp("https://javarush.com/api/1.0/rest");



    @Test
    public void shouldGetMaximumNewPosts()
    {
        //when
        List<PostInfo> newPosts = postClient.findNewPosts(30, -1);

        //then
        assertEquals(10, newPosts.size());
    }
}