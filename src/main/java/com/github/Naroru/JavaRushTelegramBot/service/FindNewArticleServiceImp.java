package com.github.Naroru.JavaRushTelegramBot.service;


import com.github.Naroru.JavaRushTelegramBot.clients.dto.PostInfo;
import com.github.Naroru.JavaRushTelegramBot.clients.postClient.JavaRushPostClient;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.GroupSubscribtion;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.TelegramUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindNewArticleServiceImp implements FindNewArtcileService {

    private final GroupSubsciptionService groupSubsciptionService;
    private final JavaRushPostClient postClient;
    private final SendMessageService sendMessageService;

    public static final String JAVARUSH_WEB_POST_FORMAT = "https://javarush.com/groups/posts/%s";

    public FindNewArticleServiceImp(GroupSubsciptionService groupSubsciptionService, JavaRushPostClient postClient, SendMessageService sendMessageService) {
        this.groupSubsciptionService = groupSubsciptionService;
        this.postClient = postClient;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void findNewArticle() {

        groupSubsciptionService.findAll().forEach(group -> {

                    List<PostInfo> articles = postClient.findNewPosts(group.getId(), group.getLastArticleID());

                    sendMessagesAboutNewArticles(group, articles);
                    setNewArticleID(group, articles);

                });

    }

    private void setNewArticleID(GroupSubscribtion group, List<PostInfo> articles) {

        articles.stream()
                .mapToInt(PostInfo::getId)
                .max()
                .ifPresent(articleID -> {
                    group.setLastArticleID(articleID);
                    groupSubsciptionService.save(group);
                });



    }

    private void sendMessagesAboutNewArticles(GroupSubscribtion group, List<PostInfo> articles) {

        List<TelegramUser> activeUsers = group.getUsers().stream()
                .filter(TelegramUser::isActive).toList();

        articles.forEach(article -> {

            String message = String.format("В группе %s вышла новая статья: %s.\n\n Ссылка %s\n",
                    group.getTitle(),
                    article.getTitle(),
                    getURL(article.getKey()));

            activeUsers.forEach(user -> sendMessageService.sendMessage(user.getChatId(), message));
        });
    }

    private String getURL(String postKey) {
        return String.format(JAVARUSH_WEB_POST_FORMAT, postKey);
    }

}
