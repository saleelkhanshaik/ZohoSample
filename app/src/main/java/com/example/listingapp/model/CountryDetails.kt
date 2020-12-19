package com.example.listingapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@Entity
data class CountryDetails(
    @ColumnInfo(name = "area")
    @SerializedName("area")
    val area: Double?,
    @ColumnInfo(name = "nativeName")
    @SerializedName("nativeName")
    val nativeName: String? = null,
    @ColumnInfo(name = "capital")
    @SerializedName("capital")
    val capital: String? = null,
    @ColumnInfo(name = "demonym")
    @SerializedName("demonym")
    val demonym: String? = null,
    @ColumnInfo(name = "flag")
    @SerializedName("flag")
    val flag: String? = null,
    @ColumnInfo(name = "alpha2Code")
    @SerializedName("alpha2Code")
    val alpha2Code: String? = null,
    @ColumnInfo(name = "borders")
    @SerializedName("borders")
    val borders: List<String?>? = null,
    @ColumnInfo(name = "subregion")
    @SerializedName("subregion")
    val subregion: String? = null,
    @ColumnInfo(name = "callingCodes")
    @SerializedName("callingCodes")
    val callingCodes: List<String?>? = null,
    @ColumnInfo(name = "gini")
    @SerializedName("gini")
    val gini: Double? = null,
    @ColumnInfo(name = "population")
    @SerializedName("population")
    val population: Double? = null,
    @ColumnInfo(name = "numericCode")
    @SerializedName("numericCode")
    val numericCode: String? = null,
    @ColumnInfo(name = "alpha3Code")
    @SerializedName("alpha3Code")
    val alpha3Code: String? = null,
    @ColumnInfo(name = "topLevelDomain")
    @SerializedName("topLevelDomain")
    val topLevelDomain: List<String?>? = null,
    @ColumnInfo(name = "timezones")
    @SerializedName("timezones")
    val timezones: List<String?>? = null,
    @ColumnInfo(name = "cioc")
    @SerializedName("cioc")
    val cioc: String? = null,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String? = null,
    @ColumnInfo(name = "altSpellings")
    @SerializedName("altSpellings")
    val altSpellings: List<String?>? = null,
    @ColumnInfo(name = "region")
    @SerializedName("region")
    val region: String? = null,
    @ColumnInfo(name = "latlng")
    @SerializedName("latlng")
    val latlng: List<Double?>? = null
) : Parcelable{
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true) var sNum:Int = 0
}


