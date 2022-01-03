package com.jskaleel.sorkuduvai.utils.extensions

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


inline fun <reified T> Gson.fromJson(value: String): T {
    return fromJson(value, toTypeToken<T>())
}

inline fun <reified T> Gson.fromJson(value: JsonElement): T {
    return fromJson(value, toTypeToken<T>())
}

/** Returns the type of the class*/
inline fun <reified T> toTypeToken(): Type {
    return object : TypeToken<T>() {}.type
}
