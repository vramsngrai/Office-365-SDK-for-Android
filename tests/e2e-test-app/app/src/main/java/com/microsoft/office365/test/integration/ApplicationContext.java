package com.microsoft.office365.test.integration;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.common.io.ByteStreams;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.aad.adal.PromptBehavior;
import com.microsoft.directoryservices.odata.DirectoryClient;
import com.microsoft.discoveryservices.odata.DiscoveryClient;
import com.microsoft.graph.odata.GraphServiceClient;
import com.microsoft.live.LiveAuthClient;
import com.microsoft.office365.test.integration.android.Constants;
import com.microsoft.office365.test.integration.framework.TestCase;
import com.microsoft.office365.test.integration.framework.TestExecutionCallback;
import com.microsoft.office365.test.integration.framework.TestResult;
import com.microsoft.outlookservices.odata.OutlookClient;
import com.microsoft.services.odata.impl.DefaultDependencyResolver;
import com.microsoft.services.odata.impl.LiveAuthDependencyResolver;
import com.microsoft.services.odata.interfaces.DependencyResolver;
import com.microsoft.services.odata.interfaces.LogLevel;
import com.microsoft.sharepointservices.ListClient;
import com.microsoft.fileservices.odata.SharePointClient;
import com.microsoft.onenote.api.odata.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;


public class ApplicationContext {

    private static Activity mActivity;
    public static AuthenticationContext mAADAuthContext = null;
    public static LiveAuthClient mLiveAuthContext = null;

     final static String[] scopes = {
             // https://msdn.microsoft.com/en-us/library/hh243646.aspx
            "wl.signin",
            "wl.basic",
            "wl.offline_access",
            "wl.skydrive_update",
            "wl.contacts_create",
             "office.onenote_create"
    };

    public static void initialize(Activity activity) {
        mActivity = activity;
        try {
            mAADAuthContext = new AuthenticationContext(mActivity, Constants.AUTHORITY_URL, true);
        } catch (Throwable e) {
            Log.e("E2ETestApp", "Error creating AuthenticationContext: " + e.getMessage());
        }
    }


