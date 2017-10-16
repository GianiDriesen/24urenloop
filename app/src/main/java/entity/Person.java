package entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koen on 01/12/2016
 */

public class Person {
    private int id;
    private String facebookId; // NULL -> not set
    private String googleId; // NULL -> not set
    private String firstName;
    private String lastName;
    private String eMail;
    private String phoneNumber;
    private Date birthDate;
    private int points; //TODO: Discuss how to with Koen
    private PersonType type;
    private int notificationSetting;
    private int emailSetting;
    private List<Prize> prizeList;
    private List<Lap> lapList;
    private List<Training> trainingList;
    private List<RunnerAchievement> achievementList;

    /** Empty constructor; only needed as a hack to get toString() methods to identify entity types */
    public Person() {}

    public Person(JSONObject obj) {
        try {
            this.id = obj.getInt("id");
            if (obj.get("facebookID") == null) {
                this.facebookId = "";
            } else {
                this.facebookId = obj.getString("facebookID");
            }
            if (obj.get("googleID") == null) {
                this.googleId= "";
            } else {
                this.googleId= obj.getString("googleID");
            }
            this.firstName = obj.getString("firstName").replace('_', ' ');
            this.lastName = obj.getString("lastName").replace('_', ' ');
            this.eMail = obj.getString("eMail");
            this.phoneNumber = obj.getString("phoneNumber");
            this.birthDate = Date.valueOf(obj.getString("birthDay"));
            switch(obj.getString("personType")) {
                case "RUNNER":
                    this.type = PersonType.RUNNER;
                    break;
                case "EMPLOYEE":
                    this.type = PersonType.EMPLOYEE;
                    break;
                case "MANAGER":
                    this.type = PersonType.MANAGER;
                    break;
                case "ADMIN":
                    this.type = PersonType.ADMIN;
                    break;
            }
            this.notificationSetting = obj.getInt("notificationSetting");
            this.emailSetting = obj.getInt("emailSetting");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor for persons already defined in the database
     *
     * @param id                  the id of the person
     * @param facebookId          [optional] the facebookid corresponding to this person
     * @param googleId            [optional] the googleID corresponding to this person
     * @param firstName           the person's first name
     * @param lastName            the person's last name
     * @param eMail               the person's e-mail
     * @param phoneNumber         the person's phone number
     * @param birthDate           the person's birth date
     * @param type                the type of person (enum: runner/admin/employee)
     * @param notificationSetting is person interested in notifications?
     * @param emailSetting        is person interested in getting e-mail updates?
     */
    public Person(int id, String facebookId, String googleId, String firstName, String lastName, String eMail, String phoneNumber, Date birthDate, PersonType type, int notificationSetting, int emailSetting) {
        this.id = id;
        this.facebookId = facebookId;
        this.googleId = googleId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.type = type;
        this.emailSetting = emailSetting;
        this.notificationSetting = notificationSetting;
    }

    /**
     * Constructor for persons new to the database
     *
     * @param facebookId          [optional] the facebookid corresponding to this person
     * @param googleId            [optional] the googleID corresponding to this person
     * @param firstName           the person's first name
     * @param lastName            the person's last name
     * @param eMail               the person's e-mail
     * @param phoneNumber         the person's phone number
     * @param birthDate           the person's birth date
     * @param type                the type of person (enum: runner/admin/employee)
     * @param notificationSetting is person interested in notifications?
     * @param emailSetting        is person interested in getting e-mail updates?
     */

    public Person(String facebookId, String googleId, String firstName, String lastName, String eMail, String phoneNumber, Date birthDate, @SuppressWarnings("SameParameterValue") PersonType type, int notificationSetting, int emailSetting) {
        this.facebookId = facebookId;
        this.googleId = googleId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.type = type;
        this.emailSetting = emailSetting;
        this.notificationSetting = notificationSetting;
    }

    /** GETTERS */

    /** @return the ID */
    public int getId() {
        return this.id;
    }

    /** @return the facebook ID */
    public String getFacebookId() {
        return facebookId;
    }

    /** @return the google ID */
    public String getGoogleId() {
        return googleId;
    }

    /** @return the firstName */
    public String getFirstName() {
        return firstName;
    }

    /** @return the lastName */
    public String getLastName() {
        return lastName;
    }

    /** @return the eMail */
    public String geteMail() {
        return eMail;
    }

    /** @return phoneNumber*/
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** @return birthDate*/
    public Date getBirthDate() {
        return birthDate;
    }

    /** @return type */
    public PersonType getType() {
        return type;
    }

    /** @return notificationSetting*/
    public int getNotificationSetting() {
        return notificationSetting;
    }

    /** @return emailSetting */
    public int getEmailSetting() {
        return emailSetting;
    }

    /** @return List<Prize> the prizes a Person recieved */
    public List<Prize> getPrizeList() {
            return prizeList;
    }

    /** @return List<Lap> the laps a Person ran */
    public List<Lap> getLapList() {
            return lapList;
    }

    /** @return List<Training> the trainings a Person attended */
    public List<Training> getTrainingList() {
        return trainingList;
    }

    /** @return List<RunnerAchievement> the achievements a Person achieved */
    public List<RunnerAchievement> getAchievementList() {
        return achievementList;
    }

    /** @return Amount of points this runner has */
    public int getPoints() { return points; }

    /** SETTERS */

    /** @param  id the id */
    public void setId(int id) {
        this.id = id;
    }

    /** @param facebookId the facebookID */
    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    /** @param googleId the googleID */
    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    /** @param firstName the first name */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /** @param lastName the last name */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /** @param eMail the e-mail */
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    /** @param phoneNumber The phone number */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /** @param birthDate The day of birth */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /** @param personType The type of person */
    public void setPersonType(PersonType personType) {this.type = personType; }

    /** @param notificationSetting whether you want phone notifications */
    public void setNotificationSetting(int notificationSetting) {
        this.notificationSetting = notificationSetting;
    }

    /** @param emailSetting wheter you want email notifications */
    public void setEmailSetting(int emailSetting) {
        this.emailSetting = emailSetting;
    }

    /** @param prizeList the list of prizes */
    public void setPrizeList(List<Prize> prizeList) {
        this.prizeList = prizeList;
    }

    /** @param achievementList the list of achievements */
    public void setAchievementList(List<RunnerAchievement> achievementList) {
        this.achievementList = achievementList;
    }

    /** @param lapList the list of laps */
    public void setLapList(List<Lap> lapList) {
        this.lapList = lapList;
    }

    /** @param trainingList the list of trainings */
    public void setTrainingList(List<Training> trainingList) {
        this.trainingList = trainingList;
    }

    /** @param points Amount of points this runner has */
    public void setPoints(int points) { this.points = points; }

    /** ADDERS */

    /** @param prize add a prize to List<Prize> */
    public void addPrize(Prize prize){
        this.prizeList.add(prize);
    }

    /** @param achievement add a prize to List<RunnerAchievement> */
    public void addAchievement(RunnerAchievement achievement){
        this.achievementList.add(achievement);
    }

    /** @param lap add a lap to List<Laps> */
    public void addLap(Lap lap) {this.lapList.add(lap); }

    /** @param training add a training to List<Training> */
    public void addTraining(Training training) {
        this.trainingList.add(training);
    }

    /** DELETERS */

    /** @param prize remove a prize to List<Prize> */
    public void removePrize(Prize prize){
        this.prizeList.remove(prize);
    }

    /** @param achievement remove a prize to List<Achievement> */
    public void removeAchievement(RunnerAchievement achievement){
        this.achievementList.remove(achievement);
    }

    /** @param lap remove a lap to List<Laps> */
    public void removeLap(Lap lap) {this.lapList.remove(lap); }

    /** @param training remove a training to List<Training> */
    public void removeTraining(Training training) {
        this.trainingList.remove(training);
    }

    @Override
    public String toString() {
        return firstName+" "+lastName;
    }

    public List<String> getExportableFields() {
        List<String> fields = new ArrayList<>();
        if (type != null && type != PersonType.RUNNER) {
            if (type == PersonType.MANAGER) {
                fields.add("This person is a "+type.toString());
            } else {
                fields.add("This person is an "+type.toString());
            }
        }
        fields.add("First name: "+firstName);
        fields.add("Last name: "+lastName);
        fields.add("E-mail: "+eMail);
        if (phoneNumber != null && !phoneNumber.equals("null")) {
            fields.add("Phone: "+phoneNumber);
        }
        fields.add("Birthdate: "+birthDate.toString());
        return fields;
    }

    public List<String> getExportableFieldsForQueue() {
        List<String> fields = new ArrayList<>();
        fields.add("Name: "+firstName+" "+lastName);
        fields.add("E-mail: "+eMail);
        if (phoneNumber != null && !phoneNumber.equals("null")) {
            fields.add("Phone: "+phoneNumber);}
        return fields;
    }
}