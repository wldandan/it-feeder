package com.it.epolice.sync.parser;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.it.epolice.domain.Image;
import com.it.epolice.domain.ImageType;
import com.it.epolice.utils.ImageUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class ImageParser {

    private static final TypeToken TOKEN = new TypeToken<List<Image>>() {};

    private static final JsonDeserializer<Date> DATE_DESERIALIZER = new JsonDeserializer<Date>() {
        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return ImageUtils.parseDate(json.getAsString()).toDate();
        }
    };


    private static final Gson GSON = new GsonBuilder()
            .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(Date.class, DATE_DESERIALIZER)
            .create();

    public List<Image> parse(String json) {
        return GSON.fromJson(json, TOKEN.getType());
    }
}