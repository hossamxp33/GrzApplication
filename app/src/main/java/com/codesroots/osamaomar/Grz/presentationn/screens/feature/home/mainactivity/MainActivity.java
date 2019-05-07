package com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.mainactivity;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.codesroots.osamaomar.Grz.R;
import com.codesroots.osamaomar.Grz.models.helper.PreferenceHelper;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.cartfragment.CartFragment;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.mainfragment.MainFragment;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.morefragment.MenuFragment;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.myorders.MyOrdersFragment;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.offerfragment.OffersFragment;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.productfragment.ProductsFragment;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements
        BottomNavigationView.OnNavigationItemSelectedListener {

    RecyclerView alldepartsinNavigation;
    BottomNavigationView bottomNavigationView;
    MainActivityViewModel mainActivityViewModel;
    public ImageView logo,search;
    public TextView head_title;
    private EditText searchName;
    private ImageView cartbtn;
    private  ArrayList<String> arrayList = new ArrayList<>();
    private  PreferenceHelper preferenceHelper;
    private  Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferenceHelper  = new PreferenceHelper(this);
        initialize();

        search.setOnClickListener(v -> {
            // shareTextUrl();
            performSearch();
        });

        searchName.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch();
                return true;
            }
            return false;
        });

        cartbtn.setOnClickListener(v -> getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new CartFragment()).addToBackStack(null).commit());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new MainFragment()).commit();
    }

    private void initialize() {
         toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        search =  findViewById(R.id.search);
        searchName =  findViewById(R.id.search_input);
        logo = findViewById(R.id.logo);
        head_title = findViewById(R.id.head_title);
        cartbtn = findViewById(R.id.cartbtn);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        mainActivityViewModel = ViewModelProviders.of(this,getViewModelFactory()).get(MainActivityViewModel.class);
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE},112);
    }

    private void performSearch() {
        if (!searchName.getText().toString().matches(""))
        {
            Fragment fragment = new ProductsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("name",searchName.getText().toString());
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment).addToBackStack(null).commit();
        }
        else
            searchName.setError(getText(R.string.nosearchname));
    }

    private MainActivityModelFactory getViewModelFactory() {
        return new MainActivityModelFactory(getApplication());
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.navigation_more:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new MenuFragment()).addToBackStack(null).commit();
                break;

            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MainFragment()).addToBackStack(null).commit();
                break;

            case R.id.navigation_order:
                if (PreferenceHelper.getUserId()>0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MyOrdersFragment()).addToBackStack(null).commit();
                }else
                    Toast.makeText(MainActivity.this,getText(R.string.loginfirst),Toast.LENGTH_SHORT).show();
                break;

            case R.id.navigation_offers:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new OffersFragment()).addToBackStack(null).commit();
                break;
        }
        return true;
    }


}
