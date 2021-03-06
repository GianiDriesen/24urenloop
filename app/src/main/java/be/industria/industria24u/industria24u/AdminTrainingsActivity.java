package be.industria.industria24u.industria24u;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import be.industria.industria24u.industria24u.editors.AdminTrainingCreateActivity;
import be.industria.industria24u.industria24u.editors.AdminTrainingEditActivity;
import be.industria.industria24u.industria24u.navigation.AdminNavigationListener;
import entity.Training;

@SuppressWarnings("UnusedParameters")
public class AdminTrainingsActivity extends AppCompatActivity implements OnChildClickListener, OnCreateContextMenuListener {
    private SingletonApplication app;
    private DrawerLayout drawer;
    private ExpandableAdapter adapter;
    private ExpandableListView listView;
    private List<Object> parents;
    private HashMap<Object, List<String>> relations;
    private Training training;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_trainings_container);
        app = (SingletonApplication) this.getApplication();
        setupViews();
        parents = new ArrayList<>();
        relations = new HashMap<>();
        app.getEntityMapper().getTrainingMapper().getTrainings(); // Get all trainings
        new GetTrainings().execute();
    }

    private void setupViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_trainings_toolbar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
        }

        drawer = (DrawerLayout) findViewById(R.id.admin_trainings_container);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.admin_trainings_nav);
        navigationView.setNavigationItemSelectedListener(new NavListener(this.getApplicationContext()));

        listView = (ExpandableListView) findViewById(R.id.admin_training_list);
        listView.setGroupIndicator(null);
        listView.setLongClickable(true);
        registerForContextMenu(listView);
    }

    private void displayData(List<Training> trainings) {
        if (!trainings.isEmpty()) {
            for (Training t:trainings) {
                List<String> fields = t.getExportableFields();
                parents.add(t);
                relations.put(t, fields);
            }
            adapter = new ExpandableAdapter(relations, parents);
            adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
            listView.setAdapter(adapter);
            listView.setOnChildClickListener(this);
        } else {
            Toast.makeText(app.getApplicationContext(), "There are no trainings in the database", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_edit_remove, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();
        training = (Training) adapter.getGroup((int) info.id);
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent editIntent = new Intent(this, AdminTrainingEditActivity.class);
                app.tmpTraining = training;
                startActivity(editIntent);
                finish();
                return true;
            case R.id.action_delete:
                Snackbar deleteSnack = Snackbar.make(findViewById(R.id.admin_trainings_coordinator), "Are you sure you want to delete this Training?", Snackbar.LENGTH_LONG);
                deleteSnack.setAction(R.string.confirm, new DeleteListener());
                deleteSnack.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void addTraining(View v) {
        Intent createIntent = new Intent(this, AdminTrainingCreateActivity.class);
        startActivity(createIntent);
        finish();
    }

    private class DeleteListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            parents.remove(training);
            relations.remove(training);
            adapter.notifyDataSetChanged();
            app.getEntityMapper().getTrainingMapper().deleteTraining(training.getId());
        }
    }

    private class GetTrainings extends AsyncTask<Void, Void, List<Training>> {
        protected List<Training> doInBackground(Void... voids) {
            List<Training> trainings = new LinkedList<>();
            while (!app.getEntityMapper().dataReady()) {
                if (isCancelled()) break;
            }
            if (app.getEntityMapper().dataReady()) {
                trainings = app.getEntityMapper().trainings;
                app.getEntityMapper().dataGrabbed();
            }
            return trainings;
        }

        protected void onPostExecute(List<Training> trainings) {
            displayData(trainings);
        }
    }

    private class NavListener extends AdminNavigationListener {
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
