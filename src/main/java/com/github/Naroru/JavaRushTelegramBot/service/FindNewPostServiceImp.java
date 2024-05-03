package com.github.Naroru.JavaRushTelegramBot.service;


import com.github.Naroru.JavaRushTelegramBot.clients.dto.PostInfo;
import com.github.Naroru.JavaRushTelegramBot.clients.postClient.JavaRushPostClient;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.GroupSubscribtion;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.TelegramUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindNewPostServiceImp implements FindNewArtcileService {

    private final GroupSubsciptionService groupSubsciptionService;
    private final JavaRushPostClient postClient;
    private final SendMessageService sendMessageService;

    public static final String JAVARUSH_WEB_POST_FORMAT = "https://javarush.com/groups/posts/%s";

    public FindNewPostServiceImp(GroupSubsciptionService groupSubsciptionService, JavaRushPostClient postClient, SendMessageService sendMessageService) {
        this.groupSubsciptionService = groupSubsciptionService;
        this.postClient = postClient;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void findNewPost() {

        groupSubsciptionService.findAll().forEach(group -> {

                    List<PostInfo> posts = postClient.findNewPosts(group.getId(), group.getLastPostID());

                    sendMessagesAboutNewposts(group, posts);
                    setNewPostID(group, posts);

                });

    }

    private void setNewPostID(GroupSubscribtion group, List<PostInfo> posts) {

        posts.stream()
                .mapToInt(PostInfo::getId)
                .max()
                .ifPresent(PostID -> {
                    group.setLastPostID(PostID);
                    groupSubsciptionService.save(group);
                });



    }

    private void sendMessagesAboutNewposts(GroupSubscribtion group, List<PostInfo> posts) {

        List<TelegramUser> activeUsers = group.getUsers().stream()
                .filter(TelegramUser::isActive).toList();

        posts.forEach(Post -> {

            String message = String.format("В группе %s вышла новая статья: %s.\n\n Ссылка %s\n",
                    group.getTitle(),
                    Post.getTitle(),
                    getURL(Post.getKey()));

            activeUsers.forEach(user -> sendMessageService.sendMessage(user.getChatId(), message));
        });
    }

    private String getURL(String postKey) {
        return String.format(JAVARUSH_WEB_POST_FORMAT, postKey);
    }

}
