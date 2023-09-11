package com.example.queue_based_planning;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonWriter;

public class JsonHandler {
    
    private Gson jsonHandler;
    private File selectedFile;
    private Scanner jsonReader;
	private JsonWriter jsonSerializer;

    public JsonHandler(String filename) {
        jsonHandler = new GsonBuilder().setPrettyPrinting().create();
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
        } catch (JsonIOException|IOException e) {
            e.printStackTrace();
        }
    }
    public Object ReadObjectFromJson(Type type) {
        Object deserializedObject = new Object();
        try {
            jsonReader = new Scanner(selectedFile);
            String jsonRepresentation = jsonReader.nextLine();
            deserializedObject = jsonHandler.fromJson(jsonRepresentation, type);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        /*
        FileReader j = new FileReader(selectedFile);
        JsonReader reader = new JsonReader(j);
        jsonHandler.fromJson(reader,)
        */
        return deserializedObject;
    }
}
