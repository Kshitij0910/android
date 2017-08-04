package in.testpress.testpress.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import in.testpress.core.TestpressSdk;
import in.testpress.exam.TestpressExam;
import in.testpress.testpress.Injector;
import in.testpress.testpress.R;
import in.testpress.testpress.TestpressServiceProvider;
import in.testpress.testpress.authenticator.LoginActivity;
import in.testpress.testpress.authenticator.ResetPasswordActivity;
import in.testpress.testpress.core.Constants;
import in.testpress.testpress.core.TestpressService;
import in.testpress.testpress.util.CommonUtils;

import static in.testpress.core.TestpressSdk.ACTION_PRESSED_HOME;
import static in.testpress.exam.network.TestpressExamApiClient.SUBJECT_ANALYTICS_PATH;
import static in.testpress.exam.ui.CarouselFragment.TEST_TAKEN_REQUEST_CODE;

public class SplashScreenActivity extends Activity {

    @Inject
    protected TestpressServiceProvider serviceProvider;

    @InjectView(R.id.splash_image) ImageView splashImage;

    // Splash screen timer
    private static final int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Injector.inject(this);
        ButterKnife.inject(this);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Uri uri = getIntent().getData();
                if (uri != null && uri.getPathSegments().size() > 0) {
                    List<String> pathSegments = uri.getPathSegments();
                    switch (pathSegments.get(0)) {
                        case "p":
                            Intent intent = new Intent(SplashScreenActivity.this, PostActivity.class);
                            intent.putExtra("shortWebUrl", uri.toString());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra(Constants.IS_DEEP_LINK, true);
                            startActivity(intent);
                            finish();
                            break;
                        case "posts":
                            Intent postsIntent =
                                    new Intent(SplashScreenActivity.this, PostsListActivity.class);
                            postsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            postsIntent.putExtra(Constants.IS_DEEP_LINK, true);
                            startActivity(postsIntent);
                            finish();
                            break;
                        case "exams":
                            authenticateUser(uri);
                            break;
                        case "user":
                        case "profile":
                            gotoActivity(ProfileDetailsActivity.class, true);
                            break;
                        case "password":
                            gotoActivity(ResetPasswordActivity.class, false);
                            break;
                        case "analytics":
                            authenticateUser(uri);
                            break;
                        case "documents":
                            gotoActivity(DocumentsListActivity.class, true);
                            break;
                        case "login":
                            gotoActivity(MainActivity.class, true);
                            break;
                        case "activate":
                            gotoAccountActivate(uri.getPath());
                            break;
                        case "courses":
                        case "chapters":
                        case "orders":
                        case "learn":
                        case "leaderboard":
                        case "dashboard":
                        case "contact":
                        case "privacy":
                        case "refund":
                        case "update":
                        case "about":
                            gotoHome();
                            break;
                        case "store":
                        case "market":
                        case "products":
                            deepLinkToProduct(uri);
                            break;
                        default:
                            gotoProductDetails(pathSegments.get(0));
                            break;
                    }
                } else {
                    // This method will be executed once the timer is over
                    // Start app main activity
                    gotoHome();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    private void gotoHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @SuppressWarnings("ConstantConditions")
    private void authenticateUser(final Uri uri) {
        final Activity activity = SplashScreenActivity.this;
        final List<String> pathSegments = uri.getPathSegments();
        CommonUtils.getAuth(activity, serviceProvider,
                new CommonUtils.CheckAuthCallBack() {
                    @Override
                    public void onSuccess(TestpressService testpressService) {
                        switch (pathSegments.get(0)) {
                            case "exams":
                                if (pathSegments.size() == 2) {
                                    if (!pathSegments.get(1).equals("available") ||
                                            !pathSegments.get(1).equals("upcoming") ||
                                            !pathSegments.get(1).equals("history")) {

                                        // If exam slug is present, directly goto the start exam screen
                                        TestpressExam.showExamAttemptedState(
                                                activity,
                                                pathSegments.get(1),
                                                TestpressSdk.getTestpressSession(activity)
                                        );
                                        return;
                                    }
                                }
                                // Show exams list
                                TestpressExam.show(activity,
                                        TestpressSdk.getTestpressSession(activity));
                                finish();
                                break;

                            case "analytics":
                                TestpressExam.showAnalytics(activity, SUBJECT_ANALYTICS_PATH,
                                        TestpressSdk.getTestpressSession(activity));
                                break;
                        }
                    }
                });
    }

    private void deepLinkToProduct(Uri uri) {
        final List<String> pathSegments = uri.getPathSegments();
        if (pathSegments.size() == 2) {
            gotoProductDetails(uri.getLastPathSegment());
        } else {
            Intent intent = new Intent(this, ProductsListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(Constants.IS_DEEP_LINK, true);
            startActivity(intent);
            finish();
        }
    }

    private void gotoProductDetails(String productSlug) {
        Intent productIntent =
                new Intent(SplashScreenActivity.this, ProductDetailsActivity.class);
        productIntent.putExtra(ProductDetailsActivity.PRODUCT_SLUG, productSlug);
        productIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        productIntent.putExtra(Constants.IS_DEEP_LINK, true);
        startActivity(productIntent);
        finish();
    }

    private void gotoAccountActivate(String activateUrlFrag) {
        Intent intent = new Intent(SplashScreenActivity.this, AccountActivateActivity.class);
        intent.putExtra(AccountActivateActivity.ACTIVATE_URL_FRAG, activateUrlFrag);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void gotoActivity(Class activityClass, boolean requireAuthentication) {
        if (requireAuthentication && !CommonUtils.isUserAuthenticated(SplashScreenActivity.this)) {
            activityClass = LoginActivity.class;
        }
        Intent intent = new Intent(SplashScreenActivity.this, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Constants.IS_DEEP_LINK, true);
        startActivity(intent);
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        splashImage.setImageResource(R.drawable.splash_screen);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEST_TAKEN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                gotoHome();
            } else if (resultCode == RESULT_CANCELED) {
                if (data != null && data.getBooleanExtra(ACTION_PRESSED_HOME, false)) {
                    gotoHome();
                } else {
                    finish();
                }
            }
        }
    }

}