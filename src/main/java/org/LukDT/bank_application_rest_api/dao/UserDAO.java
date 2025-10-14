package org.LukDT.bank_application_rest_api.dao;

import java.math.BigDecimal;

public interface UserDAO {
    BigDecimal getBalance(long ig);

    int putMoney(long id, String summa);

    int takeMoney(long id, String summa);
}
