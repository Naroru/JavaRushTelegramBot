package com.github.Naroru.JavaRushTelegramBot.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Entity
@Table(name = "group_sub")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class GroupSubscribtion {

    @Id
    private int id;

    private String title;

    @Column(name = "last_article_id")
    private int lastArticleID;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "group_x_user"
            , joinColumns = @JoinColumn(name = "group_id")
            , inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<TelegramUser> users;

    public void addUser(TelegramUser user) {
        if (isNull(users))
            users = new ArrayList<>();
        users.add(user);

    }


}
