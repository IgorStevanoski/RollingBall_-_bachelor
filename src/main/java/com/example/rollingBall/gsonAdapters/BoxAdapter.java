package com.example.rollingBall.gsonAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import javafx.scene.shape.Box;

import java.io.IOException;

public class BoxAdapter extends TypeAdapter<Box> {

    @Override
    public void write(JsonWriter writer, Box box) throws IOException {
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
    public Box read(JsonReader reader) throws IOException {
        return null;
    }
}
