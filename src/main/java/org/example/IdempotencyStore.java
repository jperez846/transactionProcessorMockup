package org.example;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class IdempotencyStore {
    // how do we keep track of the transactions coming in?
    private Set<String> processed = ConcurrentHashMap.newKeySet();

    public boolean wasProcessed(String msgId){
        return processed.contains(msgId);

    }
    public void markProcessed(String msgId){
        processed.add(msgId);
    }
}
