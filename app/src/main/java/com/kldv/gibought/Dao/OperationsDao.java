package com.kldv.gibought.Dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.kldv.gibought.Connex.Connexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class OperationsDao {
    /*Método de Inserção de Dados para a tabela de Ganhos*/
    public static int insertEarnings(double valuebank,String value, Context ctx, int idBank, int idUser) {
        int result = 0;
        Date now = new Date();
        java.sql.Date dateNow = new java.sql.Date(now.getTime());
        try {
            PreparedStatement smbkts = Connexion.join(ctx).prepareStatement(
                    "insert into earnAccount (valueEarn,operation,dateEarnCreate,idBankAccount,idCli)" +
                            "values (?,?,?,?,?)"
            );
            smbkts.setDouble(1,valuebank);
            smbkts.setString(2,value);
            smbkts.setDate(3,dateNow);
            smbkts.setInt(4,idBank);
            smbkts.setInt(5,idUser);
            result = smbkts.executeUpdate();
            smbkts.close();
            Connexion.join(ctx).close();
        }catch (Exception e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "Erro"+e, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return result ;
    }

    /*Método de Inserção de Dados para a tabela de Gastos*/
    public static int insertSpendings(double valuebank,String value, Context ctx, int idBank, int idUser) {
        int result = 0;
        Date now = new Date();
        java.sql.Date dateNow = new java.sql.Date(now.getTime());
        try {
            PreparedStatement smbkts = Connexion.join(ctx).prepareStatement(
                    "insert into spendAccount (valueSpend,operation,dateSpendCreate,idBankAccount,idCli)" +
                            "values (?,?,?,?,?)"
            );
            smbkts.setDouble(1,valuebank);
            smbkts.setString(2,value);
            smbkts.setDate(3,dateNow);
            smbkts.setInt(4,idBank);
            smbkts.setInt(5,idUser);
            result = smbkts.executeUpdate();
            smbkts.close();
            Connexion.join(ctx).close();
        }catch (Exception e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "Erro"+e, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return result ;
    }

    //Operações do gráfico
    public static void getMonthValue(Context ctx,int idUser,String val){
        SharedPreferences prefs = ctx.getSharedPreferences("bkCardSelected",
                Context.MODE_PRIVATE);
        int idBank = prefs.getInt("idBank", 0);
        try {
            int I = 1;
            int yearCa = Calendar.getInstance().get(Calendar.YEAR);
            while (I<=12) {
                PreparedStatement pst =
                        Connexion.join(ctx).prepareStatement(
                                "select " +
                                        "COUNT(CASE MONTH(date"+val+"Create) When " + I + " THEN value"+val+" ELSE NULL END) AS monthCount" + I +","+
                                        "SUM(CASE MONTH(date"+val+"Create) When " + I + " THEN value"+val+" ELSE 0 END) AS monthValue" + I +
                                        " from "+val+"Account\n" +
                                        "inner join bankAccount on bankAccount.idBankAccount = " + idBank +
                                        "where bankAccount.idCli = " + idUser +"and "+val+"Account.idBankAccount=" + idBank +
                                        " and YEAR(date"+val+"Create) = "+yearCa+"group by bankAccount.idBankAccount"
                        );
                ResultSet res = pst.executeQuery();

                if (res.next()) {
                    String month = "monthValue" + I;
                    String count = "monthCount" + I;
                    SharedPreferences monthPref = ctx.getSharedPreferences("userValue",
                            Context.MODE_PRIVATE);
                    @SuppressLint("CommitPrefEdits")
                    SharedPreferences.Editor editMonth = monthPref.edit();
                    editMonth.putString("valueAccount"+I, String.valueOf(res.getDouble(month)));
                    editMonth.putString("countAccount"+I, String.valueOf(res.getInt(count)));
                    editMonth.apply();
                }
                I = I+1;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "erro" + throwables.getMessage(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
    }

    public static void getYearValue(Context ctx, int idUser,String val) {
        SharedPreferences prefs = ctx.getSharedPreferences("bkCardSelected",
                Context.MODE_PRIVATE);
        int idBank = prefs.getInt("idBank", 0);
        try {
            int yearCa = Calendar.getInstance().get(Calendar.YEAR);
            int I = yearCa;
            int b = 1;
            while (b<=7) {
                PreparedStatement pst =
                        Connexion.join(ctx).prepareStatement(
                                "select " +
                                        "COUNT(CASE YEAR(date"+val+"Create) When " + I + " THEN value"+val+" ELSE NULL END) AS yearCount" + b +","+
                                        "SUM(CASE YEAR(date"+val+"Create) When " + I + " THEN value"+val+" ELSE 0 END) AS yearValue" + b +
                                        " from "+val+"Account\n" +
                                        "inner join bankAccount on bankAccount.idBankAccount = " + idBank +
                                        "where bankAccount.idCli = " + idUser +"and "+val+"Account.idBankAccount=" + idBank +
                                        " and YEAR(date"+val+"Create) = "+I+"group by bankAccount.idBankAccount"
                        );
                ResultSet res = pst.executeQuery();

                if (res.next()) {
                    String year = "yearValue" + b;
                    String ycount = "yearCount" + b;
                    SharedPreferences monthPref = ctx.getSharedPreferences("userValue",
                            Context.MODE_PRIVATE);
                    @SuppressLint("CommitPrefEdits")
                    SharedPreferences.Editor editMonth = monthPref.edit();
                    editMonth.putString("yvalueAccount"+b, String.valueOf(res.getDouble(year)));
                    editMonth.putString("ycountAccount"+b, String.valueOf(res.getInt(ycount)));
                    editMonth.apply();
                }
                I = I-1;
                b = b+1;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "erro" + throwables.getMessage(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
    }

    public static void getWeekValue(Context ctx, int idUser, String val) {
        SharedPreferences prefs = ctx.getSharedPreferences("bkCardSelected",
                Context.MODE_PRIVATE);
        int idBank = prefs.getInt("idBank", 0);
        @SuppressLint("SimpleDateFormat")
        DateFormat df = new SimpleDateFormat("dd");
        @SuppressLint("SimpleDateFormat")
        DateFormat mf = new SimpleDateFormat("MM");
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DAY_OF_MONTH,Calendar.SUNDAY - dayWeek);

        try {
            int I = Integer.parseInt(df.format(cal.getTime()));
            int yearCa = Calendar.getInstance().get(Calendar.YEAR);
            int month = Integer.parseInt(mf.format(cal.getTime()));
            int b = 1;
            Calendar cml = Calendar.getInstance();
            cml.set(Calendar.MONTH,month-1);
            cml.set(Calendar.DAY_OF_MONTH,cml.getActualMaximum(Calendar.DAY_OF_MONTH));
            int lastDayMonth = Integer.parseInt(df.format(cml.getTime()));
            while (b<=7) {
                if(I == lastDayMonth+1){
                    month = month+1;
                    I = 1;
                }

                PreparedStatement pst =
                        Connexion.join(ctx).prepareStatement(
                                "select COUNT(CASE DAY(date"+val+"Create) When " + I + " THEN value"+val+" ELSE NULL END) AS weekCount"+b+",\n" +
                                        "SUM(CASE DAY(date"+val+"Create) When " + I + " THEN value"+val+" ELSE 0 END) AS weekValue"+b+"\n" +
                                        "from "+val+"Account\n" +
                                        "inner join bankAccount on bankAccount.idBankAccount = "+idBank+"\n" +
                                        "where bankAccount.idCli = "+idUser+" and "+val+"Account.idBankAccount= "+idBank+"\n" +
                                        "and YEAR("+val+"Account.date"+val+"Create) = "+yearCa+" and MONTH("+val+"Account.date"+val+"Create) = "+month
                        );
                ResultSet res = pst.executeQuery();
                if (res.next()) {
                    String year = "weekValue" + b;
                    String ycount = "weekCount" + b;
                    SharedPreferences monthPref = ctx.getSharedPreferences("userValue",
                            Context.MODE_PRIVATE);
                    @SuppressLint("CommitPrefEdits")
                    SharedPreferences.Editor editMonth = monthPref.edit();
                    editMonth.putString("wvalueAccount"+b, String.valueOf(res.getDouble(year)));
                    editMonth.putString("wcountAccount"+b, String.valueOf(res.getInt(ycount)));
                    editMonth.apply();
                }
                I = I+1;
                b = b+1;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "erro" + throwables.getMessage(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
    }
}
