package org.example.handlers;

import org.example.AuthorizationState;
import org.example.InMemoryState;
import org.example.TransactionMsg;

import java.util.Map;

public class AuthorizationHandler {
    private InMemoryState state;

    public AuthorizationHandler(InMemoryState inMemoryState) {
        this.state = inMemoryState;


    }

    public String handle(TransactionMsg transactionMsg) {
        AuthorizationState authorizationState = state.entries.get(transactionMsg.getCorrelationId());
        //no authorized amount added yet
        if (authorizationState == null) {
            authorizationState = new AuthorizationState(transactionMsg.getCorrelationId(), transactionMsg.getCurrencyType(), transactionMsg.getPaymentAmountMin());
            Map<String, AuthorizationState> newEntry = state.getEntries();
            newEntry.put(transactionMsg.getCorrelationId(), authorizationState);
            state.setEntries(newEntry);
            return "APPLIED";
        } else {
            return "DUPLICATE_OR_IGNORED";
        }


    }
}
