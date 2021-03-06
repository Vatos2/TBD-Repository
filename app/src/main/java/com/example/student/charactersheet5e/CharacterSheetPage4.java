package com.example.student.charactersheet5e;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import AppModels.CharSheet;

public class CharacterSheetPage4 extends Fragment {


    private CharSheet charSheet;
    private ImageButton adjustMoney;
    private ImageButton addEquipment;
    private View rootView;

    private TextView copper;
    private TextView silver;
    private TextView electrum;
    private TextView gold;
    private TextView platinum;

    private int[] currentCoins = {0,0,0,0,0};
    private RecyclerView weaponRecView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.character_sheet_page_4, container, false);

        MainSwipeActivity activity = (MainSwipeActivity) getActivity();
        charSheet = activity.getCharSheet();

        //Connect variable to layout
        adjustMoney = rootView.findViewById(R.id.edit_money_button);
        addEquipment = rootView.findViewById(R.id.Weapons_add_button);

        copper = rootView.findViewById(R.id.copper_text);
        silver = rootView.findViewById(R.id.silver_text);
        electrum = rootView.findViewById(R.id.electrum_text);
        gold = rootView.findViewById(R.id.gold_text);
        platinum = rootView.findViewById(R.id.platinum_text);


        //Set money Texts
        copper.setText(Integer.toString(charSheet.getCopper()));
        silver.setText(Integer.toString(charSheet.getSilver()));
        electrum.setText(Integer.toString(charSheet.getElectrum()));
        gold.setText(Integer.toString(charSheet.getGold()));
        platinum.setText(Integer.toString(charSheet.getPlatinum()));

        //Connect Weapon Recycler view variables
        weaponRecView = rootView.findViewById(R.id.Weapons_Recycler);
        weaponRecView.setHasFixedSize(true);
        RecyclerView.Adapter RecViewAdapter = new WeaponRecAdapter(charSheet.getWeaponsListForRec());
        RecyclerView.LayoutManager RecViewManager = new LinearLayoutManager(getActivity());
        weaponRecView.setLayoutManager(RecViewManager);
        weaponRecView.setAdapter(RecViewAdapter);


        //Add functionality to edit money button
        adjustMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCoins [0] = charSheet.getCopper();
                currentCoins [1] = charSheet.getSilver();
                currentCoins[2] = charSheet.getElectrum();
                currentCoins[3] = charSheet.getGold();
                currentCoins[4] = charSheet.getPlatinum();
                Intent i = new Intent(getActivity(), Pop_Edit_Money.class);
                i.putExtra("currentCoinAmounts", currentCoins);
                startActivityForResult(i, 5150);

            }
        });

        addEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Pop_Add_Equipment.class);
                startActivityForResult(i, 6160);
            }
        });


        return rootView;
    }


    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data)
    {
        if(resultCode == 1505) {
            super.onActivityResult(requestCode, resultCode, data);
            int[] result = data.getIntArrayExtra("result");
            copper.setText(Integer.toString(result[0]));
            silver.setText(Integer.toString(result[1]));
            electrum.setText(Integer.toString(result[2]));
            gold.setText(Integer.toString(result[3]));
            platinum.setText(Integer.toString(result[4]));

            charSheet.setCopper(result[0]);
            charSheet.setSilver(result[1]);
            charSheet.setElectrum(result[2]);
            charSheet.setGold(result[3]);
            charSheet.setPlatinum(result[4]);
        }
        if(resultCode == 1606)
        {
            super.onActivityResult(requestCode, resultCode, data);
            String[] result = data.getStringArrayExtra("result");
            WeaponsRecItem newWeapon = new WeaponsRecItem(result[0], result[1], result[2], result[3]);
            charSheet.addToWeaponsList(newWeapon);

            RecyclerView.Adapter RecViewAdapter = new WeaponRecAdapter(charSheet.getWeaponsListForRec());
            RecyclerView.LayoutManager RecViewManager = new LinearLayoutManager(getActivity());
            weaponRecView.setLayoutManager(RecViewManager);
            weaponRecView.setAdapter(RecViewAdapter);
        }
    }
}

