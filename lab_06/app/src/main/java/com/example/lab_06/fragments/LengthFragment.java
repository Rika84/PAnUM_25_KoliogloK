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
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.lab_06.R;
import com.example.lab_06.utils.ConversionUtils;

public class LengthFragment extends Fragment {

    private EditText etLengthInput;
    private TextView tvLengthOutput;
    private Spinner spinnerLengthFrom, spinnerLengthTo;
    private Button btnSwapUnits;
    private GridLayout gridKeyboard;

    private final String[] lengthUnits = {"mm", "cm", "cale", "stopy", "yardy", "m", "km"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_length, container, false);

        etLengthInput = view.findViewById(R.id.etLengthInput);
        tvLengthOutput = view.findViewById(R.id.tvLengthResult);
        spinnerLengthFrom = view.findViewById(R.id.spinnerLengthFrom);
        spinnerLengthTo = view.findViewById(R.id.spinnerLengthTo);
        btnSwapUnits = view.findViewById(R.id.btnSwapUnits);
        gridKeyboard = view.findViewById(R.id.gridKeyboard);

        etLengthInput.setShowSoftInputOnFocus(false);
        etLengthInput.setInputType(EditorInfo.TYPE_NULL);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.spinner_item,
                lengthUnits
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerLengthFrom.setAdapter(adapter);
        spinnerLengthTo.setAdapter(adapter);

        btnSwapUnits.setOnClickListener(v -> {
            int posFrom = spinnerLengthFrom.getSelectedItemPosition();
            int posTo = spinnerLengthTo.getSelectedItemPosition();
            spinnerLengthFrom.setSelection(posTo);
            spinnerLengthTo.setSelection(posFrom);

            String inputVal = etLengthInput.getText().toString().trim();
            String outputVal = tvLengthOutput.getText().toString().trim();

            if (!TextUtils.isEmpty(outputVal) && !outputVal.equals("Błąd")) {
                etLengthInput.setText(outputVal);
                tvLengthOutput.setText(inputVal);
                etLengthInput.setSelection(etLengthInput.getText().length());
            } else if (!TextUtils.isEmpty(inputVal)) {
                tvLengthOutput.setText(inputVal);
                etLengthInput.setText("");
            }

            updateConversion();
            buildKeyboard();
        });

        spinnerLengthFrom.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                buildKeyboard();
                updateConversion();
            }
            @Override public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        spinnerLengthTo.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                updateConversion();
            }
            @Override public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        etLengthInput.addTextChangedListener(new TextWatcher() {
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
                String current = etLengthInput.getText().toString();

                if (c == '.' && current.contains(".")) return;
                if (current.equals("0") && c != '.') return;
                if (current.isEmpty() && c == '.') return;

                String newValue = current + c;
                if (newValue.matches("^0\\d+")) {
                    newValue = newValue.replaceFirst("^0+", "");
                }

                etLengthInput.setText(newValue);
                etLengthInput.setSelection(newValue.length());
            });
            gridKeyboard.addView(btn);
        }

        Button btnDel = createKeyButton("DEL");
        btnDel.setOnClickListener(v -> {
            String current = etLengthInput.getText().toString();
            if (!TextUtils.isEmpty(current)) {
                etLengthInput.setText(current.substring(0, current.length() - 1));
                etLengthInput.setSelection(etLengthInput.getText().length());
            }
        });
        gridKeyboard.addView(btnDel);

        Button btnClear = createKeyButton("CLR");
        btnClear.setOnClickListener(v -> etLengthInput.setText(""));
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
        String inputStr = etLengthInput.getText().toString().trim();
        if (TextUtils.isEmpty(inputStr) || inputStr.equals(".")) {
            tvLengthOutput.setText("");
            return;
        }

        try {
            double value = Double.parseDouble(inputStr);
            String fromUnit = spinnerLengthFrom.getSelectedItem().toString();
            String toUnit = spinnerLengthTo.getSelectedItem().toString();
            String result = ConversionUtils.convertLength(value, fromUnit, toUnit);
            tvLengthOutput.setText(result);
        } catch (NumberFormatException e) {
            tvLengthOutput.setText("Błąd");
        }
    }
}
