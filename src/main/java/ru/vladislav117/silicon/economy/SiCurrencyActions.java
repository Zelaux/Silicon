package ru.vladislav117.silicon.economy;

/**
 * Действия валюты.
 */
public interface SiCurrencyActions {
    /**
     * Получение баланса аккаунта.
     *
     * @param currency Валюта
     * @param account  Аккаунт
     * @return Баланс аккаунта.
     */
    int getBalance(SiCurrency currency, String account);

    /**
     * Установка баланса аккаунта.
     *
     * @param currency Валюта
     * @param account  Аккаунт
     * @param value    Значение
     */
    void setBalance(SiCurrency currency, String account, int value);

    /**
     * Добавление к балансу аккаунта.
     *
     * @param currency Валюта
     * @param account  Аккаунт
     * @param value    Значение
     */
    void addBalance(SiCurrency currency, String account, int value);

    /**
     * Проведение транзакции.
     *
     * @param currency Валюта
     * @param sender   Аккаунт отправителя
     * @param receiver Аккаунт получателя
     * @param value    Значение
     * @return Была ли проведена транзакция (false, если у отправителя не хватает средств).
     */
    boolean makeTransaction(SiCurrency currency, String sender, String receiver, int value);
}
