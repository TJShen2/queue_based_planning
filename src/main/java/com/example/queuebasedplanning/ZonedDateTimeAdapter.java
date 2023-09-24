package com.example.queuebasedplanning;

import java.io.IOException;
import java.time.ZonedDateTime;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class ZonedDateTimeAdapter extends TypeAdapter<ZonedDateTime> {
    public void write(JsonWriter out, ZonedDateTime value) throws IOException {
        out.value(value.toString());
    }

    public ZonedDateTime read(JsonReader in) throws IOException {
        return ZonedDateTime.parse(in.nextString());
    }
}