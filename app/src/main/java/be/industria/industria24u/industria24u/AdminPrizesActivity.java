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
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import be.industria.industria24u.industria24u.editors.AdminPrizeCreateActivity;
import be.industria.industria24u.industria24u.editors.AdminPrizeEditActivity;
import be.industria.industria24u.industria24u.navigation.AdminNavigationListener;
import entity.Person;
import entity.Person_has_prize;
import entity.Prize;

@SuppressWarnings("UnusedParameters")
public class AdminPrizesActivity extends AppCompatActivity implements OnChildClickListener, OnCreateContextMenuListener {
    private SingletonApplication app;
    private DrawerLayout drawer;
    private ExpandableAdapter adapter;
    private ExpandableListView listView;
    private List<Object> parents;
    private HashMap<Object, List<String>> relations;
    private ExpandableListView listViewOld;
    private List<Object> parentsOld;
    private HashMap<Object, List<String>> relationsOld;
    private Prize prize;
    private List<Prize> prizesAll = new LinkedList<>();
    private List<Person_has_prize> personPrizes = new LinkedList<>();
    private List<Person> persons = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_prizes_container);
        app = (SingletonApplication) this.getApplication();
        setupViews();
        parents = new ArrayList<>(); parentsOld = new ArrayList<>();
        relations = new HashMap<>(); relationsOld = new HashMap<>();
        app.getEntityMapper().getPrizeMapper().getPrizes(); // Get all prizes
        new GetPrizes().execute();
    }

    private void setupViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_prizes_toolbar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
        }

        drawer = (DrawerLayout) findViewById(R.id.admin_prizes_container);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.admin_prizes_nav);
        navigationView.setNavigationItemSelectedListener(new NavListener(this.getApplicationContext()));

        listView = (ExpandableListView) findViewById(R.id.admin_prize_list);
        listView.setGroupIndicator(null);
        listView.setLongClickable(true);
        registerForContextMenu(listView);

        listViewOld = (ExpandableListView) findViewById(R.id.admin_oldPrize_list);
        listViewOld.setGroupIndicator(null);
        listViewOld.setLongClickable(true);
        registerForContextMenu(listViewOld);
    }

    private void displayData(List<Prize> prizes) {
        if (!prizes.isEmpty()) {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            for (Prize p:prizes) {
                List<String> fields = p.getExportableFields();
                if (p.getYear() == year) {
                    parents.add(p);
                    relations.put(p, fields);
                }
                else {
                    parentsOld.add(p);
                    relationsOld.put(p, fields);
                }
            }
            adapter = new ExpandableAdapter(relations, parents);
            adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
            listView.setAdapter(adapter);
            listView.setOnChildClickListener(this);
            ExpandableAdapter adapterOld = new ExpandableAdapter(relationsOld, parentsOld);
            adapterOld.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
            listViewOld.setAdapter(adapterOld);
            listViewOld.setOnChildClickListener(this);
        } else {
            Toast.makeText(app.getApplicationContext(), "There are no prizes in the database", Toast.LENGTH_LONG).show();
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
        prize = (Prize) adapter.getGroup((int) info.id);
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent editIntent = new Intent(this, AdminPrizeEditActivity.class);
                app.tmpPrize = prize;
                startActivity(editIntent);
                finish();
                return true;
            case R.id.action_delete:
                Snackbar deleteSnack = Snackbar.make(findViewById(R.id.admin_prizes_coordinator), "Are you sure you want to delete "+prize.getName()+"?", Snackbar.LENGTH_LONG);
                deleteSnack.setAction(R.string.confirm, new DeleteListener());
                deleteSnack.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void addPrize(View v) {
        Intent createIntent = new Intent(this, AdminPrizeCreateActivity.class);
        startActivity(createIntent);
        finish();
    }





    private class DeleteListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            parents.remove(prize);
            relations.remove(prize);
            adapter.notifyDataSetChanged();
            app.getEntityMapper().getPrizeMapper().deletePrize(prize.getId());
        }
    }

    private class GetPrizes extends AsyncTask<Void, Void, List<Prize>> {
        protected List<Prize> doInBackground(Void... voids) {
            List<Prize> prizes = new LinkedList<>();
            while (!app.getEntityMapper().dataReady()) {
                if (isCancelled()) break;
            }
            if (app.getEntityMapper().dataReady()) {
                prizes = app.getEntityMapper().prizes;
                app.getEntityMapper().dataGrabbed();
            }
            return prizes;
        }

        protected void onPostExecute(List<Prize> prizes)
        {
            displayData(prizes);
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
