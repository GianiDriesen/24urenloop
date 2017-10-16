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
import java.util.LinkedList;
import java.util.List;

import be.industria.industria24u.industria24u.AdminLapHistoryActivity;
import be.industria.industria24u.industria24u.R;
import be.industria.industria24u.industria24u.SingletonApplication;
import be.industria.industria24u.industria24u.navigation.AdminNavigationListener;
import entity.Lap;
import entity.Person;

@SuppressWarnings({"deprecation", "UnusedParameters"})
public class AdminLapEditActivity extends AppCompatActivity {
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
    private EditText runnerFirstName;
    private EditText runnerLastName;
    private EditText runnerEmail;
    private Lap lap;
    private Intent intent;
    private boolean updateIt = true;

    private int yearInt=0;
    private int monthInt=0;
    private int dayInt=0;
    private int endYearInt=0;
    private int endMonthInt=0;
    private int endDayInt=0;
    private int beginHoursInt=0;
    private int beginMinutesInt=0;
    private int beginSecondsInt=0;
    private int endHoursInt=0;
    private int endMinutesInt=0;
    private int endSecondsInt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_lap_edit_container);
        app = (SingletonApplication) this.getApplication();
        setupViews();
        prefillContent();
        intent = new Intent(this, AdminLapHistoryActivity.class);
    }

    private void setupViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_lap_edit_toolbar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
        }

        drawer = (DrawerLayout) findViewById(R.id.admin_lap_edit_container);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.admin_lap_edit_nav);
        navigationView.setNavigationItemSelectedListener(new AdminLapEditActivity.NavListener(this.getApplicationContext()));

        lap = app.tmpLap;
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
        runnerFirstName = (EditText) findViewById(R.id.runnerFirstName);
        runnerLastName = (EditText) findViewById(R.id.runnerLastName);
        runnerEmail = (EditText) findViewById(R.id.runnerEMail);
    }

    private void prefillContent() {
        Timestamp ts1 = lap.getBeginDateTime();
        Timestamp ts2 = lap.getEndDateTime();
        day.setText(String.valueOf(ts1.getDate()));
        month.setText(String.valueOf((ts1.getMonth()+1)));
        year.setText(String.valueOf((ts1.getYear()+1900)));
        beginHours.setText(String.valueOf(ts1.getHours()));
        beginMinutes.setText(String.valueOf(ts1.getMinutes()));
        beginSeconds.setText(String.valueOf(ts1.getSeconds()));
        endDay.setText(String.valueOf(ts2.getDate()));
        endMonth.setText(String.valueOf((ts2.getMonth()+1)));
        endYear.setText(String.valueOf((ts2.getYear()+1900)));
        endHours.setText(String.valueOf(ts2.getHours()));
        endMinutes.setText(String.valueOf(ts2.getMinutes()));
        endSeconds.setText(String.valueOf(ts2.getSeconds()));
        if (lap.getPersonId() != 0) {
            app.getEntityMapper().getpMapper().getPerson(lap.getPersonId());
            new GetRunnerToPrefill().execute();
        }
    }

    public void updateLap(View v) {
        updateIt = true;
        if (year.getText().toString().equals("") || month.getText().toString().equals("") || day.getText().toString().equals("")) {
            Toast.makeText(this, "Fill in the necessary fields please!", Toast.LENGTH_SHORT).show();
            updateIt = false;
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

        if (beginHours.getText().toString().equals("") || beginMinutes.getText().toString().equals("") || beginSeconds.getText().toString().equals("")) {
            Toast.makeText(this, "Fill in the necessary fields please!", Toast.LENGTH_SHORT).show();
            updateIt = false;
        } else {
            beginHoursInt = Integer.parseInt(beginHours.getText().toString());
            beginMinutesInt = Integer.parseInt(beginMinutes.getText().toString());
            beginSecondsInt = Integer.parseInt(beginSeconds.getText().toString());
        }

        if (endHours.getText().toString().equals("") || endMinutes.getText().toString().equals("") || endSeconds.getText().toString().equals("")) {
            Toast.makeText(this, "Fill in the necessary fields please!", Toast.LENGTH_SHORT).show();
            updateIt = false;
        } else {
            endHoursInt = Integer.parseInt(endHours.getText().toString());
            endMinutesInt = Integer.parseInt(endMinutes.getText().toString());
            endSecondsInt = Integer.parseInt(endSeconds.getText().toString());
        }

        if (app.tmpPerson != null && runnerEmail.getText().toString().equals(app.tmpPerson.geteMail())) {
            // No changes to e-mail, so keep the same person!
            actualUpdate();
        } else {
            if (!runnerEmail.getText().toString().equals("")) {
                app.getEntityMapper().getpMapper().getPersonByEmail(runnerEmail.getText().toString());
                new GetRunner().execute();
            } else if (!(runnerFirstName.getText().toString().equals("") && runnerLastName.getText().toString().equals(""))) {
                app.getEntityMapper().getpMapper().getPersonsByName(runnerFirstName.getText().toString(), runnerLastName.getText().toString());
                new GetRunners().execute();
            } else {
                actualUpdate();
            }
        }
    }

    private void actualUpdate() {
        if (updateIt) {
            Timestamp ts1 = new Timestamp(yearInt, monthInt, dayInt, beginHoursInt, beginMinutesInt, beginSecondsInt, 0);
            Timestamp ts2 = new Timestamp(endYearInt, endMonthInt, endDayInt, endHoursInt, endMinutesInt, endSecondsInt, 0);
            lap.setBeginDateTime(ts1);
            lap.setEndDateTime(ts2);
            app.getEntityMapper().dataSent();
            app.getEntityMapper().getlMapper().updateLap(lap);
            new UpdateLap().execute();
        }
    }

    private class GetRunners extends AsyncTask<Void, Void, List<Person>> {
        protected List<Person> doInBackground(Void... voids) {
            List<Person> persons = new LinkedList<>();
            while (!app.getEntityMapper().dataReady()) {
                if (isCancelled()) break;
            }
            if (app.getEntityMapper().dataReady()) {
                persons = app.entityMapper.persons;
                app.getEntityMapper().dataGrabbed();
            }
            return persons;
        }

        protected void onPostExecute(List<Person> persons) {
            if (persons.size()==1) {
                lap.setPersonId(persons.get(0).getId());
                app.getEntityMapper().getlMapper().updateLapPerson(lap);
                actualUpdate();
            } else if (persons.size()==0) {
                Toast.makeText(app.getApplicationContext(), "Person with that name doesn't exist.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(app.getApplicationContext(), "There are multiple persons with this name. Try to fill in the runners e-mail adress", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetRunner extends AsyncTask<Void, Void, Person> {
        protected Person doInBackground(Void... voids) {
            Person person = new Person();
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
                lap.setPersonId(person.getId());
                app.getEntityMapper().getlMapper().updateLapPerson(lap);
                actualUpdate();
            } else {
                Toast.makeText(app.getApplicationContext(), "Person with that e-mail doesn't exist.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetRunnerToPrefill extends AsyncTask<Void, Void, Person> {
        protected Person doInBackground(Void... voids) {
            Person person = new Person();
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
                app.tmpPerson = person;
                runnerEmail.setText(person.geteMail());
                runnerFirstName.setText(person.getFirstName());
                runnerLastName.setText(person.getLastName());
            }
        }
    }

    private class UpdateLap extends AsyncTask<Void, Void, Lap> {
        protected Lap doInBackground(Void... voids) {
            while (!app.getEntityMapper().dataPushed()) {
                if (isCancelled()) break;
            }
            if (app.getEntityMapper().dataPushed()) {
                app.getEntityMapper().dataSent();
            }
            return new Lap();
        }

        protected void onPostExecute(Lap lap) {
            startActivity(intent);
            finish();
            Toast.makeText(app.getApplicationContext(), "Updated Lap", Toast.LENGTH_SHORT).show();
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