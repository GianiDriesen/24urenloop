package be.industria.industria24u.industria24u;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import be.industria.industria24u.industria24u.navigation.RunnerNavigationListener;

@SuppressWarnings("UnusedParameters")
public class RunnerDashboardActivity extends AppCompatActivity {
    private SingletonApplication app;
    private DrawerLayout drawer;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runner_dashboard_container);
        app = (SingletonApplication) this.getApplication();
        setupViews();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("be.industria.ACTION_LOGOUT");
        registerReceiver(new BroadcastReceiver() { @Override public void onReceive(Context context, Intent intent) { finish(); unregisterReceiver(this); }}, intentFilter);
    }

    private void setupViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.runner_dashboard_toolbar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
        }

        drawer = (DrawerLayout) findViewById(R.id.runner_dashboard_container);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.runner_dashboard_nav);
        navigationView.setNavigationItemSelectedListener(new NavListener(this.getApplicationContext()));

    }

    public void clickMyTeam(View view) {
        intent = new Intent(RunnerDashboardActivity.this, RunnerTeamsActivity.class);
        startActivity(intent);
    }

    public void clickPrizes(View view) {
        intent = new Intent(RunnerDashboardActivity.this, RunnerPrizesActivity.class);
        startActivity(intent);
    }

    public void clickMyProfile(View view) {
        intent = new Intent(RunnerDashboardActivity.this, RunnerMyProfileStaticActivity.class);
        startActivity(intent);
    }

    public void clickLaps(View view) {
        intent = new Intent(RunnerDashboardActivity.this, RunnerLapHistoryActivity.class);
        startActivity(intent);
    }

    public void clickAchievements(View view) {
        intent = new Intent(RunnerDashboardActivity.this, RunnerAchievementsActivity.class);
        startActivity(intent);
    }

    public void clickTraining(View view) {
        intent = new Intent(RunnerDashboardActivity.this, RunnerTrainingsActivity.class);
        startActivity(intent);
    }

    public void goAdmin(View view) {
        if (!app.getUser().getType().toString().equals("RUNNER")) {
            intent = new Intent(RunnerDashboardActivity.this, AdminRunnerQueueActivity.class);
            startActivity(intent);
        }
    }

    private class NavListener extends RunnerNavigationListener {
        public NavListener(Context context) {
            super(context);
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            super.onNavigationItemSelected(item);
            //finish();
            startActivity(super.resultIntent);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    }
}
