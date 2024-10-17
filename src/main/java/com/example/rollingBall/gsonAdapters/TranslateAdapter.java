package com.example.rollingBall.gsonAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import javafx.scene.transform.Translate;

import java.io.IOException;

public class TranslateAdapter extends TypeAdapter<Translate> {

    @Override
    public void write(JsonWriter writer, Translate translate) throws IOException {
        if (translate != null){
            writer.beginObject();
            writer.name("x");
            writer.value(translate.getX());
            writer.name("y");
            writer.value(translate.getY());
            writer.name("z");
            writer.value(translate.getZ());
            writer.endObject();
        } else {
            writer.beginObject();
            writer.name("x");
            writer.value(0);
            writer.name("y");
            writer.value(0);
            writer.name("z");
            writer.value(0);
            writer.endObject();
        }
    }

    @Override
    public Translate read(JsonReader reader) throws IOException {
        double x = 10;
        double y = 10;
        double z = 10;
        reader.beginObject();
        String fieldname = null;

        while (reader.hasNext()) {
            JsonToken token = reader.peek();

            if (token.equals(JsonToken.NAME)) {
                fieldname = reader.nextName();
            }

            if ("x".equals(fieldname)) {
                token = reader.peek();
                x = reader.nextDouble();
            }
            if ("y".equals(fieldname)) {
                token = reader.peek();
                y = reader.nextDouble();
            }
            if ("z".equals(fieldname)) {
                token = reader.peek();
                z = reader.nextDouble();
            }
        }
        reader.endObject();
        return new Translate(x,y,z);
    }
}
