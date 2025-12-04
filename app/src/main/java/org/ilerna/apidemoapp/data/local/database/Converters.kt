package org.ilerna.apidemoapp.data.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.ilerna.apidemoapp.domain.model.Planet
import org.ilerna.apidemoapp.domain.model.Transformation

/**
 * Converters - TypeConverters for Room database to handle complex data types
 */
class Converters {
    private val gson = Gson()

    /**
     * Convert List<Transformation> to JSON String
     */
    @TypeConverter
    fun fromTransformationList(transformations: List<Transformation>): String {
        return gson.toJson(transformations)
    }

    /**
     * Convert JSON String to List<Transformation>
     */
    @TypeConverter
    fun toTransformationList(transformationsString: String): List<Transformation> {
        val listType = object : TypeToken<List<Transformation>>() {}.type
        return gson.fromJson(transformationsString, listType)
    }

    /**
     * Convert Planet object to JSON String
     */
    @TypeConverter
    fun fromPlanet(planet: Planet?): String? {
        return planet?.let { gson.toJson(it) }
    }

    /**
     * Convert JSON String to Planet object
     */
    @TypeConverter
    fun toPlanet(planetString: String?): Planet? {
        return planetString?.let { gson.fromJson(it, Planet::class.java) }
    }
}
