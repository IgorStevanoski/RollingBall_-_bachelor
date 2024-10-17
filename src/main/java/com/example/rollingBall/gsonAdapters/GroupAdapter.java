package com.example.rollingBall.gsonAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import javafx.scene.Group;

import java.io.IOException;

public class GroupAdapter extends TypeAdapter<Group> {

    @Override
    public void write(JsonWriter writer, Group group) throws IOException {
        writer.beginObject();
//        writer.name("autoSizeChildren");
//        writer.value(group.getChildren());
        writer.endObject();
    }

    @Override
    public Group read(JsonReader reader) throws IOException {
        return null;
    }
}
