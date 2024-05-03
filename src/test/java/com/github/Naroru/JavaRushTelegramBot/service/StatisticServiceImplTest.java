package com.github.Naroru.JavaRushTelegramBot.service;

import com.github.Naroru.JavaRushTelegramBot.dto.GroupStatDto;
import com.github.Naroru.JavaRushTelegramBot.dto.StatDto;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.GroupSubscribtion;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.TelegramUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.MockitoAnnotations.openMocks;

@DisplayName("unit tests for StatisticServiceImp")
class StatisticServiceImplTest {

    @Mock
    private TelegramUserService userService;

    @Mock
    private GroupSubsciptionService groupSubsciptionService;

    private StatiscticService statiscticService;

    @BeforeEach
    public void init()
    {
        openMocks(this);
        statiscticService = new StatisticServiceImp(userService,groupSubsciptionService);

    }

    @Test
    public void shouldreturnProperStatDto()
    {
        //given
        Mockito.when(userService.findAllInactiveUsers()).thenReturn(singletonList(new TelegramUser()));

        TelegramUser activeUser = new TelegramUser();
        activeUser.setGroups(singletonList(new GroupSubscribtion()));

        Mockito.when(userService.findAllActiveUsers()).thenReturn(singletonList(activeUser));

        GroupSubscribtion groupSub = new GroupSubscribtion();
        groupSub.setTitle("group");
        groupSub.setId(1);
        groupSub.setUsers(singletonList(new TelegramUser()));

        Mockito.when(groupSubsciptionService.findAll()).thenReturn(singletonList(groupSub));

        //when
        StatDto statDto = statiscticService.getStatisctic();

        //then
        assertNotNull(statDto);
        assertEquals(1, statDto.getActiveUsers());
        assertEquals(1, statDto.getInactiveUsers());
        assertEquals(List.of(new GroupStatDto(groupSub.getId(),groupSub.getTitle(), groupSub.getUsers().size() )), statDto.getGroupStat());
        assertEquals(1,statDto.getAverageGroupCountByUser() );
    }
}