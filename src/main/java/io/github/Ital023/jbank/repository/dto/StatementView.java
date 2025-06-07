package io.github.Ital023.jbank.repository.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface StatementView {

    String getStatementId();
    String getType();
    BigDecimal getStatementValue();
    String getStatementReceiver();
    String getStatementSender();
    LocalDateTime getStatementDateTime();

}
