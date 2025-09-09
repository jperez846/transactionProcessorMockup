package org.example;

public class Validator {
    public void validateCSV(TransactionMsg transactionMsg){
        //System.out.println("validateCSV: validating");
        if(transactionMsg.msgId == null || transactionMsg.msgId.isEmpty()) throw new IllegalArgumentException("missing transaction message id");
        if(transactionMsg.accountId == null || transactionMsg.accountId.isEmpty()) throw new IllegalArgumentException("missing account id");
        if(transactionMsg.transactionType == TransactionType.UNKNOWN) throw new IllegalArgumentException("Unknown transaction type");
        if(transactionMsg.correlationId == null || transactionMsg.correlationId.isEmpty()) throw new IllegalArgumentException("missing correlation id");
        if(transactionMsg.currencyType == null || transactionMsg.currencyType.isEmpty()) throw new IllegalArgumentException("missing currency type");
        if(transactionMsg.paymentAmountMin < 0) throw new IllegalArgumentException("payment amount less than 0");
        if(transactionMsg.transactionTimeInMilliseconds < 0) throw new IllegalArgumentException("time in milliseconds less than 0");

    }
}
