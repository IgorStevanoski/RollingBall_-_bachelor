package com.example.rollingBall.gsonAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    @Override
    public void write(JsonWriter writer, LocalDate date) throws IOException {
        writer.beginObject();
        writer.name("day");
        writer.value(date.getDayOfMonth());
        writer.name("month");
        writer.value(date.getMonthValue());
        writer.name("year");
        writer.value(date.getYear());
        writer.endObject();
    }

    @Override
    public LocalDate read(JsonReader reader) throws IOException {
        int day = 0, month = 0, year = 0;
        reader.beginObject();
        String fieldname = null;

        while (reader.hasNext()) {
            JsonToken token = reader.peek();

            if (token.equals(JsonToken.NAME)) {
                fieldname = reader.nextName();
            }

            if ("day".equals(fieldname)) {
                token = reader.peek();
                day = reader.nextInt();
            }
            if ("month".equals(fieldname)) {
                token = reader.peek();
                month = reader.nextInt();
            }
            if ("year".equals(fieldname)) {
                token = reader.peek();
                year = reader.nextInt();
            }
        }
        reader.endObject();
        return LocalDate.of(year, month, day);
    }
}
