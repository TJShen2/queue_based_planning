package com.example.queuebasedplanning;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapEditor {
    public static LinkedHashMap<String, QueueItem> replaceItem(LinkedHashMap<String, QueueItem> originalHashMap, String keyOfItemToRemove, String keyToInsert, QueueItem valueToInsert) {
        LinkedHashMap<String, QueueItem> newHashMap = new LinkedHashMap<String, QueueItem>();

        for (Map.Entry<String,QueueItem> entry : originalHashMap.entrySet()) { 
            if (entry.getKey().equals(keyOfItemToRemove)) {
                newHashMap.put(keyToInsert, valueToInsert);
            } else {
                newHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return newHashMap;
    }
    public static LinkedHashMap<String, QueueItem> addItemAtIndex(LinkedHashMap<String, QueueItem> originalHashMap, String keyToInsert, QueueItem valueToInsert, int index) {
        LinkedHashMap<String, QueueItem> newHashMap = new LinkedHashMap<String, QueueItem>();
        int i = 0;

        for (Map.Entry<String,QueueItem> entry : originalHashMap.entrySet()) {
            newHashMap.put(entry.getKey(), entry.getValue());
            if (i == index) {
                newHashMap.put(keyToInsert, valueToInsert);
            }
            i++;
        }
        return newHashMap;
    }
    public static LinkedHashMap<String, QueueItem> removeItemAtIndex(LinkedHashMap<String, QueueItem> originalHashMap, int index) {
        LinkedHashMap<String, QueueItem> newHashMap = new LinkedHashMap<String, QueueItem>();
        int i = 0;

        for (Map.Entry<String,QueueItem> entry : originalHashMap.entrySet()) {
            if (i != index) {
                newHashMap.put(entry.getKey(), entry.getValue());
            }
            i++;
        }
        return newHashMap;
    }
}