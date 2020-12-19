package com.example.listingapp.roomdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.listingapp.model.CountryDetails

@Dao
interface CountryDao {

    @Insert
    suspend fun insertAll(vararg countryDetails:CountryDetails):List<Long>

    @Query("SELECT * FROM countrydetails")
    suspend fun getAll():List<CountryDetails>

    @Query("SELECT * FROM countrydetails WHERE name LIKE:name")
    suspend fun loadByName(name:String):List<CountryDetails>
}