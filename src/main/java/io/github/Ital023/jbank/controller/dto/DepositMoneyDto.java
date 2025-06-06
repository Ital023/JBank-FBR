package io.github.Ital023.jbank.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DepositMoneyDto(@NotNull @DecimalMin("10.00") BigDecimal value) {
}
