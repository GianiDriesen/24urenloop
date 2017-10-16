package be.industria.industria24u.industria24u;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.EditText;
import java.sql.Date;

import entity.Person;
import entity.PersonType;

@SuppressWarnings({"deprecation", "UnusedParameters"})
public class RegistrationActivity extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    private EditText eMail;
    private EditText birthDay;
    private EditText birthMonth;
    private EditText birthYear;
    private CheckBox isPushNotifications;
    private CheckBox isEMailNotifications;
    private SingletonApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        app = (SingletonApplication) this.getApplication();
        setupContent();
        setupHint();

    }
    private void setupContent(){
        firstName = (EditText) findViewById(R.id.firstName);
        lastName  = (EditText) findViewById(R.id.lastName);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        eMail = (EditText) findViewById(R.id.eMail);
        birthDay = (EditText) findViewById(R.id.birthdayDay);
        birthMonth = (EditText) findViewById(R.id.birthdayMonth);
        birthYear = (EditText) findViewById(R.id.birthdayYear);
        isPushNotifications = (CheckBox) findViewById(R.id.isPNotifications);
        isEMailNotifications = (CheckBox) findViewById(R.id.isENotifications);
    }

    private void setupHint() {

        firstName.setText(String.valueOf(getIntent().getSerializableExtra("firstName")));
        lastName.setText(String.valueOf(getIntent().getSerializableExtra("lastName")));
        eMail.setText(String.valueOf(getIntent().getSerializableExtra("email")));
    }

    private Person createPerson() {
        Date birthdate = new Date(0,0,0);
        int boolE = 0;
        int boolN = 0;
        if (isEMailNotifications.isChecked()) {boolE = 1;}
        if (isPushNotifications.isChecked()) {boolN = 1;}
        birthdate.setDate(Integer.parseInt(birthDay.getText().toString()));
        birthdate.setMonth(Integer.parseInt(birthMonth.getText().toString())-1);
        birthdate.setYear((Integer.parseInt(birthYear.getText().toString())-1900));
        return new Person(
            ""+null,
            ""+null,
            ""+firstName.getText().toString(),
            ""+lastName.getText().toString(),
            ""+eMail.getText().toString(),
            ""+phoneNumber.getText().toString(),
            birthdate,
            PersonType.RUNNER,
            boolN,
            boolE
        );
    }

    private class SendRunner extends AsyncTask<Void, Void, Person> {
        Person person = new Person();
        protected Person doInBackground(Void... voids) {
            while (!app.getEntityMapper().dataPushed()) {
                if (isCancelled()) break;}
            if (app.getEntityMapper().dataPushed()) {
                app.getEntityMapper().dataSent();
            }
            return person;
        }

        protected void onPostExecute(Person person) {
            updatePerson();
        }
    }
    private void updatePerson() {
        app.getEntityMapper().getpMapper().getPersonByEmail(eMail.getText().toString());
        new UpdateRunner().execute();
    }
    private class UpdateRunner extends AsyncTask<Void, Void, Person> {
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
            person.setPhoneNumber(phoneNumber.getText().toString());
            if (app.facebookID==null) {
                person.setGoogleId(app.googleID);
                app.getEntityMapper().getpMapper().updatePersonGoogle(person);

            }
            else {
                person.setFacebookId(app.facebookID);
                app.getEntityMapper().getpMapper().updatePersonFacebook(person);
            }
            app.getEntityMapper().getpMapper().updatePersonPhone(person);
            app.setUser(person);
            Intent intent = new Intent(RegistrationActivity.this, RunnerMyProfileStaticActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void registerAccount(View view) {
        app.getEntityMapper().dataSent();
        Person person = createPerson();
        app.getEntityMapper().getpMapper().createPerson(person);
        new SendRunner().execute();
    }
}
