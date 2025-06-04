package io.github.Ital023.jbank.controller;

import io.github.Ital023.jbank.controller.dto.CreateWalletDto;
import io.github.Ital023.jbank.services.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public ResponseEntity<Void> createWallet(@RequestBody @Valid CreateWalletDto dto) {
        var wallet = walletService.createWallet(dto);

        return ResponseEntity.created(URI.create("/wallets/" + wallet.getWalletId().toString())).build();
    }




}
