package com.example.rollingBall.gsonAdapters;

import com.example.rollingBall.arena.Wall;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import javafx.scene.transform.Translate;

import java.io.IOException;

public class WallAdapter extends TypeAdapter<Wall> {

    @Override
    public void write(JsonWriter writer, Wall wall) throws IOException {
        writer.beginObject();
        writer.name("width");
        writer.value(wall.getWidth());
        writer.name("height");
        writer.value(wall.getHeight());
        writer.name("depth");
        writer.value(wall.getDepth());
        writer.name("positionX");
        writer.value(wall.getPosition().getX());
        writer.name("positionY");
        writer.value(wall.getPosition().getY());
        writer.name("positionZ");
        writer.value(wall.getPosition().getZ());
        writer.endObject();
    }

    @Override
    public Wall read(JsonReader reader) throws IOException {
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
        return new Wall(width, height, depth,
                new Translate(positionX, positionY, positionZ));

    }
}
