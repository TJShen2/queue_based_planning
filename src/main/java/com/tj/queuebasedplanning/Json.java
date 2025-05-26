package com.tj.queuebasedplanning;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.Strictness;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

public class Json {

    private final Gson json;
    private final GsonBuilder jsonBuilder;
    private final File jsonFile;

    private FileReader fileReader;
    private JsonWriter jsonSerializer;

    public Json(Path path) {

        jsonBuilder = new GsonBuilder();
        jsonBuilder.setPrettyPrinting();
        jsonBuilder.setStrictness(Strictness.LENIENT);
        jsonBuilder.enableComplexMapKeySerialization();
        jsonBuilder.setVersion(1.0);
        jsonBuilder.registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter());

        json = jsonBuilder.create();
        jsonFile = path.toFile();

        try {
            if (!jsonFile.exists()) {
                jsonFile.createNewFile();
                try (FileWriter fileWriter = new FileWriter(jsonFile)) {
                    fileWriter.write("[]");
                }
            }

            fileReader = new FileReader(jsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes an object to a JSON file.
     *
     * @param objectToWrite The object to write to the JSON file.
     * @param type          The type of the object to write.
     * @return true if the write was successful, false otherwise.
     */
    public boolean writeObjectToJson(Object objectToWrite, Type type) {
        try {
            jsonSerializer = new JsonWriter(new FileWriter(jsonFile));
            json.toJson(objectToWrite, type, jsonSerializer);
            jsonSerializer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public <T> Optional<T> readObjectFromJson(TypeToken<T> token) {
        Type type = token.getType();
        try {
            T object = json.fromJson(fileReader, type);
            return Optional.of(object);
        } catch (JsonIOException | JsonSyntaxException e) {
            return Optional.empty();
        }
    }
}
