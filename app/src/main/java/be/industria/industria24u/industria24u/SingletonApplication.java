package be.industria.industria24u.industria24u;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Locale;

import entity.*;
import mapper.*;
/**
 * Application subclass, which is automatically created at runtime.
 * First method called is the onCreate() method of this class, after which
 * the first activity is automatically opened.
 * @author Koen Van Kerckhoven
 */

public class SingletonApplication extends Application {
    public String facebookID;
    public String googleID;
    private Person currentUser;

    public Lap tmpLap;
    public Team tmpTeam;
    public Prize tmpPrize;
    public Person tmpPerson;
    public Training tmpTraining;
    public Achievement tmpAchievement;
    public long lapBeginTime;
    public boolean isChronoStarted = false;

    public EntityMapper entityMapper = EntityMapper.UNIQUEMAPPER;
    public GoogleApiClient gac;

    public EntityMapper getEntityMapper() { return this.entityMapper; }

    public Timestamp getTimestamp() {
        try{
            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault());
            Date parsedDate = dateFormat.parse(dateFormat.format(date));
            return new Timestamp(parsedDate.getTime());
        } catch(Exception e) {
            System.out.println("Error while parsing date in right format");
            return null;
        }

    }

    public Person getUser() { return this.currentUser; }
    public void setUser(Person user) { this.currentUser = user; }

    @Override
    public void onCreate() {
        super.onCreate();
        RequestQueue rq = Volley.newRequestQueue(this.getApplicationContext());
        entityMapper.setRequestQueue(rq);
        currentUser = null;
        tmpLap = new Lap();
    }

    public SingletonApplication() {}
}
