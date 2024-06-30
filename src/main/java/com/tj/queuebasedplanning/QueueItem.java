package com.tj.queuebasedplanning;

import java.time.ZonedDateTime;

public class QueueItem {

    private String name;
    public String getName() { return name; }
    public void setName(String value) { name = value; }

    private String details;
    public String getDetails() { return details; }
    public void setDetails(String value) { details = value; }

    private ZonedDateTime lastChanged;
    public ZonedDateTime getLastChanged() { return lastChanged; }
    public void setLastChanged(ZonedDateTime value) { lastChanged = value; }

    public QueueItem(String name, String details, ZonedDateTime lastChanged) {
        this.name = name;
        this.details = details;
        this.lastChanged = lastChanged;
    }
}
