package be.industria.industria24u.industria24u;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.content.Intent;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

// Imports for FB sign-in
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.*;
import com.facebook.login.*;
import com.facebook.login.widget.*;

// Imports for Google sign-in
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import entity.Person;

/** Imports for Google sign-in
 *
 * To access the android debug keystore:
 * "C:\Program Files (x86)\JetBrains\IntelliJ IDEA 2016.2.4\jre\jre\b
 in\keytool.exe" -exportcert -list -v -alias androiddebugkey -keystore %HOMEPATH%
 /.android/debug.keystore
 * Which returns:
 * 75:E3:B8:1E:9C:05:56:AA:51:E5:1C:4C:B1:B7:8E:63:16:2D:F9:8B
 *
 * OR
 *
 * "C:\Program Files\Android\Android Studio\jre\b
 in\keytool.exe" -exportcert -list -v -alias androiddebugkey -keystore %HOMEPATH%
 /.android/debug.keystore
 * Which returns:
 * 75:E3:B8:1E:9C:05:56:AA:51:E5:1C:4C:B1:B7:8E:63:16:2D:F9:8B
 *
 * Web client ID for Google Sign-in: 901222791972-8h5h2i2jt08rlqoo4q0klalpq98kgd0o.apps.googleusercontent.com
 * Found on https://console.developers.google.com/apis/credentials?project=industria24u (Web client)
 *
 * TODO: Scopes as found on https://developers.google.com/android/reference/com/google/android/gms/auth/api/signin/GoogleSignInOptions.Builder#requestScopes(com.google.android.gms.common.api.Scope,%20com.google.android.gms.common.api.Scope...)
 */

