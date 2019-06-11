package com.rappi.test.rappitestapp.beans;

import android.support.annotation.StringRes;

import com.rappi.test.rappitestapp.R;

/**
 * Enum with all movie categories.
 *
 * @todo as far as this test app, categories are not dynamic. Also, I haven't seen an API to get all categories.
 */
public enum MovieCategory {

    @StringRes
    TOP_RATED(R.string.category_top_rated),

    @StringRes
    POPULAR(R.string.category_popular),

    @StringRes
    UPCOMING(R.string.category_upcoming);

    /**
     * This is a resource id from strings.xml so we can internationalize.
     */
    private int resourceId;

    MovieCategory(int resource) {
        resourceId = resource;
    }

    public int getResourceId() {
        return resourceId;
    }

}
