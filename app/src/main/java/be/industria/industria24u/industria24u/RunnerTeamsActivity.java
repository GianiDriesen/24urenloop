package be.industria.industria24u.industria24u;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;
import entity.Team;

public class RunnerTeamsActivity extends AppCompatActivity {
    private SingletonApplication app;
    private Team team = new Team();
    private Intent intentNoTeam;
    private Intent intentTeam;
    private int year = Calendar.getInstance().get(Calendar.YEAR);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (SingletonApplication) this.getApplication();
        if (app.getUser() != null) {
            checkPersonHasTeamThisYear();
        }
    }

    private void checkPersonHasTeamThisYear() {
        app.getEntityMapper().gettMapper().getTeamByPersonAndYear(app.getUser(), year);
        new GetTeam().execute();
        intentNoTeam = new Intent(this, RunnerTeamsHasNoTeamActivity.class);
        intentTeam = new Intent(this, RunnerTeamsHasTeamActivity.class);
    }


    private class GetTeam extends AsyncTask<Void, Void, Team> {
        protected Team doInBackground(Void... voids) {
            while (!app.getEntityMapper().dataReady()) {
                if (isCancelled()) break;
            }
            if (app.getEntityMapper().dataReady()) {
                team = app.getEntityMapper().team;
                app.getEntityMapper().dataGrabbed();
            }
            return team;
        }

        protected void onPostExecute(Team team) {
            if (team == null) { //Setup everything for someone having no team
                startActivity(intentNoTeam);
                finish();
            } else { //Setup everything for someone having a team
                startActivity(intentTeam);
            }
        }
    }
}
