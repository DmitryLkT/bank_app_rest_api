package org.LukDT.bank_application_rest_api.dto;

import java.time.LocalDateTime;

public record OperationFilterRequest(
        long userId,
        LocalDateTime from,
        LocalDateTime to
) { }