    public static String getExchangeServerUrl() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity).getString(Constants.PREFERENCE_EXCHANGE_RESOURCE_URL,
                "");
    }


    public static String getFileServerUrl() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity).getString(Constants.PREFERENCE_FILES_RESOURCE_URL,
                "");
    }


    public static String getSharepointServerUrl() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity).getString(Constants.PREFERENCE_SHAREPOINT_URL,
                "");
    }


    public static String getDiscoveryServerUrl() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity).getString(Constants.PREFERENCE_DISCOVERY_RESOURCE_URL,
                "");
    }


    public static String getDirectoryServerUrl() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity).getString(Constants.PREFERENCE_DIRECTORY_RESOURCE_URL,
                "");
    }

    public static String getGraphServerUrl() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity).getString(Constants.PREFERENCE_GRAPH_RESOURCE_URL,
                "");
    }

    public static String getTestListName() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity).getString(Constants.PREFERENCE_LIST_NAME, "");
    }

    public static String getTestDocumentListName() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity).getString(Constants.PREFERENCE_DOCUMENT_LIST_NAME, "");
    }


    public static String getSiteRelativeUrl() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity).getString(Constants.PREFERENCE_SITE_URL, "");
    }


    public static String getClientId() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity).getString(Constants.PREFERENCE_AAD_CLIENT_ID,
                "");
    }

    public static String getOneDriveConsumerEndpoint() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity).getString(Constants.PREFERENCE_ONEDRIVE_ENDPOINT_URL,
                "");
    }

    public static String getOneNoteEndpoint() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity).getString(Constants.PREFERENCE_ONENOTE_ENDPOINT_URL,
                "");
    }

    public static String getRedirectUrl() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity).getString(
                Constants.PREFERENCE_AAD_REDIRECT_URL, "");
    }

    public static String getExchangeEndpointUrl() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity).getString(
                Constants.PREFERENCE_EXCHANGE_ENDPOINT_URL, "");
    }


    public static String getFilesEndpointUrl() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity).getString(
                Constants.PREFERENCE_FILES_ENDPOINT_URL, "");
    }


    public static String getDiscoveryEndpointUrl() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity).getString(
                Constants.PREFERENCE_DISCOVERY_ENDPOINT_URL, "");
    }


    public static String getDirectoryEndpointUrl() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity).getString(
                Constants.PREFERENCE_DIRECTORY_ENDPOINT_URL, "");
    }

    public static String getGraphEndpointUrl() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity).getString(
                Constants.PREFERENCE_GRAPH_ENDPOINT_URL, "");
    }


    public static String getTestMail() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity).getString(
                Constants.PREFERENCE_TEST_MAIL, "");
    }


    public static AuthenticationContext getAuthenticationContext() {
        return mAADAuthContext;
    }


    public static ListenableFuture<Void> showMessage(final String message) {
        final SettableFuture<Void> result = SettableFuture.create();

        mActivity.runOnUiThread(new Runnable() {


            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

                builder.setTitle("Message");
                builder.setMessage(message);
                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {
                        result.set(null);
                    }
                });

                builder.create().show();
            }
        });

        return result;
    }


    public static void executeTest(final TestCase testCase, final TestExecutionCallback callback) {
        AsyncTask<Void, Void, TestResult> task = new AsyncTask<Void, Void, TestResult>() {


            protected TestResult doInBackground(Void... params) {
                return testCase.executeTest();
            }


            protected void onPostExecute(TestResult result) {
                callback.onTestComplete(testCase, result);
            }
        };

        task.execute();
    }


    public static void sleep(int seconds) throws Exception {
        Thread.sleep(seconds * 1000);
    }


    public static OutlookClient getMailCalendarContactClient() {
        return getTClientAAD(getExchangeServerUrl(), getExchangeEndpointUrl(), OutlookClient.class);
    }


    public static SharePointClient getFilesClient() {
        return getTClientAAD(getFileServerUrl(), getFilesEndpointUrl(), SharePointClient.class);
    }


    public static ListClient getSharePointListClient() {
        return getSharePointListClientAAD();
    }


    public static DiscoveryClient getDiscoveryClient() {
        return getTClientAAD(getDiscoveryServerUrl(), getDiscoveryEndpointUrl(), DiscoveryClient.class);
    }


    public static DirectoryClient getDirectoryClient() {
        return getTClientAAD(getDirectoryServerUrl(), getDirectoryEndpointUrl(), DirectoryClient.class);
    }


    public static InputStream getResource(int id) {
        return mActivity.getResources().openRawResource(id);
    }


    public static long getResourceSize(int id) {
        InputStream stream = mActivity.getResources().openRawResource(id);
        try {
            byte[] bytes = ByteStreams.toByteArray(stream);
            return bytes.length;
        } catch (IOException e) {
            return 0;
        }
    }

    private static <TClient> TClient getTClientAAD(String serverUrl, final String endpointUrl, final Class<TClient> clientClass) {
        final SettableFuture<TClient> future = SettableFuture.create();

        try {
            getAuthenticationContext().acquireToken(
                    mActivity, serverUrl,
                    getClientId(), getRedirectUrl(), PromptBehavior.Auto,
                    new AuthenticationCallback<AuthenticationResult>() {


                        public void onError(Exception exc) {
                            future.setException(exc);
                        }


                        public void onSuccess(AuthenticationResult result) {
                            TClient client = null;
                            try {
                                client = clientClass.getDeclaredConstructor(String.class, DependencyResolver.class)
                                        .newInstance(endpointUrl, getDependencyResolver(result.getAccessToken()));
                                future.set(client);
                            } catch (Throwable t) {
                                onError(new Exception(t));
                            }
                        }
                    });


        } catch (Throwable t) {
            future.setException(t);
        }
        try {
            return future.get();
        } catch (Throwable t) {
            Log.e(Constants.TAG, t.getMessage());
            return null;
        }
    }

    private static ListClient getSharePointListClientAAD() {
        final SettableFuture<ListClient> future = SettableFuture.create();
        try {
            getAuthenticationContext().acquireToken(
                    mActivity, getSharepointServerUrl(),
                    getClientId(), getRedirectUrl(), PromptBehavior.Auto,
                    new AuthenticationCallback<AuthenticationResult>() {


                        public void onError(Exception exc) {
                            future.setException(exc);
                        }


                        public void onSuccess(AuthenticationResult result) {
                            com.microsoft.sharepointservices.http.OAuthCredentials credentials = new com.microsoft.sharepointservices.http.OAuthCredentials(result.getAccessToken());
                            ListClient client = new ListClient(getSharepointServerUrl(), getSiteRelativeUrl(), credentials);
                            future.set(client);
                        }
                    });
        } catch (Throwable t) {
            future.setException(t);
        }

        try {
            return future.get();
        } catch (Throwable t) {
            Log.e(Constants.TAG, t.getMessage());
            return null;
        }
    }

    private static DependencyResolver getDependencyResolver(final String token) {

        DefaultDependencyResolver dependencyResolver = new DefaultDependencyResolver(token);

        dependencyResolver.getLogger().setEnabled(true);
        dependencyResolver.getLogger().setLogLevel(LogLevel.VERBOSE);
        return dependencyResolver;
    }

    private static LiveAuthDependencyResolver liveAuthDependencyResolver;

    private static LiveAuthDependencyResolver getLiveAuthDependencyResolver() {
        if (liveAuthDependencyResolver == null) {
            LiveAuthClient theAuthClient = new LiveAuthClient(mActivity.getApplicationContext(), getLiveSDKClientId(), Arrays.asList(scopes));
            liveAuthDependencyResolver = new LiveAuthDependencyResolver(theAuthClient);

            try {
                liveAuthDependencyResolver.interactiveInitialize(mActivity).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            liveAuthDependencyResolver.getLogger().setEnabled(true);
            liveAuthDependencyResolver.getLogger().setLogLevel(LogLevel.VERBOSE);
        }

        return liveAuthDependencyResolver;
    }

    public static File createTempFile(long sizeInKb) throws IOException {

        File tempFile = File.createTempFile("Office", "Test");
        tempFile.deleteOnExit();

        FileOutputStream out = new FileOutputStream(tempFile);

        for (int i = 0; i < sizeInKb; i++) {
            byte[] buffer = new byte[1024];
            out.write(buffer);
        }

        out.close();

        return tempFile;
    }

    public static OneNoteApiClient getOneNoteApiClient(){
        return getTClientLiveSDK(getOneNoteEndpoint(), com.microsoft.onenote.api.odata.OneNoteApiClient.class);
    }

    public static GraphServiceClient getGraphServiceClient(){
        return getTClientAAD(getGraphServerUrl(), getGraphEndpointUrl(), GraphServiceClient.class);
}

    private static <TClient> TClient getTClientLiveSDK(final String endpointUrl, final Class<TClient> clientClass) {

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getLiveAuthDependencyResolver();
            }
        });

        TClient client = null;
        try {
            client = clientClass.getDeclaredConstructor(String.class, DependencyResolver.class)
                                    .newInstance(endpointUrl, getLiveAuthDependencyResolver());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return client;
    }


    public static String getLiveSDKClientId() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity).getString(
                Constants.PREFERENCE_LIVESDK_CLIENT_ID, "");
    }

}
