package com.smartcalc.convterter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class CurrencyFragment extends Fragment {

    private Spinner fromSpinner, toSpinner;
    private EditText amountInput;
    private TextView resultView;
    private Button convertBtn, refreshBtn;
    private Map<String, Double> rates = new HashMap<>();
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private final String[] currencies = {"USD","NGN","EUR","GBP","CAD","JPY","CNY"};

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_currency, container, false);
        fromSpinner = v.findViewById(R.id.fromSpinner);
        toSpinner = v.findViewById(R.id.toSpinner);
        amountInput = v.findViewById(R.id.amountInput);
        resultView = v.findViewById(R.id.currencyResult);
        convertBtn = v.findViewById(R.id.currencyConvertBtn);
        refreshBtn = v.findViewById(R.id.currencyRefreshBtn);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        // default selection: USD -> NGN
        fromSpinner.setSelection(Arrays.asList(currencies).indexOf("USD"));
        toSpinner.setSelection(Arrays.asList(currencies).indexOf("NGN"));

        convertBtn.setOnClickListener(view -> convert());
        refreshBtn.setOnClickListener(view -> fetchRates("USD")); // base USD
        fetchRates("USD");
        return v;
    }

    private void fetchRates(String base) {
        resultView.setText("Loading rates...");
        String url = "https://api.exchangerate.host/latest?base=" + base;
        Request request = new Request.Builder().url(url).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                if (getActivity() != null) getActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Rate fetch failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
            @Override public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    if (getActivity() != null) getActivity().runOnUiThread(() ->
                            Toast.makeText(requireContext(), "Rate fetch failed", Toast.LENGTH_SHORT).show());
                    return;
                }
                String body = response.body().string();
                Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
                Map<String, Object> full = gson.fromJson(body, mapType);
                if (full != null && full.containsKey("rates")) {
                    Type ratesType = new TypeToken<Map<String, Double>>(){}.getType();
                    Map<String, Double> fetched = gson.fromJson(gson.toJson(full.get("rates")), ratesType);
                    rates.clear();
                    rates.putAll(fetched);
                    if (getActivity() != null) getActivity().runOnUiThread(() -> resultView.setText("Rates updated"));
                }
            }
        });
    }

    private void convert() {
        String amtStr = amountInput.getText().toString().trim();
        if (TextUtils.isEmpty(amtStr)) {
            Toast.makeText(requireContext(), "Enter amount", Toast.LENGTH_SHORT).show();
            return;
        }
        String from = fromSpinner.getSelectedItem().toString();
        String to = toSpinner.getSelectedItem().toString();
        double amount;
        try { amount = Double.parseDouble(amtStr); } catch (Exception e) { resultView.setText("Invalid"); return; }

        double converted = amount;
        if (from.equals(to)) {
            converted = amount;
        } else {
            Double rateFrom = rates.get(from);
            Double rateTo = rates.get(to);
            if (rateFrom != null && rateTo != null) {
                converted = amount * (rateTo / rateFrom);
            } else {
                converted = amount;
                Toast.makeText(requireContext(), "Rates not available for selected pair", Toast.LENGTH_SHORT).show();
            }
        }

        String out = String.format(Locale.getDefault(), "%.4f %s = %.4f %s", amount, from, converted, to);
        resultView.setText(out);

        HistoryManager hm = new HistoryManager(requireContext());
        hm.addRecord("Currency", out);
    }
}
