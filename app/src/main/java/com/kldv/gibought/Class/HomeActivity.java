package com.kldv.gibought.Class;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kldv.gibought.Class.CRUD.AddActivity;
import com.kldv.gibought.Dao.UserDao;
import com.kldv.gibought.Dao.bkAccountDao;
import com.kldv.gibought.Dao.goAccountDao;
import com.kldv.gibought.R;
import com.kldv.gibought.Utils.Animation.ProgressBarAnimation;
import com.kldv.gibought.Utils.NotiFActivity;

import java.math.BigDecimal;
import java.text.DecimalFormat;


@SuppressWarnings({"SpellCheckingInspection","deprecation","FieldCanBeLocal"})
public class HomeActivity extends AppCompatActivity {
    private final boolean exist = false;
    private ProgressBar progressBar;
    private String valueBank;
    private int resu = 0;
    private TextView txtProgress;

    @SuppressLint({"NonConstantResourceId", "ShowToast", "SetTextI18n", "UseCompatLoadingForDrawables", "CommitPrefEdits"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor edit = prefs.edit();
        int stLogin = prefs.getInt("stateLogin",0);
        ProgressDialog load = null;
        if(stLogin == 0) {
            load = new ProgressDialog(HomeActivity.this);
            load.show();
            load.setContentView(R.layout.progress_dialog);
            load.getWindow().setBackgroundDrawableResource(
                    android.R.color.transparent
            );
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ProgressDialog finalLoad = load;
        if(stLogin == 0) {
            new Handler().postDelayed(() -> {
                loadScreen(finalLoad);
            }, 1000);
        }
        else{
            loadScreen(null);
        }
        edit.putInt("stateLogin", 1);
        edit.apply();
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void loadScreen(ProgressDialog load){
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorDarkGreen));
            ImageView imgbk = findViewById(R.id.imageBank);
            TextView actName = findViewById(R.id.accountName);
            TextView actValue = findViewById(R.id.accountValueTot);
            TextView actValueTotal = findViewById(R.id.total_money);
            TextView actTxt = findViewById(R.id.actTxtValue);
            TextView actType = findViewById(R.id.accountType);
            TextView eanRctly = findViewById(R.id.eanRecently);
            CardView actCard = findViewById(R.id.account_card);
            CardView goaCard = findViewById(R.id.goal_card);
            CardView recCard = findViewById(R.id.recently_card);
            TextView goalTXT = findViewById(R.id.goalName);
            LinearLayout topCardGoal = findViewById(R.id.linearLayout2);
            TextView spdRctly = findViewById(R.id.spdRecently);
            TextView resultGoal = findViewById(R.id.resultTXT);
            TextView metaTxtValue = findViewById(R.id.metaTxtValue);
            TextView txtMn = findViewById(R.id.txtTotalMoney);
            ImageView add = findViewById(R.id.btnAdc);
            ImageView haveGoal = findViewById(R.id.imagegoalHaveBank);
            ImageView vsImgTotal = findViewById(R.id.visibleTotal);
            LinearLayout progressBarSelect = findViewById(R.id.LinearProgressBar);
            LinearLayout nhgoal = findViewById(R.id.nohaveGoal);
            TextView tpMoney = findViewById(R.id.typeMoney);
            progressBar = findViewById(R.id.progress);
            txtProgress = findViewById(R.id.progressText);

            SharedPreferences prefs = getSharedPreferences("userInfo",
                    Context.MODE_PRIVATE);
            int idUser = prefs.getInt("idUser", 0);
            bkAccountDao.searchselectbk(idUser, getApplicationContext());
            goAccountDao.searchselectgoal(idUser, getApplicationContext());
            SharedPreferences.Editor editar = prefs.edit();
            editar.putInt("SituBottomMenu", 1);
            editar.putInt("Situ", 0);
            editar.apply();
            bkAccountDao.searchselectbk(idUser, getApplicationContext());
            SharedPreferences prefers = getSharedPreferences("bkCardSelected",
                    Context.MODE_PRIVATE);
            String lev = prefers.getString("levBank", getString(R.string.null_money));
            String lsv = prefers.getString("lsvBank", getString(R.string.null_money));
            SharedPreferences preferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE);

            double totMn = UserDao.totalMoney(idUser,getApplicationContext());
            BigDecimal vltBz = BigDecimal.valueOf(totMn);
            DecimalFormat nltBz = new DecimalFormat(",##0.00");
            String totMna = nltBz.format(vltBz);
            actValueTotal.setText(totMna);
        actCard.setAlpha(0f);
        goaCard.setAlpha(0f);
        recCard.setAlpha(0f);
            actCard.setVisibility(View.VISIBLE);
            goaCard.setVisibility(View.VISIBLE);
            recCard.setVisibility(View.VISIBLE);

            if (idUser == 0) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Logged", "NO");
                editor.apply();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }

