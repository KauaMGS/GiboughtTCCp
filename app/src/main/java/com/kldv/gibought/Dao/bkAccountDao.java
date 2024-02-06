package com.kldv.gibought.Dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.widget.Toast;

import com.kldv.gibought.Connex.Connexion;
import com.kldv.gibought.Utils.bkAccount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class bkAccountDao {
    /*Método de Inserção de Dados para a tabela bankAccount*/
    public static int insertBkAccount(bkAccount bkAccount, Context ctx,int idUser){
        int result = 0;
        Date now = new Date();
        java.sql.Date dateNow = new java.sql.Date(now.getTime());
        try {
            PreparedStatement smbkts = Connexion.join(ctx).prepareStatement(
                    "insert into bankAccount (nameBank,idImage,valueBank,typeBank,selectedBank,lev,lsv,dateBankCreate,dateBankUpdate,idCli)" +
                            "values (?,?,?,?,?,?,?,?,?,?)"
            );
            smbkts.setString(1,bkAccount.getNameBank());
            smbkts.setInt(2,bkAccount.getIdImage());
            smbkts.setDouble(3,bkAccount.getValueBank());
            smbkts.setInt(4,bkAccount.getTypeBank());
            smbkts.setInt(5,0);
            smbkts.setString(6,"00.00");
            smbkts.setString(7,"00.00");
            smbkts.setDate(8,dateNow);
            smbkts.setDate(9,dateNow);
            smbkts.setInt(10,idUser);
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
    /*Método de retorno de card selecionado*/
    public static boolean searchselectbk(int idUser,Context ctx){
        boolean isSelected = false;
        try{
            PreparedStatement emv =
                    Connexion.join(ctx).prepareStatement(
                            "Select idBankAccount,nameBank,idImage,valueBank,typeBank,lev,lsv,idCli" +
                                    " from bankAccount Where idCli = "+ idUser +"and selectedBank = 1"+
                                    "Order by idBankAccount Asc"
                    );
            //emv.setString(1, nbkCard);
            ResultSet res = emv.executeQuery();
            isSelected = res.next();
            if(isSelected){
                SharedPreferences prefs = ctx.getSharedPreferences("bkCardSelected",
                        Context.MODE_PRIVATE);
                @SuppressLint("CommitPrefEdits")
                SharedPreferences.Editor edit = prefs.edit();
                edit.putInt("idBank", res.getInt(1));
                edit.putString("nameBank", res.getString(2));
                edit.putInt("idImageBank",res.getInt(3));
                edit.putString("valueBank",String.valueOf(res.getDouble(4)));
                edit.putInt("typeBank",res.getInt(5));
                edit.putString("levBank",res.getString(6));
                edit.putString("lsvBank",res.getString(7));
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
                            "Select idBankAccount" +
                                    " from bankAccount Where idCli = "+ idUser
                    );
            //emv.setString(1, nbkCard);
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
    public static List<bkAccount> searchbk(int id,Context ctx){
        // Listagem de colaboradores
        List<bkAccount> lista = null;
        // Objeto de declaração
        PreparedStatement pst;

        try {
            // Criação da declaração
            pst = Connexion.join(ctx).prepareStatement("" +
                    "Select idBankAccount,nameBank,idImage,valueBank,typeBank,idCli" +
                    " from bankAccount Where idCli = "+ id +
                    "Order by idBankAccount Asc");
            // Execução da pesquisa e retorno das linhas
            ResultSet res = pst.executeQuery();
            lista = new ArrayList<>();
            while(res.next()){
                lista.add(new bkAccount(
                        res.getInt(1),
                        res.getString(2),
                        res.getInt(3),
                        res.getDouble(4),
                        res.getInt(5)
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

    public static ArrayList<String> searchgobk(int id, Context ctx){
        // Listagem de colaboradores
        ArrayList<String> lista = new ArrayList<String>();
        int I = 0;
        // Objeto de declaração
        PreparedStatement pst;

        try {
            // Criação da declaração
            pst = Connexion.join(ctx).prepareStatement("" +
                    "Select idBankAccount,nameBank,idImage,valueBank,typeBank,idCli" +
                    " from bankAccount Where idCli = "+ id +
                    "Order by idBankAccount Asc");
            // Execução da pesquisa e retorno das linhas
            ResultSet res = pst.executeQuery();
            while(res.next()){
                SharedPreferences usBankPref = ctx.getSharedPreferences("userBanks",
                        Context.MODE_PRIVATE);
                String name = res.getString(2);
                lista.add(name);
                @SuppressLint("CommitPrefEdits")
                SharedPreferences.Editor editUsBank = usBankPref.edit();
                editUsBank.putInt("IdBankUser"+I, res.getInt(1));
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

    /*Método de retorno de listagem dos Cards*/
    public static List<bkAccount> searchbkn(String databk,Integer idCli,
                                            Context ctx){
        // Listagem de colaboradores
        List<bkAccount> lista = null;
        // Objeto de declaração
        PreparedStatement pst;

        try {
            // Criação da declaração
            pst = Connexion.join(ctx).prepareStatement("" +
                    "Select idBankAccount,nameBank,idImage,valueBank,typeBank " +
                    " from bankAccount " +
                    " Where nameBank like '" + databk + "%' and idCli ="+idCli+"" +
                    " Order by idBankAccount ASC");
            // Execução da pesquisa e retorno das linhas
            ResultSet res = pst.executeQuery();
            lista = new ArrayList<>();
            while(res.next()){


                lista.add(new bkAccount(
                        res.getInt(1),
                        res.getString(2),
                        res.getInt(3),
                        res.getDouble(4),
                        res.getInt(5)
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

    /*Método de verificação de existencia dos Cards*/
    public static boolean searchEx(String nbkCard,Integer idCli,Context ctx){
        boolean isVersionPro = false;
        try{
            PreparedStatement emv =
                    Connexion.join(ctx).prepareStatement(
                            "select * from bankAccount"+
                                    " WHERE nameBank like '"+ nbkCard + "%' and idCli ="+idCli+""
                    );
            //emv.setString(1, nbkCard);
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
    public static int deletedbkCard(Integer bkCard, Context ctx){

        int result = 0;
        SharedPreferences prefs = ctx.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);
        int idCli = prefs.getInt("idUser", 0);
        try {
            PreparedStatement smts = Connexion.join(ctx).prepareStatement(
                    "delete from bankAccount"+
                            " Where idBankAccount = '"+ bkCard + "' and idCli ='"+idCli+"'"
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
    public static int updateBkAccount(String name,Double value,Integer type,Integer idBank,Integer idCli, Context ctx){
        int val = 0;
        Date now = new Date();
        java.sql.Date dateNow = new java.sql.Date(now.getTime());

        try {
            PreparedStatement smts = Connexion.join(ctx).prepareStatement(
                    "UPDATE bankAccount set nameBank ='"+name+"'"+
                            ","+"valueBank='"+value+"'"+","+"dateBankUpdate='"+dateNow+"'"+","+"typeBank='"+type+"'"+
                            "Where idCli ="+idCli+" and idBankAccount ="+idBank
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

    /*Método para atualizar os cards de ganhos*/
    public static int updateBkEarnAccount(double valuebank,String lev,int idCli,Integer idBank, Context ctx){
        int val = 0;
        Date now = new Date();
        java.sql.Date dateNow = new java.sql.Date(now.getTime());

        try {
            PreparedStatement smts = Connexion.join(ctx).prepareStatement(
                    "UPDATE bankAccount set valueBank='"+valuebank+"'"+","+"dateBankUpdate='"+dateNow+"'"+","+"lev='"+lev+"'"+
                            "Where idCli ="+idCli+" and idBankAccount ="+idBank
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
    /*Método para atualizar os cards de gastos*/
    public static int updateBkSpdAccount(double valuebank,String lsv,int idCli,Integer idBank, Context ctx){
        int val = 0;
        Date now = new Date();
        java.sql.Date dateNow = new java.sql.Date(now.getTime());

        try {
            PreparedStatement smts = Connexion.join(ctx).prepareStatement(
                    "UPDATE bankAccount set valueBank='"+valuebank+"'"+","+"dateBankUpdate='"+dateNow+"'"+","+"lsv='"+lsv+"'"+
                            "Where idCli ="+idCli+" and idBankAccount ="+idBank
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
    public static int updateBkAccountDeselect(int idUser, Context ctx){
        int val = 0;

        try {
            PreparedStatement smts = Connexion.join(ctx).prepareStatement(
                    "update bankAccount set selectedBank = 0 " +
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
    public static int updateBkAccountSelectd(int idUser,int idBank, Context ctx){
        int val = 0;
        SharedPreferences monthPref = ctx.getSharedPreferences("userValue",
                Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editMonth = monthPref.edit();
        editMonth.putInt("idBankEarn", idBank);
        editMonth.apply();

        try {
            PreparedStatement smts = Connexion.join(ctx).prepareStatement(
                    "update bankAccount set selectedBank = 0 " +
                            "where idCli ="+idUser+
                            "update bankAccount set selectedBank = 1"+
                            "where idCli ="+idUser+" and idBankAccount ="+idBank

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

}
