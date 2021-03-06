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

import java.sql.Date;

import be.industria.industria24u.industria24u.AdminTrainingsActivity;
import be.industria.industria24u.industria24u.R;
import be.industria.industria24u.industria24u.SingletonApplication;
import be.industria.industria24u.industria24u.navigation.AdminNavigationListener;
import entity.Training;

@SuppressWarnings({"deprecation", "UnusedParameters"})
public class AdminTrainingEditActivity extends AppCompatActivity {
    private SingletonApplication app;
    private DrawerLayout drawer;
    private EditText location;
    private EditText distance;
    private EditText day;
    private EditText month;
    private EditText year;
    private Training training = new Training();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_training_edit_container);
        app = (SingletonApplication) this.getApplication();
        setupViews();
        prefillContent();
        intent = new Intent(this, AdminTrainingsActivity.class);
    }

    private void setupViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_training_edit_toolbar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
        }

        drawer = (DrawerLayout) findViewById(R.id.admin_training_edit_container);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.admin_training_edit_nav);
        navigationView.setNavigationItemSelectedListener(new AdminTrainingEditActivity.NavListener(this.getApplicationContext()));

        location = (EditText) findViewById(R.id.location);
        distance = (EditText) findViewById(R.id.distance);
        day = (EditText) findViewById(R.id.day);
        month = (EditText) findViewById(R.id.month);
        year = (EditText) findViewById(R.id.year);
    }

    private void prefillContent() {
        training = app.tmpTraining;
        location.setText(training.getMeetingPoint());
        distance.setText(String.valueOf(training.getDistance()));
        Date beginDate = training.getBeginDateTime();
        year.setText(String.valueOf(beginDate.getYear()+1900));
        month.setText(String.valueOf(beginDate.getMonth()+1));
        day.setText(String.valueOf(beginDate.getDate()));
    }

    public void updateTraining(View v) {
        training.setMeetingPoint(location.getText().toString());
        training.setDistance(Double.parseDouble(distance.getText().toString()));
        Date beginDate = new Date(0);
        beginDate.setDate(Integer.parseInt(day.getText().toString()));
        beginDate.setMonth(Integer.parseInt(month.getText().toString())-1);
        beginDate.setYear(Integer.parseInt(year.getText().toString())-1900);
        Date endDate = new Date(0);
        endDate.setDate(Integer.parseInt(day.getText().toString()));
        endDate.setMonth(Integer.parseInt(month.getText().toString())-1);
        endDate.setYear(Integer.parseInt(year.getText().toString())-1900);
        training.setBeginDateTime(beginDate);
        training.setEndDateTime(endDate);
        app.getEntityMapper().dataSent();
        app.getEntityMapper().getTrainingMapper().updateTraining(training);
        app.tmpTraining = training;
        new UpdateTraining().execute();
    }

    private class UpdateTraining extends AsyncTask<Void, Void, Training> {
        protected Training doInBackground(Void... voids) {
            Training training = new Training();
            while (!app.getEntityMapper().dataPushed()) {
                if (isCancelled()) break;
            }
            if (app.getEntityMapper().dataPushed()) {
                app.getEntityMapper().dataSent();
            }
            return training;
        }

        protected void onPostExecute(Training training) {
            app.getEntityMapper().getTrainingMapper().updateTrainingLocation(app.tmpTraining);
            app.getEntityMapper().getTrainingMapper().updateTrainingDistance(app.tmpTraining);
            startActivity(intent);
            finish();
            Toast.makeText(app.getApplicationContext(), "Updated Training", Toast.LENGTH_SHORT).show();
        }
    }

    private class NavListener extends AdminNavigationListener {
        public NavListener(Context context) { super(context); }
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            super.onNavigationItemSelected(item);
            startActivity(super.resultIntent);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    }
}
