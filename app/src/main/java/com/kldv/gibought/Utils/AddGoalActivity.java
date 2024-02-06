package com.kldv.gibought.Utils;

import static android.text.TextUtils.isEmpty;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kldv.gibought.Class.GoalActivity;
import com.kldv.gibought.Dao.bkAccountDao;
import com.kldv.gibought.Dao.goAccountDao;
import com.kldv.gibought.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AddGoalActivity extends AppCompatActivity {
    private String current = "0,00";
    private final String Currency = "";
    private final String Separator = ",";
    private final Boolean Spacing = false;
    private final Boolean Delimiter = false;
    private final Boolean Decimals = true;
    int idBankUser = 0;
    int Goal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);
        EditText nameBank = findViewById(R.id.nameInfo);
        EditText valueBank = findViewById(R.id.valueInfo);
        ImageButton bkBank = findViewById(R.id.btnConAccount);
        TextView selectActTXT = findViewById(R.id.selectActTXT);
        LinearLayout selectedAct = findViewById(R.id.selectedAccount);
        LinearLayout specificAct = findViewById(R.id.specificAccount);
        Spinner spinner = findViewById(R.id.spinnerCond);
        Spinner spinnerBank = findViewById(R.id.spinnerAct);
        BottomNavigationView mbottomNavigationView = findViewById(R.id.bot_nav_add_view);
        SharedPreferences prefs = getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);
        int idCli = prefs.getInt("idUser", 0);

        SharedPreferences prefsBkSelected = getSharedPreferences("bkCardSelected",
                Context.MODE_PRIVATE);
        String nameBkCardSelected = prefsBkSelected.getString("nameBank", "None");
        selectActTXT.setText(nameBkCardSelected);

        List lista = bkAccountDao.searchgobk(idCli,getApplicationContext());
        ArrayAdapter array = new ArrayAdapter(this, R.layout.simple_item_dark,lista);
        array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBank.setAdapter(array);
        spinnerBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int spinnerItemBank = spinnerBank.getSelectedItemPosition();
                SharedPreferences prefsUsBank = getSharedPreferences("userBanks",
                        Context.MODE_PRIVATE);
                idBankUser = prefsUsBank.getInt("IdBankUser"+spinnerItemBank, 0);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        valueBank.setText(current);
        if(nameBkCardSelected.equals("None")){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                    R.array.type_goals1, R.layout.simple_item_dark);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }else {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                    R.array.type_goals, R.layout.simple_item_dark);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int spinnerItem = spinner.getSelectedItemPosition();
                if(nameBkCardSelected.equals("None")) {
                    switch (spinnerItem) {
                        case 0:
                            Goal = 1;
                            selectedAct.setVisibility(View.GONE);
                            specificAct.setVisibility(View.GONE);
                            break;
                        case 1:
                            Goal = 2;
                            selectedAct.setVisibility(View.GONE);
                            specificAct.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            Goal = 4;
                            selectedAct.setVisibility(View.GONE);
                            specificAct.setVisibility(View.GONE);
                            break;
                    }
                }
                else {
                    switch (spinnerItem) {
                        case 0:
                            Goal = 1;
                            selectedAct.setVisibility(View.GONE);
                            specificAct.setVisibility(View.GONE);
                            break;
                        case 1:
                            Goal = 2;
                            selectedAct.setVisibility(View.GONE);
                            specificAct.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            Goal = 3;
                            selectedAct.setVisibility(View.VISIBLE);
                            specificAct.setVisibility(View.GONE);
                            break;
                        case 3:
                            Goal = 4;
                            selectedAct.setVisibility(View.GONE);
                            specificAct.setVisibility(View.GONE);
                            break;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //formatação do texto

        valueBank.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    valueBank.removeTextChangedListener(this);
                    String cleanString = s.toString().replaceAll("[,.]", "").replaceAll(Currency, "").replaceAll("\\s+", "");
                    if (cleanString.length() != 0) {
                        try {

                            String currencyFormat;
                            if (Spacing) {
                                if (Delimiter) {
                                    currencyFormat = Currency + ". ";
                                } else {
                                    currencyFormat = Currency + " ";
                                }
                            } else {
                                if (Delimiter) {
                                    currencyFormat = Currency + ".";
                                } else {
                                    currencyFormat = Currency;
                                }
                            }

                            double parsed;
                            int parsedInt;
                            String formatted;

                            if (Decimals) {
                                parsed = Double.parseDouble(cleanString);
                                formatted = NumberFormat.getCurrencyInstance().format((parsed / 100)).replace(Objects.requireNonNull(NumberFormat.getCurrencyInstance().getCurrency()).getSymbol(), currencyFormat);
                            } else {
                                parsedInt = Integer.parseInt(cleanString);
                                formatted = currencyFormat + NumberFormat.getNumberInstance(Locale.US).format(parsedInt);
                            }
                            current = formatted;

                            //noinspection ConstantConditions
                            if (!Separator.equals(",") && !Decimals) {
                                valueBank.setText(formatted.replaceAll(",", Separator));
                            } else {
                                valueBank.setText(formatted);
                            }
                            valueBank.setSelection(formatted.length());
                        } catch (NumberFormatException ignored) {

                        }
                    }
                    valueBank.addTextChangedListener(this);
                }
            }
        });

        mbottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            if (menuItem.getItemId() == R.id.app_back) {
                onBackPressed();
                return true;
            }
            return false;
        });

        bkBank.setOnClickListener(v -> {
            if (isEmpty(nameBank.getText().toString())) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Do not leave the field empty", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            } else {
                goalAccount bkact;
                String a = String.valueOf(valueBank.getText());
                String b = a.replaceAll("\\s+", "");
                String c = b.replace(".", "");
                String d = c.replace(",", ".");
                double re = Double.parseDouble(d);
                bkact = new goalAccount(
                        nameBank.getText().toString(),
                        1,
                        re,
                        0.0,
                        Goal,
                        idBankUser
                );

                //noinspection RedundantSuppression
                @SuppressWarnings("unused")
                int res = goAccountDao.insertgoalAccount(bkact, getApplicationContext(), idCli,Goal,idBankUser);
                String goal = "Meta: "+nameBank.getText().toString()+" Adicionada com Sucesso";
                Toast toast = Toast.makeText(getApplicationContext(),
                        goal, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                nameBank.setText("");
                valueBank.setText("");

            }
        });
    }




    @Override
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), GoalActivity.class));
    }
}
