package org.example;

public class TransactionMsg {
    String msgId;
    TransactionType transactionType; // Authorization, Reversal, Capture, Refund
    String correlationId; // relationship id with merchant
    String accountId; // users account id

    String currencyType;
    //64-bit signed integer (can hold more)
    long paymentAmountMin;

    long transactionTimeInMilliseconds;


    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public long getPaymentAmountMin() {
        return paymentAmountMin;
    }

    public void setPaymentAmountMin(long paymentAmountMin) {
        this.paymentAmountMin = paymentAmountMin;
    }

    public long getTransactionTimeInMilliseconds() {
        return transactionTimeInMilliseconds;
    }

    public void setTransactionTimeInMilliseconds(long transactionTimeInMilliseconds) {
        this.transactionTimeInMilliseconds = transactionTimeInMilliseconds;
    }

    public TransactionMsg() {
        // empty constructor needed for Jackson
    }

    public TransactionMsg(String msgId, TransactionType transactionType, String correlationId, String accountId, long paymentAmountMin, String currencyType, long transactionTimeInMilliseconds) {
        this.msgId = msgId;
        this.transactionType = transactionType;
        this.correlationId = correlationId;
        this.accountId = accountId;
        this.currencyType = currencyType;
        this.paymentAmountMin = paymentAmountMin;
        this.transactionTimeInMilliseconds = transactionTimeInMilliseconds;
    }


}
