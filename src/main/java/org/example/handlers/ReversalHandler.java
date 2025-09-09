package org.example.handlers;

import org.example.AuthorizationState;
import org.example.InMemoryState;
import org.example.TransactionMsg;

import java.util.HashMap;
import java.util.Map;

public class ReversalHandler {
    private InMemoryState state;
    public ReversalHandler(InMemoryState state){
        this.state = state;
    }
    public String handle(TransactionMsg msg){
        AuthorizationState currentState = state.getEntries().get(msg.getCorrelationId());
        if(currentState.remainingToRevert() > currentState.getAuthorizedTotal()){
            return "REJECTED: revert amount greater than authorized amount";
        }
        if(currentState.remainingToCapture() > 0){
            return "REJECTED: capture amount detected, do refund instead";

        }
        currentState.setRevertedTotal(currentState.getRefundedTotal() + msg.getPaymentAmountMin());
        Map<String, AuthorizationState> newEntry = state.getEntries();
        newEntry.put(msg.getCorrelationId(), currentState);
        state.setEntries(newEntry);
        return "APPLIED";




    }
}

