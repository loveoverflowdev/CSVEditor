package org.asura.csveditor.validation.configuration;

import com.google.gson.annotations.SerializedName;

public enum Type {
    @SerializedName("string")STRING,
    @SerializedName("integer")INTEGER,
    @SerializedName("number")NUMBER,
    @SerializedName("date")DATE,
    @SerializedName("datetime")DATETIME,
    @SerializedName("time")TIME
// TODO: currently not supported
//        @SerializedName("object") OBJECT,
//        @SerializedName("array") ARRAY,
//        @SerializedName("duration") DURATION,
//        @SerializedName("geopoint") GEOPOINT,
//        @SerializedName("geojson") GEOJSON
}
