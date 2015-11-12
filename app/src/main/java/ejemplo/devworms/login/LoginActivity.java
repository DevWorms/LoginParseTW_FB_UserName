package ejemplo.devworms.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.appevents.AppEventsLogger;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.devworms.editorial.mango",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        ///***************Parse***************************************************


        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "THcXZNHO4KUAR9StiAp9uSZ6ogpVvMu4P7EGd0B2", "oYALR4CrZhDOYlrOk7zCLszZXixJEXsDtOV4e0zt");


        ParseFacebookUtils.initialize(this.getApplicationContext());

        ParseTwitterUtils.initialize("af09lpCbgHZv0mDHXjJGT1uq4", "Rmj3opgLofx36g41cI3JakAxGHMSwWIruKwN508RwvrMtQXQdr");
        ///***************Parse***************************************************


        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // show the signup or login screen


        }

    }

    public void loguearConMail(View view)
    {
        String userName=( (TextView)findViewById(R.id.usuario) ).getText().toString();
        String pass=( (TextView)findViewById(R.id.password) ).getText().toString();

        ParseUser.logInInBackground(userName, pass, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Hooray! The user is logged in.
                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                }
            }
        });
    }

    public void registrarUsuario(View view)
    {
        String userName=( (TextView)findViewById(R.id.usuario) ).getText().toString();
        String mail=( (TextView)findViewById(R.id.correo) ).getText().toString();
        String pass=( (TextView)findViewById(R.id.password) ).getText().toString();

        ParseUser user = new ParseUser();
        user.setUsername(userName);
        user.setPassword(pass);
        user.setEmail(mail);

        // other fields can be set just like with ParseObject
        //user.put("phone", "650-253-0000");

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Ocurrio error al registrar", Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    public void loguearConFacebook(View view)
    {
        List<String> permissions = Arrays.asList("user_birthday", "user_location", "user_friends", "email", "public_profile");

        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions , new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                if (user == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d("MyApp", "User signed up and logged in through Facebook!");

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                    Log.d("MyApp", "User logged in through Facebook!");
                }
            }
        });

    }

    private void linkearConFb(final ParseUser user)
    {
        List<String> permissions = Arrays.asList("user_birthday", "user_location", "user_friends", "email", "public_profile");

        if (!ParseFacebookUtils.isLinked(user)) {
            ParseFacebookUtils.linkWithReadPermissionsInBackground(user, this, permissions, new SaveCallback() {
                @Override
                public void done(ParseException ex) {
                    if (ParseFacebookUtils.isLinked(user)) {
                        Log.d("MyApp", "Woohoo, user logged in with Facebook!");
                    }
                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    public void loguearConTwitter(View view)
    {

        ParseTwitterUtils.logIn(this, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                if (user == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Twitter login.");
                } else if (user.isNew()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Log.d("MyApp", "User signed up and logged in through Twitter!");
                } else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Log.d("MyApp", "User logged in through Twitter!");
                }
            }
        });

    }
}

