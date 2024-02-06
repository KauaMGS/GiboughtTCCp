package com.kldv.gibought.Dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.kldv.gibought.Class.HomeActivity;
import com.kldv.gibought.Connex.Connexion;
import com.kldv.gibought.Utils.goalAccount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class goAccountDao {
    /*Método de Inserção de Dados para a tabela GoalAccount*/
    public static int insertgoalAccount(goalAccount goalAccount, Context ctx,int idUser,int Goal,int BankUser){
        SharedPreferences prefs = ctx.getSharedPreferences("bkCardSelected",
                Context.MODE_PRIVATE);
        int idBank = prefs.getInt("idBank", 0);
        int result = 0;
        Date now = new Date();
        java.sql.Date dateNow = new java.sql.Date(now.getTime());
        try {
            PreparedStatement smgoalts = Connexion.join(ctx).prepareStatement(
                    "insert into Goals(nameGoal,idImage,valueGoal,selectedGoal,dateGoalCreate,dateGoalUpdate,idBankAccount,idCli,typeGoal,valGoal)" +
                            "values (?,?,?,?,?,?,?,?,?,?)"
            );
            if(Goal == 1){
                smgoalts.setInt(7,0);
            }
            else if (Goal == 2) {
                smgoalts.setInt(7,BankUser);
            }
            else{
                smgoalts.setInt(7,idBank);
            }
            smgoalts.setString(1,goalAccount.getNameGoal());
            smgoalts.setInt(2,goalAccount.getIdImage());
            smgoalts.setDouble(3,goalAccount.getValueGoal());
            smgoalts.setInt(4,0);
            smgoalts.setDate(5,dateNow);
            smgoalts.setDate(6,dateNow);
            smgoalts.setInt(8,idUser);
            smgoalts.setInt(9,Goal);
            smgoalts.setDouble(10,0);
            result = smgoalts.executeUpdate();
            smgoalts.close();
            Connexion.join(ctx).close();
        }catch (Exception e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "Erro"+e, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return result ;
    }
    /*Método de retorno de card selecionado*/
    public static boolean searchselectgoal(int idUser,Context ctx){
        SharedPreferences bkCard = ctx.getSharedPreferences("bkCardSelected",
                Context.MODE_PRIVATE);
        int idBank = bkCard.getInt("idBank", 0);
        boolean isSelected = false;
        try{
            PreparedStatement emv =
                    Connexion.join(ctx).prepareStatement(
                            "Select idGoals,nameGoal,Goals.idImage,valueGoal,Goals.idCli,nameBank,valueBank,valGoal,Goals.idBankAccount from Goals " +
                                    "inner join bankAccount on bankAccount.idBankAccount = "+idBank+
                                    "Where Goals.idCli =  "+idUser+" and selectedGoal = 1 " +
                                    "Order by idGoals Asc"
                    );
            //emv.setString(1, ngoalCard);
            ResultSet res = emv.executeQuery();
            isSelected = res.next();
            if(isSelected){
                String nameBk = goAccountDao.searchNameGoalBank(res.getInt(9), ctx);
                SharedPreferences prefs = ctx.getSharedPreferences("goalCardSelected",
                        Context.MODE_PRIVATE);
                @SuppressLint("CommitPrefEdits")
                SharedPreferences.Editor edit = prefs.edit();
                edit.putInt("idGoal", res.getInt(1));
                edit.putString("nameGoal", res.getString(2));
                edit.putInt("idImageGoal",res.getInt(3));
                edit.putString("valueGoal",String.valueOf(res.getDouble(4)));
                edit.putString("valueBankGoalSelect",res.getString(6));
                edit.putString("valueGoalBK",res.getString(7));
                edit.putString("valGoal",res.getString(8));
                edit.putString("nameBank", nameBk);
                edit.apply();
            }
            emv.close();
            res.close();
            Connexion.join(ctx).close();
        }catch(SQLException e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "erro" + e.getMessage(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return isSelected;
    }
    /*Existencia de Dados*/
    public static boolean searchData(int idUser,Context ctx){
        boolean isVersionPro = false;
        try{
            PreparedStatement emv =
                    Connexion.join(ctx).prepareStatement(
                            "Select idGoals" +
                                    " from Goals Where idCli = "+ idUser
                    );
            //emv.setString(1, ngoalCard);
            ResultSet res = emv.executeQuery();
            isVersionPro = res.next();
            emv.close();
            res.close();
            Connexion.join(ctx).close();
        }catch(SQLException e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "erro" + e.getMessage(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return isVersionPro;
    }
    /*Método de pesquisa dos cards*/
    public static List<goalAccount> searchgoal(int id,Context ctx,int Goal,int Esp){
        SharedPreferences prefs = ctx.getSharedPreferences("bkCardSelected",
                Context.MODE_PRIVATE);
        int idBank = prefs.getInt("idBank", 0);
        // Listagem de colaboradores
        List<goalAccount> lista = null;
        // Objeto de declaração
        PreparedStatement pst = null;

        try {
            // Criação da declaração
            if(Goal == 1) {
                pst = Connexion.join(ctx).prepareStatement("" +
                        "Select idGoals,nameGoal,idImage,valueGoal,idCli,valGoal,typeGoal,idBankAccount" +
                        " from Goals Where idCli = " + id +
                        " and typeGoal != 0"+
                        "Order by idGoals Asc");
            }
            else if(Goal == 5){
                pst = Connexion.join(ctx).prepareStatement("" +
                        "Select idGoals,nameGoal,idImage,valueGoal,idCli,valGoal,typeGoal,idBankAccount" +
                        " from Goals Where idCli = " + id +
                        " and typeGoal != 4"+ "and (idBankAccount = 0 OR idBankAccount = "+Esp+")"+
                        "Order by idGoals Asc");
            }
            else if(Goal == 3){
                pst = Connexion.join(ctx).prepareStatement("" +
                        "Select idGoals,nameGoal,idImage,valueGoal,idCli,valGoal,typeGoal,idBankAccount" +
                        " from Goals Where idCli = " + id +
                        " and typeGoal != 4"+"and (idBankAccount = 0 OR idBankAccount ="+idBank+")"+
                        "Order by idGoals Asc");
            }
            else if(Goal == 4){
                pst = Connexion.join(ctx).prepareStatement("" +
                        "Select idGoals,nameGoal,idImage,valueGoal,idCli,valGoal,typeGoal,idBankAccount" +
                        " from Goals Where idCli = " + id +
                        " and typeGoal = 4"+
                        "Order by idGoals Asc");
            }
            else {
                pst = Connexion.join(ctx).prepareStatement("" +
                        "Select idGoals,nameGoal,idImage,valueGoal,idCli,valGoal,typeGoal,idBankAccount" +
                        " from Goals Where idCli = " + id +
                        " and typeGoal != 0"+
                        "Order by idGoals Asc");
            }
            // Execução da pesquisa e retorno das linhas
            ResultSet res = pst.executeQuery();
            lista = new ArrayList<>();
            while(res.next()){
                lista.add(new goalAccount(
                        res.getInt(1),
                        res.getString(2),
                        res.getInt(3),
                        res.getDouble(4),
                        res.getDouble(6),
                        res.getInt(7),
                        res.getInt(8)
                ));
            }
            pst.close();
            res.close();
            Connexion.join(ctx).close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /*Método de retorno de listagem dos Cards*/
    public static List<goalAccount> searchgoaln(String query, int id, Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences("bkCardSelected",
                Context.MODE_PRIVATE);
        int idBank = prefs.getInt("idBank", 0);
        // Listagem de colaboradores
        List<goalAccount> lista = null;
        // Objeto de declaração
        PreparedStatement pst;

        try {
            // Criação da declaração
            pst = Connexion.join(ctx).prepareStatement("" +
                    "Select idGoals,nameGoal,idImage,valueGoal,idCli,valGoal,idBank" +
                    " from Goals Where nameGoal like '" + query + "%' and idCli = "+ id +
                    "Order by idGoals Asc");
            // Execução da pesquisa e retorno das linhas
            ResultSet res = pst.executeQuery();
            lista = new ArrayList<>();
            while(res.next()){
                lista.add(new goalAccount(
                        res.getInt(1),
                        res.getString(2),
                        res.getInt(3),
                        res.getDouble(4),
                        res.getDouble(6),
                        res.getInt(7),
                        res.getInt(8)
                ));
            }
            pst.close();
            res.close();
            Connexion.join(ctx).close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    /*Retorno do nome da conta*/
    public static String searchNameGoalBank(Integer idBank,Context ctx){
        String nameBank = "";
        SharedPreferences prefs = ctx.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);
        int idCli = prefs.getInt("idUser", 0);
        try{
            PreparedStatement emv =
                    Connexion.join(ctx).prepareStatement(
                            "Select distinct nameBank from Goals " +
                                    "inner join bankAccount on bankAccount.idBankAccount = Goals.idBankAccount " +
                                    "Where Goals.idCli = "+idCli+" and Goals.idBankAccount = "+idBank +
                                    "Group by nameBank,idGoals");
            //emv.setString(1, ngoalCard);
            ResultSet res = emv.executeQuery();
            while (res.next()){
                nameBank = res.getString(1);
            }
            emv.close();
            res.close();
            Connexion.join(ctx).close();
        }catch(SQLException e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "erro" + e.getMessage(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return nameBank;
    }

    /*Método de verificação de existencia dos Cards*/
    public static boolean searchExGoal(String ngoalCard,Integer idCli,Context ctx){
        boolean isVersionPro = false;
        try{
            PreparedStatement emv =
                    Connexion.join(ctx).prepareStatement(
                            "select * from Goals"+
                                    " WHERE nameGoal like '"+ ngoalCard + "%' and idCli ="+idCli
                    );
            //emv.setString(1, ngoalCard);
            ResultSet res = emv.executeQuery();
            isVersionPro = res.next();
            emv.close();
            res.close();
            Connexion.join(ctx).close();
        }catch(SQLException e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "erro" + e.getMessage(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return isVersionPro;
    }
    /**/
    /*Método de Exclusão dos cards*/
    public static int deletedgoalCard(Integer goalCard, Context ctx){

        int result = 0;
        SharedPreferences prefs = ctx.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);
        int idCli = prefs.getInt("idUser", 0);
        try {
            PreparedStatement smts = Connexion.join(ctx).prepareStatement(
                    "delete from Goals"+
                            " WHERE idGoalAccount = '"+ goalCard + "' and idCli ="+idCli+""
            );
            result = smts.executeUpdate();
            smts.close();
            Connexion.join(ctx).close();
        }catch (Exception e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "Erro"+e, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return result ;
    }
    /*Método para atualizar os cards*/
    public static int updategoalAccount(String name,Double value,Integer idGoal,Integer idCli, Context ctx){
        int val = 0;
        Date now = new Date();
        java.sql.Date dateNow = new java.sql.Date(now.getTime());

        try {
            PreparedStatement smts = Connexion.join(ctx).prepareStatement(
                    "UPDATE Goals set nameGoal ='"+name+"'"+
                            ","+"valGoal='"+value+"'"+","+"dateGoalUpdate='"+dateNow+"'"+
                            "Where idCli ="+idCli+" and idGoals ="+idGoal
            );
            val = smts.executeUpdate();
            smts.close();
            Connexion.join(ctx).close();
        }catch (Exception e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "Erro"+e, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return val;
    }

    /*Método para desselecionar o card Selecionado*/
    public static int updategoalAccountDeselect(int idUser, Context ctx,int Goal){
        int val = 0;

        try {
            PreparedStatement smts = Connexion.join(ctx).prepareStatement(
                    "update Goals set selectedGoal = 0 " +
                            "where idCli ="+idUser
            );

            val = smts.executeUpdate();
            smts.close();
            Connexion.join(ctx).close();
        }catch (Exception e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "Erro"+e, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return val;
    }


    /*Método para atualizar o card Selecionado*/
    public static int updategoalAccountSelectd(int idUser,int idGoal, Context ctx){
        int val = 0;
        SharedPreferences monthPref = ctx.getSharedPreferences("userValue",
                Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editMonth = monthPref.edit();
        editMonth.putInt("idGoalEarn", idGoal);
        editMonth.apply();

        try {
            PreparedStatement smts = Connexion.join(ctx).prepareStatement(
                    "update Goals set selectedGoal = 0 " +
                            "where idCli ="+idUser+
                            "update Goals set selectedGoal = 1"+
                            "where idCli ="+idUser+" and idGoals ="+idGoal

            );
            val = smts.executeUpdate();
            smts.close();
            Connexion.join(ctx).close();
        }catch (Exception e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "Erro"+e, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return val;
    }

    public static ArrayList<String> searchgobk(int id, Context ctx){
        // Listagem de colaboradores
        ArrayList<String> lista = new ArrayList<String>();
        int I = 0;
        // Objeto de declaração
        PreparedStatement pst;

        try {
            // Criação da declaração
            pst = Connexion.join(ctx).prepareStatement("" +
                    "Select idGoals,nameGoal" +
                    " from Goals Where idCli = "+ id +
                    "Order by idGoals Asc");
            // Execução da pesquisa e retorno das linhas
            ResultSet res = pst.executeQuery();
            while(res.next()){
                SharedPreferences usBankPref = ctx.getSharedPreferences("userGoals",
                        Context.MODE_PRIVATE);
                String name = res.getString(2);
                lista.add(name);
                @SuppressLint("CommitPrefEdits")
                SharedPreferences.Editor editUsBank = usBankPref.edit();
                editUsBank.putInt("IdGoalUser"+I, res.getInt(1));
                editUsBank.apply();
                I = I + 1;
            }

            pst.close();
            res.close();
            Connexion.join(ctx).close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
