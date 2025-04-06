package com.example.lab_07.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_07.R;
import com.example.lab_07.data.GameStats;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GameHistoryAdapter extends RecyclerView.Adapter<GameHistoryAdapter.GameViewHolder> {

    private final List<GameStats> gameStatsList;

    public GameHistoryAdapter(List<GameStats> gameStatsList) {
        this.gameStatsList = gameStatsList;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game_history, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        GameStats game = gameStatsList.get(position);

        String resultText;
        if (game.winsX > game.winsO) {
            resultText = "Wygrał gracz X";
        } else if (game.winsO > game.winsX) {
            resultText = "Wygrał gracz O";
        } else {
            resultText = "Remis";
        }

        String formattedDate = formatDate(game.timestamp);
        holder.tvGameInfo.setText(resultText + " - " + formattedDate);
    }

    @Override
    public int getItemCount() {
        return gameStatsList.size();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        TextView tvGameInfo;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGameInfo = itemView.findViewById(R.id.tvGameInfo);
        }
    }

    private String formatDate(Date timestamp) {
        if (timestamp == null) return "Brak daty";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return sdf.format(timestamp);
    }
}
