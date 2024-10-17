package com.example.rollingBall.gsonAdapters;

import com.example.rollingBall.arena.IceToken;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import javafx.scene.transform.Translate;

import java.io.IOException;

public class IceTokenAdapter extends TypeAdapter<IceToken> {

    @Override
    public void write(JsonWriter writer, IceToken iceToken) throws IOException {
        writer.beginObject();
        writer.name("positionX");
        writer.value(iceToken.getPosition().getX());
        writer.name("positionY");
        writer.value(iceToken.getPosition().getY());
        writer.name("positionZ");
        writer.value(iceToken.getPosition().getZ());
        writer.endObject();
    }

    @Override
    public IceToken read(JsonReader reader) throws IOException {
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
        return new IceToken(new Translate(positionX, positionY, positionZ));
    }
}
