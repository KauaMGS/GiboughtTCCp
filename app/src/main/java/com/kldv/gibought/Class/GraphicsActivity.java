package com.kldv.gibought.Class;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kldv.gibought.Class.CRUD.AddActivity;
import com.kldv.gibought.Dao.OperationsDao;
import com.kldv.gibought.Dao.bkAccountDao;
import com.kldv.gibought.R;
import com.kldv.gibought.Utils.Graph.DalesData;
import com.kldv.gibought.Utils.Graph.charts.BarChart;
import com.kldv.gibought.Utils.Graph.components.Description;
import com.kldv.gibought.Utils.Graph.components.XAxis;
import com.kldv.gibought.Utils.Graph.data.BarData;
import com.kldv.gibought.Utils.Graph.data.BarDataSet;
import com.kldv.gibought.Utils.Graph.data.BarEntry;
import com.kldv.gibought.Utils.Graph.data.Entry;
import com.kldv.gibought.Utils.Graph.formatter.IndexAxisValueFormatter;
import com.kldv.gibought.Utils.Graph.highlight.Highlight;
import com.kldv.gibought.Utils.Graph.listener.OnChartValueSelectedListener;
import com.kldv.gibought.Utils.NotiFActivity;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import pl.droidsonroids.gif.GifImageView;

