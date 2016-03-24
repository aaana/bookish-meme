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
        final JsonObject serverObject = jsonObject.getAsJsonObject("server");
        JsonElement hostElement = serverObject.get("HOST");
        final String host = hostElement.getAsString();

        final int port = serverObject.get("PORT").getAsInt();
        final int maxMsgNumber = serverObject.get("MaxMsgNumber").getAsInt();
        final int maxMsgNumberPerSec = serverObject.get("MaxMsgNumberPerSec").getAsInt();

        //
        final Conf conf = new Conf();
        conf.setHost(host);
        conf.setPort(port);
        conf.setMaxMsgNumber(maxMsgNumber);
        conf.setMaxMsgNumberPerSec(maxMsgNumberPerSec);
        return conf;
    }
}
