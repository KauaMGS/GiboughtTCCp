package com.kldv.gibought.Class.CRUD;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kldv.gibought.Class.ConfigActivity;
import com.kldv.gibought.Class.GraphicsActivity;
import com.kldv.gibought.Class.HomeActivity;
import com.kldv.gibought.Dao.OperationsDao;
import com.kldv.gibought.Dao.bkAccountDao;
import com.kldv.gibought.Dao.goAccountDao;
import com.kldv.gibought.R;
import com.kldv.gibought.Utils.NotiFActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

@SuppressWarnings({"SpellCheckingInspection","deprecation"})
public class AddActivity extends AppCompatActivity implements View.OnClickListener{
    private String current = "";
    private final String Currency = "";
    private final String Separator = ",";
    private final Boolean Spacing = false;
    private final Boolean Delimiter = false;
    private final Boolean Decimals = true;
    private Boolean Type = true;
    int typeAdc;
    Double valuebanknf;
    DecimalFormat n ;
    String valueBank;
    double resultFn;
    String nameBk, Account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorDarkGreen));
        TextView optEad = findViewById(R.id.optEad);
        TextView optm = findViewById(R.id.optmn);
        TextView ActNm = findViewById(R.id.actName);
        TextView vexp = findViewById(R.id.vexpress);
        TextView vtOp = findViewById(R.id.vultOp);
        TextView vres = findViewById(R.id.vresult);
        BottomNavigationView mbottomNavigationView = findViewById(R.id.bot_nav_add_view);
        TextView vVal = findViewById(R.id.vValue);
        EditText money = findViewById(R.id.addmn);
        Button numberZero = findViewById(R.id.number_0);
        Button numberOne = findViewById(R.id.number_1);
        Button numberTwo = findViewById(R.id.number_2);
        Button numberThree = findViewById(R.id.number_3);
        Button numberFour = findViewById(R.id.number_4);
        Button numberFive = findViewById(R.id.number_5);
        Button numberSix = findViewById(R.id.number_6);
        Button numberSeven = findViewById(R.id.number_7);
        Button numberEight = findViewById(R.id.number_8);
        Button numberNine = findViewById(R.id.number_9);
        Button dot = findViewById(R.id.btn_dot);
        Button Confirm = findViewById(R.id.btn_confirm);
        ImageButton BackSpace = findViewById(R.id.backspace);
        Button Clean = findViewById(R.id.btn_clean);
        Button Cancel = findViewById(R.id.btn_cancel);
        LinearLayout viewBox = findViewById(R.id.view);
        LinearLayout keyBd = findViewById(R.id.keyboard);
        TextView optSpd = findViewById(R.id.optSpd);
        SharedPreferences prefersGoal = getApplicationContext().getSharedPreferences("goalCardSelected",
                Context.MODE_PRIVATE);
        SharedPreferences prefers = getSharedPreferences("bkCardSelected",
                Context.MODE_PRIVATE);
        SharedPreferences prefs = getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);
        int idUser = prefs.getInt("idUser", 0);
        boolean existCardBankselect = bkAccountDao.searchselectbk(idUser, getApplicationContext());
        n = new DecimalFormat(",##0.00");
        if(existCardBankselect){
                  typeAdc = 1;
                  valuebanknf = Double.valueOf(prefers.getString("valueBank", "00,00"));
                  nameBk = prefers.getString("nameBank", "");
                  Account = nameBk+": " +valuebanknf;
                  valueBank = n.format(valuebanknf);
                  Account = nameBk+": " +valueBank;
                  ActNm.setText(Account);
                  money.setText(getString(R.string.null_money));
                }else{
                  typeAdc = 1;
                  valuebanknf = Double.valueOf(prefers.getString("valueBank", "00,00"));
                  nameBk = prefers.getString("nameBank", "");
                  Account = nameBk+": " +valuebanknf;
                  valueBank = n.format(valuebanknf);
                  Account = nameBk+": " +valueBank;
                  ActNm.setText(Account);
                  money.setText(getString(R.string.null_money));
                 }

        //
        numberZero.setOnClickListener(this);
        numberOne.setOnClickListener(this);
        numberTwo.setOnClickListener(this);
        numberThree.setOnClickListener(this);
        numberFour.setOnClickListener(this);
        numberFive.setOnClickListener(this);
        numberSix.setOnClickListener(this);
        numberSeven.setOnClickListener(this);
        numberEight.setOnClickListener(this);
        numberNine.setOnClickListener(this);
        dot.setOnClickListener(this);
        Clean.setOnClickListener(view -> money.setText(getText(R.string.null_money)));

        SharedPreferences.Editor editar = prefs.edit();
        editar.putInt("Situ", 1);
        editar.apply();


        BackSpace.setOnClickListener(view -> {
            String mn = money.getText().toString();

            if(!mn.isEmpty() && !mn.equals(getString(R.string.null_money))){
                byte var0 = 0;
                int var1 = mn.length()-1;
                String txtMn = mn.substring(var0,var1);
                money.setText(txtMn);
            }
        });
        mbottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            if (menuItem.getItemId() == R.id.app_back) {
                onBackPressed();
                return true;
            }
            return false;
        });
        money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    money.removeTextChangedListener(this);
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
                                money.setText(formatted.replaceAll(",", Separator));
                            } else {
                                money.setText(formatted);
                                SharedPreferences prefers = getSharedPreferences("bkCardSelected",
                                        Context.MODE_PRIVATE);
                                DecimalFormat n = new DecimalFormat(",##0.00");
                                String valueBank = n.format(valuebanknf);

                                String val1b = valueBank.replaceAll("\\s+","");
                                String val1c = val1b.replace(".", "");
                                String val1d = val1c.replace(",", ".");
                                double val1 = Double.parseDouble(val1d);

                                String val2a = String.valueOf(money.getText());
                                String val2b = val2a.replaceAll("\\s+","");
                                String val2c = val2b.replace(".", "");
                                String val2d = val2c.replace(",", ".");
                                double val2 = Double.parseDouble(val2d);
                                vexp.setText(val2d);

                                String sinal = optm.getText().toString();
                                if(optm.getText().equals(getString(R.string.add_buttonadd))){
                                    double result = val1 + val2;
                                    DecimalFormat resultformat = new DecimalFormat(",##0.00");
                                    String finalResult = resultformat.format(result);
                                    vres.setText(finalResult);
                                    vVal.setText(formatted);
                                    String operation = valueBank + " " + sinal + formatted + " = " +finalResult;
                                    vtOp.setText(operation);
                                    String Account = nameBk + ": "+"\n" + valueBank + " " + sinal + formatted + " = " +finalResult;
                                    resultFn = result;
                                    ActNm.setText(Account);
                                    Type = true;
                                }else{
                                    double result = val1 - val2;
                                    DecimalFormat resultformat = new DecimalFormat(",##0.00");
                                    String finalResult = resultformat.format(result);
                                    String Account = nameBk + ": "+"\n" + valueBank + " " + sinal + formatted + " = " +finalResult;
                                    ActNm.setText(Account);
                                    String operation = valueBank + " " + sinal + formatted + " = " +finalResult;
                                    vtOp.setText(operation);
                                    vres.setText(finalResult);
                                    vVal.setText(formatted);
                                    resultFn = result;
                                    Type = false;
                                }

                            }
                            money.setSelection(formatted.length());
                        } catch (NumberFormatException ignored) {

                        }
                    }
                    money.addTextChangedListener(this);
                }
            }
        });
        //troca de gastos para ganhos
        optEad.setOnClickListener(v -> {
            optEad.setTextColor(getResources().getColor(R.color.colorSelect));
            optSpd.setTextColor(getResources().getColor(R.color.colorSelectNo));
            optm.setText(getText(R.string.add_buttonadd));
            optm.setBackgroundResource(R.color.colorDarkGreen);
            money.setBackgroundResource(R.color.colorDarkGreen);
            ActNm.setBackgroundResource(R.color.colorDarkGreenAct);

            String val1b = valueBank.replaceAll("\\s+","");
            String val1c = val1b.replace(".", "");
            String val1d = val1c.replace(",", ".");
            double val1 = Double.parseDouble(val1d);

            String val2a = String.valueOf(money.getText());
            String val2b = val2a.replaceAll("\\s+","");
            String val2c = val2b.replace(".", "");
            String val2d = val2c.replace(",", ".");
            double val2 = Double.parseDouble(val2d);

            double result = val1 + val2;
            DecimalFormat resultformat = new DecimalFormat(",##0.00");
            String finalResult = resultformat.format(result);
            String sinal = optm.getText().toString();
            String Account = nameBk + ": "+"\n" + valueBank + " " + sinal + val2a + " = " + finalResult;
            ActNm.setText(Account);
            viewBox.setBackgroundResource(R.color.colorDarkGreen);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorDarkGreen));
            Type = true;
            vres.setText(finalResult);
            String operation = valueBank + " " + sinal + val2a + " = " +finalResult;
            resultFn = result;
            vtOp.setText(operation);
        });
        //Troca de ganhos para gastos
        optSpd.setOnClickListener(v -> {
            optEad.setTextColor(getResources().getColor(R.color.colorSelectNo));
            optSpd.setTextColor(getResources().getColor(R.color.colorSelect));
            optm.setText(getText(R.string.add_buttonspd));
            optm.setBackgroundResource(R.color.colorRedLight);
            money.setBackgroundResource(R.color.colorRedLight);

            String val1b = valueBank.replaceAll("\\s+","");
            String val1c = val1b.replace(".", "");
            String val1d = val1c.replace(",", ".");
            double val1 = Double.parseDouble(val1d);

            String val2a = String.valueOf(money.getText());
            String val2b = val2a.replaceAll("\\s+","");
            String val2c = val2b.replace(".", "");
            String val2d = val2c.replace(",", ".");
            double val2 = Double.parseDouble(val2d);

            double result = val1 - val2;
            DecimalFormat resultformat = new DecimalFormat(",##0.00");
            String finalResult = resultformat.format(result);
            String sinal = optm.getText().toString();
            String Account = nameBk + ": "+"\n" + valueBank + " " + sinal + val2a + " = " + finalResult;
            ActNm.setText(Account);
            viewBox.setBackgroundResource(R.color.colorRedLight);
            ActNm.setBackgroundResource(R.color.colorDarkRedAct);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorRedLight));
            Type = false;
            vres.setText(finalResult);
            String operation = valueBank + " " + sinal + val2a + " = " +finalResult;
            vtOp.setText(operation);
            resultFn = result;
        });
        //Abre o Keyboard
        money.setOnClickListener(view -> keyBd.setVisibility(View.VISIBLE));
        //retornar o nome e valor da conta
        String Account = nameBk+": " +valueBank;
        ActNm.setText(Account);
        //Cancelar
        Cancel.setOnClickListener(view -> onBackPressed());
        //Confirma
        Confirm.setOnClickListener(view -> {
            String verif = vres.getText().toString();
            if(verif.equals(getString(R.string.null_money))||verif.equals("0,00")){
                Toast toast= Toast.makeText(getApplicationContext(),
                        "Adicione um numero maior que 0", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
            else {
                String value = vVal.getText().toString();
                String val2b = value.replaceAll("\\s+", "");
                String val2c = val2b.replace(".", "");
                String val2d = val2c.replace(",", ".");
                double val2 = Double.parseDouble(val2d);

                String value3 = vres.getText().toString();
                String val3b = value3.replaceAll("\\s+", "");
                String val3c = val3b.replace(".", "");
                String val3d = val3c.replace(",", ".");
                double val3 = Double.parseDouble(val3d);
                SharedPreferences prefers1 = getSharedPreferences("bkCardSelected",
                        Context.MODE_PRIVATE);
                int idBank = prefers1.getInt("idBank", 0);
                String operation = vtOp.getText().toString();
                String mney = money.getText().toString();
                if (Type) {
                    if(typeAdc == 1) {
                        OperationsDao.insertEarnings(val2, operation, getApplicationContext(), idBank, idUser);
                        bkAccountDao.updateBkEarnAccount(val3, mney, idUser, idBank, getApplicationContext());
                        bkAccountDao.searchselectbk(idUser, getApplicationContext());
                        money.setText(getString(R.string.null_money));
                        valuebanknf = Double.valueOf(prefers.getString("valueBank", "00,00"));
                        n = new DecimalFormat(",##0.00");
                        valueBank = n.format(valuebanknf);
                        nameBk = prefers.getString("nameBank", "");
                        DecimalFormat nfi = new DecimalFormat(",##0.00");
                        String valueBankfi = nfi.format(valuebanknf);
                        String AccountFi = nameBk + ": " + valueBankfi;
                        ActNm.setText(AccountFi);
                    }else if(typeAdc == 2){
                        SharedPreferences prefsGoal = getSharedPreferences("goalCardSelected",
                                Context.MODE_PRIVATE);
                        int idGoals = prefsGoal.getInt("idGoal", 0);
                        goAccountDao.updategoalAccount(nameBk,resultFn,idGoals,idUser, getApplicationContext());
                        goAccountDao.searchselectgoal(idUser, getApplicationContext());
                        money.setText(getString(R.string.null_money));
                        valuebanknf = Double.valueOf(prefersGoal.getString("valGoal", getString(R.string.none)));
                        nameBk = prefersGoal.getString("nameGoal", getString(R.string.none));
                        valueBank = n.format(valuebanknf);
                        String Account1 = nameBk+": " +valueBank;
                        ActNm.setText(Account1);
                    }
                } else {
                    if(typeAdc == 1) {
                        OperationsDao.insertSpendings(val2, operation, getApplicationContext(), idBank, idUser);
                        bkAccountDao.updateBkSpdAccount(val3, mney, idUser, idBank, getApplicationContext());
                        bkAccountDao.searchselectbk(idUser, getApplicationContext());
                        money.setText(getString(R.string.null_money));
                        valuebanknf = Double.valueOf(prefers.getString("valueBank", "00,00"));
                        n = new DecimalFormat(",##0.00");
                        valueBank = n.format(valuebanknf);
                        nameBk = prefers.getString("nameBank", "");
                        DecimalFormat nfi = new DecimalFormat(",##0.00");
                        String valueBankfi = nfi.format(valuebanknf);
                        String AccountFi = nameBk + ": " + valueBankfi;
                        ActNm.setText(AccountFi);
                    }
                    else if(typeAdc == 2){
                        SharedPreferences prefsGoal = getSharedPreferences("goalCardSelected",
                                Context.MODE_PRIVATE);
                        int idGoals = prefsGoal.getInt("idGoal", 0);
                        goAccountDao.updategoalAccount(nameBk,resultFn,idGoals,idUser, getApplicationContext());
                        goAccountDao.searchselectgoal(idUser, getApplicationContext());
                        money.setText(getString(R.string.null_money));
                        valuebanknf = Double.valueOf(prefersGoal.getString("valGoal", getString(R.string.none)));
                        nameBk = prefersGoal.getString("nameGoal", getString(R.string.none));
                        valueBank = n.format(valuebanknf);
                        String Account1 = nameBk+": " +valueBank;
                        ActNm.setText(Account1);
                    }

                }
            }
        });
    }

    @SuppressWarnings("SameParameterValue")
    private void addText(String string, boolean limpar_dados){
        TextView money = findViewById(R.id.addmn);
        if(money.getText().equals(getString(R.string.null_money))){
            money.setText("");
        }

        if(limpar_dados){
            money.append(string);
        }
        else{
            money.append(string);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.number_0:
                addText("0",true);
                break;
            case R.id.number_1:
                addText("1",true);
                break;
            case R.id.number_2:
                addText("2",true);
                break;
            case R.id.number_3:
                addText("3",true);
                break;
            case R.id.number_4:
                addText("4",true);
                break;
            case R.id.number_5:
                addText("5",true);
                break;
            case R.id.number_6:
                addText("6",true);
                break;
            case R.id.number_7:
                addText("7",true);
                break;
            case R.id.number_8:
                addText("8",true);
                break;
            case R.id.number_9:
                addText("9",true);
                break;
            case R.id.btn_dot:
                addText(".",true);
                break;
        }
    }
     @Override
    public void onBackPressed(){
         @SuppressWarnings("ConstantConditions")
         SharedPreferences prefersD = getApplicationContext().getSharedPreferences("userInfo",
                 Context.MODE_PRIVATE);
         int sit = prefersD.getInt("SituAdd",0);
         if(sit == 0) {
             startActivity(new Intent(getApplicationContext(), HomeActivity.class));
         }
         else if(sit == 1){
             startActivity(new Intent(getApplicationContext(), GraphicsActivity.class));
         }
         else if(sit == 2){
             startActivity(new Intent(getApplicationContext(), NotiFActivity.class));
         }
         else{
             startActivity(new Intent(getApplicationContext(), ConfigActivity.class));
         }
    }
}