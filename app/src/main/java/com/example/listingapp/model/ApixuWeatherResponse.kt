package com.example.listingapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ApixuWeatherResponse(

	@field:SerializedName("request")
	val request: Request? = null,

	@field:SerializedName("current")
	val current: Current? = null,

	@field:SerializedName("location")
	val location: Location? = null
) : Parcelable

@Parcelize
data class Request(

	@field:SerializedName("unit")
	val unit: String? = null,

	@field:SerializedName("query")
	val query: String? = null,

	@field:SerializedName("language")
	val language: String? = null,

	@field:SerializedName("type")
	val type: String? = null
) : Parcelable

@Parcelize
data class Current(

	@field:SerializedName("weather_descriptions")
	val weatherDescriptions: List<String?>? = null,

	@field:SerializedName("observation_time")
	val observationTime: String? = null,

	@field:SerializedName("wind_degree")
	val windDegree: Int? = null,

	@field:SerializedName("visibility")
	val visibility: Int? = null,

	@field:SerializedName("weather_icons")
	val weatherIcons: List<String?>? = null,

	@field:SerializedName("feelslike")
	val feelslike: Int? = null,

	@field:SerializedName("is_day")
	val isDay: String? = null,

	@field:SerializedName("wind_dir")
	val windDir: String? = null,

	@field:SerializedName("pressure")
	val pressure: Int? = null,

	@field:SerializedName("cloudcover")
	val cloudcover: Int? = null,

	@field:SerializedName("precip")
	val precip: Double? = null,

	@field:SerializedName("uv_index")
	val uvIndex: Double? = null,

	@field:SerializedName("temperature")
	val temperature: Double? = null,

	@field:SerializedName("humidity")
	val humidity: Double? = null,

	@field:SerializedName("wind_speed")
	val windSpeed: Int? = null,

	@field:SerializedName("weather_code")
	val weatherCode: Int? = null
) : Parcelable

@Parcelize
data class Location(

	@field:SerializedName("localtime")
	val localtime: String? = null,

	@field:SerializedName("utc_offset")
	val utcOffset: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("localtime_epoch")
	val localtimeEpoch: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("timezone_id")
	val timezoneId: String? = null,

	@field:SerializedName("lon")
	val lon: String? = null,

	@field:SerializedName("region")
	val region: String? = null,

	@field:SerializedName("lat")
	val lat: String? = null
) : Parcelable
