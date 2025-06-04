package io.github.Ital023.jbank.controller;

import io.github.Ital023.jbank.controller.dto.CreateWalletDto;
import io.github.Ital023.jbank.exception.WalletDataAlreadyExistsException;
import io.github.Ital023.jbank.services.WalletService;
import org.springframework.http.ProblemDetail;
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
    public ResponseEntity<Void> createWallet(@RequestBody CreateWalletDto dto) {
        var wallet = walletService.createWallet(dto);

        return ResponseEntity.created(URI.create("/wallets/" + wallet.getWalletId().toString())).build();
    }

    @ExceptionHandler(WalletDataAlreadyExistsException.class)
    public ProblemDetail WalletDataAlreadyExistsException(WalletDataAlreadyExistsException e) {
        var pd = ProblemDetail.forStatus(422);
        pd.setTitle("Wallet data already exists");
        pd.setDetail(e.getMessage());

        return pd;
    }


}
