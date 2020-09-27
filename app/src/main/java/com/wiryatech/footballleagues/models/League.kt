package com.wiryatech.footballleagues.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League (
    var idLeague: Int? = 0,
    var strLeague: String? = null,
    var badge: Int? = 0,
    var intFormedYear: Int? = 0,
    var dateFirstEvent: String? = null,
    var strCountry: String? = null
) : Parcelable