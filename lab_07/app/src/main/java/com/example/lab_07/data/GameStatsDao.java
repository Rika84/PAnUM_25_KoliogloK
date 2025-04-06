package com.example.lab_07.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface GameStatsDao {
    @Insert
    void insert(GameStats stats);

    @Query("SELECT * FROM game_stats ORDER BY timestamp DESC")
    List<GameStats> getAll();

    @Query("DELETE FROM game_stats")
    void clearStats();
}
