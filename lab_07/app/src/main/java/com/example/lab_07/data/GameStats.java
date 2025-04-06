package com.example.lab_07.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "game_stats")
@TypeConverters({Converters.class})  // konwertery typów
public class GameStats {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public Date timestamp;      // data i godzina utworzenia wpisu
    public int totalGames;      // łączna liczba rozegranych gier
    public int winsX;           // liczba wygranych przez X
    public int winsO;           // liczba wygranych przez O
    public int draws;           // liczba remisów

    @Ignore
    public GameStats(Date timestamp, int totalGames, int winsX, int winsO, int draws) {
        this.timestamp = timestamp;
        this.totalGames = totalGames;
        this.winsX = winsX;
        this.winsO = winsO;
        this.draws = draws;
    }

    public GameStats() {}
}
