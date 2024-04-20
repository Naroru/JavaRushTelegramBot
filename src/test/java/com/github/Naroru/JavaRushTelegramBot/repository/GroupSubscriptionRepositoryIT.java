package com.github.Naroru.JavaRushTelegramBot.repository;

import com.github.Naroru.JavaRushTelegramBot.repository.entity.GroupSubscribtion;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.TelegramUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import java.util.List;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class GroupSubscriptionRepositoryIT {

    @Autowired
    private GroupSubscriptionRepository groupSubscriptionRepository;

    @DisplayName("Should get 5 users for a group")
    @Test
    @Sql(scripts = {"/sql/clear_tables.sql", "/sql/fiveUserForGroupSub.sql"})
    public void shouldGetFiveUsersForGroupSub()
    {
        //when
        Optional<GroupSubscribtion> groupSubscribtion = groupSubscriptionRepository.findById(1);
        
        //then
        assertTrue(groupSubscribtion.isPresent());
        assertEquals(1, groupSubscribtion.get().getId());

        List<TelegramUser> users = groupSubscribtion.get().getUsers();

        for (int i = 0; i < 5; i++) {
            assertEquals(i + 1,parseInt(users.get(i).getChatId() ));
            assertTrue(users.get(i).isActive());
        }
        
    }
}