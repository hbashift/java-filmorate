package ru.yandex.practicum.filmorate.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;

public class DurationSerializer extends StdSerializer<Duration> implements JsonSerializer<Duration> {
    public DurationSerializer() {
        this(null);
    }

    protected DurationSerializer(Class<Duration> t) {
        super(t);
    }

    // Override method for StdSerializer (jackson)
    @Override
    public void serialize(Duration duration, JsonGenerator jGen, SerializerProvider serializerProvider)
            throws
            IOException {
        jGen.writeNumber(duration.toSeconds());
    }

    // Override method for JsonSerializer (gson)
    @Override
    public JsonElement serialize(Duration src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toSeconds());
    }
}
