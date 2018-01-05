package in.testpress.testpress.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import junit.framework.Assert;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import in.testpress.core.TestpressSdk;
import in.testpress.core.TestpressSession;
import in.testpress.course.TestpressCourse;
import in.testpress.exam.TestpressExam;
import in.testpress.testpress.Injector;
import in.testpress.testpress.R;
import in.testpress.testpress.TestpressServiceProvider;
import in.testpress.testpress.authenticator.LoginActivity;
import in.testpress.testpress.authenticator.ResetPasswordActivity;
import in.testpress.testpress.core.Constants;
import in.testpress.testpress.core.TestpressService;
import in.testpress.testpress.util.CommonUtils;
import in.testpress.util.ViewUtils;

import static in.testpress.core.TestpressSdk.ACTION_PRESSED_HOME;
import static in.testpress.core.TestpressSdk.COURSE_CHAPTER_REQUEST_CODE;
import static in.testpress.core.TestpressSdk.COURSE_CONTENT_DETAIL_REQUEST_CODE;
import static in.testpress.core.TestpressSdk.COURSE_CONTENT_LIST_REQUEST_CODE;
import static in.testpress.course.TestpressCourse.CHAPTER_URL;
import static in.testpress.course.TestpressCourse.COURSE_ID;
import static in.testpress.course.TestpressCourse.PARENT_ID;
import static in.testpress.exam.network.TestpressExamApiClient.SUBJECT_ANALYTICS_PATH;
import static in.testpress.testpress.core.Constants.Http.CHAPTERS_PATH;
import static in.testpress.testpress.core.Constants.Http.URL_BASE;

public class SplashScreenActivity extends Activity {

    @Inject
    protected TestpressServiceProvider serviceProvider;

    @InjectView(R.id.splash_image) ImageView splashImage;

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPreferences =
                getSharedPreferences(Constants.SPLASH_SCREEN_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.getBoolean(Constants.SPLASH_SCREEN_TIME_OUT_PROPERTY_NAME, true)) {
            SPLASH_TIME_OUT = 2000;
            editor.putBoolean(Constants.SPLASH_SCREEN_TIME_OUT_PROPERTY_NAME, false);
            editor.apply();
        } else {
            SPLASH_TIME_OUT = 0;
        }
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
                            intent.putExtra("url", uri.toString());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra(Constants.IS_DEEP_LINK, true);
                            startActivity(intent);
                            finish();
                            break;
                        case "forums":
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
                        case "chapters":
                            authenticateUser(uri);
                            break;
                        case "courses":
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

    private void authenticateUser(final Uri uri) {
        final Activity activity = SplashScreenActivity.this;
        final List<String> pathSegments = uri.getPathSegments();
        CommonUtils.getAuth(activity, serviceProvider,
                new CommonUtils.CheckAuthCallBack() {
                    @Override
                    public void onSuccess(TestpressService testpressService) {
                        TestpressSession testpressSession = TestpressSdk.getTestpressSession(activity);
                        Assert.assertNotNull("TestpressSession must not be null.", testpressSession);
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
                                                testpressSession
                                        );
                                        return;
                                    }
                                }
                                // Show exams list
                                TestpressExam.show(activity, testpressSession);
                                finish();
                                break;

                            case "analytics":
                                TestpressExam.showAnalytics(activity, SUBJECT_ANALYTICS_PATH,
                                        testpressSession);
                                break;
                            case "chapters":
                                deepLinkToChapter(uri, testpressSession);
                                break;
                        }
                    }
                });
    }

    private void deepLinkToChapter(Uri uri, TestpressSession testpressSession) {
        final List<String> pathSegments = uri.getPathSegments();
        switch (pathSegments.size()) {
            case 1:
                // /chapters/
                gotoHome();
                break;
            case 2:
                // Contents list url - /chapters/chapter-slug/
                String chapterAPI = URL_BASE + CHAPTERS_PATH + uri.getLastPathSegment();
                TestpressCourse.showContents(this, chapterAPI, testpressSession);
                break;
            case 3:
                // Content detail url - /chapters/chapter-slug/{content-id}/
                TestpressCourse.showContentDetail(this, uri.getLastPathSegment(), testpressSession);
                break;
        }
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
        if (resultCode == RESULT_OK) {
            // Result code OK will come if attempted an exam & back press
            gotoHome();
        } else if (resultCode == RESULT_CANCELED) {
            if (data != null && data.getBooleanExtra(ACTION_PRESSED_HOME, false)) {
                TestpressSession testpressSession = TestpressSdk.getTestpressSession(this);
                Assert.assertNotNull("TestpressSession must not be null.", testpressSession);
                switch (requestCode) {
                    case COURSE_CONTENT_DETAIL_REQUEST_CODE:
                        String chapterUrl = data.getStringExtra(CHAPTER_URL);
                        if (chapterUrl != null) {
                            // Show contents list on home pressed from content detail
                            TestpressCourse.showContents(this, chapterUrl, testpressSession);
                            return;
                        }
                        break;
                    case COURSE_CONTENT_LIST_REQUEST_CODE:
                    case COURSE_CHAPTER_REQUEST_CODE:
                        String courseId = data.getStringExtra(COURSE_ID);
                        String parentId = data.getStringExtra(PARENT_ID);
                        if (courseId != null) {
                            // Show chapters list on home press from contents list or sub chapters list
                            TestpressCourse.showChapters(this, courseId, parentId, testpressSession);
                            return;
                        }
                        break;
                }
                // Go to home if user pressed home button & no other data passed in result intent
                gotoHome();
            } else {
                finish();
            }
        }
    }

}