package com.kldv.gibought.Class;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kldv.gibought.Dao.bkAccountDao;
import com.kldv.gibought.R;
import com.kldv.gibought.Utils.bkAccount;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

import static android.text.TextUtils.isEmpty;

@SuppressWarnings({"SpellCheckingInspection"})
public class SelectAccountActivity extends AppCompatActivity {
    private BottomSheetDialog bottomSheetDialog;
    private Integer idImage;
    private String current = "0,00";
    private final String Currency = "";
    private final String Separator = ",";
    private final Boolean Spacing = false;
    private final Boolean Delimiter = false;
    private final Boolean Decimals = true;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account);
        ImageView bkOne = findViewById(R.id.BankOne);
        ImageView bkTw = findViewById(R.id.BankTwo);
        ImageView bkTh = findViewById(R.id.BankThree);
        ImageView bkFo = findViewById(R.id.BankFour);
        ImageView bkFi = findViewById(R.id.BankFive);
        BottomNavigationView mbottomNavigationView = findViewById(R.id.bot_nav_add_view);
        ImageView bkSi = findViewById(R.id.BankSix);
        ImageView bkSe = findViewById(R.id.bankSeven);
        ImageView bkEi = findViewById(R.id.bankEight);
        ImageView bkNi = findViewById(R.id.BankNine);
        ImageView bkOThs = findViewById(R.id.OthersBank);

        bottomSheetDialog = new BottomSheetDialog(SelectAccountActivity.this, R.style.BottomSheetBank);
        View sheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet_layout,
                findViewById(R.id.bank_sheet));
        Spinner spinner = sheetView.findViewById(R.id.spinnerBank);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.type_account, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        bottomSheetDialog.setContentView(sheetView);
        ImageButton bkBank = sheetView.findViewById(R.id.btnConAccount);
        EditText nameBank = sheetView.findViewById(R.id.nameInfo);
        EditText valueBank = sheetView.findViewById(R.id.valueInfo);
        valueBank.setText(current);

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

        //muda a imagem
        bkOne.setOnClickListener(v -> {
            nameBank.setText("");
            valueBank.setText("0,00");
            idImage = 1;
            bottomSheetDialog.show();
        });
        bkTw.setOnClickListener(v -> {
            nameBank.setText("");
            valueBank.setText("0,00");
            idImage = 2;
            bottomSheetDialog.show();
        });
        bkTh.setOnClickListener(v -> {
            nameBank.setText("");
            valueBank.setText("0,00");
            idImage = 3;
            bottomSheetDialog.show();
        });
        bkFo.setOnClickListener(v -> {
            nameBank.setText("");
            valueBank.setText("0,00");
            idImage = 4;
            bottomSheetDialog.show();
        });
        bkFi.setOnClickListener(v -> {
            nameBank.setText("");
            valueBank.setText("0,00");
            idImage = 5;
            bottomSheetDialog.show();
        });
        bkSi.setOnClickListener(v -> {
            nameBank.setText("");
            valueBank.setText("0,00");
            idImage = 6;
            bottomSheetDialog.show();
        });
        bkSe.setOnClickListener(v -> {
            nameBank.setText("");
            valueBank.setText("0,00");
            idImage = 7;
            bottomSheetDialog.show();
        });
        bkEi.setOnClickListener(v -> {
            nameBank.setText("");
            valueBank.setText("0,00");
            idImage = 8;
            bottomSheetDialog.show();
        });
        bkNi.setOnClickListener(v -> {
            nameBank.setText("");
            valueBank.setText("0,00");
            idImage = 9;
            bottomSheetDialog.show();
        });
        bkOThs.setOnClickListener(v -> {
            nameBank.setText("");
            valueBank.setText("0,00");
            idImage = 0;
            bottomSheetDialog.show();
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
                SharedPreferences prefs = getSharedPreferences("userInfo",
                        Context.MODE_PRIVATE);
                int idCli = prefs.getInt("idUser", 0);
                bkAccount bkact;
                String a = String.valueOf(valueBank.getText());
                String b = a.replaceAll("\\s+", "");
                String c = b.replace(".", "");
                String d = c.replace(",", ".");
                double re = Double.parseDouble(d);
                bkact = new bkAccount(
                        nameBank.getText().toString(),
                        idImage,
                        spinner.getSelectedItemPosition(),
                        re
                );
                //noinspection RedundantSuppression
                @SuppressWarnings("unused")
                int res = bkAccountDao.insertBkAccount(bkact, getApplicationContext(), idCli);
                bottomSheetDialog.hide();
                nameBank.setText("");
            }
        });
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), AddAccount.class));
    }
}

