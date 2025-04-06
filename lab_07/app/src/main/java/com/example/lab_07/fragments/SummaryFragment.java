package com.example.lab_07.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_07.R;
import com.example.lab_07.adapters.GameHistoryAdapter;
import com.example.lab_07.data.AppDatabase;
import com.example.lab_07.data.GameStats;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class SummaryFragment extends Fragment {

    private TextView tvScoreX, tvScoreO, tvDraws, tvTotal;
    private Button btnResetScores;
    private RecyclerView rvGameHistory;
    private GameHistoryAdapter historyAdapter;
    private List<GameStats> gameHistoryList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        tvScoreX = view.findViewById(R.id.tvScoreX);
        tvScoreO = view.findViewById(R.id.tvScoreO);
        tvDraws = view.findViewById(R.id.tvDraws);
        tvTotal = view.findViewById(R.id.tvTotal);
        btnResetScores = view.findViewById(R.id.btnResetScores);
        rvGameHistory = view.findViewById(R.id.rvGameHistory);

        rvGameHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        historyAdapter = new GameHistoryAdapter(gameHistoryList);
        rvGameHistory.setAdapter(historyAdapter);

        btnResetScores.setOnClickListener(v -> {
            AppDatabase db = AppDatabase.getInstance(requireContext());
            Executors.newSingleThreadExecutor().execute(() -> {
                db.gameStatsDao().clearStats();
                requireActivity().runOnUiThread(this::updateScores);
            });
        });

        updateScores();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateScores();
    }

    private void updateScores() {
        AppDatabase db = AppDatabase.getInstance(requireContext());
        Executors.newSingleThreadExecutor().execute(() -> {
            List<GameStats> statsList = db.gameStatsDao().getAll();

            int totalGames = 0;
            int winsX = 0;
            int winsO = 0;
            int draws = 0;

            if (!statsList.isEmpty()) {
                GameStats latestStats = statsList.get(0);
                totalGames = latestStats.totalGames;
                winsX = latestStats.winsX;
                winsO = latestStats.winsO;
                draws = latestStats.draws;
            }

            double pointsX = winsX + draws * 0.5;
            double pointsO = winsO + draws * 0.5;

            int finalWinsX = winsX;
            int finalWinsO = winsO;
            int finalDraws = draws;
            int finalTotalGames = totalGames;
            double finalPointsX = pointsX;
            double finalPointsO = pointsO;

            // Update UI
            requireActivity().runOnUiThread(() -> {
                tvScoreX.setText("Gracz X: " + finalPointsX + " pkt (wygrane: " + finalWinsX + ")");
                tvScoreO.setText("Gracz O: " + finalPointsO + " pkt (wygrane: " + finalWinsO + ")");
                tvDraws.setText("Remisy: " + finalDraws);
                tvTotal.setText("Łącznie rozegranych gier: " + finalTotalGames);

                // Update game history
                gameHistoryList.clear();
                gameHistoryList.addAll(statsList);
                historyAdapter.notifyDataSetChanged();
            });
        });
    }
}
