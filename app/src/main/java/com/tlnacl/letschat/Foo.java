package com.tlnacl.letschat;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by tlnacl on 14/03/17.
 */

@AutoValue
public abstract class Foo {
    // properties...

    public static TypeAdapter<Foo> typeAdapter(Gson gson) {
        return new AutoValue_Foo.GsonTypeAdapter(gson);
    }
}