@SuppressWarnings("UnusedParameters")
public class LoginListActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private SingletonApplication app;
    private Person unauthenticatedPerson = new Person();

    // Facebook login
    private Intent loginIntent;
    private Profile currentProfile;
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;
    private CallbackManager fbCallbackManager;

    // Google login
    private GoogleApiClient gac;
    private GoogleSignInAccount tmpAccount;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_list);
        app = (SingletonApplication) this.getApplication();
        loginIntent = new Intent(this.getApplicationContext(), RunnerDashboardActivity.class);

        // Facebook login
        fbCallbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(this.getApplication());
        LoginButton fbLoginButton = (LoginButton) this.findViewById(R.id.login_button);
        fbLoginButton.setReadPermissions("email"); // TODO: This doesn't actually do anything, right?
        fbLoginButton.registerCallback(fbCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) { checkLoginFB(); }
            @Override
            public void onCancel() { Toast.makeText(app.getApplicationContext(), "Why would you cancel the login?", Toast.LENGTH_SHORT).show(); }
            @Override
            public void onError(FacebookException exception) { Toast.makeText(app.getApplicationContext(), exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show(); }
        });
        ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                checkLoginFB();
            }
        };

        // Google login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gac = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        app.gac = gac;
        SignInButton gLoginButton = (SignInButton) findViewById(R.id.sign_in_button);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        // Try to silently login, giving FB priority before Google.
        accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            checkLoginFB();
        } else {
            // TODO: Implement Google silent login
        }
    }

    private void checkLoginFB() {
        accessToken = AccessToken.getCurrentAccessToken();
        currentProfile = Profile.getCurrentProfile();
        if (accessToken != null && currentProfile != null) {
            // 1. See if there is a person with the same FB-ID:
            app.getEntityMapper().getpMapper().getPersonByFacebook(currentProfile.getId());
            new CheckFacebookId().execute();
            // 2a. Login with that person if found in DB
            // 2b. Request the person's e-mail through the FB Graph API if not:
            // 3. See if there is a person with the same e-mail, and keep track of this e-mail in unauthenticatedPerson:
            // 3a. If there is, but with an empty FB-ID, login as that person (AND UPDATE THAT PERSON!)
            // 3b. If there is, but with another FB-ID, show an error message and ask them to send an e-mail.
            // 4. If none of this happens, show a registration form (and fill out as much personal info as possible).
            // 5. After the user fills in the registration, insert the Person object into the DB and login as that person.
        }
    }

    // 2a. If there is, login as that person.
    private void realLogin(Person person) {
        app.setUser(person);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fbCallbackManager.onActivityResult(requestCode, resultCode, data); // Facebook callback for app events

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                tmpAccount = result.getSignInAccount();
                if (tmpAccount != null) {
                    // 1. See if there is a person with the same Google-ID:
                    app.getEntityMapper().getpMapper().getPersonByGoogle(tmpAccount.getId());
                    new CheckGoogleId().execute();
                    // 2a. Login with that person if found in DB
                    // 2b. If not found, see if there is a person with the same e-mail, and keep track of this e-mail in unauthenticatedPerson:
                    // 3a. If there is, but with an empty Google-ID, login as that person (AND UPDATE THAT PERSON!)
                    // 3b. If there is, but with another Google-ID, show an error message and ask them to send an e-mail.
                    // 4. If none of this happens, show a registration form (and fill out as much personal info as possible).
                    // 5. After the user fills in the registration, insert the Person object into the DB and login as that person.
                }
            }
        }
    }

    private void registerNewPersonWithFacebook() {
        Intent intent = new Intent(this.getApplicationContext(), RegistrationActivity.class);

        intent.putExtra("email",unauthenticatedPerson.geteMail()); // Only field we would have to get AGAIN through Graph API
        intent.putExtra("firstName",currentProfile.getFirstName());
        intent.putExtra("lastName",currentProfile.getLastName());
        app.facebookID = currentProfile.getId();
        startActivity(intent);
        finish();
    }

    private void registerNewPersonWithGoogle() {
        Intent intent = new Intent(this.getApplicationContext(), RegistrationActivity.class);
        // TODO: Get google profile foto from tmpAccount.getPhotoURL
        intent.putExtra("email",unauthenticatedPerson.geteMail());
        intent.putExtra("firstName", tmpAccount.getGivenName());
        intent.putExtra("lastName",tmpAccount.getFamilyName());
        app.googleID = tmpAccount.getId();
        startActivity(intent);
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(gac);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    public void fakeLogin(View view) {
        // TODO: Remove this and the "confirm" button before releasing the app!
        Person person = new Person();
        realLogin(person);
    }

    // 1. See if there is a person with the same Google-ID:
    private class CheckGoogleId extends AsyncTask<Void, Void, Person> {
        protected Person doInBackground(Void... voids) {
            Person person = null;
            while (!app.getEntityMapper().dataReady()) {
                if (isCancelled()) break;
            }
            if (app.getEntityMapper().dataReady()) {
                person = app.entityMapper.person;
                app.getEntityMapper().dataGrabbed();
            }
            return person;
        }

        protected void onPostExecute(Person person) {
            if (person != null) {
                // 2a. Login with that person if found in DB
                realLogin(person);
            } else {
                // 2b. If not found, see if there is a person with the same e-mail, and keep track of this e-mail in unauthenticatedPerson:
                app.getEntityMapper().getpMapper().getPersonByEmail(tmpAccount.getEmail());
                new CheckEmailWithGoogle().execute();
                unauthenticatedPerson.seteMail(tmpAccount.getEmail());
            }
        }
    }

    // 1. See if there is a person with the same FB-ID:
    private class CheckFacebookId extends AsyncTask<Void, Void, Person> {
        protected Person doInBackground(Void... voids) {
            Person person = null;
            while (!app.getEntityMapper().dataReady()) {
                if (isCancelled()) break;
            }
            if (app.getEntityMapper().dataReady()) {
                person = app.entityMapper.person;
                app.getEntityMapper().dataGrabbed();
            }
            return person;
        }

        protected void onPostExecute(Person person) {
            if (person != null) {
                // 2a. Login with that person if found in DB
                realLogin(person);
            } else {
                // 2b. If there isn't, use the FB Graph request API to request the e-mail for this FB login
                // TODO: This is the FB GraphRequest for an e-mail address. Also use it for profile picture?
                new GraphRequest(AccessToken.getCurrentAccessToken(), "/me?fields=email", null, HttpMethod.GET, new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            // 3. See if there is a person with the same e-mail, and keep track of this e-mail in unauthenticatedPerson:
                            String email = response.getJSONObject().getString("email");
                            app.getEntityMapper().getpMapper().getPersonByEmail(email);
                            new CheckEmailWithFacebook().execute();
                            unauthenticatedPerson.seteMail(email);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).executeAsync();
            }
        }
    }

    private class CheckEmailWithFacebook extends AsyncTask<Void, Void, Person> {
        protected Person doInBackground(Void... voids) {
            Person person = null;
            while (!app.getEntityMapper().dataReady()) {
                if (isCancelled()) break;
            }
            if (app.getEntityMapper().dataReady()) {
                person = app.entityMapper.person;
                app.getEntityMapper().dataGrabbed();
            }
            return person;
        }

        protected void onPostExecute(Person person) {
            if (person != null) {
                // 3. If there is a person in the DB with the same e-mail:

                if (person.getFacebookId().equals("null")) {
                    // 3a. empty FB-ID: login as that person (AND UPDATE THAT PERSON!)
                    person.setFacebookId(currentProfile.getId());
                    app.getEntityMapper().getpMapper().updatePersonFacebook(person);
                    realLogin(person);
                } else {
                    Toast.makeText(app.getApplicationContext(), "Your identity could not be verified.", Toast.LENGTH_SHORT).show();
                    // 3b. another FB-ID: refuse login, show an error message and ask them to send an e-mail.
                }
            } else {
                // 4. If none of this happens, show a registration form (and fill out as much personal info as possible).
                registerNewPersonWithFacebook();
            }
        }
    }

    private class CheckEmailWithGoogle extends AsyncTask<Void, Void, Person> {
        protected Person doInBackground(Void... voids) {
            Person person = null;
            while (!app.getEntityMapper().dataReady()) {
                if (isCancelled()) break;
            }
            if (app.getEntityMapper().dataReady()) {
                person = app.entityMapper.person;
                app.getEntityMapper().dataGrabbed();
            }
            return person;
        }

        protected void onPostExecute(Person person) {
            if (person != null) {
                // 3. If there is a person in the DB with the same e-mail:
                if (person.getGoogleId().equals("null")) {
                    // 3a. empty Google-ID: login as that person (AND UPDATE THAT PERSON!)
                    person.setGoogleId(tmpAccount.getId());
                    app.getEntityMapper().getpMapper().updatePersonGoogle(person);
                    realLogin(person);
                } else {
                    Toast.makeText(app.getApplicationContext(), "Your identity could not be verified.", Toast.LENGTH_SHORT).show();
                    // 3b. another Google-ID: refuse login, show an error message and ask them to send an e-mail.
                }
            } else {
                // 4. If none of this happens, show a registration form (and fill out as much personal info as possible).
                registerNewPersonWithGoogle();
            }
        }
    }
}
