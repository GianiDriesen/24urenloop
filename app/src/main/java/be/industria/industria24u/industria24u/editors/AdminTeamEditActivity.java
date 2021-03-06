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
import be.industria.industria24u.industria24u.AdminTeamsActivity;
import be.industria.industria24u.industria24u.R;
import be.industria.industria24u.industria24u.SingletonApplication;
import be.industria.industria24u.industria24u.navigation.AdminNavigationListener;
import entity.Person;
import entity.Team;

@SuppressWarnings("UnusedParameters")
public class AdminTeamEditActivity extends AppCompatActivity {
    private SingletonApplication app;
    private DrawerLayout drawer;
    private EditText teamName;
    private EditText year;
    private EditText leaderEmail;
    private Team team = new Team();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_team_edit_container);
        app = (SingletonApplication) this.getApplication();
        setupViews();
        prefillContent();
        intent = new Intent(this, AdminTeamsActivity.class);
    }

    private void setupViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_team_edit_toolbar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
        }

        drawer = (DrawerLayout) findViewById(R.id.admin_team_edit_container);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.admin_team_edit_nav);
        navigationView.setNavigationItemSelectedListener(new NavListener(this.getApplicationContext()));

        teamName = (EditText) findViewById(R.id.teamName);
        year = (EditText) findViewById(R.id.year);
        leaderEmail = (EditText) findViewById(R.id.leaderEmail);
    }

    private void prefillContent() {
        team = app.tmpTeam;
        teamName.setText(team.getName());
        year.setText(String.valueOf(team.getYear()));
        new GetTeamLeaderEmail().execute();
    }

    public void updateTeam(View v) {
        team.setName(teamName.getText().toString());
        team.setYear(Integer.parseInt(year.getText().toString()));
        if (!leaderEmail.getText().toString().equals("")) {
            app.getEntityMapper().getpMapper().getPersonByEmail(leaderEmail.getText().toString());
            new GetTeamLeader().execute();
        } else {
            team.setLeaderId(0);
            app.getEntityMapper().gettMapper().updateTeam(team);
            startActivity(intent);
            finish();
            Toast.makeText(app.getApplicationContext(), "Updated team "+team.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    private class GetTeamLeaderEmail extends AsyncTask<Void, Void, Person> {
        protected Person doInBackground(Void... voids) {
            Person person = new Person();
            while (!app.getEntityMapper().dataReady()) {
                if (isCancelled()) break;
            }
            if (app.getEntityMapper().dataReady()) {
                person = app.getEntityMapper().person;
                app.getEntityMapper().dataGrabbed();
            }
            return person;
        }
        protected void onPostExecute(Person person) {
            if (!(person == null)) {
                leaderEmail.setText(person.geteMail());
            }
        }
    }

    private class GetTeamLeader extends AsyncTask<Void, Void, Person> {
        protected Person doInBackground(Void... voids) {
            Person person = new Person();
            while (!app.getEntityMapper().dataReady()) {
                if (isCancelled()) break;
            }
            if (app.getEntityMapper().dataReady()) {
                person = app.getEntityMapper().person;
                app.getEntityMapper().dataGrabbed();
            }
            return person;
        }
        protected void onPostExecute(Person person) {
            if (!(person == null)) {
                team.setLeaderId(person.getId());
            } else {
                team.setLeaderId(0);
            }
            app.getEntityMapper().gettMapper().updateTeam(team);
            startActivity(intent);
            finish();
            Toast.makeText(app.getApplicationContext(), "Updated team "+team.getName(), Toast.LENGTH_SHORT).show();
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