package com.github.Naroru.JavaRushTelegramBot.service;

import com.github.Naroru.JavaRushTelegramBot.repository.entity.TelegramUser;

import java.util.List;
import java.util.Optional;

public interface TelegramUserService {

    /**
     * Save provided {@link TelegramUser} entity.
     *
     * @param  telegramUser provided telegram user.
     */
    void save(TelegramUser telegramUser);

    /**
     * Retrieve all active {@link TelegramUser}.
     *
     * @return the collection of the active {@link TelegramUser} objects.
     */
    List<TelegramUser> retrieveAllActiveUsers();

    List<TelegramUser> retrieveAllInactiveUsers();


    /**
     * Find {@link TelegramUser} by chatId.
     *
     * @param chatId provided Chat ID
     * @return {@link TelegramUser} with provided chat ID or null otherwise.
     */
    Optional<TelegramUser> findByChatId(String chatId);
}
