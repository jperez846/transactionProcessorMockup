package org.example;

import java.util.HashMap;
import java.util.Map;

public class InMemoryState {
    public Map<String,AuthorizationState> entries = new HashMap<>();

    public Map<String, AuthorizationState> getEntries() {
        return entries;
    }

    public void setEntries(Map<String, AuthorizationState> entries) {
        this.entries = entries;
    }
}
