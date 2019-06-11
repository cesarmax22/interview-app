package com.rappi.test.rappitestapp.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rappi.test.rappitestapp.R;
import com.rappi.test.rappitestapp.ui.adapters.SectionsPagerAdapter;
import com.rappi.test.rappitestapp.ui.view.SearchViewModel;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private SearchViewModel searchViewModel;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);

        // set view model for search functionality to share between fragments
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        searchView = findViewById(R.id.search_view);
        searchView.setIconified(false);
        searchView.setQueryHint(getResources().getString(R.string.search_by_title));
        searchView.setOnQueryTextListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // request focus so keyboard doesn't bother
        findViewById(R.id.root).requestFocus();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                searchViewModel.setSearch(null);

                return true;
            }
        });

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search by title");
        searchView.setOnQueryTextListener(this);
        // TODO: check if we should set this
        searchView.setIconified(false);


        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onQueryTextSubmit(String s) {
        searchViewModel.setSearch(s);

        // request focus so keyboard doesn't bother
        searchView.clearFocus();

        return true;
    }

    // TODO: no need for this kind of detail for this app
    @Override
    public boolean onQueryTextChange(String s) {
        if (s == null || "".equals(s)) {
            searchViewModel.setSearch(null);
        }

        return true;
    }
}