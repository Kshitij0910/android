

package in.testpress.testpress.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;

import in.testpress.testpress.R;

/**
 * Pager adapter
 */
public class TestpressPagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;
    Bundle bundle;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    public TestpressPagerAdapter(final Resources resources, final FragmentManager fragmentManager, Bundle bundle) {
        super(fragmentManager);
        this.resources = resources;
        this.bundle = bundle;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(final int position) {
        final Fragment result;
        String subclass;
        switch (position) {
            case 0:
                subclass = "available";
                break;
            case 1:
                subclass = "upcoming";
                break;
            case 2:
                subclass = "history";
                break;
            default:
                subclass = null;
                break;
        }
        result = new ExamsListFragment();
        Bundle bundle;
        if (this.bundle == null) {
            bundle = new Bundle();
        } else {
            bundle = new Bundle(this.bundle);
        }
        if (subclass != null) {
            bundle.putString("subclass", subclass);
            result.setArguments(bundle);
        }
        return result;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
            case 0:
                return resources.getString(R.string.page_available_exams);
            case 1:
                return resources.getString(R.string.page_upcoming_exams);
            case 2:
                return resources.getString(R.string.page_history_exams);
            default:
                return null;
        }
    }
}
