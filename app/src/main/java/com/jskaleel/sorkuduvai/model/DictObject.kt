package com.jskaleel.sorkuduvai.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DictObject(
    @SerializedName("EnWo")
    val enWord: String,
    @SerializedName("TaWo")
    val taWord: String,
    @SerializedName("POS")
    val pos: String,
    @SerializedName("Catg")
    val category: String
)
