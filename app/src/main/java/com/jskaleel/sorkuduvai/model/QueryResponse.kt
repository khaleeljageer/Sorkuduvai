package com.jskaleel.sorkuduvai.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * list1 - செந்தமிழ்ச் சொற்பிறப்பியல் பேரகரமுதலி
 * list2 - சொற்பிறப்பியல்
 * list3 - தமிழ் இணையக் கல்விக்கழக கலைச்சொல் பேரகராதி
 * list4 - ஆட்சிச் சொல் அகராதி
 * list5 - சொல் அகராதி
 * list6 - Unknown Category
 * */
@Keep
sealed class QueryResponse {
    data class QuerySuccess(
        val list1: List<DictObject>,
        val list2: List<String>,
        val list3: List<DictObject>,
        val list4: List<DictObject>,
        val list5: List<DictObject>,
        val list6: List<String>,
        val lang: String,
        val status: String,
        @SerializedName("TotalCount")
        val totalCount: Int
    ) : QueryResponse()

    data class QueryError(
        val lang: String,
        val status: String
    ) : QueryResponse()
}
