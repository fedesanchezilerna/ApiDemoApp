package org.ilerna.apidemoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.ilerna.apidemoapp.data.local.entity.FavoriteCharacterEntity

/**
 * CharacterDao - Data Access Object for favorite characters operations
 */
@Dao
interface CharacterDao {
    
    /**
     * Get all favorite characters
     */
    @Query("SELECT * FROM favorite_characters")
    suspend fun getAllFavorites(): List<FavoriteCharacterEntity>
    
    /**
     * Get a specific character by ID
     */
    @Query("SELECT * FROM favorite_characters WHERE id = :characterId")
    suspend fun getCharacterById(characterId: Int): FavoriteCharacterEntity?
    
    /**
     * Add a character to favorites
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(character: FavoriteCharacterEntity)
    
    /**
     * Remove a character from favorites
     */
    @Delete
    suspend fun deleteFavorite(character: FavoriteCharacterEntity)
    
    /**
     * Delete a character by ID
     */
    @Query("DELETE FROM favorite_characters WHERE id = :characterId")
    suspend fun deleteFavoriteById(characterId: Int)
    
    /**
     * Check if a character is in favorites
     */
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_characters WHERE id = :characterId)")
    suspend fun isFavorite(characterId: Int): Boolean
}
