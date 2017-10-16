package be.industria.industria24u.industria24u.navigation;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import be.industria.industria24u.industria24u.LogoutActivity;
import be.industria.industria24u.industria24u.R;
import be.industria.industria24u.industria24u.RunnerAchievementsActivity;
import be.industria.industria24u.industria24u.RunnerLapHistoryActivity;
import be.industria.industria24u.industria24u.RunnerMyProfileEditActivity;
import be.industria.industria24u.industria24u.RunnerMyProfileStaticActivity;
import be.industria.industria24u.industria24u.RunnerPrizesActivity;
import be.industria.industria24u.industria24u.RunnerStatisticsActivity;
import be.industria.industria24u.industria24u.RunnerTeamsActivity;
import be.industria.industria24u.industria24u.RunnerTrainingsActivity;

public class RunnerNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
    private Context context;
    public Intent resultIntent;

    public RunnerNavigationListener(Context context) {
        this.context = context;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_my_profile) {
            resultIntent = new Intent(context, RunnerMyProfileStaticActivity.class);
        } else if (id == R.id.nav_my_team) {
            resultIntent = new Intent(context, RunnerTeamsActivity.class);
        } else if (id == R.id.nav_my_statistics) {
            resultIntent = new Intent(context, RunnerStatisticsActivity.class);
        } else if (id == R.id.nav_achievements) {
            resultIntent = new Intent(context, RunnerAchievementsActivity.class);
        } else if (id == R.id.nav_prizes) {
            resultIntent = new Intent(context, RunnerPrizesActivity.class);
        } else if (id == R.id.nav_trainings) {
            resultIntent = new Intent(context, RunnerTrainingsActivity.class);
        } else if (id == R.id.nav_laps) {
            resultIntent = new Intent(context, RunnerLapHistoryActivity.class);
        } else if (id == R.id.nav_logout) {
            resultIntent = new Intent(context, LogoutActivity.class);
        } else {
            resultIntent = new Intent(context, RunnerMyProfileEditActivity.class);
        }
        return true;
    }

}