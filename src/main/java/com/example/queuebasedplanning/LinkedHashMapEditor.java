package com.example.queuebasedplanning;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class LinkedHashMapEditor {
    public static void replaceItem(LinkedHashMap<String, QueueItem> originalHashMap, String keyOfItemToRemove, String keyToInsert, QueueItem valueToInsert) {
        Object[] originalHashMapEntrySet = originalHashMap.entrySet().toArray();
        originalHashMap.clear();

        for (Object objectEntry : originalHashMapEntrySet) { 
            Entry<String,QueueItem> entry = (Entry<String,QueueItem>) objectEntry;
            if (entry.getKey().equals(keyOfItemToRemove)) {
                originalHashMap.put(keyToInsert, valueToInsert);
            } else {
                originalHashMap.put(entry.getKey(), entry.getValue());
            }
        }
    }
    public static void addItemAtIndex(LinkedHashMap<String, QueueItem> originalHashMap, String keyToInsert, QueueItem valueToInsert, int index) {
        Object[] originalHashMapEntrySet = originalHashMap.entrySet().toArray();
        originalHashMap.clear();
        int i = 0;

        for (Object objectEntry : originalHashMapEntrySet) { 
            Entry<String,QueueItem> entry = (Entry<String,QueueItem>) objectEntry;
            originalHashMap.put(entry.getKey(), entry.getValue());
            if (i == index) {
                originalHashMap.put(keyToInsert, valueToInsert);
            }
            i++;
        }
    }
    public static void removeItemAtIndex(LinkedHashMap<String, QueueItem> originalHashMap, int index) {
        Object[] originalHashMapEntrySet = originalHashMap.entrySet().toArray();
        originalHashMap.clear();
        int i = 0;

        for (Object objectEntry : originalHashMapEntrySet) { 
            Entry<String,QueueItem> entry = (Entry<String,QueueItem>) objectEntry;
            if (i != index) {
                originalHashMap.put(entry.getKey(), entry.getValue());
            }
            i++;
        }
    }
}