package com.tj.queuebasedplanning;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.ZonedDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.Strictness;
import com.google.gson.stream.JsonWriter;

public class Json {

    private final Gson json;
    private final GsonBuilder jsonBuilder;
    private final File jsonFile;

    private FileReader fileReader;
    private JsonWriter jsonSerializer;

    public Json(String pathname) {

        jsonBuilder = new GsonBuilder();
        jsonBuilder.setPrettyPrinting();
        jsonBuilder.setStrictness(Strictness.LENIENT);
        jsonBuilder.enableComplexMapKeySerialization();
        jsonBuilder.setVersion(1.0);
        jsonBuilder.registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter());

        json = jsonBuilder.create();
        jsonFile = new File(pathname);

        try {
            if (!jsonFile.exists()) {
                jsonFile.createNewFile();
                FileWriter fileWriter = new FileWriter(jsonFile);
                fileWriter.write("[]");
                fileWriter.close();
            }

            fileReader = new FileReader(jsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeObjectToJson(Object objectToWrite, Type type) {
        try {
            jsonSerializer = new JsonWriter(new FileWriter(jsonFile));
            json.toJson(objectToWrite, type, jsonSerializer);
            jsonSerializer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public <T> T readObjectFromJson(Type type) {
        try {
            T object = json.fromJson(fileReader, type);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
