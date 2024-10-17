package com.example.rollingBall.gsonAdapters;

import com.example.rollingBall.Const;
import com.example.rollingBall.arena.RoundWall;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import javafx.scene.transform.Translate;

import java.io.IOException;

public class RoundWallAdapter extends TypeAdapter<RoundWall> {

    @Override
    public void write(JsonWriter writer, RoundWall roundWall) throws IOException {
        writer.beginObject();
        writer.name("radius");
        writer.value(roundWall.getRadius());
        writer.name("height");
        writer.value(roundWall.getHeight());
        writer.name("positionX");
        writer.value(roundWall.getPosition().getX());
        writer.name("positionY");
        writer.value(roundWall.getPosition().getY());
        writer.name("positionZ");
        writer.value(roundWall.getPosition().getZ());
        writer.endObject();
    }

    @Override
    public RoundWall read(JsonReader reader) throws IOException {
        double radius = Const.ROUNDWALL_RADIUS;
        double height = Const.ROUNDWALL_HEIGHT;
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
        }
        reader.endObject();
        return new RoundWall(radius, height, new Translate(positionX, positionY, positionZ));
    }
}
