package be.industria.industria24u.industria24u.editors;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

import java.sql.Timestamp;

import be.industria.industria24u.industria24u.AdminLapHistoryActivity;
import be.industria.industria24u.industria24u.R;
import be.industria.industria24u.industria24u.SingletonApplication;
import be.industria.industria24u.industria24u.navigation.AdminNavigationListener;
import entity.Lap;

@SuppressWarnings({"deprecation", "UnusedParameters"})
public class AdminLapCreateActivity extends AppCompatActivity {
    private SingletonApplication app;
    private DrawerLayout drawer;
    private EditText day;
    private EditText month;
    private EditText year;
    private EditText endDay;
    private EditText endMonth;
    private EditText endYear;
    private EditText beginHours;
    private EditText beginMinutes;
    private EditText beginSeconds;
    private EditText endHours;
    private EditText endMinutes;
    private EditText endSeconds;
    private Lap lap = new Lap();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_lap_create_container);
        app = (SingletonApplication) this.getApplication();
        setupViews();
        intent = new Intent(this, AdminLapHistoryActivity.class);
    }

    private void setupViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_lap_create_toolbar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
        }

        drawer = (DrawerLayout) findViewById(R.id.admin_lap_create_container);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.admin_lap_create_nav);
        navigationView.setNavigationItemSelectedListener(new AdminLapCreateActivity.NavListener(this.getApplicationContext()));

        day = (EditText) findViewById(R.id.day);
        month = (EditText) findViewById(R.id.month);
        year = (EditText) findViewById(R.id.year);
        endDay = (EditText) findViewById(R.id.endDay);
        endMonth = (EditText) findViewById(R.id.endMonth);
        endYear = (EditText) findViewById(R.id.endYear);
        beginHours = (EditText) findViewById(R.id.beginHours);
        beginMinutes = (EditText) findViewById(R.id.beginMinutes);
        beginSeconds = (EditText) findViewById(R.id.beginSeconds);
        endHours = (EditText) findViewById(R.id.endHours);
        endMinutes = (EditText) findViewById(R.id.endMinutes);
        endSeconds = (EditText) findViewById(R.id.endSeconds);
    }

    public void insertLap(View v) {
        boolean insertIt = true;
        int yearInt=0;
        int monthInt=0;
        int dayInt=0;
        int endYearInt=0;
        int endMonthInt=0;
        int endDayInt=0;
        if (year.getText().toString().equals("") || month.getText().toString().equals("") || day.getText().toString().equals("")) {
            Toast.makeText(this, "Fill in the necessary fields please!", Toast.LENGTH_SHORT).show();
            insertIt = false;
        } else {
            yearInt = Integer.parseInt(year.getText().toString())-1900;
            monthInt = Integer.parseInt(month.getText().toString())-1;
            dayInt = Integer.parseInt(day.getText().toString());
            if (endYear.getText().toString().equals("") || endMonth.getText().toString().equals("") || endDay.getText().toString().equals("")) {
                endYearInt = yearInt;
                endMonthInt = monthInt;
                endDayInt = dayInt;
            } else {
                endYearInt = Integer.parseInt(endYear.getText().toString())-1900;
                endMonthInt = Integer.parseInt(endMonth.getText().toString())-1;
                endDayInt = Integer.parseInt(endDay.getText().toString());
            }
        }

        int beginHoursInt=0;
        int beginMinutesInt=0;
        int beginSecondsInt=0;
        if (beginHours.getText().toString().equals("") || beginMinutes.getText().toString().equals("") || beginSeconds.getText().toString().equals("")) {
            Toast.makeText(this, "Fill in the necessary fields please!", Toast.LENGTH_SHORT).show();
            insertIt = false;
        } else {
            beginHoursInt = Integer.parseInt(beginHours.getText().toString());
            beginMinutesInt = Integer.parseInt(beginMinutes.getText().toString());
            beginSecondsInt = Integer.parseInt(beginSeconds.getText().toString());
        }

        int endHoursInt=0;
        int endMinutesInt=0;
        int endSecondsInt=0;
        if (endHours.getText().toString().equals("") || endMinutes.getText().toString().equals("") || endSeconds.getText().toString().equals("")) {
            Toast.makeText(this, "Fill in the necessary fields please!", Toast.LENGTH_SHORT).show();
            insertIt = false;
        } else {
            endHoursInt = Integer.parseInt(endHours.getText().toString());
            endMinutesInt = Integer.parseInt(endMinutes.getText().toString());
            endSecondsInt = Integer.parseInt(endSeconds.getText().toString());
        }

        if (insertIt) {
            Timestamp ts1 = new Timestamp(yearInt, monthInt, dayInt, beginHoursInt, beginMinutesInt, beginSecondsInt, 0);
            Timestamp ts2 = new Timestamp(endYearInt, endMonthInt, endDayInt, endHoursInt, endMinutesInt, endSecondsInt, 0);
            lap.setBeginDateTime(ts1);
            lap.setEndDateTime(ts2);
            lap.setPersonId(0);
            app.getEntityMapper().dataSent();
            app.getEntityMapper().getlMapper().insertLap(lap);
            new InsertLap().execute();
        }
    }

    private class InsertLap extends AsyncTask<Void, Void, Lap> {
        protected Lap doInBackground(Void... voids) {
            Lap lap = new Lap();
            while (!app.getEntityMapper().dataPushed()) {
                if (isCancelled()) break;
            }
            if (app.getEntityMapper().dataPushed()) {
                app.getEntityMapper().dataSent();
            }
            return lap;
        }

        protected void onPostExecute(Lap lap) {
            startActivity(intent);
            finish();
            Toast.makeText(app.getApplicationContext(), "Created new Lap", Toast.LENGTH_SHORT).show();
        }
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
