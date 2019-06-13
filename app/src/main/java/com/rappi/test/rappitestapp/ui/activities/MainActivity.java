package com.rappi.test.rappitestapp.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;

import com.rappi.test.rappitestapp.R;
import com.rappi.test.rappitestapp.ui.adapters.SectionsPagerAdapter;
import com.rappi.test.rappitestapp.model.SearchViewModel;

/**
 * Main entry activity for this application.
 */
public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    /**
     * This view-model class is used to update child fragments about search query string.
     */
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

        // set view model for search functionality to share between fragments
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
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

    @Override
    public boolean onQueryTextSubmit(String s) {
        searchViewModel.setSearch(s);

        // request focus so keyboard doesn't bother
        searchView.clearFocus();

        return true;
    }

    // for this app, we only perform a search when submit button is clicked.
    // for a more detailed app we can have a more complex logic
    @Override
    public boolean onQueryTextChange(String s) {
        if (s == null || "".equals(s)) {
            searchViewModel.setSearch(null);
        }

        return true;
    }
}