package com.example.rollingBall.gsonAdapters;

import com.example.rollingBall.Const;
import com.example.rollingBall.arena.BadHole;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import javafx.scene.transform.Translate;

import java.io.IOException;

public class BadHoleAdapter extends TypeAdapter<BadHole> {

    @Override
    public void write(JsonWriter writer, BadHole hole) throws IOException {
        writer.beginObject();
        writer.name("radius");
        writer.value(hole.getRadius());
        writer.name("height");
        writer.value(hole.getHeight());
        writer.name("positionX");
        writer.value(hole.getPosition().getX());
        writer.name("positionY");
        writer.value(hole.getPosition().getY());
        writer.name("positionZ");
        writer.value(hole.getPosition().getZ());
        writer.name("color");
        writer.value(hole.getColorINDEX());
        writer.endObject();
    }

    @Override
    public BadHole read(JsonReader reader) throws IOException {
        double radius = Const.HOLE_RADIUS;
        double height = Const.HOLE_HEIGHT;
        double positionX = 10;
        double positionY = 10;
        double positionZ = 10;
        int color = 0;
        reader.beginObject();
        String fieldname = null;

        while (reader.hasNext()) {
            JsonToken token = reader.peek();

            if (token.equals(JsonToken.NAME)) {
                fieldname = reader.nextName();
            }

            if ("radius".equals(fieldname)) {
                token = reader.peek();
                radius = reader.nextDouble();
            }
            if ("height".equals(fieldname)) {
                token = reader.peek();
                height = reader.nextDouble();
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
            if ("color".equals(fieldname)) {
                token = reader.peek();
                color = reader.nextInt();
            }
        }

        reader.endObject();
        BadHole badHole = new BadHole(radius, height, new Translate(positionX, positionY, positionZ));
        badHole.setColor(color);
        return badHole;
    }
}
