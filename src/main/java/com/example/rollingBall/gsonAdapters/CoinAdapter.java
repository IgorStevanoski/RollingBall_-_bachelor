package com.example.rollingBall.gsonAdapters;

import com.example.rollingBall.Const;
import com.example.rollingBall.arena.Coin;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import javafx.scene.transform.Translate;

import java.io.IOException;

public class CoinAdapter extends TypeAdapter<Coin> {

    @Override
    public void write(JsonWriter writer, Coin coin) throws IOException {
        writer.beginObject();
        writer.name("radius");
        writer.value(coin.getRadius());
        writer.name("height");
        writer.value(coin.getHeight());
        writer.name("positionX");
        writer.value(coin.getPosition().getX());
        writer.name("positionY");
        writer.value(coin.getPosition().getY());
        writer.name("positionZ");
        writer.value(coin.getPosition().getZ());
        writer.endObject();
    }

    @Override
    public Coin read(JsonReader reader) throws IOException {
        double radius = Const.HOLE_RADIUS;
        double height = Const.HOLE_HEIGHT;
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
        return new Coin(radius, height, null, new Translate(positionX, positionY, positionZ));
    }
}