@SuppressWarnings({"SpellCheckingInspection","deprecation","unchecked"})
public class GraphicsActivity extends AppCompatActivity {
    BarChart barChart;
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> labelsNames;
    ArrayList<DalesData> dalesData = new ArrayList<>();
    String nameBk,type;
    @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables","deprecation"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);
        Button genrtGraph = findViewById(R.id.generateGraph);
        @SuppressLint("CutPasteId")
        ImageView adc = findViewById(R.id.btnAdc);
        TextView dataMonthtxt = findViewById(R.id.dataMonthtxt);
        LinearLayout othersInfo = findViewById(R.id.othersInfo);
        LinearLayout dataMonth = findViewById(R.id.dataMonth);
        LinearLayout genExcel = findViewById(R.id.generateExcel);
        TextView dataWeektxt = findViewById(R.id.dataWeektxt);
        LinearLayout othersInfoWeek = findViewById(R.id.othersInfoWeek);
        LinearLayout dataWeek = findViewById(R.id.dataWeek);
        TextView dataYeartxt = findViewById(R.id.dataYeartxt);
        TextView txtJan = findViewById(R.id.janText);
        TextView txtFev = findViewById(R.id.fevText);
        TextView txtMar = findViewById(R.id.marText);
        TextView txtApr = findViewById(R.id.aprText);
        TextView txtMai = findViewById(R.id.maiText);
        TextView txtJun = findViewById(R.id.junText);
        TextView txtJul = findViewById(R.id.julText);
        TextView txtAgo = findViewById(R.id.agoText);
        TextView txtSep = findViewById(R.id.sepText);
        GifImageView animBarChart = findViewById(R.id.barGifAnimation);
        TextView txtOct = findViewById(R.id.outText);
        TextView txtNov = findViewById(R.id.novText);
        TextView txtDez = findViewById(R.id.dezText);
        TextView txtone = findViewById(R.id.yearTxtOne);
        TextView txttwo = findViewById(R.id.yearTxtTwo);
        TextView txtThree = findViewById(R.id.yearTXTThree);
        TextView txtfour = findViewById(R.id.yearTXTFour);
        TextView txtfive = findViewById(R.id.yearTXTFive);
        TextView txtsix = findViewById(R.id.yearTXTSix);
        TextView txtseven = findViewById(R.id.yearTXTSeven);
        TextView yearone = findViewById(R.id.yearOne);
        TextView yeartwo = findViewById(R.id.yearTwo);
        TextView yearThree = findViewById(R.id.yearThree);
        TextView yearfour = findViewById(R.id.yearFour);
        TextView yearfive = findViewById(R.id.yearFive);
        TextView yearsix = findViewById(R.id.yearSix);
        TextView yearseven = findViewById(R.id.yearSeven);
        TextView vseg = findViewById(R.id.segTXT);
        TextView vter = findViewById(R.id.terTXT);
        TextView vqua = findViewById(R.id.quaTXT);
        TextView vqui = findViewById(R.id.quiTXT);
        TextView vsex = findViewById(R.id.sexTXT);
        TextView cEarn = findViewById(R.id.changeEarn);
        TextView cSpd = findViewById(R.id.changeSpd);
        TextView vsab = findViewById(R.id.sabTXT);
        TextView vdom = findViewById(R.id.domTXT);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorDarGreen));
        LinearLayout othersInfoYear = findViewById(R.id.othersInfoYear);
        LinearLayout dataYear = findViewById(R.id.dataYear);
        LinearLayout vbox = findViewById(R.id.viewBox);
        LinearLayout vbox1 = findViewById(R.id.viewBox1);
        barChart = findViewById(R.id.barChart);
        Spinner spinner = findViewById(R.id.spinnerGraph);
        TextView txtAct = findViewById(R.id.txtAccount);
        type = "Earn";
        SharedPreferences prefers = getSharedPreferences("bkCardSelected",
                Context.MODE_PRIVATE);
        nameBk = prefers.getString("nameBank", getString(R.string.none));
        SharedPreferences prefs = getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editar = prefs.edit();
        editar.putInt("Situ", 2);
        editar.apply();

        dataMonth.setVisibility(View.GONE);
        dataWeek.setVisibility(View.GONE);
        dataYear.setVisibility(View.GONE);
        txtAct.setText(nameBk);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.type_graphs_adf, R.layout.spinner_item_account);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        txtAct.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), AddAccount.class));
            finish();
        });

        cEarn.setOnClickListener(view -> {
            animBarChart.setVisibility(View.VISIBLE);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorDarGreen));
            cSpd.setTextColor(getResources().getColor(R.color.colorWhite));
            cEarn.setTextColor(getResources().getColor(R.color.colorBlack));
            vbox.setBackgroundColor(getResources().getColor(R.color.colorDarGreen));
            vbox1.setBackgroundColor(getResources().getColor(R.color.colorDarGreen));
            dataWeek.setBackgroundColor(getResources().getColor(R.color.colorDarGreen));
            dataYear.setBackgroundColor(getResources().getColor(R.color.colorDarGreen));
            dataMonth.setBackgroundColor(getResources().getColor(R.color.colorDarGreen));
            genExcel.setBackgroundColor(getResources().getColor(R.color.colorDarGreen));
            genrtGraph.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            genrtGraph.setTextColor(getResources().getColor(R.color.colorBlack));
            barChart.setVisibility(View.GONE);
            dataWeek.setVisibility(View.GONE);
            dataMonth.setVisibility(View.GONE);
            dataYear.setVisibility(View.GONE);
            genExcel.setVisibility(View.GONE);
            type = "Earn";
        });

        cSpd.setOnClickListener(view -> {
            animBarChart.setVisibility(View.VISIBLE);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorDarRed));
            cEarn.setTextColor(getResources().getColor(R.color.colorWhite));
            cSpd.setTextColor(getResources().getColor(R.color.colorBlack));
            vbox.setBackgroundColor(getResources().getColor(R.color.colorDarRed));
            vbox1.setBackgroundColor(getResources().getColor(R.color.colorDarRed));
            dataWeek.setBackgroundColor(getResources().getColor(R.color.colorDarRed));
            dataYear.setBackgroundColor(getResources().getColor(R.color.colorDarRed));
            dataMonth.setBackgroundColor(getResources().getColor(R.color.colorDarRed));
            genExcel.setBackgroundColor(getResources().getColor(R.color.colorDarRed));
            genrtGraph.setBackgroundColor(getResources().getColor(R.color.colorRedLight));
            genrtGraph.setTextColor(getResources().getColor(R.color.colorWhite));
            barChart.setVisibility(View.GONE);
            dataWeek.setVisibility(View.GONE);
            dataMonth.setVisibility(View.GONE);
            dataYear.setVisibility(View.GONE);
            genExcel.setVisibility(View.GONE);
            type = "Spend";
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int spinnerItem = spinner.getSelectedItemPosition();
                switch (spinnerItem) {
                    case 1:
                    case 3:
                        animBarChart.setVisibility(View.VISIBLE);
                        animBarChart.setImageResource(R.drawable.graphbaseseven);
                        genrtGraph.setVisibility(View.VISIBLE);
                        barChart.setVisibility(View.GONE);
                        dataWeek.setVisibility(View.GONE);
                        dataMonth.setVisibility(View.GONE);
                        dataYear.setVisibility(View.GONE);
                        genExcel.setVisibility(View.GONE);
                        break;
                    case 2:
                        animBarChart.setVisibility(View.VISIBLE);
                        animBarChart.setImageResource(R.drawable.graphbasetwelve);
                        genrtGraph.setVisibility(View.VISIBLE);
                        barChart.setVisibility(View.GONE);
                        dataWeek.setVisibility(View.GONE);
                        dataMonth.setVisibility(View.GONE);
                        dataYear.setVisibility(View.GONE);
                        genExcel.setVisibility(View.GONE);
                        break;

                    default:
                        genrtGraph.setVisibility(View.GONE);
                        barChart.setVisibility(View.GONE);
                        dataWeek.setVisibility(View.GONE);
                        dataMonth.setVisibility(View.GONE);
                        dataYear.setVisibility(View.GONE);
                        genExcel.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dataMonthtxt.setOnClickListener(view -> {
            if (othersInfo.getVisibility() == View.VISIBLE) {
                othersInfo.setVisibility(View.GONE);
            }
            else{
                othersInfo.setVisibility(View.VISIBLE);
            }

        });

        dataWeektxt.setOnClickListener(view -> {
            if (othersInfoWeek.getVisibility() == View.VISIBLE) {
                othersInfoWeek.setVisibility(View.GONE);
            }
            else{
                othersInfoWeek.setVisibility(View.VISIBLE);
            }

        });

        dataYeartxt.setOnClickListener(view -> {
            if (othersInfoYear.getVisibility() == View.VISIBLE) {
                othersInfoYear.setVisibility(View.GONE);
            }
            else{
                othersInfoYear.setVisibility(View.VISIBLE);
            }

        });

        genExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    CreateExc();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (BiffException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        });

        genrtGraph.setOnClickListener(view -> {
            if(nameBk.equals(getString(R.string.none))){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Selecione uma conta", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
            else {
                int selectGraph = spinner.getSelectedItemPosition();
                if (selectGraph == 0) {
                    barChart.setVisibility(View.GONE);
                    dataWeek.setVisibility(View.GONE);
                    dataMonth.setVisibility(View.GONE);
                    dataYear.setVisibility(View.GONE);
                    genExcel.setVisibility(View.GONE);
                } else if (selectGraph == 1) {
                    animBarChart.setImageResource(R.drawable.graphanimseven);

                    barEntryArrayList = new ArrayList<>();
                    labelsNames = new ArrayList<>();
                    SharedPreferences WeekPref = getSharedPreferences("userValue",
                            Context.MODE_PRIVATE);

                    SharedPreferences.Editor editWeek = WeekPref.edit();
                    editWeek.putString("wcountAccount1", "0");
                    editWeek.putString("wcountAccount2", "0");
                    editWeek.putString("wcountAccount3", "0");
                    editWeek.putString("wcountAccount4", "0");
                    editWeek.putString("wcountAccount5", "0");
                    editWeek.putString("wcountAccount6", "0");
                    editWeek.putString("wcountAccount7", "0");
                    editWeek.apply();
                    fillWeekValue();
                    for (int i = 0; i < dalesData.size(); i++) {
                        String text = dalesData.get(i).getText();
                        double value = dalesData.get(i).getValues();
                        barEntryArrayList.add(new BarEntry(i, (float) value));
                        labelsNames.add(text);
                    }
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        barChart.setVisibility(View.VISIBLE);

                        BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "Semanal");
                        barDataSet.setDrawValues(false);
                        if(type.equals("Earn")) {
                            barDataSet = new BarDataSet(barEntryArrayList, "Ganho Semanal");
                            barDataSet.setDrawValues(false);
                            barDataSet.setColors(getResources().getColor(R.color.colorDarGreen));
                        }else{
                            barDataSet = new BarDataSet(barEntryArrayList, "Gasto Semanal");
                            barDataSet.setDrawValues(false);
                            barDataSet.setColors(getResources().getColor(R.color.colorDarRed));
                        }
                        Description description = new Description();
                        description.setText("");
                        barChart.setDescription(description);
                        BarData barData = new BarData(barDataSet);
                        barChart.setData(barData);

                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsNames));
                        xAxis.setDrawGridLines(false);
                        xAxis.setDrawAxisLine(false);
                        xAxis.setGranularity(1f);
                        xAxis.setLabelCount(labelsNames.size());
                        xAxis.setLabelRotationAngle(0);
                        barChart.animateY(2000);
                        barChart.invalidate();


                        vseg.setText(WeekPref.getString("wcountAccount2","0"));
                        vter.setText(WeekPref.getString("wcountAccount3","0"));
                        vqua.setText(WeekPref.getString("wcountAccount4","0"));
                        vqui.setText(WeekPref.getString("wcountAccount5","0"));
                        vsex.setText(WeekPref.getString("wcountAccount6","0"));
                        vsab.setText(WeekPref.getString("wcountAccount7","0"));
                        vdom.setText(WeekPref.getString("wcountAccount1","0"));
                        dataMonth.setVisibility(View.GONE);
                        dataYear.setVisibility(View.GONE);
                        animBarChart.setVisibility(View.GONE);
                        dataWeek.setVisibility(View.VISIBLE);
                        othersInfoWeek.setVisibility(View.VISIBLE);
                        genExcel.setVisibility(View.VISIBLE);
                        animBarChart.setImageResource(R.drawable.graphbaseseven);

                    }, 7000);

                } else if (selectGraph == 2) {
                    animBarChart.setImageResource(R.drawable.graphanimtwelve);
                    barEntryArrayList = new ArrayList<>();
                    labelsNames = new ArrayList<>();
                    SharedPreferences monthPref = getSharedPreferences("userValue",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editMonth = monthPref.edit();
                    editMonth.putString("countAccount1", "0");
                    editMonth.putString("countAccount2", "0");
                    editMonth.putString("countAccount3", "0");
                    editMonth.putString("countAccount4", "0");
                    editMonth.putString("countAccount5", "0");
                    editMonth.putString("countAccount6", "0");
                    editMonth.putString("countAccount7", "0");
                    editMonth.putString("countAccount8", "0");
                    editMonth.putString("countAccount9", "0");
                    editMonth.putString("countAccount10", "0");
                    editMonth.putString("countAccount11", "0");
                    editMonth.putString("countAccount12", "0");
                    editMonth.apply();
                    fillMonthValue();
                    for (int i = 0; i < dalesData.size(); i++) {
                        String text = dalesData.get(i).getText();
                        double value = dalesData.get(i).getValues();
                        barEntryArrayList.add(new BarEntry(i, (float) value));
                        labelsNames.add(text);
                    }
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        barChart.setVisibility(View.VISIBLE);
                        BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "Mensal");
                        barDataSet.setDrawValues(false);
                        if(type.equals("Earn")) {
                            barDataSet = new BarDataSet(barEntryArrayList, "Ganho Mensa");
                            barDataSet.setDrawValues(false);
                            barDataSet.setColors(getResources().getColor(R.color.colorDarGreen));
                        }else{
                            barDataSet = new BarDataSet(barEntryArrayList, "Gasto Mensal");
                            barDataSet.setDrawValues(false);
                            barDataSet.setColors(getResources().getColor(R.color.colorDarRed));
                        }
                        Description description = new Description();
                        description.setText("");
                        barChart.setDescription(description);
                        BarData barData = new BarData(barDataSet);
                        barChart.setData(barData);

                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsNames));
                        xAxis.setDrawGridLines(false);
                        xAxis.setDrawAxisLine(false);
                        xAxis.setGranularity(1f);
                        xAxis.setLabelCount(labelsNames.size());
                        xAxis.setLabelRotationAngle(0);
                        barChart.animateY(2000);
                        barChart.invalidate();


                        txtJan.setText(monthPref.getString("countAccount1","0"));
                        txtFev.setText(monthPref.getString("countAccount2","0"));
                        txtMar.setText(monthPref.getString("countAccount3","0"));
                        txtApr.setText(monthPref.getString("countAccount4","0"));
                        txtMai.setText(monthPref.getString("countAccount5","0"));
                        txtJun.setText(monthPref.getString("countAccount6","0"));
                        txtJul.setText(monthPref.getString("countAccount7","0"));
                        txtAgo.setText(monthPref.getString("countAccount8","0"));
                        txtSep.setText(monthPref.getString("countAccount9","0"));
                        txtOct.setText(monthPref.getString("countAccount10","0"));
                        txtNov.setText(monthPref.getString("countAccount11","0"));
                        txtDez.setText(monthPref.getString("countAccount12","0"));
                        dataWeek.setVisibility(View.GONE);
                        dataYear.setVisibility(View.GONE);
                        animBarChart.setVisibility(View.GONE);
                        dataMonth.setVisibility(View.VISIBLE);
                        othersInfo.setVisibility(View.VISIBLE);
                        genExcel.setVisibility(View.VISIBLE);
                        animBarChart.setImageResource(R.drawable.graphbasetwelve);
                    }, 7000);
                } else {
                    animBarChart.setImageResource(R.drawable.graphanimseven);
                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    int year1 = year - 1;
                    int year2 = year1 - 1;
                    int year3 = year2 - 1;
                    int year4 = year3 - 1;
                    int year5 = year4 - 1;
                    int year6 = year5 - 1;
                    String yearAnow = String.valueOf(year);
                    String yearAOne = String.valueOf(year1);
                    String yearAtwo = String.valueOf(year2);
                    String yearAThree = String.valueOf(year3);
                    String yearAFour = String.valueOf(year4);
                    String yearAFive = String.valueOf(year5);
                    String yearASix = String.valueOf(year6);
                    barEntryArrayList = new ArrayList<>();
                    labelsNames = new ArrayList<>();
                    SharedPreferences YearPref = getSharedPreferences("userValue",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editYear = YearPref.edit();
                    editYear.putString("ycountAccount1", "0");
                    editYear.putString("ycountAccount2", "0");
                    editYear.putString("ycountAccount3", "0");
                    editYear.putString("ycountAccount4", "0");
                    editYear.putString("ycountAccount5", "0");
                    editYear.putString("ycountAccount6", "0");
                    editYear.putString("ycountAccount7", "0");
                    editYear.apply();

                    fillYearValue();
                    for (int i = 0; i < dalesData.size(); i++) {
                        String text = dalesData.get(i).getText();
                        double value = dalesData.get(i).getValues();
                        barEntryArrayList.add(new BarEntry(i, (float) value));
                        labelsNames.add(text);
                    }
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        barChart.setVisibility(View.VISIBLE);
                        BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "Anual");
                        barDataSet.setDrawValues(false);
                        if(type.equals("Earn")) {
                            barDataSet = new BarDataSet(barEntryArrayList, "Ganho Anual");
                            barDataSet.setDrawValues(false);
                            barDataSet.setColors(getResources().getColor(R.color.colorDarGreen));
                        }else{
                            barDataSet = new BarDataSet(barEntryArrayList, "Gasto Anual");
                            barDataSet.setDrawValues(false);
                            barDataSet.setColors(getResources().getColor(R.color.colorDarRed));
                        }
                        Description description = new Description();
                        description.setText("");
                        barChart.setDescription(description);
                        BarData barData = new BarData(barDataSet);
                        barChart.setData(barData);

                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsNames));
                        xAxis.setDrawGridLines(false);
                        xAxis.setDrawAxisLine(false);
                        xAxis.setGranularity(1f);
                        xAxis.setLabelCount(labelsNames.size());
                        xAxis.setLabelRotationAngle(0);
                        barChart.animateY(2000);
                        barChart.invalidate();


                        txtone.setText(YearPref.getString("ycountAccount1","0"));
                        txttwo.setText(YearPref.getString("ycountAccount2","0"));
                        txtThree.setText(YearPref.getString("ycountAccount3","0"));
                        txtfour.setText(YearPref.getString("ycountAccount4","0"));
                        txtfive.setText(YearPref.getString("ycountAccount5","0"));
                        txtsix.setText(YearPref.getString("ycountAccount6","0"));
                        txtseven.setText(YearPref.getString("ycountAccount7","0"));
                        yearone.setText(yearAnow);
                        yeartwo.setText(yearAOne);
                        yearThree.setText(yearAtwo);
                        yearfour.setText(yearAThree);
                        yearfive.setText(yearAFour);
                        yearsix.setText(yearAFive);
                        yearseven.setText(yearASix);
                        animBarChart.setVisibility(View.GONE);
                        dataMonth.setVisibility(View.GONE);
                        dataWeek.setVisibility(View.GONE);
                        dataYear.setVisibility(View.VISIBLE);
                        othersInfoYear.setVisibility(View.VISIBLE);
                        genExcel.setVisibility(View.VISIBLE);
                        animBarChart.setImageResource(R.drawable.graphbaseseven);
                    }, 7000);
                }
            }
        });

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int x = barChart.getData().getDataSetForEntry(e).getEntryIndex((BarEntry) e);
                String region = dalesData.get(x).getText();
                String value = NumberFormat.getCurrencyInstance().format(dalesData.get(x).getValues());
                AlertDialog.Builder builder = new AlertDialog.Builder(GraphicsActivity.this);
                builder.setCancelable(true);
                View view = LayoutInflater.from(GraphicsActivity.this).inflate(R.layout.graph_dialog,null);
                TextView regionTxtView = view.findViewById(R.id.txtRegion);
                TextView earnTxtView = view.findViewById(R.id.txtValue);
                Button btClose = view.findViewById(R.id.btnClose);
                SharedPreferences prefs = getSharedPreferences("userInfo",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = prefs.edit();
                edit.putInt("SituBottomMenu", 2);
                edit.apply();
                regionTxtView.setText(region);
                if(type.equals("Earn")) {
                    btClose.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorDarGreen)));
                    regionTxtView.setTextColor(getResources().getColor(R.color.colorDarGreen));
                }else{
                    btClose.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorDarRed)));
                    regionTxtView.setTextColor(getResources().getColor(R.color.colorDarRed));
                }
                earnTxtView.setText(value);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                btClose.setOnClickListener(view1 -> alertDialog.hide());

            }

            @Override
            public void onNothingSelected() {

            }
        });

        //Bottom_Menu
        adc.setOnClickListener(view -> {
            @SuppressLint("CutPasteId") ImageView bt_add = findViewById(R.id.btnAdc);
            bt_add.setImageResource(R.drawable.baddoff);
            startActivity(new Intent(getApplicationContext(), AddActivity.class));
            SharedPreferences.Editor edit = prefs.edit();
            edit.putInt("SituAdd", 1);
            edit.apply();
        });
        int idUser = prefs.getInt("idUser", 0);
        boolean exist = bkAccountDao.searchselectbk(idUser, getApplicationContext());
        if (!exist){
            adc.setImageDrawable(getDrawable(R.drawable.baddoff));
            adc.setEnabled(false);
        }
        else{
            adc.setImageDrawable(getDrawable(R.drawable.badd));
            adc.setEnabled(true);
        }
        BottomNavigationView mbottomNavigationView = findViewById(R.id.bot_nav_view);

        mbottomNavigationView.setSelectedItemId(R.id.app_graphics);

        mbottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()){

                case R.id.app_home:
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    overridePendingTransition(
                            R.transition.anim_slide_in_right,
                            R.transition.anim_slide_out_right
                    );
                    return true;

                case R.id.app_notif:
                    startActivity(new Intent(getApplicationContext(), NotiFActivity.class));
                    overridePendingTransition(
                            0,
                            0
                    );
                    return true;


                case R.id.app_engine:
                    startActivity(new Intent(getApplicationContext(),ConfigActivity.class));
                    overridePendingTransition(
                            R.transition.anim_slide_in_left,
                            R.transition.anim_slide_out_left
                    );
                    return true;

            }

            return false;
        });
    }
    private void fillWeekValue(){
        SharedPreferences WeekPref = getSharedPreferences("userValue",
                Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editWeek = WeekPref.edit();
        editWeek.putString("wvalueAccount1", "0");
        editWeek.putString("wvalueAccount2", "0");
        editWeek.putString("wvalueAccount3", "0");
        editWeek.putString("wvalueAccount4", "0");
        editWeek.putString("wvalueAccount5", "0");
        editWeek.putString("wvalueAccount6", "0");
        editWeek.putString("wvalueAccount7", "0");
        editWeek.apply();

        SharedPreferences prefs = getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);
        int idUser = prefs.getInt("idUser", 0);
        OperationsDao.getWeekValue(getApplicationContext(),idUser,this.type);

        double mon,tue,wedn,thu,fri,sat,sun;
        mon = Double.parseDouble(WeekPref.getString("wvalueAccount2", "0"));
        tue = Double.parseDouble(WeekPref.getString("wvalueAccount3", "0"));
        wedn = Double.parseDouble(WeekPref.getString("wvalueAccount4", "0"));
        thu = Double.parseDouble(WeekPref.getString("wvalueAccount5", "0"));
        fri = Double.parseDouble(WeekPref.getString("wvalueAccount6", "0"));
        sat = Double.parseDouble(WeekPref.getString("wvalueAccount7", "0"));
        sun = Double.parseDouble(WeekPref.getString("wvalueAccount1", "0"));

        dalesData.clear();
        dalesData.add(new DalesData("Dom",sun));
        dalesData.add(new DalesData("Seg",mon));
        dalesData.add(new DalesData("Ter",tue));
        dalesData.add(new DalesData("Quar",wedn));
        dalesData.add(new DalesData("Qui",thu));
        dalesData.add(new DalesData("Sex",fri));
        dalesData.add(new DalesData("Sab",sat));
    }


    private void fillMonthValue(){
        SharedPreferences monthPref = getSharedPreferences("userValue",
                Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editMonth = monthPref.edit();
        editMonth.putString("valueAccount1", "0");
        editMonth.putString("valueAccount2", "0");
        editMonth.putString("valueAccount3", "0");
        editMonth.putString("valueAccount4", "0");
        editMonth.putString("valueAccount5", "0");
        editMonth.putString("valueAccount6", "0");
        editMonth.putString("valueAccount7", "0");
        editMonth.putString("valueAccount8", "0");
        editMonth.putString("valueAccount9", "0");
        editMonth.putString("valueAccount10", "0");
        editMonth.putString("valueAccount11", "0");
        editMonth.putString("valueAccount12", "0");
        editMonth.apply();

        SharedPreferences prefs = getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);
        int idUser = prefs.getInt("idUser", 0);
        OperationsDao.getMonthValue(getApplicationContext(),idUser, this.type);

        double jan,fev,mar,apr,mai,jun,jul,ago,sep,oct,nov,dez;
        jan = Double.parseDouble(monthPref.getString("valueAccount1", "0"));
        fev = Double.parseDouble(monthPref.getString("valueAccount2", "0"));
        mar = Double.parseDouble(monthPref.getString("valueAccount3", "0"));
        apr = Double.parseDouble(monthPref.getString("valueAccount4", "0"));
        mai = Double.parseDouble(monthPref.getString("valueAccount5", "0"));
        jun = Double.parseDouble(monthPref.getString("valueAccount6", "0"));
        jul = Double.parseDouble(monthPref.getString("valueAccount7", "0"));
        ago = Double.parseDouble(monthPref.getString("valueAccount8", "0"));
        sep = Double.parseDouble(monthPref.getString("valueAccount9", "0"));
        oct = Double.parseDouble(monthPref.getString("valueAccount10", "0"));
        nov = Double.parseDouble(monthPref.getString("valueAccount11", "0"));
        dez = Double.parseDouble(monthPref.getString("valueAccount12", "0"));
        dalesData.clear();
        dalesData.add(new DalesData("Jane",jan));
        dalesData.add(new DalesData("Fev",fev));
        dalesData.add(new DalesData("Mar",mar));
        dalesData.add(new DalesData("Ab",apr));
        dalesData.add(new DalesData("Mai",mai));
        dalesData.add(new DalesData("Jun",jun));
        dalesData.add(new DalesData("Jul",jul));
        dalesData.add(new DalesData("Ago",ago));
        dalesData.add(new DalesData("Set",sep));
        dalesData.add(new DalesData("Out",oct));
        dalesData.add(new DalesData("Nov",nov));
        dalesData.add(new DalesData("Dez",dez));
    }

    private void fillYearValue(){
        SharedPreferences yearPref = getSharedPreferences("userValue",
                Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editYear = yearPref.edit();
        editYear.putString("yvalueAccount1", "0");
        editYear.putString("yvalueAccount2", "0");
        editYear.putString("yvalueAccount3", "0");
        editYear.putString("yvalueAccount4", "0");
        editYear.putString("yvalueAccount5", "0");
        editYear.putString("yvalueAccount6", "0");
        editYear.putString("yvalueAccount7", "0");
        editYear.apply();

        SharedPreferences prefs = getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);
        int idUser = prefs.getInt("idUser", 0);
        OperationsDao.getYearValue(getApplicationContext(),idUser,this.type);

        double yearvalue,yearvalue1,yearvalue2,yearvalue3,yearvalue4,yearvalue5,yearvalue6;
        yearvalue = Double.parseDouble(yearPref.getString("yvalueAccount1", "0"));
        yearvalue1 = Double.parseDouble(yearPref.getString("yvalueAccount2", "0"));
        yearvalue2 = Double.parseDouble(yearPref.getString("yvalueAccount3", "0"));
        yearvalue3 = Double.parseDouble(yearPref.getString("yvalueAccount4", "0"));
        yearvalue4 = Double.parseDouble(yearPref.getString("yvalueAccount5", "0"));
        yearvalue5 = Double.parseDouble(yearPref.getString("yvalueAccount6", "0"));
        yearvalue6 = Double.parseDouble(yearPref.getString("yvalueAccount7", "0"));
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int year1 = year - 1;
        int year2 = year1 - 1;
        int year3 = year2 - 1;
        int year4 = year3 - 1;
        int year5 = year4 - 1;
        int year6 = year5 - 1;
        String yearnow = String.valueOf(year);
        String yearOne = String.valueOf(year1);
        String yeartwo = String.valueOf(year2);
        String yearThree = String.valueOf(year3);
        String yearFour = String.valueOf(year4);
        String yearFive = String.valueOf(year5);
        String yearSix = String.valueOf(year6);
        dalesData.clear();
        dalesData.add(new DalesData(yearnow,yearvalue));
        dalesData.add(new DalesData(yearOne,yearvalue1));
        dalesData.add(new DalesData(yeartwo,yearvalue2));
        dalesData.add(new DalesData(yearThree,yearvalue3));
        dalesData.add(new DalesData(yearFour,yearvalue4));
        dalesData.add(new DalesData(yearFive,yearvalue5));
        dalesData.add(new DalesData(yearSix,yearvalue6));
    }

    public void onBackPressed(){}
    @Override
    protected void onResume() {
        SharedPreferences prefers = getSharedPreferences("bkCardSelected",
                Context.MODE_PRIVATE);
        nameBk = prefers.getString("nameBank", getString(R.string.none));
        TextView txtAct = findViewById(R.id.txtAccount);

        txtAct.setText(nameBk);
        super.onResume();
    }
    public void CreateExc() throws IOException, BiffException, WriteException {
        File file = new File(getExternalFilesDir(null),"excel.xls");
        WritableWorkbook wb = null;

        try{
            wb = Workbook.createWorkbook(file);
        }catch (IOException e){
            e.printStackTrace();
        }

        wb.createSheet("planilha",0);
        WritableSheet plan = wb.getSheet(0);
        Label label = new Label(0,0,"Conta x");

        try {
            plan.addCell(label);
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
        try{
            wb.write();
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }

        File xlsFile = new File("/storage/emulated/0/android/data/com.kldv.gibought/files/excel.xls");
        Workbook wbb = Workbook.getWorkbook(xlsFile);
        WritableWorkbook copy = Workbook.createWorkbook(new File("storage/emulated/0/android/data/com.kldv.gibought/files/excel.xls"),wbb);
        WritableSheet sheet = copy.getSheet(0);
        Label label1 = new Label(1,2,"janeiro");
        Label label2 = new Label(2,2,"Fevereiro");
        Label label3 = new Label(3,2,"março");
        Label label4 = new Label(4,2,"Abril");
        Label label5 = new Label(5,2,"Maio");
        Label label6 = new Label(6,2,"Junho");
        Label label7 = new Label(7,2,"julho");
        Label label8 = new Label(8,2,"agosto");
        Label label9 = new Label(9,2,"Setembro");
        Label label10 = new Label(10,2,"Outubro");
        Label label11 = new Label(11,2,"Novembro");
        Label label12 = new Label(12,2,"Dezembro");
        sheet.addCell(label1);
        sheet.addCell(label2);
        sheet.addCell(label3);
        sheet.addCell(label4);
        sheet.addCell(label5);
        sheet.addCell(label6);
        sheet.addCell(label7);
        sheet.addCell(label8);
        sheet.addCell(label9);
        sheet.addCell(label10);
        sheet.addCell(label11);
        sheet.addCell(label12);
        copy.write();
        copy.close();

    }
}