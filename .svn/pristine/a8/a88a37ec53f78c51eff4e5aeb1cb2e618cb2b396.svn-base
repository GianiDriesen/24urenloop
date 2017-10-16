package be.industria.industria24u.industria24u.editors;

import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Toast;

import be.industria.industria24u.industria24u.AdminPrizesActivity;
import be.industria.industria24u.industria24u.R;
import be.industria.industria24u.industria24u.SingletonApplication;
import be.industria.industria24u.industria24u.navigation.AdminNavigationListener;
import entity.Prize;

@SuppressWarnings("UnusedParameters")
public class AdminPrizeCreateActivity extends AppCompatActivity {
    private SingletonApplication app;
    private DrawerLayout drawer;
    private EditText prizeName;
    private EditText prizeDescription;
    private EditText pointCost;
    private EditText year;
    private Prize prize = new Prize();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_prize_create_container);
        app = (SingletonApplication) this.getApplication();
        setupViews();
        intent = new Intent(this, AdminPrizesActivity.class);
    }

    private void setupViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_prize_create_toolbar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
        }

        drawer = (DrawerLayout) findViewById(R.id.admin_prize_create_container);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.admin_prize_create_nav);
        navigationView.setNavigationItemSelectedListener(new AdminPrizeCreateActivity.NavListener(this.getApplicationContext()));

        prizeName = (EditText) findViewById(R.id.prizeName);
        prizeDescription = (EditText) findViewById(R.id.prizeDescription);
        year = (EditText) findViewById(R.id.year);
        pointCost = (EditText) findViewById(R.id.pointCost);
    }

    public void insertPrize(View v) {
        prize.setName(prizeName.getText().toString());
        prize.setDescription(prizeDescription.getText().toString());
        prize.setYear(Integer.parseInt(year.getText().toString()));
        prize.setPointCost(Integer.parseInt(pointCost.getText().toString()));
        app.getEntityMapper().getPrizeMapper().insertPrize(prize);
        startActivity(intent);
        finish();
        Toast.makeText(app.getApplicationContext(), "Created "+prize.getName(), Toast.LENGTH_SHORT).show();
    }

    private class NavListener extends AdminNavigationListener {
        public NavListener(Context context) { super(context); }
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
