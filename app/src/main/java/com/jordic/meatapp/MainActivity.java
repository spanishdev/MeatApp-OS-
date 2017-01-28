package com.jordic.meatapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.jordic.meatapp.fragments.HomeFragment;
import com.jordic.meatapp.fragments.MapFragment;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    //PREFERENCES
    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUAGE_SPANISH = "es";

    public final String LANGUAGE_PREF = "Language";
    SharedPreferences preferences;


    //TABS
    public static final int FRAGMENT_HOME = 1;
    public static final int FRAGMENT_MAPS = 2;

    int currentFragment = FRAGMENT_HOME;

    //VIEWS
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //We load the preferences and the language before we set the Content View
        preferences = getPreferences(Context.MODE_PRIVATE);
        initializeLanguage();

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Initialize the content
        setFragment(FRAGMENT_HOME);
        tabLayout.addOnTabSelectedListener(this);
    }


    /**
     * If Preferences has a Language stored, we load it. Otherwise, the application will load the default language
     */
    private void initializeLanguage()
    {
        if(preferences.contains(LANGUAGE_PREF))
        {
            setLanguage(preferences.getString(LANGUAGE_PREF,LANGUAGE_ENGLISH),true);
        }
    }

    /**
     * Set the language of the aplication
     * @param language Locale code as string (Ex: "es" is Spanish, "en" is English)
     * @param init Tells if it is called from application start or change from config.
     *             It justs is used in case to recreate the Activity if it is set from the menu
     */
    @SuppressWarnings("deprecation")
    private void setLanguage(String language, boolean init) {

        Resources res = getResources();

        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(language);
        res.updateConfiguration(conf, dm);
        preferences.edit().putString(LANGUAGE_PREF,language).commit();
        if(!init) recreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.languageMenuItem:

                //Default language is English, so if we click to the Option and current language is ENGLISH,
                //we switch to SPANISH, otherwise we set the language to ENGLISH
                if(preferences.getString(LANGUAGE_PREF,LANGUAGE_ENGLISH).equalsIgnoreCase(LANGUAGE_ENGLISH))
                {
                    setLanguage(LANGUAGE_SPANISH,false);
                }
                else setLanguage(LANGUAGE_ENGLISH,false);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * We set the Fragment and change it for navigation
     * @param fragment
     */
    public void setFragment(int fragment)
    {
        if(fragment<FRAGMENT_HOME && fragment>FRAGMENT_MAPS)
            throw new IllegalArgumentException("The fragment selected is out of range: "+fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if(fragment==FRAGMENT_HOME)
        {
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout,homeFragment);
            fragmentTransaction.commit();
        }
        else if(fragment==FRAGMENT_MAPS)
        {
            MapFragment mapFragment = new MapFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout,mapFragment);
            fragmentTransaction.commit();
        }
        currentFragment=fragment;

    }

    /**
     * This listens to the Tab change events. Only changes tab when the current is different of the selected.
     * We add 1 to the position due to the fragment int values
     * @param tab
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if(currentFragment!=tab.getPosition()+1) setFragment(tab.getPosition()+1);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
