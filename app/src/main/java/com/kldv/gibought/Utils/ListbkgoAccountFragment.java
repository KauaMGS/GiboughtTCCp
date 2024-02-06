package com.kldv.gibought.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.kldv.gibought.Dao.bkAccountDao;
import com.kldv.gibought.Dao.goAccountDao;
import com.kldv.gibought.R;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A fragment representing a list of Items.
 */
@SuppressWarnings({"unused", "RedundantSuppression","SpellCheckingInspection","deprecation"})
public class ListbkgoAccountFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private String deleted;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    int spinnerItemBank = 1;
    int idBankUser;
    int esp;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListbkgoAccountFragment() {
    }
    // TODO: Customize parameter initialization
    public static ListbkgoAccountFragment newInstance(int columnCount) {
        ListbkgoAccountFragment fragment = new ListbkgoAccountFragment();
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
        View viewList = inflater.inflate(R.layout.listbk_goal_list, container, false);
        BottomNavigationView mbottomNavigationView = viewList.findViewById(R.id.bot_nav_add_view);
        AppCompatImageView btnAdd = viewList.findViewById(R.id.btnAdd);
        AppCompatEditText nullResult = viewList.findViewById(R.id.null_go);
        AppCompatImageView btnSearch = viewList.findViewById(R.id.app_search);
        mbottomNavigationView.setSelectedItemId(R.id.app_none);
        TextView addgoalText = viewList.findViewById(R.id.null_go_text);
        ImageView addgoalImg = viewList.findViewById(R.id.null_go_img);
        View deselect = viewList.findViewById(R.id.goaldeselect);
        TextView goalSelectTxt = viewList.findViewById(R.id.goalSelectTxt);
        View bankSpd = viewList.findViewById(R.id.bankSpd);
        Spinner spinner = viewList.findViewById(R.id.spinner);
        Spinner spinnerBank = viewList.findViewById(R.id.spinnerBank);
        bankSpd.setVisibility(View.GONE);
        Context conte = viewList.getContext();

        SharedPreferences prefs = conte.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);
        int idCli = prefs.getInt("idUser", 0);
        SharedPreferences prefsBkSelected = conte.getSharedPreferences("bkCardSelected",
                Context.MODE_PRIVATE);
        String nameBkCardSelected = prefsBkSelected.getString("nameBank", "None");

        List lista = bkAccountDao.searchgobk(idCli,conte.getApplicationContext());
        ArrayAdapter array = new ArrayAdapter(conte, R.layout.spinner_item_account,lista);
        array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBank.setAdapter(array);
        spinnerBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int spinnerItemBank = spinnerBank.getSelectedItemPosition();
                SharedPreferences prefsUsBank = conte.getSharedPreferences("userBanks",
                        Context.MODE_PRIVATE);
                idBankUser = prefsUsBank.getInt("IdBankUser"+spinnerItemBank, 0);
                spinnerItemBank = 5;
                Context context = viewList.getContext();
                RecyclerView recyclerView = viewList.findViewById(R.id.listgo);
                if (mColumnCount <= 1) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                }
                recyclerView.setAdapter(new MybkgoalAccountRecyclerViewAdapter(
                        goAccountDao.searchgoal(id, context, spinnerItemBank,idBankUser)
                ));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        nullResult.setEnabled(false);
        if(nameBkCardSelected.equals("None")){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.type_goals1, R.layout.spinner_item_account);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }else {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.type_goals, R.layout.spinner_item_account);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }

        @SuppressWarnings("ConstantConditions")
        SharedPreferences prefers = getContext().getSharedPreferences("goalCardSelected",
                Context.MODE_PRIVATE);
        String nameBk = prefers.getString("nameGoal", getString(R.string.none));
        boolean exist = goAccountDao.searchselectgoal(idCli, getContext());
        if(!exist) {
            deselect.setVisibility(View.GONE);
            goalSelectTxt.setText(R.string.none);
        }

        else {
            deselect.setVisibility(View.VISIBLE);
            goalSelectTxt.setText(nameBk);
        }

        deselect.setOnClickListener(view1 -> {
            goAccountDao.updategoalAccountDeselect(id,getContext(),spinnerItemBank);
            deselect.setVisibility(View.GONE);
            goalSelectTxt.setText(R.string.none);
            SharedPreferences.Editor editar = prefers.edit();
            editar.putString("nameBank", getString(R.string.none));
            editar.apply();
        });

        SearchView bxSearch = viewList.findViewById(R.id.boxsearch);
        AtomicInteger vcSearch = new AtomicInteger();
        btnAdd.setOnClickListener(v -> startActivity(
                new Intent(getContext(), AddGoalActivity.class)));
        bxSearch.setIconified(false);
        bxSearch.onActionViewExpanded();
        bxSearch.clearFocus();


        //Retorna a caixa de Pesquisa
        btnSearch.setOnClickListener(v -> {
            if(vcSearch.get() == 0){
                addgoalImg.setVisibility(View.GONE);
                addgoalText.setVisibility(View.GONE);
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
                Context context = viewList.getContext();
                RecyclerView recyclerView = viewList.findViewById(R.id.listgo);
                if (mColumnCount <= 1) {

                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                }
                else {
                    addgoalImg.setVisibility(View.GONE);
                    addgoalText.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                }
                recyclerView.setAdapter(new MybkgoalAccountRecyclerViewAdapter(
                        goAccountDao.searchgoal(id,context,spinnerItemBank,idBankUser)
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
                Context context = viewList.getContext();
                RecyclerView recyclerView = viewList.findViewById(R.id.listgo);
                if (mColumnCount <= 1) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                }
                if (query.isEmpty()){

                    recyclerView.setAdapter(new MybkgoalAccountRecyclerViewAdapter(null));
                    nullResult.setVisibility(View.VISIBLE);
                    Toast toast = Toast.makeText(getContext(),
                            query, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
                else {
                    recyclerView.setAdapter(new MybkgoalAccountRecyclerViewAdapter(
                            goAccountDao.searchgoaln(query,id,context)
                    ));
                    nullResult.setVisibility(View.INVISIBLE);
                    boolean existDeluxe = goAccountDao.searchExGoal(query,id, context);
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
                    startActivity(new Intent(getContext(), HomeActivity.class));
                return true;
            }
            return false;
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int spdBank = spinner.getSelectedItemPosition();
                if(nameBkCardSelected.equals("None")){
                    switch (spdBank) {
                        case 0:
                            spinnerItemBank = 1;
                            bankSpd.setVisibility(View.GONE);
                            break;
                        case 1:
                            spinnerItemBank = 2;
                            bankSpd.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            spinnerItemBank = 4;
                            bankSpd.setVisibility(View.GONE);
                            break;
                    }
                }else {
                    switch (spdBank) {
                        case 0:
                            spinnerItemBank = 1;
                            bankSpd.setVisibility(View.GONE);
                            break;
                        case 1:
                            spinnerItemBank = 2;
                            bankSpd.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            spinnerItemBank = 3;
                            bankSpd.setVisibility(View.GONE);
                            break;
                        case 3:
                            spinnerItemBank = 4;
                            bankSpd.setVisibility(View.GONE);
                            break;
                    }
                }
                       Context context = viewList.getContext();
                       RecyclerView recyclerView = viewList.findViewById(R.id.listgo);
                       if (mColumnCount <= 1) {
                           recyclerView.setLayoutManager(new LinearLayoutManager(context));
                       } else {
                           recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                       }
                       recyclerView.setAdapter(new MybkgoalAccountRecyclerViewAdapter(
                               goAccountDao.searchgoal(id, context, spinnerItemBank,idBankUser)
                       ));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Seta o Adapter
        Context context = viewList.getContext();
        RecyclerView recyclerView = viewList.findViewById(R.id.listgo);
        boolean mBankCount;
        if (mColumnCount <= 1) {
            mBankCount = goAccountDao.searchData(id,getContext());
            if(!mBankCount){
                btnSearch.setEnabled(false);
                addgoalImg.setVisibility(View.VISIBLE);
                addgoalText.setVisibility(View.VISIBLE);
            }
            else {
                btnSearch.setEnabled(true);
                addgoalImg.setVisibility(View.GONE);
                addgoalText.setVisibility(View.GONE);
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            mBankCount = goAccountDao.searchData(id,getContext());
            if(!mBankCount){
                addgoalImg.setVisibility(View.GONE);
                addgoalText.setVisibility(View.GONE);
            }
            else {
                addgoalImg.setVisibility(View.VISIBLE);
                addgoalText.setVisibility(View.VISIBLE);
            }
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        recyclerView.setAdapter(new MybkgoalAccountRecyclerViewAdapter(
                goAccountDao.searchgoal(id,context,spinnerItemBank,idBankUser)
        ));
        return viewList;

    }

}