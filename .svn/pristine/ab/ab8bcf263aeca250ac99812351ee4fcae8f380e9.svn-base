package be.industria.industria24u.industria24u;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.login.LoginManager;

public class LogoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runner_dashboard_container);
        SingletonApplication app = (SingletonApplication) this.getApplication();
        app.setUser(null);

        LoginManager.getInstance().logOut(); // Logout FB
        if (app.gac != null) {
            if (app.gac.isConnected()) {
                app.gac.disconnect(); // Logout Google
            }
        }

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("be.industria.ACTION_LOGOUT");
        sendBroadcast(broadcastIntent); // Remove all activities left on the stack

        Intent intent = new Intent(this, LoginListActivity.class);
        startActivity(intent);
        finish();
    }
}
