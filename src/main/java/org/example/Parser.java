package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;

import static org.example.TransactionType.*;

public class Parser {
    private String msgId;
    private TransactionType transactionType;

    private String correlationId;

    private String accountId;
    private String currencyType;
    private long minAmount;
    private long timeInMilliseconds;

    public TransactionMsg parseCSV(String input) {
       // System.out.println("Parsing out csv input");
        if (input != null) {
            String[] values = input.split(",");
            if (values.length < 7)
                throw new IllegalArgumentException("expected 7 values on the input, got: " + values.length);
            this.msgId = values[0];
            this.transactionType = parseTransactionType(values[1].trim());
            this.correlationId = values[2];
            this.accountId = values[3];
            this.minAmount = Long.parseLong(values[4]);
            this.currencyType = values[5];
            this.timeInMilliseconds = Long.parseLong(values[6]);
        }


        return new TransactionMsg(msgId, transactionType, correlationId, accountId, minAmount, currencyType, timeInMilliseconds);


    }
    public TransactionMsg parseJSON(String input){
        System.out.println("Parsing JSON");
        ObjectMapper objectMapper = new ObjectMapper();
        TransactionMsg transactionMsg;
        try{
            transactionMsg = objectMapper.readValue(input, TransactionMsg.class);

        }
        catch(Exception e){
            throw new IllegalArgumentException("input type json, there was an issue: "+ e.getMessage());

        }

        return transactionMsg;
    }

    public TransactionMsg parseString(String input){
        if(input == null) throw new IllegalArgumentException("could not parse empty string");
        String firstFour = input.substring(0, 4); // 1-4
        String lengthOfAccNumber = input.substring(5,6); // 5-6
        String accountNumber = processAccountNumber(input, Integer.parseInt(lengthOfAccNumber));
        long amountInCents = processAmountInCents(input, Integer.parseInt(lengthOfAccNumber));


        TransactionType type = firstFour.equals("1010") ? DEPOSIT : WITHDRAWAL;
        this.msgId = firstFour;
        this.currencyType = "USD";
        this.accountId = accountNumber;
        this.transactionType = type;
        this.correlationId = accountNumber;
        this.minAmount = amountInCents;
        this.timeInMilliseconds = Instant.now().toEpochMilli();





    return new TransactionMsg(msgId, transactionType, correlationId, accountId, minAmount, currencyType, timeInMilliseconds);
    }

    public TransactionType parseTransactionType(String type) {
        switch (type.toUpperCase()) {
            case "AUTHORIZATION":
                return AUTHORIZATION;
            case "REVERSAL":
                return REVERSAL;
            case "CAPTURE":
                return CAPTURE;
            case "REFUND":
                return REFUND;
            default:
                return UNKNOWN;

        }

    }
    public String processAccountNumber(String input, int lengthOfAccNumber){
        return input.substring(6, lengthOfAccNumber + 6);


    }
    public long processAmountInCents(String input, int lengthOfAccNumber){
        int lengthOfInput = input.length();
        return Long.parseLong(input.substring(lengthOfAccNumber + 6, lengthOfInput));

    }
}
