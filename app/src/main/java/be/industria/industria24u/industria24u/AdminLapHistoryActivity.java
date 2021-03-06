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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import be.industria.industria24u.industria24u.editors.AdminLapCreateActivity;
import be.industria.industria24u.industria24u.editors.AdminLapEditActivity;
import be.industria.industria24u.industria24u.navigation.AdminNavigationListener;
import entity.Lap;
import entity.Person;

@SuppressWarnings("UnusedParameters")
public class AdminLapHistoryActivity extends AppCompatActivity implements OnChildClickListener, OnCreateContextMenuListener {
    private SingletonApplication app;
    private DrawerLayout drawer;
    private ExpandableAdapter adapter;
    private ExpandableListView listView;
    private List<Object> parents;
    private HashMap<Object, List<String>> relations;
    private List<Lap> laps = new LinkedList<>();
    private List<List<String>> lapList = new LinkedList<>();
    private List<List<String>> idList = new LinkedList<>();
    private int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_laps_container);
        app = (SingletonApplication) this.getApplication();
        setupViews();
        parents = new ArrayList<>();
        relations = new HashMap<>();
        app.getEntityMapper().getlMapper().getLaps();
        new GetLaps().execute();
    }

    private void setupViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_laps_toolbar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
        }

        drawer = (DrawerLayout) findViewById(R.id.admin_laps_container);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.admin_laps_nav);
        navigationView.setNavigationItemSelectedListener(new NavListener(this.getApplicationContext()));

        listView = (ExpandableListView) findViewById(R.id.admin_lap_list);
        listView.setGroupIndicator(null);
        listView.setLongClickable(true);
        registerForContextMenu(listView);
    }

    private void displayData(List<Lap> laps) {
        if (!laps.isEmpty()) {
            for (Lap l:laps) {
                //List<String> fields = l.getExportableFieldsAdmin();
                //parents.add(l);
                //relations.put(l, fields);

                List<String> fields = new ArrayList<>();
                fields.addAll(l.getExportableFields());
                fields.addAll(lapList.get(laps.indexOf(l)));
                parents.add(l);
                relations.put(l, fields);
            }
            adapter = new ExpandableAdapter(relations, parents);
            adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
            listView.setAdapter(adapter);
            listView.setOnChildClickListener(this);
        } else {
            Toast.makeText(app.getApplicationContext(), "There are no laps in the database", Toast.LENGTH_LONG).show();
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
        app.tmpLap = (Lap) adapter.getGroup((int) info.id);
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent editIntent = new Intent(this, AdminLapEditActivity.class);
                startActivity(editIntent);
                finish();
                return true;
            case R.id.action_delete:
                Snackbar deleteSnack = Snackbar.make(findViewById(R.id.admin_laps_coordinator), "Are you sure you want to delete this Lap?", Snackbar.LENGTH_LONG);
                deleteSnack.setAction(R.string.confirm, new DeleteListener());
                deleteSnack.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void addLap(View v) {
        Intent createIntent = new Intent(this, AdminLapCreateActivity.class);
        startActivity(createIntent);
        finish();
    }

    private class DeleteListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            parents.remove(app.tmpLap);
            relations.remove(app.tmpLap);
            adapter.notifyDataSetChanged();
            app.getEntityMapper().getlMapper().deleteLap(app.tmpLap.getId());
        }
    }

    private class GetLaps extends AsyncTask<Void, Void, List<Lap>> {
        protected List<Lap> doInBackground(Void... voids) {
            while (!app.getEntityMapper().dataReady()) {
                if (isCancelled()) break;
            }
            if (app.getEntityMapper().dataReady()) {
                laps = app.getEntityMapper().laps;
                app.getEntityMapper().dataGrabbed();
            }
            return laps;
        }

        protected void onPostExecute(List<Lap> laps) {
            for (Lap l:laps){
                app.getEntityMapper().getpMapper().getPerson(l.getPersonId());
                 new GetPersonRanLap().execute();
            }
        }
    }


    private class GetPersonRanLap extends AsyncTask<Void, Void, Person> {
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

            List<String> lapByPerson= new LinkedList<>();
            List<String> idOfThisLap= new LinkedList<>();
            lapByPerson.add("Ran by: "+person.toString());
            idOfThisLap.add(""+person.getId());
            lapList.add(lapByPerson);
            idList.add(idOfThisLap);
            if (lapList.size() == laps.size()) {
                setLaplistInRightOrder(0);
            }
        }
    }

    private void setLaplistInRightOrder(int currentPlace) {
        // Because we get the lapList not in the right order from the database, this recursive method makes sure that
        // lapList corresponds to laps.
        if (currentPlace == laps.size()) {
            displayData(laps);
        }
        else {
            Lap l = laps.get(currentPlace);
            if (Integer.parseInt(idList.get(currentPlace + number).get(0)) != l.getPersonId()) {
                number++;
                setLaplistInRightOrder(currentPlace);
            } else {
                Collections.swap(lapList, currentPlace, currentPlace + number);
                Collections.swap(idList, currentPlace, currentPlace + number);
                number = 0;
                setLaplistInRightOrder(currentPlace + 1);
            }
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
