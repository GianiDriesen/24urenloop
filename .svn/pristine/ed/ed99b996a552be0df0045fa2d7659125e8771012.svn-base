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

import be.industria.industria24u.industria24u.editors.AdminRunnerCreateActivity;
import be.industria.industria24u.industria24u.editors.AdminRunnerEditActivity;
import be.industria.industria24u.industria24u.navigation.AdminNavigationListener;
import entity.Person;

@SuppressWarnings("UnusedParameters")
public class AdminRunnersActivity extends AppCompatActivity implements OnChildClickListener, OnCreateContextMenuListener {
    private SingletonApplication app;
    private DrawerLayout drawer;
    private ExpandableAdapter adapter;
    private ExpandableListView listView;
    private List<Object> parents;
    private HashMap<Object, List<String>> relations;
    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (SingletonApplication) this.getApplication();
        setContentView(R.layout.admin_runners_container);
        setupViews();
        parents = new ArrayList<>();
        relations = new HashMap<>();
        app.getEntityMapper().getpMapper().getPersons(); // Get all runners
        new GetRunners().execute();
    }

    private void setupViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_runners_toolbar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
        }

        drawer = (DrawerLayout) findViewById(R.id.admin_runners_container);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.admin_runners_nav);
        navigationView.setNavigationItemSelectedListener(new NavListener(this.getApplicationContext()));

        listView = (ExpandableListView) findViewById(R.id.admin_runner_list);
        listView.setGroupIndicator(null);
        listView.setLongClickable(true);
            registerForContextMenu(listView);
        }

    private void displayData(List<Person> runners) {
        if (!runners.isEmpty()) {
            for (Person p:runners) {
                List<String> fields = p.getExportableFields();
                parents.add(p);
                relations.put(p, fields);
            }
            adapter = new ExpandableAdapter(relations, parents);
            adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
            listView.setAdapter(adapter);
            listView.setOnChildClickListener(this);
        } else {
            Toast.makeText(app.getApplicationContext(), "There are no persons in the database", Toast.LENGTH_LONG).show();
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
        person = (Person) adapter.getGroup((int) info.id);
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent editIntent = new Intent(this, AdminRunnerEditActivity.class);
                app.tmpPerson = person;
                startActivity(editIntent);
                finish();
                return true;
            case R.id.action_delete:
                Snackbar deleteSnack = Snackbar.make(findViewById(R.id.admin_runners_coordinator), "Are you sure you want to delete "+person.getFirstName()+"?", Snackbar.LENGTH_LONG);
                deleteSnack.setAction(R.string.confirm, new DeleteListener());
                deleteSnack.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void addRunner(View v) {
        Intent createIntent = new Intent(this, AdminRunnerCreateActivity.class);
        startActivity(createIntent);
        finish();
    }

    private class DeleteListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            parents.remove(person);
            relations.remove(person);
            adapter.notifyDataSetChanged();
            app.getEntityMapper().getpMapper().deletePerson(person.getId());
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
        protected void onPostExecute(List<Person> persons) {displayData(persons);}
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
