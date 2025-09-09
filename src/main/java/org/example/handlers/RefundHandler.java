package org.example.handlers;

import org.example.AuthorizationState;
import org.example.InMemoryState;
import org.example.TransactionMsg;

import java.util.Map;

public class RefundHandler {
    InMemoryState state;

    public RefundHandler(InMemoryState state){
        this.state = state;

    }
    public String handle(TransactionMsg transactionMsg){
        AuthorizationState currentState = state.getEntries().get(transactionMsg.getCorrelationId());
        if( transactionMsg.getPaymentAmountMin() > currentState.remainingToRefund()){
            return "REJECTED: refund greater than captured amount";
        }
        Map<String, AuthorizationState> newEntry = state.getEntries();
        currentState.setRefundedTotal(currentState.getRefundedTotal() + transactionMsg.getPaymentAmountMin());
        newEntry.put(transactionMsg.getCorrelationId(), currentState);
        state.setEntries(newEntry);

        return "APPLIED";

    }
}
