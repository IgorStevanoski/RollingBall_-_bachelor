package com.example.rollingBall.gsonAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import javafx.scene.PointLight;

import java.io.IOException;

public class PointLightAdapter extends TypeAdapter<PointLight> {


    @Override
    public void write(JsonWriter writer, PointLight pointLight) throws IOException {
        writer.beginObject();
        writer.name("position");
        writer.value("nesto");
        writer.endObject();
    }

    @Override
    public PointLight read(JsonReader reader) throws IOException {
        PointLight pointLight = new PointLight();
        reader.beginObject();
        String fieldname = null;
        String tmp = null;

        while (reader.hasNext()) {
            JsonToken token = reader.peek();

            if (token.equals(JsonToken.NAME)) {
                fieldname = reader.nextName();
            }

            if ("position".equals(fieldname)) {
                token = reader.peek();
                tmp = reader.nextString();
            }
        }
        reader.endObject();
        return pointLight;
    }
}