            boolean exist = goAccountDao.searchselectgoal(idUser, getApplicationContext());
            SharedPreferences prefersGoal = getApplicationContext().getSharedPreferences("goalCardSelected",
                    Context.MODE_PRIVATE);
            String nameGoal = prefersGoal.getString("nameGoal", getString(R.string.none));
            String valueGoal2 = prefersGoal.getString("valGoal", "0");

            String valueGoalBank = prefersGoal.getString("valueGoal", "0");
            double valueGoalBank2 = Double.parseDouble(valueGoalBank);
            double valueGoalBank3 = Double.parseDouble(valueGoal2);
            double res = valueGoalBank2 - valueGoalBank3;

            BigDecimal vltB = BigDecimal.valueOf(valueGoalBank2);
            DecimalFormat nltB = new DecimalFormat(",##0.00");
            String valueBlt = nltB.format(vltB);

            BigDecimal vltA = BigDecimal.valueOf(valueGoalBank3);
            DecimalFormat nltA = new DecimalFormat(",##0.00");
            String valueAlt = nltA.format(vltA);

            BigDecimal vlt = BigDecimal.valueOf(res);
            DecimalFormat nlt = new DecimalFormat(",##0.00");
            String valuelt = nlt.format(vlt);

            if (!exist) {
                progressBarSelect.setVisibility(View.GONE);
                topCardGoal.setVisibility(View.GONE);
                goalTXT.setText(R.string.goal);
                haveGoal.setVisibility(View.GONE);
                nhgoal.setVisibility(View.VISIBLE);
            } else {
                progressBarSelect.setVisibility(View.VISIBLE);
                topCardGoal.setVisibility(View.VISIBLE);
                haveGoal.setVisibility(View.VISIBLE);
                goalTXT.setText(nameGoal);
                String nameBkk = prefersGoal.getString("nameBank", getString(R.string.none));
                actTxt.setText(nameBkk);
                metaTxtValue.setText(valueBlt);
                String result;
                double res1 = valueGoalBank3 * 100;
                double res2 = res1 / valueGoalBank2;
                double res3 = Math.ceil(res2);

                if (res > 0) {
                    result = "Você tem: " + valueAlt + " Ainda falta: " + valuelt;
                    resu = (int) res3;
                } else {
                    result = "Parabens Você atingiu sua meta";
                    resu = 100;
                }
                resultGoal.setText(result);
                nhgoal.setVisibility(View.GONE);
            }
            //Ações de Progresso
            progressBar.setMax(100);
            progressBar.setScaleY(2f);
            progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
            progressAnimation(Math.max(resu, 0));

            //Verificação de existencia
            exist = bkAccountDao.searchselectbk(idUser, getApplicationContext());
            if (exist) {
                add.setImageDrawable(getDrawable(R.drawable.badd));
                add.setEnabled(true);
                actValueTotal.setVisibility(View.VISIBLE);
            } else {
                add.setImageDrawable(getDrawable(R.drawable.baddoff));
                add.setEnabled(false);
                actValueTotal.setVisibility(View.GONE);
            }

