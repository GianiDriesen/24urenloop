package mapper;

/**
 * Constants for the JSON API service
 * @author Koen Van Kerckhoven 20/12/2016
 */

public enum MapperConstants {
    FINALCONSTANTS;

    public final String BASE_URL = "http://api.a16_sd302.studev.groept.be/";

    // DB Tables
    public final String TABLE_LAP = "Lap";
    public final String TABLE_TEAM = "Team";
    public final String TABLE_PRIZE = "Prize";
    public final String TABLE_PERSON = "Person";
    public final String TABLE_TRAINING = "Training";
    public final String TABLE_ACHIEVEMENT = "Achievement";
    public final String TABLE_RUNNERQUEUE = "RunnerQueue";
    public final String TABLE_TEAM_HAS_PRIZE = "Team_has_prize";
    public final String TABLE_TEAM_HAS_PERSON = "Team_has_person";
    public final String TABLE_TEAM_HAS_ACHIEVEMENT = "Team_has_achievement";
    public final String TABLE_PERSON_HAS_PRIZE = "Person_has_prize";
    public final String TABLE_PERSON_HAS_TRAINING = "Person_has_training";
    public final String TABLE_PERSON_HAS_ACHIEVEMENT = "Person_has_achievement";

    // Interjection modifiers
    public final String MULTIPLE = "s";
    public final String SWAP = "Swap";
    public final String BY = "By";
    public final String AND = "And";
    public final String BEFORE = "Before";
    public final String AFTER = "After";
    public final String BETWEEN = "Between";
    public final String NEWEST = "Newest";

    // DB Columns
    public final String NAME = "Name";
    public final String EMAIL = "Email";
    public final String FACEBOOK = "Facebook";
    public final String GOOGLE = "Google";
    public final String PHONE = "Phone";
    public final String PHOTO = "Photo";
    public final String BEGIN_TIME = "BeginTime";
    public final String END_TIME = "EndTime";
    public final String TIMES = "Times";
    public final String YEAR = "Year";
    public final String LOCATION = "Location";
    public final String DISTANCE = "Distance";
    public final String RUNNERS = "Runners";

    // DB operations
    public final String SELECT = "select";
    public final String INSERT = "insert";
    public final String UPDATE = "update";
    public final String DELETE = "delete";
}
