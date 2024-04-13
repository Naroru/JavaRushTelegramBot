package com.github.Naroru.JavaRushTelegramBot.javarushclient;

import com.github.Naroru.JavaRushTelegramBot.javarushclient.dto.GroupCountRequestArgs;
import com.github.Naroru.JavaRushTelegramBot.javarushclient.dto.GroupDiscussionInfo;
import com.github.Naroru.JavaRushTelegramBot.javarushclient.dto.GroupInfo;
import com.github.Naroru.JavaRushTelegramBot.javarushclient.dto.GroupRequestArgs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Integration-level testing for JavaRushGroupClientImplTest")
class JavaRushGroupClientImpTest {

    private final JavaRushGroupClientImp javaRushGroupClientImp = new JavaRushGroupClientImp("https://javarush.com/api/1.0/rest");

    @Test
    public void ShouldProperlyGetGroupsWithEmptyArgs()
    {
        // given
        GroupRequestArgs requestArgs = GroupRequestArgs.builder().build();

      //when
       List<GroupInfo> list = javaRushGroupClientImp.getGroupList(requestArgs);

       //then
        assertNotNull(list);
        assertFalse(list.isEmpty());

    }

    @Test
    @DisplayName("Should get a proper list of groups with Limit and Offser args")
    public void ShouldProperlyGetGroupsWithLimitAndOffset()
    {
        //given
        GroupRequestArgs requestArgs = GroupRequestArgs.builder()
                .offset(3)
                .limit(3)
                .build();

        //when
        List<GroupInfo> list = javaRushGroupClientImp.getGroupList(requestArgs);

        //then
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertEquals(3, list.size());

    }

    @Test
    @DisplayName("Should get a proper list of Discussion Info with Offset and Limit args")
    public void ShouldProperlyGetDiscInfoWithLimitAndOffset()
    {
        //given
        GroupRequestArgs requestArgs = GroupRequestArgs.builder()
                .offset(5)
                .limit(3)
                .build();
        //when
        List<GroupDiscussionInfo> list = javaRushGroupClientImp.getGroupDiscussionList(requestArgs);

        //then
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertEquals(3, list.size());
    }


    @Test
    @DisplayName("Shoulg get proper list of Group discussions with empty args")
    public void ShouldProperlyGetDiscussionInfoListWithEmptyArgs()
    {
        //given
        GroupRequestArgs requestArgs = GroupRequestArgs.builder().build();

        //when
        List<GroupDiscussionInfo> list = javaRushGroupClientImp.getGroupDiscussionList(requestArgs);

        //then
        assertNotNull(list);
        assertFalse(list.isEmpty());

    }

    @Test
    public void ShouldGetGroupCountWithNoArgs()
    {
        //given
        GroupCountRequestArgs requestArgs = GroupCountRequestArgs.builder().build();

        //when
        int count = javaRushGroupClientImp.getGroupCount(requestArgs);

        //then
        assertTrue(count >= 25 && count <=30);
    }

    @Test
    public void ShouldGetPropereDiscussionInfoWithNoArgs()
    {
        //given
        int id = 18;

        //when
        GroupDiscussionInfo groupDiscussionInfo = javaRushGroupClientImp.getGroupByID(id);

        //then
        assertNotNull(groupDiscussionInfo);
        assertEquals(18, groupDiscussionInfo.getId());
    }
}