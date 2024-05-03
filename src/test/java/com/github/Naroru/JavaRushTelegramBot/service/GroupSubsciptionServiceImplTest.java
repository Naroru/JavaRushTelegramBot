package com.github.Naroru.JavaRushTelegramBot.service;

import com.github.Naroru.JavaRushTelegramBot.clients.dto.GroupDiscussionInfo;
import com.github.Naroru.JavaRushTelegramBot.repository.GroupSubscriptionRepository;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.GroupSubscribtion;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.TelegramUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("unit-testing for GroupDiscussionService")
@ExtendWith(MockitoExtension.class)
class GroupSubsciptionServiceImplTest {


    @Mock
    TelegramUserService telegramUserService;

    @Mock
    GroupSubscriptionRepository groupRepository;

    private GroupSubsciptionService groupSubsciptionService;

    private TelegramUser newUser;

    private final static Long CHAT_ID = 1L;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        groupSubsciptionService = new GroupSubsciptionServiceImpl(telegramUserService, groupRepository);


        newUser = new TelegramUser();
        newUser.setChatId(CHAT_ID);
        newUser.setActive(true);

        when(telegramUserService.findByChatId(CHAT_ID)).thenReturn(Optional.of(newUser));


    }

    @Test
    public void shouldProperlySaveNewGroup() {

        //given
        GroupDiscussionInfo groupDiscussionInfo = new GroupDiscussionInfo();
        groupDiscussionInfo.setId(1);
        groupDiscussionInfo.setTitle("g1");


        GroupSubscribtion expectedGroupSubscribtion = new GroupSubscribtion();
        expectedGroupSubscribtion.setId(1);
        expectedGroupSubscribtion.setTitle("g1");
        expectedGroupSubscribtion.addUser(newUser);

        //when
        groupSubsciptionService.save(CHAT_ID, groupDiscussionInfo);

        //then
        verify(groupRepository).save(expectedGroupSubscribtion);

    }

    @Test
    public void ShouldProperlyUpdateGroup()
    {
        //given
        TelegramUser existingUser = new TelegramUser();
        existingUser.setActive(true);
        existingUser.setChatId(2L);

        GroupSubscribtion groupFromDB = new GroupSubscribtion();
        groupFromDB.addUser(existingUser);
        groupFromDB.setId(1);
        groupFromDB.setTitle("g1");

        GroupDiscussionInfo groupDTO = new GroupDiscussionInfo();
        groupDTO.setId(1);
        groupDTO.setTitle("g1");

        GroupSubscribtion expectedGroupSub = new GroupSubscribtion();
        expectedGroupSub.setId(groupDTO.getId());
        expectedGroupSub.setTitle(groupDTO.getTitle());
        expectedGroupSub.addUser(existingUser);
        expectedGroupSub.addUser(newUser);

        when(groupRepository.findById(groupDTO.getId())).thenReturn(Optional.of(groupFromDB));

        //when
        groupSubsciptionService.save(CHAT_ID,groupDTO);

        //then
        verify(groupRepository).findById(groupDTO.getId());
        verify(groupRepository).save(expectedGroupSub);


    }
}