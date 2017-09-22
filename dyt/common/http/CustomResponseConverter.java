package com.hxyd.dyt.common.http;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by win7 on 2017/2/21.
 */

public final class CustomResponseConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private Type type;

    CustomResponseConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }
    CustomResponseConverter(Gson gson, TypeAdapter<T> adapter,Type type) {
        this.gson = gson;
        this.adapter = adapter;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        Result result = new Result();
        JSONObject json = null;
        try {
            json = new JSONObject(response);
            Logger.e( "CustomResponseConverter: " + response);
            int code = json.optInt("code");
            if ( code == 0) {
                return gson.fromJson(response, type);
            } else {
                result.setCode(code);
                result.setMsg(json.optString("msg"));
            return (T)result;
            }
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e.getMessage());
        } catch (JSONException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            value.close();
        }
    }
}



