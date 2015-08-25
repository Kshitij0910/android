

package in.testpress.testpress.ui;

import android.accounts.OperationCanceledException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;

import in.testpress.testpress.BuildConfig;
import in.testpress.testpress.R;
import in.testpress.testpress.TestpressServiceProvider;
import in.testpress.testpress.authenticator.LogoutService;
import in.testpress.testpress.core.TestpressService;
import in.testpress.testpress.events.NavItemSelectedEvent;
import in.testpress.testpress.models.Update;
import in.testpress.testpress.util.Ln;
import in.testpress.testpress.util.SafeAsyncTask;
import in.testpress.testpress.util.UIUtils;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;


/**
 * Initial activity for the application.
 *
 * If you need to remove the authentication from the application please see
 * {@link in.testpress.testpress.authenticator.ApiKeyProvider#getAuthKey(android.app.Activity)}
 */
public class MainActivity extends TestpressFragmentActivity {

    @Inject protected TestpressServiceProvider serviceProvider;
    @Inject protected TestpressService testpressService;
    @Inject protected LogoutService logoutService;

    private boolean userHasAuthenticated = false;
    private MaterialDialog materialDialog;
    private CarouselFragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        // View injection with Butterknife
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);

        if(isTablet()) {
            setContentView(R.layout.main_activity_tablet);
        } else {
            setContentView(R.layout.main_activity);
        }
        materialDialog = new MaterialDialog.Builder(this).build();
        //checkAuth();
        checkUpdate();

    }

    private boolean isTablet() {
        return UIUtils.isTablet(this);
    }

    private void initScreen() {
        if (userHasAuthenticated) {

            final Intent intent = getIntent();
            Bundle data = intent.getExtras();
            fragment = new CarouselFragment();
            fragment.setArguments(data);
            Ln.d("Foo");
            final FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitAllowingStateLoss();
        }

    }

    private void checkAuth() {
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                final TestpressService svc = serviceProvider.getService(MainActivity.this);
                if(materialDialog.isShowing())
                    materialDialog.dismiss();
                return svc != null;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof OperationCanceledException) {
                    // User cancelled the authentication process (back button, etc).
                    // Since auth could not take place, lets finish this activity.
                    finish();
                }
            }

            @Override
            protected void onSuccess(final Boolean hasAuthenticated) throws Exception {
                super.onSuccess(hasAuthenticated);
                userHasAuthenticated = true;
                initScreen();
            }
        }.execute();
    }

    private void checkUpdate() {
        new SafeAsyncTask<Update>() {
            @Override
            public Update call() throws Exception {
                return testpressService.checkUpdate("" + BuildConfig.VERSION_CODE);
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                checkAuth();
            }

            @Override
            protected void onSuccess(final Update update) throws Exception {
                if(update.getUpdateRequired()) {
                    materialDialog = new MaterialDialog.Builder(MainActivity.this)
                            .cancelable(true)
                            .content(update.getMessage())
                            .cancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialogInterface) {
                                    if (update.getForce()) {
                                        finish();
                                    } else {
                                        checkAuth();
                                    }
                                }
                            })
                            .neutralText("Update")
                            .buttonsGravity(GravityEnum.CENTER)
                            .neutralColorRes(R.color.primary)
                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onNeutral(MaterialDialog dialog) {
                                    dialog.cancel();
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "in.testpress.testpress")));
                                    //Should change "in.testpress.testpress" to "in.testpress.<App name>" for different apps
                                    finish();
                                }
                            })
                            .show();
                } else checkAuth();
            }
        }.execute();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                //menuDrawer.toggleMenu();
                return true;
            case R.id.logout:
                materialDialog = new MaterialDialog.Builder(this)
                        .title(R.string.label_logging_out)
                        .content(R.string.please_wait)
                        .widgetColorRes(R.color.primary)
                        .progress(true, 0)
                        .show();
                serviceProvider.invalidateAuthToken();
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                logoutService.logout(new Runnable() {
                    @Override
                    public void run() {
                        // Calling a refresh will force the service to look for a logged in user
                        // and when it finds none the user will be requested to log in again.
                        checkAuth();
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Subscribe
    public void onNavigationItemSelected(NavItemSelectedEvent event) {

        Ln.d("Selected: %1$s", event.getItemPosition());

        switch(event.getItemPosition()) {
            case 0:
                // Home
                // do nothing as we're already on the home screen.
                break;
        }
    }
}
