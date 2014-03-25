package com.it.epolice.sync.parser;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.it.epolice.domain.Image;
import com.it.epolice.domain.ImageType;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class ImageParser {

    private static final TypeToken TOKEN = new TypeToken<List<Image>>() {};

    private static final Gson GSON = new GsonBuilder()
            .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public List<Image> parse(String json) {
        return GSON.fromJson(json, TOKEN.getType());
    }
}