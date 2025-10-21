package org.LukDT.bank_application_rest_api.service;

import org.LukDT.bank_application_rest_api.entity.Operation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ApplicationService {
    List<Operation> getOperationList(long id, LocalDateTime from, LocalDateTime to);

    BigDecimal getBalance(long id);

    int putMoney(long id, String summa);

    int takeMoney(long id, String summa);
}
