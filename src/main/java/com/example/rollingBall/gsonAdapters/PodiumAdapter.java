package com.example.rollingBall.gsonAdapters;

import com.example.rollingBall.arena.Podium;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class PodiumAdapter extends TypeAdapter<Podium> {

    @Override
    public void write(JsonWriter writer, Podium box) throws IOException {
        writer.beginObject();
        writer.name("width");
        writer.value(box.getWidth());
        writer.name("height");
        writer.value(box.getHeight());
        writer.name("depth");
        writer.value(box.getDepth());
        writer.endObject();
    }

    @Override
    public Podium read(JsonReader reader) throws IOException {
        double width = 10;
        double height = 10;
        double depth = 10;
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
        }
        reader.endObject();
        return new Podium(width, height, depth);
    }
}
