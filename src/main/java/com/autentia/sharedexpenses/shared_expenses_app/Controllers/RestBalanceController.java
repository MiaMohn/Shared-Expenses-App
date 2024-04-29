package com.autentia.sharedexpenses.shared_expenses_app.Controllers;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Services.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/balance")
public class RestBalanceController {

    @Autowired
    private BalanceService balanceService;

    @GetMapping
    public ResponseEntity<Map<String, Double>> getbalances() {

        Map<String, Double> balances = balanceService.calculateUsersBalance();

        return ResponseEntity.ok(balances);

    }

    /*public ResponseEntity<Map<User, Double>> getbalances() {

        Map<User, Double> balances = balanceService.calculateUsersBalance();

        return ResponseEntity.ok(balances);

    }*/
}