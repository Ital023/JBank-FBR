package io.github.Ital023.jbank.controller;

import io.github.Ital023.jbank.controller.dto.CreateWalletDto;

import io.github.Ital023.jbank.controller.dto.TransferMoneyDto;
import io.github.Ital023.jbank.services.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping(path = "/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<Void> transfer(@RequestBody @Valid TransferMoneyDto dto) {
        transferService.transferMoney(dto);

        return ResponseEntity.ok().build();
    }





}
