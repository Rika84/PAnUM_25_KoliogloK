package com.example.lab_06.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.lab_06.R;
import com.example.lab_06.utils.ConversionUtils;

public class NumbersFragment extends Fragment {

    private Spinner spinnerFrom, spinnerTo;
    private Button btnSwap;
    private EditText etNumberInput, etNumberOutput;
    private GridLayout gridKeyboard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_numbers, container, false);

        spinnerFrom = view.findViewById(R.id.spinnerFrom);
        spinnerTo = view.findViewById(R.id.spinnerTo);
        btnSwap = view.findViewById(R.id.btnSwap);
        etNumberInput = view.findViewById(R.id.etNumberInput);
        etNumberOutput = view.findViewById(R.id.etNumberOutput);
        gridKeyboard = view.findViewById(R.id.gridKeyboard);

        etNumberInput.setShowSoftInputOnFocus(false);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.numeral_systems,
                R.layout.spinner_item
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        // obsługa przycisku zamiany
        btnSwap.setOnClickListener(v -> {
            int oldPosFrom = spinnerFrom.getSelectedItemPosition();
            int oldPosTo = spinnerTo.getSelectedItemPosition();

            String oldSystemFrom = spinnerFrom.getSelectedItem().toString();
            String oldSystemTo = spinnerTo.getSelectedItem().toString();

            String oldInputStr = etNumberInput.getText().toString().trim();
            String oldOutputStr = etNumberOutput.getText().toString().trim();

            spinnerFrom.setSelection(oldPosTo);
            spinnerTo.setSelection(oldPosFrom);

            String newSystemFrom = spinnerFrom.getSelectedItem().toString();
            String newSystemTo = spinnerTo.getSelectedItem().toString();

            int oldOutputVal = parseNumber(oldOutputStr, oldSystemTo);
            int oldInputVal = parseNumber(oldInputStr, oldSystemFrom);

            String newInputStr = convertNumber(oldOutputVal, newSystemFrom);
            String newOutputStr = convertNumber(oldInputVal, newSystemTo);

            etNumberInput.setText(newInputStr);
            etNumberOutput.setText(newOutputStr);

            buildKeyboard(newSystemFrom);
        });

        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                buildKeyboard(spinnerFrom.getSelectedItem().toString());
                updateConversion();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateConversion();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        etNumberInput.addTextChangedListener(new TextWatcher(){
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count){
                updateConversion();
            }
            @Override public void afterTextChanged(Editable s){}
        });

        buildKeyboard(spinnerFrom.getSelectedItem().toString());

        return view;
    }

    private void buildKeyboard(String systemName) {
        gridKeyboard.removeAllViews();

        String keys;
        switch(systemName.toLowerCase()) {
            case "dwókowy":
                keys = "01";
                break;
            case "czwórkowy":
                keys = "0123";
                break;
            case "ósemkowy":
                keys = "01234567";
                break;
            case "szesnastkowy":
                keys = "0123456789ABCDEF";
                break;
            default: // "dziesiętny"
                keys = "0123456789";
        }

        for (char c : keys.toCharArray()) {
            Button btn = new Button(getContext());
            btn.setText(String.valueOf(c));
            btn.setTextSize(18f);

            btn.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.colorPrimaryDark));
            btn.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));

            btn.setOnClickListener(v -> {
                String current = etNumberInput.getText().toString();
                etNumberInput.setText(current + c);
                updateConversion();
            });

            gridKeyboard.addView(btn);
        }

        Button btnDel = new Button(getContext());
        btnDel.setText("DEL");
        btnDel.setTextSize(18f);
        btnDel.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.colorPrimaryDark));
        btnDel.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));

        btnDel.setOnClickListener(v -> {
            String current = etNumberInput.getText().toString();
            if (!TextUtils.isEmpty(current)) {
                etNumberInput.setText(current.substring(0, current.length() - 1));
                updateConversion();
            }
        });
        gridKeyboard.addView(btnDel);

        Button btnClear = new Button(getContext());
        btnClear.setText("CLR");
        btnClear.setTextSize(18f);
        btnClear.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.colorPrimaryDark));
        btnClear.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));

        btnClear.setOnClickListener(v -> {
            etNumberInput.setText("");
            updateConversion();
        });
        gridKeyboard.addView(btnClear);
    }


    private void updateConversion() {
        String inputStr = etNumberInput.getText().toString().trim();
        if (TextUtils.isEmpty(inputStr)) {
            etNumberOutput.setText("");
            return;
        }
        try {
            String fromSystem = spinnerFrom.getSelectedItem().toString();
            String toSystem = spinnerTo.getSelectedItem().toString();
            String result = ConversionUtils.convertNumber(inputStr, fromSystem, toSystem);
            etNumberOutput.setText(result);
        } catch (NumberFormatException e) {
            etNumberOutput.setText("Err");
        }
    }

    private int parseNumber(String s, String systemName) {
        if (TextUtils.isEmpty(s)) return 0;
        try {
            int base = getBaseForSystem(systemName);
            return Integer.parseInt(s, base);
        } catch (NumberFormatException e) {
            return 0;
        }
    }


    private String convertNumber(int number, String systemName) {
        int base = getBaseForSystem(systemName);
        return Integer.toString(number, base).toUpperCase();
    }

    private int getBaseForSystem(String systemName) {
        switch(systemName.toLowerCase()) {
            case "dwókowy": return 2;
            case "czwórkowy": return 4;
            case "ósemkowy": return 8;
            case "szesnastkowy": return 16;
            case "dziesiętny":
            default:
                return 10;
        }
    }
}
