package com.example.rollingBall.gsonAdapters;

import com.example.rollingBall.arena.Lamp;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import javafx.scene.transform.Translate;

import java.io.IOException;

public class LampAdapter extends TypeAdapter<Lamp> {

    @Override
    public void write(JsonWriter writer, Lamp lamp) throws IOException {
        writer.beginObject();
        writer.name("width");
        writer.value(lamp.getWidth());
        writer.name("height");
        writer.value(lamp.getHeight());
        writer.name("depth");
        writer.value(lamp.getDepth());
        writer.name("positionX");
        writer.value(lamp.getPosition().getX());
        writer.name("positionY");
        writer.value(lamp.getPosition().getY());
        writer.name("positionZ");
        writer.value(lamp.getPosition().getZ());
        writer.endObject();
    }

    @Override
    public Lamp read(JsonReader reader) throws IOException {
        double width = 10;
        double height = 10;
        double depth = 10;
        double positionX = 10;
        double positionY = 10;
        double positionZ = 10;
        reader.beginObject();
        String fieldname = null;

        while (reader.hasNext()) {
            JsonToken token = reader.peek();

            if (token.equals(JsonToken.NAME)) {
                fieldname = reader.nextName();
            }

            if ("width".equals(fieldname)) {
                token = reader.peek();
                width = reader.nextDouble();
            }
            if ("height".equals(fieldname)) {
                token = reader.peek();
                height = reader.nextDouble();
            }
            if ("depth".equals(fieldname)) {
                token = reader.peek();
                depth = reader.nextDouble();
            }
            if ("positionX".equals(fieldname)) {
                token = reader.peek();
                positionX = reader.nextDouble();
            }
            if ("positionY".equals(fieldname)) {
                token = reader.peek();
                positionY = reader.nextDouble();
            }
            if ("positionZ".equals(fieldname)) {
                token = reader.peek();
                positionZ = reader.nextDouble();
            }
        }
        reader.endObject();
        return new Lamp(width, height, depth,
                new Translate(positionX, positionY, positionZ));
    }
}