            if (!exist) {
                imgbk.setVisibility(View.GONE);
                actName.setVisibility(View.GONE);
                actName.setText("");

                tpMoney.setVisibility(View.GONE);
                txtMn.setText(R.string.slc_anAccount);
                vsImgTotal.setVisibility(View.GONE);
                actValue.setVisibility(View.GONE);
                actValue.setText("");
                actType.setText(R.string.select_act);
            } else {
                int typeBank = prefers.getInt("typeBank", -1);
                int idImg = prefers.getInt("idImageBank", -1);
                String nameBk = prefers.getString("nameBank", "");
                int statesInfs = prefers.getInt("visBk", -1);
                //formatação do valor
                Double valuebanknf = Double.valueOf(prefers.getString("valueBank", "00,00"));
                DecimalFormat n = new DecimalFormat(",##0.00");

                //Visibilidade do dinheiro
                if (statesInfs != 0) {
                    valueBank = n.format(valuebanknf);
                    actValueTotal.setText(totMna);
                    eanRctly.setText("+ " + lev);
                    spdRctly.setText("- " + lsv);
                    vsImgTotal.setImageDrawable(getDrawable(R.drawable.eye));
                } else {
                    vsImgTotal.setImageDrawable(getDrawable(R.drawable.eyeoff));
                    actValueTotal.setText("----------");
                    valueBank = "----------";
                    eanRctly.setText("----------");
                    spdRctly.setText("----------");

                }
                //Verifica a Imagem
                if (idImg == 1) {
                    imgbk.setImageResource(R.drawable.bdone);
                } else if (idImg == 2) {

                    imgbk.setImageResource(R.drawable.bdtwo);
                } else if (idImg == 3) {

                    imgbk.setImageResource(R.drawable.bdthree);
                } else if (idImg == 4) {

                    imgbk.setImageResource(R.drawable.bdfour);
                } else if (idImg == 5) {

                    imgbk.setImageResource(R.drawable.bdfive);
                } else if (idImg == 6) {

                    imgbk.setImageResource(R.drawable.bdsix);
                } else if (idImg == 7) {

                    imgbk.setImageResource(R.drawable.bdseven);
                } else if (idImg == 8) {

                    imgbk.setImageResource(R.drawable.bdeight);
                } else if (idImg == 9) {

                    imgbk.setImageResource(R.drawable.bdnine);
                } else {
                    imgbk.setImageResource(R.drawable.bdothers);
                }
                //Seta os ultimos ganhos ou gastos adicionados
                if (statesInfs != 0) {
                    eanRctly.setText("+ " + lev);
                    spdRctly.setText("- " + lsv);
                }
                //Verifica o tipo de conta
                if (typeBank == 0) {
                    actType.setText(R.string.crt_account);
                } else {
                    actType.setText(R.string.svg_account);
                }
                imgbk.setVisibility(View.VISIBLE);
                actName.setVisibility(View.VISIBLE);
                actValue.setVisibility(View.VISIBLE);
                tpMoney.setVisibility(View.VISIBLE);
                actValue.setVisibility(View.VISIBLE);
                txtMn.setText(R.string.total_balance);
                actName.setText(nameBk);
                vsImgTotal.setVisibility(View.VISIBLE);
                actValue.setText(valueBank);

            }
            //Visibilidade
            vsImgTotal.setOnClickListener(v -> {
                @SuppressLint("CommitPrefEdits")
                int statesInfs = prefers.getInt("visBk", -1);
                if (statesInfs == 0) {
                    @SuppressLint("CommitPrefEdits")
                    SharedPreferences.Editor edit = prefers.edit();
                    edit.putInt("visBk", 1);
                    edit.apply();
                    Double valuebanknf = Double.valueOf(prefers.getString("valueBank", "00,00"));
                    DecimalFormat n = new DecimalFormat(",##0.00");
                    String valueon = n.format(valuebanknf);
                    vsImgTotal.setImageDrawable(getDrawable(R.drawable.eye));
                    actValue.setText(valueon);
                    actValueTotal.setText(totMna);
                    eanRctly.setText("+ " + lev);
                    spdRctly.setText("- " + lsv);
                } else {
                    @SuppressLint("CommitPrefEdits")
                    SharedPreferences.Editor edit = prefers.edit();
                    edit.putInt("visBk", 0);
                    edit.apply();
                    vsImgTotal.setImageDrawable(getDrawable(R.drawable.eyeoff));
                    actValue.setText("----------");
                    actValueTotal.setText("----------");
                    eanRctly.setText("----------");
                    spdRctly.setText("----------");
                }

            });
            //Ações do Menu
            Button btnAdc = findViewById(R.id.chooseBank);
            Button btnGoal = findViewById(R.id.choosegoalBank);
            add.setOnClickListener(view -> {
                SharedPreferences.Editor edit = prefs.edit();
                edit.putInt("SituAdd", 0);
                edit.apply();
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
                finish();
            });


            BottomNavigationView mbottomNavigationView = findViewById(R.id.bot_nav_view);

            mbottomNavigationView.setSelectedItemId(R.id.app_home);

            btnAdc.setOnClickListener(view -> {

                startActivity(new Intent(getApplicationContext(), AddAccount.class));

            });

            btnGoal.setOnClickListener(view -> {
                goAccountDao.searchselectgoal(idUser, getApplicationContext());
                startActivity(new Intent(getApplicationContext(), GoalActivity.class));

            });

            mbottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

                switch (menuItem.getItemId()) {

                    case R.id.app_notif:
                        startActivity(new Intent(getApplicationContext(), NotiFActivity.class));
                        overridePendingTransition(
                                0,
                                0
                        );
                        return true;
                    case R.id.app_graphics:
                        startActivity(new Intent(getApplicationContext(), GraphicsActivity.class));
                        overridePendingTransition(
                                R.transition.anim_slide_in_left,
                                R.transition.anim_slide_out_left
                        );
                        return true;

                    case R.id.app_engine:
                        startActivity(new Intent(getApplicationContext(), ConfigActivity.class));
                        overridePendingTransition(
                                R.transition.anim_slide_in_left,
                                R.transition.anim_slide_out_left
                        );
                        return true;
                }

                return false;
            });

            actCard.animate().alpha(1f).setDuration(500);
            goaCard.animate().alpha(1f).setDuration(1000);
            recCard.animate().alpha(1f).setDuration(1500);
            if(load != null) {
                load.dismiss();
            }

    }

    private void progressAnimation(int resu) {
        ProgressBarAnimation animation = new ProgressBarAnimation(this,txtProgress,progressBar,0f,resu);
        animation.setDuration(1000);
        progressBar.setAnimation(animation);
    }

    //fechar ao voltar
    @Override
    public void onBackPressed(){
            finishAffinity();
    }

}