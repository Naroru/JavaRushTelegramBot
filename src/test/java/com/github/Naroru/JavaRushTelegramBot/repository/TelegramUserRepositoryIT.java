package com.github.Naroru.JavaRushTelegramBot.repository;

import com.github.Naroru.JavaRushTelegramBot.repository.entity.GroupSubscribtion;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.TelegramUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.validation.constraints.AssertTrue;
import java.util.List;
import java.util.Optional;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class TelegramUserRepositoryIT {

    @Autowired
    private TelegramUserRepository telegramUserRepository;

    @Sql(scripts = {"/sql/clear_tables.sql", "/sql/insert_tg_users.sql"})
    @Test
    public void shouldProperlyFindAllActiveUsers() {
        //when
        List<TelegramUser> users = telegramUserRepository.findAllByActiveTrue();

        //then
        Assertions.assertEquals(5, users.size());
    }

    @Sql(scripts = {"/sql/clear_tables.sql"})
    @Test
    public void shouldProperlySaveTelegramUser() {
        //given
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId("1234567890");
        telegramUser.setActive(false);
        telegramUserRepository.save(telegramUser);

        //when
        Optional<TelegramUser> saved = telegramUserRepository.findById(telegramUser.getChatId());

        //then
        Assertions.assertTrue(saved.isPresent());
        Assertions.assertEquals(telegramUser, saved.get());
    }

    @DisplayName("Successfully get user with 5 groups")
    @Test
    @Sql(scripts = {"/sql/clear_tables.sql", "/sql/fiveGroupSubsForUser.sql"})
    public void getUserWithFiveGroups()
    {


        //when
        Optional<TelegramUser> userFromDB = telegramUserRepository.findById("1");

        //then
        Assertions.assertTrue(userFromDB.isPresent());

        List<GroupSubscribtion> groupSubs = userFromDB.get().getGroups();

        Assertions.assertEquals(5,groupSubs.size());

        for (int i = 0; i < 5; i++) {
            GroupSubscribtion groupSub = groupSubs.get(i);

            Assertions.assertEquals(i+1, groupSub.getId());
            Assertions.assertEquals(i+1, groupSub.getLastArticleID());
            Assertions.assertEquals("g"+(i+1), groupSub.getTitle());
        }


    }
}