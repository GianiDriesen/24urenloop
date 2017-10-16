package be.industria.industria24u.industria24u;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import be.industria.industria24u.industria24u.navigation.RunnerNavigationListener;
import entity.Training;

public class RunnerTrainingsActivity extends AppCompatActivity implements OnChildClickListener{
    private SingletonApplication app;
    private DrawerLayout drawer;
    private ExpandableListView listView;
    private List<Object> parents;
    private HashMap<Object, List<String>> relations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runner_trainings_container);
        app = (SingletonApplication) this.getApplication();
        setupViews();
        parents = new ArrayList<>();
        relations = new HashMap<>();
        app.getEntityMapper().getTrainingMapper().getTrainingsByPerson(app.getUser()); // Get all trainings for this user
        new GetTrainings().execute();
    }

    private void setupViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.runner_trainings_toolbar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
        }

        drawer = (DrawerLayout) findViewById(R.id.runner_trainings_container);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.runner_trainings_nav);
        navigationView.setNavigationItemSelectedListener(new NavListener(this.getApplicationContext()));

        listView = (ExpandableListView) findViewById(R.id.runner_training_list);
        listView.setGroupIndicator(null);
    }

    private void displayData(List<Training> trainings) {
        if (!trainings.isEmpty()) {
            for (Training t:trainings) {
                List<String> fields = t.getExportableFields();
                parents.add(t);
                relations.put(t, fields);
            }
            ExpandableAdapter adapter = new ExpandableAdapter(relations, parents);
            adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
            listView.setAdapter(adapter);
            listView.setOnChildClickListener(this);
        } else {
            Toast.makeText(app.getApplicationContext(), "You have no trainings to speak of (yet)!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
        return false;
    }

    private class GetTrainings extends AsyncTask<Void, Void, List<Training>> {
        protected List<Training> doInBackground(Void... voids) {
            List<Training> trainings = new LinkedList<>();
            while (!app.getEntityMapper().dataReady()) {
                if (isCancelled()) break;
            }
            if (app.getEntityMapper().dataReady()) {
                trainings = app.entityMapper.trainings;
                app.getEntityMapper().dataGrabbed();
            }
            return trainings;
        }

        protected void onPostExecute(List<Training> trainings) {
            displayData(trainings);
        }
    }

    private class NavListener extends RunnerNavigationListener {
        public NavListener(Context context) {
            super(context);
        }

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
