package io.github.Ital023.jbank.controller.dto;

import io.github.Ital023.jbank.entities.Wallet;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferMoneyDto(@NotNull UUID sender,
                               @NotNull @DecimalMin("0.01") BigDecimal value,
                               @NotNull UUID receiver) {
}
