package com.kldv.gibought.Utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import com.kldv.gibought.Dao.goAccountDao;
import com.kldv.gibought.R;
import com.kldv.gibought.Utils.Animation.ProgressBarAnimation;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link goalAccount}.
 * TODO: Replace the implementation with code for your data type.
 */
@SuppressWarnings("SpellCheckingInspection")
public class MybkgoalAccountRecyclerViewAdapter extends RecyclerView.Adapter<MybkgoalAccountRecyclerViewAdapter.ViewHolder> {

    private final List<goalAccount> mValues;
    @SuppressLint("StaticFieldLeak")
    private static Context ctx;

    public MybkgoalAccountRecyclerViewAdapter(List<goalAccount> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listbkgo_account, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        goalAccount bkcurrent = mValues.get(position);
        holder.mItem = bkcurrent;
        holder.mIdView.setText(String.valueOf(mValues.get(position).getId()));
        int idImg;
        idImg = bkcurrent.getIdImage();
        BigDecimal vlbk = BigDecimal.valueOf(bkcurrent.getValueGoal());
        DecimalFormat n = new DecimalFormat(",##0.00");
        String valuebk = n.format(vlbk);
        holder.imgBkItem.setImageResource(R.drawable.bdothers);

        String name = mValues.get(position).getNameGoal()+":";
        holder.actNameItem.setText(name);

        holder.actValueItem.setText(valuebk);

        holder.progressBarG.setMax(100);
        holder.progressBarG.setScaleY(2f);
        double valueB = bkcurrent.getValGoal();
        int type = bkcurrent.getTypeGoal();
        if(type == 1){
            holder.txtName.setText("ALL");
        }else if(type == 4){
            holder.txtName.setText("Sem conta");
        }else{
            int idBk = bkcurrent.getIdBank();
            String nameBk = goAccountDao.searchNameGoalBank(idBk,holder.cont);
            holder.txtName.setText(nameBk);
        }
        double valueA = bkcurrent.getValueGoal();
        double count1 = valueB * 100;
        double count2 = count1 / valueA;
        double count3 = Math.ceil(count2);
        double sub = valueA - valueB;

        BigDecimal vlt = BigDecimal.valueOf(valueB);
        DecimalFormat nlt = new DecimalFormat(",##0.00");
        String valueAlt = nlt.format(vlt);

        BigDecimal vltB = BigDecimal.valueOf(sub);
        DecimalFormat nltB = new DecimalFormat(",##0.00");
        String valueBlt = nltB.format(vltB);

        int result = (int) count3;

        if (sub > 0) {
            String resSub = "Você tem: "+valueAlt+" Ainda Falta: "+valueBlt;
            holder.resTXT.setText(resSub);
        }
        else {
            String resSub = "Parabêns você concluiu sua meta";
            holder.resTXT.setText(resSub);
        }

        progressAnimation(holder,result);

        holder.setListener();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private void progressAnimation(final ViewHolder holder, int valuebka) {
        if(valuebka > 0 && valuebka < 100) {
            ProgressBarAnimation animation = new ProgressBarAnimation(ctx, holder.txtProg, holder.progressBarG, 0f, valuebka);
            animation.setDuration(1000);
            holder.progressBarG.setAnimation(animation);

        }
        else if(valuebka > 100){
            ProgressBarAnimation animation = new ProgressBarAnimation(ctx, holder.txtProg, holder.progressBarG, 0f, 100);
            animation.setDuration(1000);
            holder.progressBarG.setAnimation(animation);
        }
        else {
            ProgressBarAnimation animation = new ProgressBarAnimation(ctx, holder.txtProg, holder.progressBarG, 0f, 0);
            animation.setDuration(1000);
            holder.progressBarG.setAnimation(animation);
        }

    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mIdView;
        public AppCompatImageView imgBkItem,imgEdit;
        public AppCompatTextView actNameItem,txtProg,resTXT,txtName;
        public AppCompatTextView actValueItem;
        public AppCompatTextView actTypeItem;
        public goalAccount mItem;
        public ProgressBar progressBarG;
        public Context cont;

        public ViewHolder(View view) {
            super(view);

            mView = view;
            imgBkItem = view.findViewById(R.id.imageGoalItem);
            actNameItem = view.findViewById(R.id.goalNameItem);
            actValueItem = view.findViewById(R.id.goalValueItem);
            progressBarG = view.findViewById(R.id.progressGoal);
            txtProg = view.findViewById(R.id.progressTextGoal);
            imgEdit = view.findViewById(R.id.editgoCard);
            txtName = view.findViewById(R.id.actTxtValue);
            resTXT = view.findViewById(R.id.resultTXT);
            mIdView = view.findViewById(R.id.Id_go);
            cont = view.getContext();

            progressBarG.setProgressTintList(ColorStateList.valueOf(view.getResources().getColor(R.color.colorDarGreen)));
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
                builder.setTitle("Choose a option");
                builder.setMessage("Update or delete a account");
                builder.setPositiveButton("Update", (dialog, which) -> {
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
                builder.setNeutralButton("Cancel", (dialog, which) -> dialog.dismiss());
                builder.setNegativeButton("Delete", (dialog, which) -> {
                    int idCard = Integer.parseInt(mIdView.getText().toString());
                    goAccountDao.deletedgoalCard(idCard,v.getContext());
                    Intent update = new Intent(v.getContext(), AddAccount.class);
                    v.getContext().startActivity(update);
                });
                builder.create().show();
            }
            else{
                ctx = mView.getContext();
                //goalAccount
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
                goAccountDao.updategoalAccountSelectd(idUser,idCard,ctx);
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