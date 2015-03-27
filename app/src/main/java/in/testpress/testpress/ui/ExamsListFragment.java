package in.testpress.testpress.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;

import in.testpress.testpress.TestpressServiceProvider;
import in.testpress.testpress.Injector;
import in.testpress.testpress.R;
import in.testpress.testpress.authenticator.LogoutService;
import in.testpress.testpress.core.Exam;
import in.testpress.testpress.util.Ln;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class ExamsListFragment extends ItemListFragment<Exam> {

    String subclass;
    @Inject protected TestpressServiceProvider serviceProvider;
    @Inject protected LogoutService logoutService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_news);
    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter()
                .addHeader(activity.getLayoutInflater()
                        .inflate(R.layout.news_list_item_labels, null));
    }

    @Override
    protected LogoutService getLogoutService() {
        return logoutService;
    }

    @Override
    public void onDestroyView() {
        setListAdapter(null);

        super.onDestroyView();
    }

    @Override
    public Loader<List<Exam>> onCreateLoader(int id, final Bundle args) {
        final List<Exam> initialItems = items;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            subclass = bundle.getString("subclass");
        }
        return new ThrowableLoader<List<Exam>>(getActivity(), items) {

            @Override
            public List<Exam> loadData() throws Exception {
                try {
                    if (getActivity() != null && subclass != null) {
                        if (subclass.equals("available")) {
                            return serviceProvider.getService(getActivity()).getAvailableExams();
                        } else if (subclass.equals("upcoming")) {
                            return serviceProvider.getService(getActivity()).getUpcomingExams();
                        } else if (subclass.equals("history")) {
                            return serviceProvider.getService(getActivity()).getHistoryExams();
                        } else {
                            return serviceProvider.getService(getActivity()).getAvailableExams();
                        }
                    } else {
                        return serviceProvider.getService(getActivity()).getAvailableExams();
                    }

                } catch (OperationCanceledException e) {
                    Activity activity = getActivity();
                    if (activity != null)
                        activity.finish();
                    return initialItems;
                }
            }
        };
    }

    @Override
    protected SingleTypeAdapter<Exam> createAdapter(List<Exam> items) {
        int layout = R.layout.available_exams_list_item;
        if (subclass != null) {
            if (subclass.equals("available")) {
                layout = R.layout.available_exams_list_item;
            } else if (subclass.equals("upcoming")) {
                layout = R.layout.available_exams_list_item;
                //return serviceProvider.getService(getActivity()).getUpcomingExams();
            } else if (subclass.equals("history")) {
                layout = R.layout.available_exams_list_item;
            } else {
                layout = R.layout.available_exams_list_item;
            }
        }
        return new ExamsListAdapter(getActivity().getLayoutInflater(), items, layout);
    }

//    public void onListItemClick(ListView l, View v, int position, long id) {
//        Exam news = ((Exam) l.getItemAtPosition(position));
//
//        startActivity(new Intent(getActivity(), NewsActivity.class).putExtra(NEWS_ITEM, news));
//    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_news;
    }
}
