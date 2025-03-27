package com.example.lab_06.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.lab_06.R;
import com.example.lab_06.utils.ConversionUtils;
import com.google.android.material.textfield.TextInputEditText;

public class CurrencyFragment extends Fragment {

    private TextInputEditText etAmountInput, etAmountOutput;
    private Spinner spCurrencyFrom, spCurrencyTo;
    private Button btnSwap;
    private GridLayout gridKeyboard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currency, container, false);

        // inicjalizacja widoków
        etAmountInput = view.findViewById(R.id.etAmountInput);
        etAmountOutput = view.findViewById(R.id.etAmountOutput);
        spCurrencyFrom = view.findViewById(R.id.spCurrencyFrom);
        spCurrencyTo = view.findViewById(R.id.spCurrencyTo);
        btnSwap = view.findViewById(R.id.btnSwap);
        gridKeyboard = view.findViewById(R.id.gridKeyboard);

        // wyłączanie systemową klawiatury
        etAmountInput.setShowSoftInputOnFocus(false);
        etAmountInput.setInputType(EditorInfo.TYPE_NULL);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.currency_array,
                R.layout.spinner_item
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spCurrencyFrom.setAdapter(adapter);
        spCurrencyTo.setAdapter(adapter);

        // obsługa przycisku zamiany walut – zamienia spinnery oraz wartości w polach
        btnSwap.setOnClickListener(v -> {
            int posFrom = spCurrencyFrom.getSelectedItemPosition();
            int posTo = spCurrencyTo.getSelectedItemPosition();
            spCurrencyFrom.setSelection(posTo);
            spCurrencyTo.setSelection(posFrom);

            String inputVal = etAmountInput.getText().toString().trim();
            String outputVal = etAmountOutput.getText().toString().trim();
            if (!TextUtils.isEmpty(inputVal) || !TextUtils.isEmpty(outputVal)) {
                etAmountInput.setText(outputVal);
                etAmountOutput.setText(inputVal);
            }
            updateConversion();
            buildKeyboard();
        });

        spCurrencyFrom.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                buildKeyboard();
                updateConversion();
            }
            @Override public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });
        spCurrencyTo.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                updateConversion();
            }
            @Override public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        etAmountInput.addTextChangedListener(new TextWatcher(){
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count){
                updateConversion();
            }
            @Override public void afterTextChanged(Editable s){}
        });

        buildKeyboard();

        return view;
    }

    private void buildKeyboard() {
        if (gridKeyboard == null) return;
        gridKeyboard.removeAllViews();

        String keys = "0123456789.";
        for (char c : keys.toCharArray()){
            Button btn = new Button(getContext());
            btn.setText(String.valueOf(c));
            btn.setTextSize(18f);
            btn.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.colorPrimaryDark));
            btn.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));
            btn.setOnClickListener(v -> {
                String current = etAmountInput.getText().toString();
                etAmountInput.setText(current + c);
            });
            gridKeyboard.addView(btn);
        }

        Button btnDel = new Button(getContext());
        btnDel.setText("DEL");
        btnDel.setTextSize(18f);
        btnDel.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.colorPrimaryDark));
        btnDel.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));
        btnDel.setOnClickListener(v -> {
            String current = etAmountInput.getText().toString();
            if (!TextUtils.isEmpty(current)) {
                etAmountInput.setText(current.substring(0, current.length() - 1));
            }
        });
        gridKeyboard.addView(btnDel);

        Button btnClear = new Button(getContext());
        btnClear.setText("CLR");
        btnClear.setTextSize(18f);
        btnClear.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.colorPrimaryDark));
        btnClear.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));
        btnClear.setOnClickListener(v -> etAmountInput.setText(""));
        gridKeyboard.addView(btnClear);
    }


    private void updateConversion(){
        String inputStr = etAmountInput.getText().toString().trim();
        if (TextUtils.isEmpty(inputStr)) {
            etAmountOutput.setText("");
            return;
        }
        try {
            double amount = Double.parseDouble(inputStr);
            String from = spCurrencyFrom.getSelectedItem().toString();
            String to = spCurrencyTo.getSelectedItem().toString();
            double result = ConversionUtils.convertCurrency(amount, from, to);
            etAmountOutput.setText(String.format("%.2f", result));
        } catch (NumberFormatException e) {
            etAmountOutput.setText("Err");
        }
    }
}
