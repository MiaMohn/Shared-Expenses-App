package com.autentia.sharedexpenses.shared_expenses_app.Controllers;

import com.autentia.sharedexpenses.shared_expenses_app.Services.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/balance")
public class RestBalanceController {

    @Autowired
    private BalanceService balanceService;

    //List balances for all users:

    @GetMapping
    public ResponseEntity<Map<String, Double>> getBalances() {

        Map<String, Double> balances = balanceService.calculateUsersBalance();

        return ResponseEntity.ok(balances);

    }

    //List the minimum transactions:

    @GetMapping(path = "/transactions")
    public List<String> getTransactions() {
        List<String> transactions = balanceService.calculateMinTransactions();
        return transactions;
    }
}
