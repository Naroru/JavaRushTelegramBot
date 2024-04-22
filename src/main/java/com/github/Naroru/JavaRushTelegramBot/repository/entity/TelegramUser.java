package com.github.Naroru.JavaRushTelegramBot.repository.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * Telegram User entity.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tg_user")
public class TelegramUser {

    @Id
    @Column(name = "chat_id")
    private String chatId;

    @Column(name = "active")
    private boolean active;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
   /* @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "group_x_user"
            , joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "group_id"))*/
    private List<GroupSubscribtion> groups;


    public void addGroup(GroupSubscribtion groupSubscribtion)
    {
        if(isNull(groupSubscribtion))
            groups = new ArrayList<>();
        groups.add(groupSubscribtion);


    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelegramUser that = (TelegramUser) o;
        return active == that.active && chatId.equals(that.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, active);
    }
}