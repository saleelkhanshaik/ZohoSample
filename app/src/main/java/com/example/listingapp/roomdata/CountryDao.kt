package com.example.listingapp.roomdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.listingapp.model.CountryDetails

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(vararg countryDetails:CountryDetails):List<Long>

    @Query("SELECT * FROM countrydetails")
     fun getAll():List<CountryDetails>

    @Query("SELECT * FROM countrydetails WHERE name LIKE:name")
     fun loadByName(name:String):List<CountryDetails>
}