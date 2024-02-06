package com.kldv.gibought.Class;

import static android.text.TextUtils.isEmpty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.kldv.gibought.Connex.Connexion;
import com.kldv.gibought.Dao.UserDao;
import com.kldv.gibought.R;
import com.kldv.gibought.Utils.Encrypt;
import com.kldv.gibought.Utils.Error.Maintenance;
import com.kldv.gibought.Utils.User;

import java.sql.Connection;

@SuppressWarnings({"SpellCheckingInspection"})
public class RegisterActivity extends AppCompatActivity {
    private String emai,pass;
    EditText name,email,password,conpassword;
    private User user;
    private boolean exist = false;

    @SuppressWarnings("RegExpRedundantEscape")
    private static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    @SuppressLint("ShowToast")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ConstraintLayout content = findViewById(R.id.content);
        ConstraintLayout contentTwo = findViewById(R.id.contenttwo);
        name = findViewById(R.id.txt_name);
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);
        conpassword = findViewById(R.id.txt_conPassword);
        TextView signIn = findViewById(R.id.signin);
        Button btnNext = findViewById(R.id.btnNext);
        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnBack = findViewById(R.id.btnBack);
        SharedPreferences preferences = getSharedPreferences("PREFERENCES",MODE_PRIVATE);
        String UserState = preferences.getString("Logged","NO");
        if(UserState.equals("YES")){
            Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }

        signIn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(email.getText().toString().contains(" ")){
                    email.setText(email.getText().toString().replaceAll(" ",""));
                    email.setSelection(email.getText().length());
                }
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(password.getText().toString().contains(" ")){
                    password.setText(password.getText().toString().replaceAll(" ",""));
                    password.setSelection(password.getText().length());
                }
            }
        });
        conpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(conpassword.getText().toString().contains(" ")){
                    conpassword.setText(conpassword.getText().toString().replaceAll(" ",""));
                    conpassword.setSelection(conpassword.getText().length());
                }
            }
        });
        btnNext.setOnClickListener(view -> {
            emai = email.getText().toString();
            if (isEmpty(name.getText().toString())) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Do not leave the name empty", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
            else if (isEmpty(email.getText().toString())) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Do not leave the email empty", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
            else if(!(isValid(email.getText().toString()))){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Use a valid address", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
            else {
                //Detecta Se há conexão com Internet
                ConnectivityManager connectivityManager = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                //Se houver Internet
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() ==
                        NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() ==
                        NetworkInfo.State.CONNECTED) {
                    Handler handler = new Handler();
                    //Fecha o teclado após campos preenchidos
                    closeKeyboard();
                    handler.postDelayed(() -> {
                    //Detecta Se o Banco de Dados Está online ou offline
                    Connection conn = Connexion.join(getApplicationContext());
                    if (conn != null) {
                        //se o email ja existir no Banco de Dados
                        exist = UserDao.verificationEmail(emai, getApplicationContext());
                        if (exist) {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "This address is already used!", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }
                        //se o email não existir no Banco de Dados
                        else {
                            btnNext.setVisibility(View.INVISIBLE);
                            btnBack.setVisibility(View.VISIBLE);
                            content.setVisibility(View.INVISIBLE);
                            contentTwo.setVisibility(View.VISIBLE);
                            btnRegister.setVisibility(View.VISIBLE);
                        }
                    }
                    else{
                        startActivity(new Intent(getApplicationContext(), Maintenance.class));
                    }
                    },800);
                }
                //Se não Houver internet
                else{
                    Toast toast= Toast.makeText(getApplicationContext(),
                            "Verifique sua conexão com a internet", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }

            }

        });
        btnBack.setOnClickListener(view -> {
                    btnNext.setVisibility(View.VISIBLE);
                    btnBack.setVisibility(View.INVISIBLE);
                    content.setVisibility(View.VISIBLE);
                    contentTwo.setVisibility(View.INVISIBLE);
                    btnRegister.setVisibility(View.INVISIBLE);
                }

        );
        btnRegister.setOnClickListener(view -> {
            pass = password.getText().toString();
            if (isEmpty(password.getText().toString())) {
                Toast toast= Toast.makeText(getApplicationContext(),
                        "Do not leave the password empty", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
            else if(pass.trim().length()<8){
                Toast toast= Toast.makeText(getApplicationContext(),
                        "A senha deve ter no minimo 8 caracteres", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
            else if (isEmpty(conpassword.getText().toString())) {
                Toast toast= Toast.makeText(getApplicationContext(),
                        "Do not leave the Confirm Password empty", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
            else if (!password.getText().toString().equals(conpassword.getText().toString())){
                Toast toast= Toast.makeText(getApplicationContext(),
                        "Passwords do not match", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
            else {
                //Detecta Se há conexão com Internet
                ConnectivityManager connectivityManager = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                //Se houver Internet
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() ==
                        NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() ==
                        NetworkInfo.State.CONNECTED) {
                    Handler handler = new Handler();
                    //Fecha o teclado após campos preenchidos
                    closeKeyboard();
                    handler.postDelayed(() -> {
                    //Detecta Se o Banco de Dados Está online ou offline
                    Connection conn = Connexion.join(getApplicationContext());
                    if (conn != null) {
                        String passEnd = Encrypt.encryptPass(password.getText().toString());

                        //insere Usuario No Banco De Dados
                        User userd = new User(
                                name.getText().toString(),
                                email.getText().toString(),
                                passEnd
                        );

                        int res = UserDao.insertUser(userd, getApplicationContext());
                        Toast toast;
                        if (res <= 0) {

                            toast = Toast.makeText(getApplicationContext(),
                                    "Erro", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        } else {
                            user = UserDao.existUser(emai, passEnd, getApplicationContext());
                            SharedPreferences prefs = getSharedPreferences("userInfo",
                                    Context.MODE_PRIVATE);
                            @SuppressLint("CommitPrefEdits")
                            SharedPreferences.Editor edit = prefs.edit();
                            edit.putInt("idUser", user.getId());
                            edit.putString("nameUser", user.getNameCli());
                            edit.putString("emailUser", user.getEmailCli());
                            edit.apply();
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("Logged", "YES");
                            editor.apply();
                            Intent intentDeluxe = new Intent(getApplicationContext(), HomeActivity.class);
                    /*Bundle parameters = new Bundle();
                    parameters.putString("dx_emai", emai);
                    intentDeluxe.putExtras(parameters);*/
                            startActivity(intentDeluxe);
                        }
                        }
                    else{
                        startActivity(new Intent(getApplicationContext(), Maintenance.class));
                    }
                    },800);
                    }
                //Se não Houver internet
                else{
                    Toast toast= Toast.makeText(getApplicationContext(),
                            "Verifique sua conexão com a internet", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
            }

        });
    }
    //Fecha O teclado após preencher os campos
    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}
