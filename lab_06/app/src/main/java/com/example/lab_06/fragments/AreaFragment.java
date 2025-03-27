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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.lab_06.R;
import com.example.lab_06.utils.ConversionUtils;
import com.google.android.material.textfield.TextInputEditText;

public class AreaFragment extends Fragment {

    private TextInputEditText etAreaInput, etAreaOutput;
    private Spinner spinnerAreaFrom, spinnerAreaTo;
    private Button btnSwapUnits;
    private GridLayout gridKeyboard;

    private final String[] areaUnits = {"mm²", "cm²", "m²", "km²", "ary", "hektary"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_area, container, false);

        etAreaInput = view.findViewById(R.id.etAreaInput);
        etAreaOutput = view.findViewById(R.id.etAreaOutput);
        spinnerAreaFrom = view.findViewById(R.id.spinnerAreaFrom);
        spinnerAreaTo = view.findViewById(R.id.spinnerAreaTo);
        btnSwapUnits = view.findViewById(R.id.btnSwapUnits);
        gridKeyboard = view.findViewById(R.id.gridKeyboard);

        etAreaInput.setShowSoftInputOnFocus(false);
        etAreaInput.setInputType(EditorInfo.TYPE_NULL);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.spinner_item,
                areaUnits
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerAreaFrom.setAdapter(adapter);
        spinnerAreaTo.setAdapter(adapter);

        btnSwapUnits.setOnClickListener(v -> {
            int posFrom = spinnerAreaFrom.getSelectedItemPosition();
            int posTo = spinnerAreaTo.getSelectedItemPosition();
            spinnerAreaFrom.setSelection(posTo);
            spinnerAreaTo.setSelection(posFrom);

            String inputVal = etAreaInput.getText().toString().trim();
            String outputRaw = etAreaOutput.getText().toString().trim();
            String outputVal = outputRaw.split(" ")[0];

            if (!TextUtils.isEmpty(outputVal) && !outputVal.equals("Błąd")) {
                etAreaInput.setText(outputVal);
                etAreaOutput.setText(inputVal);
                etAreaInput.setSelection(etAreaInput.getText().length());
            } else if (!TextUtils.isEmpty(inputVal)) {
                etAreaOutput.setText(inputVal);
                etAreaInput.setText("");
            }

            updateConversion();
            buildKeyboard();
        });

        spinnerAreaFrom.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                buildKeyboard();
                updateConversion();
            }
            @Override public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });
        spinnerAreaTo.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                updateConversion();
            }
            @Override public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        etAreaInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateConversion();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        buildKeyboard();
        return view;
    }

    private void buildKeyboard() {
        if (gridKeyboard == null) return;
        gridKeyboard.removeAllViews();

        String keys = "1234567890.";
        for (char c : keys.toCharArray()) {
            Button btn = createKeyButton(String.valueOf(c));
            btn.setOnClickListener(v -> {
                String current = etAreaInput.getText().toString();

                if (c == '.' && current.contains(".")) return;
                if (current.equals("0") && c != '.') return;
                if (current.isEmpty() && c == '.') return;

                String newValue = current + c;
                if (newValue.matches("^0\\d+")) {
                    newValue = newValue.replaceFirst("^0+", "");
                }

                etAreaInput.setText(newValue);
                etAreaInput.setSelection(newValue.length());
            });
            gridKeyboard.addView(btn);
        }

        Button btnDel = createKeyButton("DEL");
        btnDel.setOnClickListener(v -> {
            String current = etAreaInput.getText().toString();
            if (!TextUtils.isEmpty(current)) {
                etAreaInput.setText(current.substring(0, current.length() - 1));
                etAreaInput.setSelection(etAreaInput.getText().length());
            }
        });
        gridKeyboard.addView(btnDel);

        Button btnClear = createKeyButton("CLR");
        btnClear.setOnClickListener(v -> etAreaInput.setText(""));
        gridKeyboard.addView(btnClear);
    }

    private Button createKeyButton(String text) {
        Button btn = new Button(getContext());
        btn.setText(text);
        btn.setTextSize(18f);
        btn.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.colorPrimaryDark));
        btn.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        btn.setLayoutParams(params);
        return btn;
    }

    private void updateConversion() {
        String inputStr = etAreaInput.getText().toString().trim();
        if (TextUtils.isEmpty(inputStr) || inputStr.equals(".")) {
            etAreaOutput.setText("");
            return;
        }
        try {
            double value = Double.parseDouble(inputStr);
            String fromUnit = spinnerAreaFrom.getSelectedItem().toString();
            String toUnit = spinnerAreaTo.getSelectedItem().toString();
            String result = ConversionUtils.convertArea(value, fromUnit, toUnit);
            etAreaOutput.setText(result);
        } catch (NumberFormatException e) {
            etAreaOutput.setText("Błąd");
        }
    }
}
