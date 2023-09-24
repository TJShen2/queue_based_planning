package com.example.queuebasedplanning;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

public class JsonHandler {
    
    private Gson jsonHandler;
    private GsonBuilder jsonHandlerBuilder;
    private File selectedFile;
    private Scanner jsonReader;
	private JsonWriter jsonSerializer;

    public JsonHandler(String filename) {
        jsonHandlerBuilder = new GsonBuilder();
        jsonHandlerBuilder.setPrettyPrinting();
        jsonHandlerBuilder.setLenient();
        jsonHandlerBuilder.enableComplexMapKeySerialization();
        jsonHandlerBuilder.registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter());

        jsonHandler = jsonHandlerBuilder.create();
        selectedFile = new File(filename);
    }
    public String GetJsonRepresentation(Object objectToSerialize) {
        String jsonRepresentation = jsonHandler.toJson(objectToSerialize);
        return jsonRepresentation;
    }
    public void WriteObjectAsJson(Object objectToSerialize, Type type) {
        try {
            jsonSerializer = new JsonWriter(new FileWriter(selectedFile, false));
            jsonHandler.toJson(objectToSerialize, type, jsonSerializer);
            jsonSerializer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<QueueItem> ReadObjectFromJson() {
        Type type = new TypeToken<List<QueueItem>>() {}.getType();
        List<QueueItem> deserializedObject = new ArrayList<QueueItem>();
        
        try {
            jsonReader = new Scanner(selectedFile);

            String jsonRepresentation = new String();
            while (jsonReader.hasNext()) {
                jsonRepresentation += jsonReader.nextLine();
            }
            deserializedObject = jsonHandler.fromJson(jsonRepresentation, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deserializedObject;
    }
}