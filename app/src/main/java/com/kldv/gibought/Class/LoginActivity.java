package com.kldv.gibought.Class;

import static android.text.TextUtils.isEmpty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import androidx.appcompat.app.AppCompatActivity;

import com.kldv.gibought.Connex.Connexion;
import com.kldv.gibought.Dao.UserDao;
import com.kldv.gibought.R;
import com.kldv.gibought.Utils.Encrypt;
import com.kldv.gibought.Utils.Error.Maintenance;
import com.kldv.gibought.Utils.Error.Network.OfflineNetwork;
import com.kldv.gibought.Utils.ForgotPassword;
import com.kldv.gibought.Utils.User;

import java.sql.Connection;

@SuppressWarnings({"SpellCheckingInspection", "RedundantIfStatement", "deprecation"})
public class LoginActivity extends AppCompatActivity {
    private String emai, sen;
    private boolean exist = false;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Pega Dados do Banner
        SharedPreferences preferences = getSharedPreferences("PREFERENCES",MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = preferences.edit();

        String UserState = preferences.getString("Logged","NO");
        //linka as classes e os arquivos xml
        TextView signUp = findViewById(R.id.signup);
        TextView forgotPassword = findViewById(R.id.forgotPassword);
        EditText email = findViewById(R.id.inputEmail);
        EditText password = findViewById(R.id.inputPassword);
        Button btnLogin = findViewById(R.id.btnlogin);
        //Verifica Banco Antes de logar

        //Verifica o Usuario está logado
        if(UserState.equals("YES")){
                    SharedPreferences prefs = getSharedPreferences("userInfo",
                    Context.MODE_PRIVATE);
                    @SuppressLint("CommitPrefEdits")
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putInt("stateLogin", 0);
                    edit.apply();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
        }

        //Manda para a tela de registro
        signUp.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));

        //Retira os Espaços em branco do Email
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

        //Retira os Espaços em branco da senha
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
        forgotPassword.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ForgotPassword.class)));
        /*Realiza a função do Login*/
        btnLogin.setOnClickListener(view -> {
            emai = email.getText().toString();
            sen = password.getText().toString();
            //Verifica se o campo de Email está preenchido
            if (isEmpty(email.getText().toString())) {
                Toast toast= Toast.makeText(getApplicationContext(),
                        "Do not leave the email empty", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
            //Verifica se o campo de Senha está preenchido
            else  if (isEmpty(password.getText().toString())) {
                Toast toast= Toast.makeText(getApplicationContext(),
                        "Do not leave the password empty", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
            else {
                    Handler handler = new Handler();
                    //Fecha o teclado após campos preenchidos
                    closeKeyboard();
                    handler.postDelayed(() -> {
                            exist = UserDao.searchUser(emai, sen, getApplicationContext());
                            user = UserDao.existUser(emai, sen, getApplicationContext());
                            //Verifica a existencia do Usuario no banco de dados
                            if (!exist) {
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "User not Found", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast.show();
                            } else {
                                //Faz o Login Caso os Dados de Usuarios estejam corretos.
                                SharedPreferences prefs = getSharedPreferences("userInfo",
                                        Context.MODE_PRIVATE);
                                @SuppressLint("CommitPrefEdits")
                                SharedPreferences.Editor edit = prefs.edit();
                                edit.putInt("idUser", user.getId());
                                edit.putInt("Situ", 0);
                                edit.putString("nameUser", user.getNameCli());
                                edit.putString("emailUser", user.getEmailCli());
                                edit.putInt("usAct", 0);
                                edit.putInt("stateLogin", 0);
                                edit.apply();
                                editor.putString("Logged", "YES");
                                editor.apply();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                            }
                    },800);
                }
        });
    }
    @Override
    public void onBackPressed(){
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