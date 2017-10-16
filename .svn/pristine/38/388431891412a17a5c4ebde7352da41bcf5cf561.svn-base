package be.industria.industria24u.industria24u.navigation;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import be.industria.industria24u.industria24u.AdminAchievementsActivity;
import be.industria.industria24u.industria24u.AdminLapHistoryActivity;
import be.industria.industria24u.industria24u.AdminPrizesActivity;
import be.industria.industria24u.industria24u.AdminRunnerQueueActivity;
import be.industria.industria24u.industria24u.AdminRunnersActivity;
import be.industria.industria24u.industria24u.AdminChronometerActivity;
import be.industria.industria24u.industria24u.AdminTeamsActivity;
import be.industria.industria24u.industria24u.AdminTrainingsActivity;
import be.industria.industria24u.industria24u.R;

public class AdminNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
    private Context context;
    public Intent resultIntent;

    public AdminNavigationListener(Context context) {
        this.context = context;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_runner_queue) {
            resultIntent = new Intent(context, AdminRunnerQueueActivity.class);
        } else if (id == R.id.nav_runners) {
            resultIntent = new Intent(context, AdminRunnersActivity.class);
        } else if (id == R.id.nav_teams) {
            resultIntent = new Intent(context, AdminTeamsActivity.class);
        } else if (id == R.id.nav_lap_history) {
            resultIntent = new Intent(context, AdminLapHistoryActivity.class);
        } else if (id == R.id.nav_achievements) {
            resultIntent = new Intent(context, AdminAchievementsActivity.class);
        } else if (id == R.id.nav_prizes) {
            resultIntent = new Intent(context, AdminPrizesActivity.class);
        } else if (id == R.id.nav_trainings) {
            resultIntent = new Intent(context, AdminTrainingsActivity.class);
        } else if (id == R.id.nav_chronometer) {
            resultIntent = new Intent(context, AdminChronometerActivity.class);
        } else {
            resultIntent = new Intent(context, AdminRunnerQueueActivity.class);
        }
        return true;
    }
}
