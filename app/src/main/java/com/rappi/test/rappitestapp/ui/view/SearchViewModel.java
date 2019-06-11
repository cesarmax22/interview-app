package com.rappi.test.rappitestapp.ui.view;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Observable class to share query search data between activity and fragments.
 */
public class SearchViewModel extends ViewModel {

    private MutableLiveData<String> search;
    private String searchQuery;

    public MutableLiveData<String> getSearch() {
        if (search == null)
            search = new MutableLiveData<>();

        return search;
    }

    public void setSearch(String query) {
        if (search == null)
            search = new MutableLiveData<>();

        searchQuery = query;

        // TODO: set or post?
        search.setValue(query);
    }

    public String getSearchQuery() {
        return searchQuery;
    }
}
