package com.github.Naroru.JavaRushTelegramBot.repository;

import com.github.Naroru.JavaRushTelegramBot.repository.entity.TelegramUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelegramUserRepository extends CrudRepository<TelegramUser, Long> {

    List<TelegramUser> findAllByActiveTrue();

    List<TelegramUser> findAllByActiveFalse();
}
