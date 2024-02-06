package com.kldv.gibought.Utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.kldv.gibought.Class.AddAccount;
import com.kldv.gibought.Class.CRUD.AddActivity;
import com.kldv.gibought.Class.CRUD.EditCardBank;
import com.kldv.gibought.Class.GraphicsActivity;
import com.kldv.gibought.Class.HomeActivity;
import com.kldv.gibought.Dao.bkAccountDao;
import com.kldv.gibought.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link bkAccount}.
 * TODO: Replace the implementation with code for your data type.
 */
@SuppressWarnings("SpellCheckingInspection")
public class MybkAccountRecyclerViewAdapter extends RecyclerView.Adapter<MybkAccountRecyclerViewAdapter.ViewHolder> {

    private final List<bkAccount> mValues;
    private static Context ctx;
    @SuppressLint("StaticFieldLeak")

    public MybkAccountRecyclerViewAdapter(List<bkAccount> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listbk_account, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        bkAccount bkcurrent = mValues.get(position);
        holder.mItem = bkcurrent;
        holder.mIdView.setText(String.valueOf(mValues.get(position).getId()));
        int idImg,idType;
        idImg = bkcurrent.getIdImage();
        idType = bkcurrent.getTypeBank();
        BigDecimal vlbk = BigDecimal.valueOf(bkcurrent.getValueBank());
        DecimalFormat n = new DecimalFormat(",##0.00");
        String valuebk = n.format(vlbk);
//Verifica a Imagem
        if(idImg == 1){
            holder.imgBkItem.setImageResource(R.drawable.bdone);
        }
        else if(idImg == 2){

            holder.imgBkItem.setImageResource(R.drawable.bdtwo);
        }
        else if(idImg == 3){

            holder.imgBkItem.setImageResource(R.drawable.bdthree);
        }
        else if(idImg == 4){

            holder.imgBkItem.setImageResource(R.drawable.bdfour);
        }else if(idImg == 5){

            holder.imgBkItem.setImageResource(R.drawable.bdfive);
        }else if(idImg == 6){

            holder.imgBkItem.setImageResource(R.drawable.bdsix);
        }else if(idImg == 7){

            holder.imgBkItem.setImageResource(R.drawable.bdseven);
        }else if(idImg == 8){

            holder.imgBkItem.setImageResource(R.drawable.bdeight);
        }else if(idImg == 9){

            holder.imgBkItem.setImageResource(R.drawable.bdnine);
        }
        else {
            holder.imgBkItem.setImageResource(R.drawable.bdothers);
        }
//Verifica o Tipo da conta
        if (idType == 0){

            holder.actTypeItem.setText(R.string.crt_account);
        }
        else {

            holder.actTypeItem.setText(R.string.svg_account);
        }

        holder.actNameItem.setText(mValues.get(position).getNameBank());

        holder.actValueItem.setText(valuebk);

        holder.setListener();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mIdView;
        public AppCompatImageView imgBkItem,imgEdit;
        public AppCompatTextView actNameItem;
        public AppCompatTextView actValueItem;
        public AppCompatTextView actTypeItem;
        public bkAccount mItem;

        public ViewHolder(View view) {
            super(view);

            mView = view;
            imgBkItem = view.findViewById(R.id.imageBankItem);
            actNameItem = view.findViewById(R.id.accountNameItem);
            actValueItem = view.findViewById(R.id.accountValueItem);
            actTypeItem = view.findViewById(R.id.accountTypeItem);
            imgEdit = view.findViewById(R.id.editCard);
            mIdView = view.findViewById(R.id.Id_card);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }

        public void setListener() {
            imgEdit.setOnClickListener(ViewHolder.this);
            mView.setOnClickListener(ViewHolder.this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.editCard) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Escolha uma opção");
                builder.setMessage("Editar ou Deletar uma counta");
                builder.setPositiveButton("Editar", (dialog, which) -> {
                    String idCard = mIdView.getText().toString();
                    String nameBank = actNameItem.getText().toString();
                    String valueBank= actValueItem.getText().toString();
                    String typeBank = actTypeItem.getText().toString();
                    Intent updateCard = new Intent(v.getContext(), EditCardBank.class);
                    updateCard.putExtra("nameupdate",nameBank).
                            putExtra("valueUpdate", valueBank).
                            putExtra("typeUpdate",typeBank).
                            putExtra("idUpdate",idCard);
                    v.getContext().startActivity(updateCard);

                });
                builder.setNeutralButton("Cancelar", (dialog, which) -> dialog.dismiss());
                builder.setNegativeButton("Deletar", (dialog, which) -> {
                    int idCard = Integer.parseInt(mIdView.getText().toString());
                    bkAccountDao.deletedbkCard(idCard,v.getContext());
                    Intent update = new Intent(v.getContext(), AddAccount.class);
                    v.getContext().startActivity(update);
                });
                builder.create().show();
            }
            else{
                ctx = mView.getContext();
                //bkAccount
                SharedPreferences prefers = ctx.getSharedPreferences("bkCardSelected",
                        Context.MODE_PRIVATE);
                @SuppressLint("CommitPrefEdits")
                SharedPreferences.Editor edit = prefers.edit();
                edit.putInt("visBk", 1);
                edit.apply();
                //User
                SharedPreferences prefs = ctx.getSharedPreferences("userInfo",
                        Context.MODE_PRIVATE);
                int idUser = prefs.getInt("idUser", 0);
                int idCard = Integer.parseInt(mIdView.getText().toString());
                bkAccountDao.updateBkAccountSelectd(idUser,idCard,ctx);
                int sit = prefs.getInt("Situ",0);
                if(sit == 0) {
                    v.getContext().startActivity(new Intent(v.getContext(), HomeActivity.class));
                }
                else if(sit == 1){
                    v.getContext().startActivity(new Intent(v.getContext(), AddActivity.class));
                }
                else{
                    v.getContext().startActivity(new Intent(v.getContext(), GraphicsActivity.class));
                }

            }
        }
    }
}