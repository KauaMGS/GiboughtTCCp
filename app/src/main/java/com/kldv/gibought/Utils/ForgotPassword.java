package com.kldv.gibought.Utils;

import static android.text.TextUtils.isEmpty;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kldv.gibought.Class.LoginActivity;
import com.kldv.gibought.Dao.UserDao;
import com.kldv.gibought.R;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgotPassword extends AppCompatActivity {
    int res;
    int cod;


    private static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Button btnReset = findViewById(R.id.btnReset);
        EditText emailInf = findViewById(R.id.emailInfo);
        LinearLayout codEmail = findViewById(R.id.codEmail);
        EditText codForgot = findViewById(R.id.codForgot);
        LinearLayout verfEmail = findViewById(R.id.verfEmail);
        LinearLayout codNewPassword = findViewById(R.id.codNewPassword);
        EditText newPassword = findViewById(R.id.newPassword);
        EditText conNewPassword = findViewById(R.id.newConPassword);
        BottomNavigationView mbottomNavigationView = findViewById(R.id.bot_nav_add_view);
        res = 0;
        mbottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.app_back) {
                onBackPressed();
                return true;
            }
            return false;
        });
        btnReset.setOnClickListener(view -> {
            String emai = emailInf.getText().toString();

            if(isEmpty(emai)){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "O email não pode Estar vazio", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
            else if(!(isValid(emai))){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Digite um email valido", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
            else
            {
                if(res == 0){
                    boolean exist = UserDao.verificationEmail(emai, getApplicationContext());
                    if(exist){
                        cod = (int) ((Math.random() * ((99999 - 17801) + 1)) + 17801);
                        sendEmail(String.valueOf(cod),emai);

                        Toast toast = Toast.makeText(getApplicationContext(),
                                "um código foi enviado para seu email", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                        emailInf.setFocusable(false);
                        emailInf.setEnabled(false);
                        codEmail.setVisibility(View.VISIBLE);
                        res = 1;
                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "email não cadastrado", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }
                }else if(res == 2){
                    String npassword = newPassword.getText().toString();
                    String conpassword = conNewPassword.getText().toString();
                    if(isEmpty(npassword)){
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "O campo Senha não pode estar vazio", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }
                    else if(npassword.trim().length()<8){
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "a Senha deve ter no minimo 8 caracteres", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }
                    else if(isEmpty(conpassword)){
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "O campo Confirmar Senha não pode estar vazio", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }
                    else if(!npassword.equals(conpassword)){
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "As senhas não se batem", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }
                    else{
                        String passEnd = Encrypt.encryptPass(npassword);
                        UserDao.resetPassword(emai,passEnd,getApplicationContext());
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Senha alterada com sucesso", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                }
                else if(res == 1){
                    String vercod = codForgot.getText().toString();
                    if(vercod.equals("")||vercod.equals(" ")){
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "o Código não pode ficar vazio", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }else {
                        int codVerif = Integer.parseInt(vercod);
                        if (cod == codVerif) {
                            verfEmail.setVisibility(View.GONE);
                            codEmail.setVisibility(View.GONE);
                            codNewPassword.setVisibility(View.VISIBLE);
                            btnReset.setText("Confirmar");
                            res = 2;
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "os Códigos não se batem", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }
                    }
                }
            }

        });
    }
    @Override
    public void onBackPressed(){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    protected void sendEmail(String cod,String emRec) {
        final String from = "kldvgibought@gmail.com";
        final String pass = "giboughtkldv1234";
        final String subject = "código de verificação Gibought";
        final String messg= "O código de verificação é:"+cod;
        // Email Host - Gmail
        String host = "smtp.gmail.com";
        // Configuração de envio ao Host
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.user", from);
        props.put("mail.password", pass);
        props.put("mail.port", "443");
        //Autorizar Email
        Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
        });
        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(emRec));
            message.setSubject(subject);
            String fiMsg = messg + "\nNão responda essa mensagem.";

            message.setText(fiMsg);
            Transport.send(message);

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}