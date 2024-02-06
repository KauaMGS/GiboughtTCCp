package com.kldv.gibought.Class.CRUD;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kldv.gibought.Class.AddAccount;
import com.kldv.gibought.Dao.bkAccountDao;
import com.kldv.gibought.R;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

import static android.text.TextUtils.isEmpty;

public class EditCardBank extends AppCompatActivity {
    private String current = "";
    private final String Currency = "";
    private final String Separator = ",";
    private final Boolean Spacing = false;
    private final Boolean Delimiter = false;
    private final Boolean Decimals = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card_bank);
        Intent intentcard = getIntent();
        int idcard = Integer.parseInt(intentcard.getStringExtra("idUpdate"));
        String namecard = intentcard.getStringExtra("nameupdate");
        String valuecard = intentcard.getStringExtra("valueUpdate");
        String typecard = intentcard.getStringExtra("typeUpdate");
        SharedPreferences prefs = getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);
        int idUser = prefs.getInt("idUser", 0);

        Spinner spinner = findViewById(R.id.spinnerBank);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_account, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (typecard.equals(getString(R.string.crt_account))){
            spinner.setSelection(0);
        }
        else {
            spinner.setSelection(1);
        }
        ImageButton bkBank = findViewById(R.id.btnConAccount);
        ImageButton bkBankcan = findViewById(R.id.btnCanAccount);
        EditText nameBank = findViewById(R.id.nameInfo);
        EditText valueBank = findViewById(R.id.valueInfo);
        nameBank.setText(namecard);
        valueBank.setText(valuecard);
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

        bkBankcan.setOnClickListener(v -> {
            Intent intent = new Intent(EditCardBank.this,AddAccount.class);
            startActivity(intent);
        });
        bkBank.setOnClickListener(v -> {
            if (isEmpty(nameBank.getText().toString())) {
                Toast toast= Toast.makeText(getApplicationContext(),
                        "Do not leave the field empty", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
            else {
                String a = String.valueOf(valueBank.getText());
                String b = a.replaceAll("\\s+","");
                String c = b.replace(".", "");
                String d = c.replace(",", ".");
                String name = nameBank.getText().toString();
                int type;

                if (spinner.getSelectedItemId() == 0){
                    type = 0;
                }
                else {
                    spinner.setSelection(1);
                    type = 1;
                }
                double value = Double.parseDouble(d);
                bkAccountDao.updateBkAccount(name,value,type,idcard,idUser,getApplicationContext());
                Intent i = new Intent(EditCardBank.this,AddAccount.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), AddAccount.class));
    }
}