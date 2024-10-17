package com.example.rollingBall.gsonAdapters;

import com.example.rollingBall.arena.Arena;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ArenaAdapter extends TypeAdapter<Arena> {

    @Override
    public void write(JsonWriter writer, Arena group) throws IOException {
        writer.beginObject();
        writer.name("rotateX");
        writer.value(group.getRotateX().getAngle());
        writer.name("rotateZ");
        writer.value(group.getRotateZ().getAngle());
        writer.endObject();
    }

    @Override
    public Arena read(JsonReader reader) throws IOException {
        Arena arena = new Arena();
        double rotateX = 0;
        double rotateZ = 0;
        reader.beginObject();
        String fieldname = null;

        while (reader.hasNext()) {
            JsonToken token = reader.peek();

            if (token.equals(JsonToken.NAME)) {
                fieldname = reader.nextName();
            }

            if ("rotateX".equals(fieldname)) {
                token = reader.peek();
                rotateX = reader.nextDouble();
            }

            if("rotateZ".equals(fieldname)) {
                token = reader.peek();
                rotateZ = reader.nextDouble();
            }
        }
        reader.endObject();
        arena.setRotate(rotateX, rotateZ);
        return arena;
    }

}
