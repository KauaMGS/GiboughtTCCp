package com.kldv.gibought.Dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.kldv.gibought.Connex.Connexion;
import com.kldv.gibought.Utils.User;
import com.kldv.gibought.Utils.bkAccount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;

@SuppressWarnings({"SpellCheckingInspection"})
public class UserDao {

    /*Método de Inserção de Usuário*/
    public static int insertUser(User user, Context ctx){

        int result = 0;

        try {
            PreparedStatement smts = Connexion.join(ctx).prepareStatement(
                    "insert into UserCli (nameCli,emailCli,passCli,activedCli)" +
                            "values (?,?,?,?)"
            );
            smts.setString(1,user.getNameCli());
            smts.setString(2,user.getEmailCli());
            smts.setString(3,user.getPassCli());
            smts.setInt(4,1);

            result = smts.executeUpdate();

        }catch (Exception e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "Erro"+e.getMessage(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return result ;
    }
    /*Método de Verificação de Existência de Usuário*/
    public static boolean searchUser(String emailCli, String passCli,
                                     Context ctx){
        boolean isAlreadyExist = false;
        try{
            PreparedStatement pst =
                    Connexion.join(ctx).prepareStatement("select idCli,nameCli,emailCli,passCli from UserCli WHERE emailCli = ?");
            pst.setString(1, emailCli);
            ResultSet res = pst.executeQuery();
            while(res.next()){
                String passUser = res.getString(4);
                BCrypt.Result result = BCrypt.verifyer().verify(passCli.toCharArray(), passUser);
                if(result.verified){
                    isAlreadyExist = true;
                }

            }
        }catch(SQLException e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "erro" + e.getMessage(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return isAlreadyExist;
    }
    /*Retorno User*/
    public static User existUser(String emailCli, String passCli,
                                 Context ctx){
        User existUser = new User();

        try{
            PreparedStatement pst =
                    Connexion.join(ctx).prepareStatement("select idCli,nameCli,emailCli,passcli from UserCli WHERE emailCli = ?");
            pst.setString(1, emailCli);
            ResultSet res = pst.executeQuery();
            //isAlreadyExist = res.next();
            if (res != null){
                while (res.next()){
                    existUser.setId(res.getInt(1));
                    existUser.setNameCli(res.getString(2));
                    existUser.setEmailCli(res.getString(3));
                    existUser.setPassCli(res.getString(4));
                }
            }
            pst.close();
            Connexion.join(ctx).close();
        }catch(SQLException e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "erro" + e.getMessage(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return existUser;
    }
    /*Método de Verificação de Email para a tabela UserCli*/
    public static boolean verificationEmail(String emailCli,Context ctx){
        boolean isAlreadyUsed = false;
        try{
            PreparedStatement emv =
                    Connexion.join(ctx).prepareStatement("select idCli,nameCli,emailCli,passcli from UserCli WHERE emailCli = ?");
            emv.setString(1, emailCli);
            ResultSet res = emv.executeQuery();
            isAlreadyUsed = res.next();
        }catch(SQLException e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "erro" + e.getMessage(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return isAlreadyUsed;
    }
    /*Metodo de Verificação Deluxe*/
    @SuppressWarnings("unused")
    public static boolean verificationDeluxe(String emailCli, Context ctx){
        boolean isVersionPro = false;
        try{
            PreparedStatement emv =
                    Connexion.join(ctx).prepareStatement("select * from Deluxe WHERE emailCli = ?");
            emv.setString(1, emailCli);
            ResultSet res = emv.executeQuery();
            isVersionPro = res.next();
        }catch(SQLException e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "erro" + e.getMessage(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return isVersionPro;
    }
    /*Método de Inserção de Dados para a tabela bankAccount*/
    public static int insertBkAccount(bkAccount bkAccount, Context ctx){

        int result = 0;

        try {
            PreparedStatement smbkts = Connexion.join(ctx).prepareStatement(
                    "insert into bankAccount (nameBank,idImage,valueBank,typeBank)" +
                            "values (?,?,?,?)"
            );
            smbkts.setString(1,bkAccount.getNameBank());
            smbkts.setInt(2,bkAccount.getIdImage());
            smbkts.setDouble(3,bkAccount.getValueBank());
            smbkts.setInt(4,bkAccount.getTypeBank());

            result = smbkts.executeUpdate();

        }catch (Exception e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "Erro"+e.getMessage(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return result ;
    }
    /*Método de inserção dos cards*/
    public static List<bkAccount> searchbk(Context contexto){
        // Listagem de colaboradores
        List<bkAccount> lista = null;
        // Objeto de declaração
        PreparedStatement pst;

        try {
            // Criação da declaração
            pst = Connexion.join(contexto).prepareStatement("" +
                    "Select idBankAccount,nameBank,idImage,valueBank,typeBank from bankAccount Order by idBankAccount Asc");
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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    /*Método de retorno de listagem dos Cards*/
    public static List<bkAccount> searchbkn(String databk,
                                            Context contexto){
        // Listagem de colaboradores
        List<bkAccount> lista = null;
        // Objeto de declaração
        PreparedStatement pst;

        try {
            // Criação da declaração
            pst = Connexion.join(contexto).prepareStatement("" +
                    "Select idBankAccount,nameBank,idImage,valueBank,typeBank " +
                    " from bankAccount " +
                    " Where nameBank like '" + databk + "%' " +
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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    /*Método de verificação de existencia dos Cards*/
    public static boolean searchEx(String nbkCard,Context ctx){
        boolean isVersionPro = false;
        try{
            PreparedStatement emv =
                    Connexion.join(ctx).prepareStatement(
                            "select * from bankAccount"+
                                    " WHERE nameBank like '"+ nbkCard + "%'"
                    );
            //emv.setString(1, nbkCard);
            ResultSet res = emv.executeQuery();
            isVersionPro = res.next();
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

        try {
            PreparedStatement smts = Connexion.join(ctx).prepareStatement(
                    "delete from bankAccount"+
                            " WHERE idBankAccount = '"+ bkCard + "'"
            );
            result = smts.executeUpdate();

        }catch (Exception e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "Erro"+e, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return result ;
    }

    /*Método de Exclusão dos cards*/
    public static void resetPassword(String email,String password,Context ctx){
        try {
            PreparedStatement smts = Connexion.join(ctx).prepareStatement(
                    "UPDATE UserCli set passCli ='"+password+"'"+" WHERE emailCli = '"+ email + "'"
            );
           smts.executeUpdate();

        }catch (Exception e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "Erro"+e, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
    }

    /*Método para atualizar os cards*/
    public static int updateBkAccount(String name,Double value,Integer type,Integer idBank, Context ctx){
        int val = 0;

        try {
            PreparedStatement smts = Connexion.join(ctx).prepareStatement(
                    "UPDATE bankAccount set nameBank ='"+name+"'"+
                            ","+"valueBank='"+value+"'"+","+"typeBank='"+type+"'"+
                            "WHERE idBankAccount = '"+ idBank + "'"
            );
            val = smts.executeUpdate();

        }catch (Exception e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "Erro"+e, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return val;
    }

    public static double totalMoney(int idCli,Context ctx) {
        double totMoney = 00.00;
        try{
            PreparedStatement emv =
                    Connexion.join(ctx).prepareStatement(
                            "select sum(valueBank) as ttMoney from bankAccount"+
                                    " WHERE idCli = "+idCli
                    );
            ResultSet resd = emv.executeQuery();

            while(resd.next()){
                totMoney = resd.getDouble("ttMoney");
            }

        }catch(SQLException e){
            Toast toast= Toast.makeText(ctx.getApplicationContext(),
                    "erro" + e.getMessage(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }

        return totMoney;
    }
}
