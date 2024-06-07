package com.autentia.sharedexpenses.shared_expenses_app.Utilities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinTransaction {

    public static List<String> determinesMinTransactions(Map<String, Double> balance) {
        List<String> transactions = new ArrayList<>();
        Map<String, Double> negativeBalance = new HashMap<>();
        Map<String, Double> positiveBalance = new HashMap<>();

        for (Map.Entry<String, Double> value : balance.entrySet()) {

            if (value.getValue() > 0) {
                positiveBalance.put(value.getKey(), value.getValue());
            } else if (value.getValue() < 0) {
                negativeBalance.put(value.getKey(), value.getValue());
            }
        }

        while (!negativeBalance.isEmpty()) {

            String debtor = negativeBalance.keySet().iterator().next();
            BigDecimal amountToPay = BigDecimal.valueOf(negativeBalance.get(debtor));

            String beneficiary = positiveBalance.keySet().iterator().next();
            BigDecimal amountToReceive = BigDecimal.valueOf(positiveBalance.get(beneficiary));

            BigDecimal minAmount = amountToPay.abs().min(amountToReceive);
            transactions.add(debtor + " has to pay " + beneficiary + " " + minAmount + "€" );

            if (amountToPay.abs().compareTo(amountToReceive) > 0) {
                negativeBalance.put(debtor, amountToPay.add(minAmount).doubleValue());
                positiveBalance.remove(beneficiary);
            } else if (minAmount.abs().compareTo(amountToReceive) < 0) {
                positiveBalance.put(beneficiary, amountToReceive.subtract(minAmount).doubleValue());
                negativeBalance.remove(debtor);
            } else {
                negativeBalance.remove(debtor);
                positiveBalance.remove(beneficiary);
            }
        }

        return transactions;

    }
}
