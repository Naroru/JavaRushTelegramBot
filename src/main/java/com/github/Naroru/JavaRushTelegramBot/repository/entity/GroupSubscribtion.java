package com.github.Naroru.JavaRushTelegramBot.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

@Entity
@Table(name = "group_sub")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class GroupSubscribtion {

    @Id
    private int id;

    private String title;

    @Column(name = "last_post_id")
    private int lastPostID;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupSubscribtion that = (GroupSubscribtion) o;
        return id == that.id && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
