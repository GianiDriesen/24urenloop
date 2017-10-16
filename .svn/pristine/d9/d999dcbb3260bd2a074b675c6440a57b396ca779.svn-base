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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
import be.industria.industria24u.industria24u.AdminAchievementsActivity;
import be.industria.industria24u.industria24u.R;
import be.industria.industria24u.industria24u.SingletonApplication;
import be.industria.industria24u.industria24u.navigation.AdminNavigationListener;
import entity.Achievement;
import entity.RunnerAchievement;
import entity.TeamAchievement;

@SuppressWarnings("UnusedParameters")
public class AdminAchievementCreateActivity extends AppCompatActivity implements OnItemSelectedListener {
    private SingletonApplication app;
    private DrawerLayout drawer;
    private EditText achievementName;
    private EditText achievementDescription;
    private Achievement achievement;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_achievement_create_container);
        app = (SingletonApplication) this.getApplication();
        setupViews();
        intent = new Intent(this, AdminAchievementsActivity.class);
    }

    private void setupViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_achievement_create_toolbar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
        }

        drawer = (DrawerLayout) findViewById(R.id.admin_achievement_create_container);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.admin_achievement_create_nav);
        navigationView.setNavigationItemSelectedListener(new NavListener(this.getApplicationContext()));

        achievementName = (EditText) findViewById(R.id.achievementName);
        achievementDescription = (EditText) findViewById(R.id.achievementDescription);
        Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.types_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setOnItemSelectedListener(this);
        typeSpinner.setSelection(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        CharSequence charSequence = (CharSequence) parent.getItemAtPosition(pos);
        if (charSequence.toString().equals("For runners")) {
            achievement = new RunnerAchievement();
        } else if (charSequence.toString().equals("For teams")) {
            achievement = new TeamAchievement();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void insertAchievement(View v) {
        achievement.setName(achievementName.getText().toString());
        achievement.setDescription(achievementDescription.getText().toString());
        app.getEntityMapper().getaMapper().insertAchievement(achievement);
        startActivity(intent);
        finish();
        Toast.makeText(app.getApplicationContext(), "Created "+achievement.getName(), Toast.LENGTH_SHORT).show();
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
