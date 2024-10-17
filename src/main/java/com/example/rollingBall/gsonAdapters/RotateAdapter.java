package com.example.rollingBall.gsonAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import javafx.scene.transform.Rotate;

import java.io.IOException;

public class RotateAdapter extends TypeAdapter<Rotate> {

    @Override
    public void write(JsonWriter writer, Rotate rotate) throws IOException {
        writer.beginObject();
        writer.name("angle");
        writer.value(rotate.getAngle());
        writer.endObject();
    }

    @Override
    public Rotate read(JsonReader reader) throws IOException {
        return null;
    }
}
