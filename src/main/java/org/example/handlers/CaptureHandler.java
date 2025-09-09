package org.example.handlers;

import org.example.AuthorizationState;
import org.example.InMemoryState;
import org.example.TransactionMsg;
import org.example.TransactionType;

import java.util.Map;

public class CaptureHandler {
    InMemoryState state;
    public CaptureHandler(InMemoryState state){
        this.state = state;

    }
    public String handle(TransactionMsg transactionMsg){
        AuthorizationState currentState = state.getEntries().get(transactionMsg.getCorrelationId());
        if(currentState == null){
            return "REJECTED: no authoization found";
        }
        if(currentState.remainingToCapture() < transactionMsg.getPaymentAmountMin()){
            return "REJECTED: captured amount is greater than the authorized amount";
        }
        Map<String, AuthorizationState> newEntry = state.getEntries();
        currentState.setCapturedTotal(currentState.getCapturedTotal() + transactionMsg.getPaymentAmountMin());
        newEntry.put(transactionMsg.getCorrelationId(), currentState);
        state.setEntries(newEntry);

        return "APPLIED";



    }
}
