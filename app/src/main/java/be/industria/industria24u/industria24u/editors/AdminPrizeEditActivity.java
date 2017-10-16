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
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import be.industria.industria24u.industria24u.AdminPrizesActivity;
import be.industria.industria24u.industria24u.ExpandableAdapter;
import be.industria.industria24u.industria24u.R;
import be.industria.industria24u.industria24u.SingletonApplication;
import be.industria.industria24u.industria24u.navigation.AdminNavigationListener;
import entity.Person;
import entity.PersonType;
import entity.Person_has_prize;
import entity.Prize;

@SuppressWarnings("UnusedParameters")
public class AdminPrizeEditActivity extends AppCompatActivity implements ExpandableListView.OnChildClickListener {
    private SingletonApplication app;
    private DrawerLayout drawer;
    private EditText prizeName;
    private EditText prizeDescription;
    private EditText pointCost;
    private EditText year;
    private Person person = new Person();
    private Prize prize = new Prize();
    private Intent intent;
    private ExpandableAdapter adapterNotGiven;
    private ExpandableAdapter adapterGiven;
    private ExpandableListView listViewNotGiven;
    private List<Object> parentsNotGiven;
    private HashMap<Object, List<String>> relationsNotGiven;
    private ExpandableListView listViewGiven;
    private List<Object> parentsGiven;
    private HashMap<Object, List<String>> relationsGiven;
    private List<Person_has_prize> personPrizes = new LinkedList<>();
    private List<Object> persons = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_prize_edit_container);
        app = (SingletonApplication) this.getApplication();
        parentsNotGiven = new ArrayList<>(); parentsGiven = new ArrayList<>();
        relationsNotGiven = new HashMap<>(); relationsGiven = new HashMap<>();
        setupViews();
        prefillContent();
        intent = new Intent(this, AdminPrizesActivity.class);
        app.getEntityMapper().getPersonHasPrizeMapper().getPerson_has_prizesByPrize(app.tmpPrize);
        new GetPerson_has_prizes().execute();
    }

    private void setupViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_prize_edit_toolbar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
        }

        drawer = (DrawerLayout) findViewById(R.id.admin_prize_edit_container);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.admin_prize_edit_nav);
        navigationView.setNavigationItemSelectedListener(new AdminPrizeEditActivity.NavListener(this.getApplicationContext()));

        prizeName = (EditText) findViewById(R.id.prizeName);
        prizeDescription = (EditText) findViewById(R.id.prizeDescription);
        year = (EditText) findViewById(R.id.year);
        pointCost = (EditText) findViewById(R.id.pointCost);

        listViewNotGiven = (ExpandableListView) findViewById(R.id.admin_notGiven_list);
        listViewNotGiven.setGroupIndicator(null);
        listViewNotGiven.setLongClickable(true);
        registerForContextMenu(listViewNotGiven);

        listViewGiven = (ExpandableListView) findViewById(R.id.admin_Given_list);
        listViewGiven.setGroupIndicator(null);
        listViewGiven.setLongClickable(true);
        registerForContextMenu(listViewGiven);
    }

    private void displayData() {
        if (!personPrizes.isEmpty() || persons.isEmpty()) {
            for (Person_has_prize p:personPrizes) {
                List<String> fields = new LinkedList<>();
                if (p.getIsRecieved() == 0) {
                    parentsNotGiven.add(persons.get(personPrizes.indexOf(p)));
                    relationsNotGiven.put(persons.get(personPrizes.indexOf(p)), fields);
                }
                else {
                    parentsGiven.add(persons.get(personPrizes.indexOf(p)));
                    relationsGiven.put(persons.get(personPrizes.indexOf(p)), fields);
                }
            }
            adapterNotGiven = new ExpandableAdapter(relationsNotGiven, parentsNotGiven);
            adapterNotGiven.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
            listViewNotGiven.setAdapter(adapterNotGiven);
            listViewNotGiven.setOnChildClickListener(this);
            adapterGiven = new ExpandableAdapter(relationsGiven, parentsGiven);
            adapterGiven.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
            listViewGiven.setAdapter(adapterGiven);
            listViewGiven.setOnChildClickListener(this);
        } else {
            Toast.makeText(app.getApplicationContext(), "Nobody has a prize yet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v == listViewGiven) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.admin_given, menu);
        }
        else {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.admin_not_given, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();
        person = (Person) adapterNotGiven.getGroup((int) info.id);
        int id = person.getId();
        switch (item.getItemId()) {
            case R.id.action_given:
                for (Person_has_prize p:personPrizes) {
                    if (p.getPerson_id() == id) {
                        app.getEntityMapper().getPersonHasPrizeMapper().updatePerson_has_prize(prize,person,0);
                        relationsGiven.remove(person); relationsNotGiven.put(person, new LinkedList<String>());
                        parentsGiven.remove(person); parentsNotGiven.add(person);
                        adapterGiven.notifyDataSetChanged();
                        break;
                    }
                }
                return true;
            case R.id.action_not_given:
                for (Person_has_prize p:personPrizes) {
                    if (p.getPerson_id() == id) {
                        app.getEntityMapper().getPersonHasPrizeMapper().updatePerson_has_prize(prize,person,1);
                        relationsNotGiven.remove(person); relationsGiven.put(person, new LinkedList<String>());
                        parentsNotGiven.remove(person); parentsGiven.add(person);
                        adapterNotGiven.notifyDataSetChanged();
                        break;
                    }
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void prefillContent() {
        prize = app.tmpPrize;
        prizeName.setText(prize.getName());
        prizeDescription.setText(prize.getDescription());
        year.setText(String.valueOf(prize.getYear()));
        pointCost.setText(String.valueOf(prize.getPointCost()));
    }

    public void updatePrize(View v) {
        prize.setName(prizeName.getText().toString());
        prize.setDescription(prizeDescription.getText().toString());
        prize.setYear(Integer.parseInt(year.getText().toString()));
        prize.setPointCost(Integer.parseInt(pointCost.getText().toString()));
        app.getEntityMapper().getPrizeMapper().updatePrize(prize);
        startActivity(intent);
        finish();
        Toast.makeText(app.getApplicationContext(), "Updated "+prize.getName(), Toast.LENGTH_SHORT).show();
    }


    private class GetPerson extends AsyncTask<Void, Void, Person> {
        protected Person doInBackground(Void... voids) {
            Person person = new Person();
            while (!app.getEntityMapper().dataReady()) {
                if (isCancelled()) break;
            }
            if (app.getEntityMapper().dataReady()) {
                person = app.entityMapper.person;
                app.getEntityMapper().dataGrabbed();
            }
            return person;
        }
        protected void onPostExecute(Person person) {
            if (persons.size() == personPrizes.size()) {
                displayData();
            }
        }
    }

    private class GetPerson_has_prizes extends AsyncTask<Void, Void, List<Person_has_prize>> {
        protected List<Person_has_prize> doInBackground(Void... voids) {
            while (!app.getEntityMapper().dataReady()) {
                if (isCancelled()) break;
            }
            if (app.getEntityMapper().dataReady()) {
                personPrizes = app.entityMapper.person_has_prizes;
                app.getEntityMapper().dataGrabbed();
            }
            return personPrizes;
        }

        protected void onPostExecute(List<Person_has_prize> personPrizes) {
            System.out.println(personPrizes);
            for (Person_has_prize p:personPrizes) {
                app.getEntityMapper().getpMapper().getPerson(p.getPerson_id());
                new GetPerson().execute();
            }
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
