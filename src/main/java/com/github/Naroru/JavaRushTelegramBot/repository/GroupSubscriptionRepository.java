package com.github.Naroru.JavaRushTelegramBot.repository;

import com.github.Naroru.JavaRushTelegramBot.repository.entity.GroupSubscribtion;
import com.github.Naroru.JavaRushTelegramBot.repository.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupSubscriptionRepository extends JpaRepository<GroupSubscribtion, Integer> {


}
