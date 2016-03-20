package Util;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by huanganna on 16/3/20.
 */
public class ConfDeserializer implements JsonDeserializer<Conf> {

    @Override
    public Conf deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        //deserialize
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement hostElement = jsonObject.get("HOST");
        final String host = hostElement.getAsString();

        final int port = jsonObject.get("PORT").getAsInt();

        //
        final Conf conf = new Conf();
        conf.setHost(host);
        conf.setPort(port);
        return conf;
    }
}
