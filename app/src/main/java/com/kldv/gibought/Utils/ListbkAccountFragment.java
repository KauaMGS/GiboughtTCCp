package com.kldv.gibought.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kldv.gibought.Class.CRUD.AddActivity;
import com.kldv.gibought.Class.GraphicsActivity;
import com.kldv.gibought.Class.HomeActivity;
import com.kldv.gibought.Class.SelectAccountActivity;
import com.kldv.gibought.Dao.UserDao;
import com.kldv.gibought.Dao.bkAccountDao;
import com.kldv.gibought.R;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A fragment representing a list of Items.
 */
@SuppressWarnings({"unused", "RedundantSuppression","SpellCheckingInspection","deprecation"})
public class ListbkAccountFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private String deleted;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private boolean mBankCount = false;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListbkAccountFragment() {
    }
    // TODO: Customize parameter initialization
    public static ListbkAccountFragment newInstance(int columnCount) {
        ListbkAccountFragment fragment = new ListbkAccountFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        int id = Objects.requireNonNull(args).getInt("idUser");
        Log.d("Your id is", String.valueOf(id));
        View view = inflater.inflate(R.layout.listbk_account_list, container, false);
        BottomNavigationView mbottomNavigationView = view.findViewById(R.id.bot_nav_add_view);
        AppCompatImageView btnAdd = view.findViewById(R.id.btnAdd);
        AppCompatEditText nullResult = view.findViewById(R.id.null_card);
        AppCompatImageView btnSearch = view.findViewById(R.id.app_search);
        mbottomNavigationView.setSelectedItemId(R.id.app_none);
        TextView addActText = view.findViewById(R.id.null_card_text);
        ImageView addActImg = view.findViewById(R.id.null_card_img);
        View deselect = view.findViewById(R.id.deselect);
        TextView actSelectTxt = view.findViewById(R.id.actSelectTxt);
        nullResult.setEnabled(false);
        @SuppressWarnings("ConstantConditions")
        SharedPreferences prefers = getContext().getSharedPreferences("bkCardSelected",
                Context.MODE_PRIVATE);
        String nameBk = prefers.getString("nameBank", getString(R.string.none));
        if(nameBk.equals(getString(R.string.none))) {
            deselect.setVisibility(View.GONE);
            actSelectTxt.setText(R.string.none);
        }

        else {
            deselect.setVisibility(View.VISIBLE);
            actSelectTxt.setText(nameBk);
        }

        deselect.setOnClickListener(view1 -> {
            bkAccountDao.updateBkAccountDeselect(id,getContext());
            deselect.setVisibility(View.GONE);
            actSelectTxt.setText(R.string.none);
            SharedPreferences.Editor editar = prefers.edit();
            editar.putString("nameBank", getString(R.string.none));
            editar.apply();
        });

        SearchView bxSearch = view.findViewById(R.id.boxsearch);
        AtomicInteger vcSearch = new AtomicInteger();
        btnAdd.setOnClickListener(v -> startActivity(new Intent(getContext(), SelectAccountActivity.class)));
        bxSearch.setIconified(false);
        bxSearch.onActionViewExpanded();
        bxSearch.clearFocus();

        //Retorna a caixa de Pesquisa
        btnSearch.setOnClickListener(v -> {
            if(vcSearch.get() == 0){
                addActImg.setVisibility(View.GONE);
                addActText.setVisibility(View.GONE);
                btnSearch.setImageResource(R.drawable.ic_search_cancel);
                btnSearch.setColorFilter(getResources().getColor(R.color.colorSubGray));
                mbottomNavigationView.setSelectedItemId(R.id.app_none);
                btnAdd.setVisibility(View.INVISIBLE);
                bxSearch.setVisibility(View.VISIBLE);
                vcSearch.set(1);
            }
            else {
                btnSearch.setImageResource(R.drawable.ic_search_nav);
                mbottomNavigationView.setSelectedItemId(R.id.app_none);
                nullResult.setVisibility(View.INVISIBLE);
                btnAdd.setVisibility(View.VISIBLE);
                //volta a Listagem ao cancelar a pesquisa
                Context context = view.getContext();
                RecyclerView recyclerView = view.findViewById(R.id.list);
                if (mColumnCount <= 1) {

                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                }
                else {
                    addActImg.setVisibility(View.GONE);
                    addActText.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                }
                recyclerView.setAdapter(new MybkAccountRecyclerViewAdapter(
                        bkAccountDao.searchbk(id,context)
                ));
                bxSearch.setVisibility(View.INVISIBLE);
                vcSearch.set(0);
            }
        });
        // Retorna a listagem do Card a partir do nome
        bxSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("ShowToast")
            @Override
            public boolean onQueryTextSubmit(String query) {
                Context context = view.getContext();
                RecyclerView recyclerView = view.findViewById(R.id.list);
                if (mColumnCount <= 1) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                }
                if (query.isEmpty()){

                    recyclerView.setAdapter(new MybkAccountRecyclerViewAdapter(null));
                    nullResult.setVisibility(View.VISIBLE);
                    Toast toast = Toast.makeText(getContext(),
                            query, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
                else {
                    recyclerView.setAdapter(new MybkAccountRecyclerViewAdapter(
                            bkAccountDao.searchbkn(query,id,context)
                    ));
                    nullResult.setVisibility(View.INVISIBLE);
                    boolean existDeluxe = bkAccountDao.searchEx(query,id, getContext());
                    if(!existDeluxe){
                        nullResult.setVisibility(View.VISIBLE);
                    }
                    else{
                        nullResult.setVisibility(View.INVISIBLE);
                    }
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mbottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            if (menuItem.getItemId() == R.id.app_back) {
                @SuppressWarnings("ConstantConditions")
                SharedPreferences prefs = getContext().getSharedPreferences("userInfo",
                        Context.MODE_PRIVATE);
                int sit = prefs.getInt("Situ",0);
                if(sit == 0) {
                    startActivity(new Intent(getContext(), HomeActivity.class));
                }
                else if(sit == 1){
                    startActivity(new Intent(getContext(), AddActivity.class));
                }
                else{
                    startActivity(new Intent(getContext(), GraphicsActivity.class));
                }
                return true;
            }
            return false;
        });
        // Seta o Adapter
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.list);
        if (mColumnCount <= 1) {
            mBankCount = bkAccountDao.searchData(id,getContext());
            if(!mBankCount){
                btnSearch.setEnabled(false);
                addActImg.setVisibility(View.VISIBLE);
                addActText.setVisibility(View.VISIBLE);
            }
            else {
                btnSearch.setEnabled(true);
                addActImg.setVisibility(View.GONE);
                addActText.setVisibility(View.GONE);
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            mBankCount = bkAccountDao.searchData(id,getContext());
            if(!mBankCount){
                addActImg.setVisibility(View.GONE);
                addActText.setVisibility(View.GONE);
            }
            else {
                addActImg.setVisibility(View.VISIBLE);
                addActText.setVisibility(View.VISIBLE);
            }
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        recyclerView.setAdapter(new MybkAccountRecyclerViewAdapter(
                bkAccountDao.searchbk(id,context)
        ));
        return view;
    }
}