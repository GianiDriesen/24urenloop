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

import be.industria.industria24u.industria24u.AdminRunnersActivity;
import be.industria.industria24u.industria24u.R;
import be.industria.industria24u.industria24u.SingletonApplication;
import be.industria.industria24u.industria24u.navigation.AdminNavigationListener;
import entity.Person;

@SuppressWarnings({"deprecation", "UnusedParameters"})
public class AdminRunnerCreateActivity extends AppCompatActivity {
    private SingletonApplication app;
    private DrawerLayout drawer;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText birthDay;
    private EditText birthMonth;
    private EditText birthYear;
    private Person person = new Person();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_runner_create_container);
        app = (SingletonApplication) this.getApplication();
        setupViews();
        intent = new Intent(this, AdminRunnersActivity.class);
    }

    private void setupViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_runner_create_toolbar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
        }

        drawer = (DrawerLayout) findViewById(R.id.admin_runner_create_container);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.admin_runner_create_nav);
        navigationView.setNavigationItemSelectedListener(new NavListener(this.getApplicationContext()));

        firstName = (EditText) findViewById(R.id.firstName);
        lastName  = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        birthDay = (EditText) findViewById(R.id.birthDay);
        birthMonth = (EditText) findViewById(R.id.birthMonth);
        birthYear = (EditText) findViewById(R.id.birthYear);
        birthDay.setHint("day");
        birthMonth.setHint("month");
        birthYear.setHint("year");
    }

    public void createRunner(View v) {
        person.setFirstName(firstName.getText().toString());
        person.setLastName(lastName.getText().toString());
        person.seteMail(email.getText().toString());
        Date birthDate = new Date(0);
        birthDate.setDate(Integer.parseInt(birthDay.getText().toString()));
        birthDate.setMonth(Integer.parseInt(birthMonth.getText().toString())-1);
        birthDate.setYear(Integer.parseInt(birthYear.getText().toString())-1900);
        person.setBirthDate(birthDate);

        app.getEntityMapper().dataSent();
        app.getEntityMapper().getpMapper().createPerson(person);
        new WaitForInsert().execute();
    }

    private class WaitForInsert extends AsyncTask<Void, Void, Person> {
        protected Person doInBackground(Void... voids) {
            Person person = new Person();
            while (!app.getEntityMapper().dataPushed()) {
                if (isCancelled()) break;
            }
            if (app.getEntityMapper().dataPushed()) {
                app.getEntityMapper().dataSent();
            }
            return person;
        }
        protected void onPostExecute(Person person) {
            startActivity(intent);
            finish();
            Toast.makeText(app.getApplicationContext(), "Created new runner", Toast.LENGTH_SHORT).show();
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
