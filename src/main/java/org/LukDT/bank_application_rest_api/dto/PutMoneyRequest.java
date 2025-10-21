package org.LukDT.bank_application_rest_api.dto;

import java.math.BigDecimal;

public record PutMoneyRequest(BigDecimal summa) { }
