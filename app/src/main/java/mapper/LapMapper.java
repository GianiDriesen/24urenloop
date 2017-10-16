package mapper;

import java.sql.Timestamp;
import entity.Lap;
import entity.Person;

/**
 * Singleton design pattern: map Lap entities to-and-from the database.
 * Created on on 04/12/2016.
 * @version 1.1 21/12/2016
 * @author Koen Van Kerckhoven
 * @author Giani-Luigi Driesen
 */
public enum LapMapper {
    UNIQUEMAPPER;

    private Lap lap = new Lap();
    private EntityMapper eMapper;
    private final MapperConstants mc = MapperConstants.FINALCONSTANTS;

    /**
     * Use only one entity mapper for all mappers
     * @param entityMapper Applying the singleton design
     */
    public void setEntityMapper(EntityMapper entityMapper) {
        this.eMapper = entityMapper;
    }

    /**
     * Get a lap object by its id
     * @param id The id of the Lap to be found
     */
    public void getLap(int id) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_LAP+"/"+id;
        eMapper.queryEntity(lap, url);
    }

    /**
     * Get the newest lap object
     */
    public void getLapNewest() {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_LAP+mc.NEWEST;
        eMapper.queryEntity(lap, url);
    }

    /**
     * Get all lap objects
     */
    public void getLaps() {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_LAP+mc.MULTIPLE;
        eMapper.queryEntities(lap, url);
    }

    /**
     * Get lap objects by their runner
     * @param person The Person that ran the laps
     */
    public void getLapsByPerson(Person person) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_LAP+mc.MULTIPLE+mc.BY+mc.TABLE_PERSON+"/"+person.getId();
        eMapper.queryEntities(lap, url);
    }

    /**
     * Get lap objects starting after a certain point in time
     * @param ts Get all laps beginning after this timestamp
     */
    public void getLapsAfterBeginTime(Timestamp ts) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_LAP+mc.MULTIPLE+mc.AFTER+mc.BEGIN_TIME+"/"+ts;
        eMapper.queryEntities(lap, url);
    }

    /**
     * Get lap objects starting before a certain point in time
     * @param ts Get all laps beginning before this timestamp
     */
    public void getLapsBeforeBeginTime(Timestamp ts) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_LAP+mc.MULTIPLE+mc.BEFORE+mc.BEGIN_TIME+"/"+ts;
        url = url.replace(' ','_');
        eMapper.queryEntities(lap, url);
    }

    /**
     * Get lap objects in between two moments in time
     * @param ts1 Get all laps beginning after this date ..
     * @param ts2 .. but beginning before this date.
     */
    public void getLapsBetweenDates(Timestamp ts1, Timestamp ts2) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_LAP+mc.MULTIPLE+mc.BETWEEN+mc.TIMES+"/"+ts1+"/"+ts2;
        url = url.replace(' ','_');
        eMapper.queryEntities(lap, url);
    }

    /**
     * Store a lap in the database
     * @param lap The Lap object that needs to be stored
     */
    public void insertLap(Lap lap) {
        String url = mc.BASE_URL+mc.INSERT+mc.TABLE_LAP+"/"+lap.getBeginDateTime()+"/"+lap.getEndDateTime()+"/"+lap.getPersonId();
        url = url.replace(' ','_');
        eMapper.queryAnswerless(url);
    }

    /**
     * Update all columns of a Lap in the database
     * @param lap The Lap object with the new data
     */
    public void updateLap(Lap lap) {
        String url = mc.BASE_URL+mc.UPDATE+mc.TABLE_LAP+"/"+lap.getBeginDateTime()+"/"+lap.getEndDateTime()+"/"+lap.getPersonId()+"/"+lap.getId();
        url = url.replace(' ','_');
        eMapper.queryAnswerless(url);
    }

    /**
     * Update only the soft-nullable column "person_id" of a Lap in the database
     * @param lap The Lap object with the new data
     */
    public void updateLapPerson(Lap lap) {
        String url = mc.BASE_URL+mc.UPDATE+mc.TABLE_LAP+mc.TABLE_PERSON+"/"+lap.getPersonId()+"/"+lap.getId();
        eMapper.queryAnswerless(url);
    }

    /**
     * Delete a lap from the database
     * @param id The id of the Lap to be deleted
     */
    public void deleteLap(int id) {
        String url = mc.BASE_URL+mc.DELETE+mc.TABLE_LAP+"/"+id;
        eMapper.queryAnswerless(url);
    }

    /**
     * Delete all laps from the database done by a certain person
     * @param person The Person of whom to delete all Laps
     */
    public void deleteLapsByPerson(Person person) {
        String url = mc.BASE_URL+mc.DELETE+mc.TABLE_LAP+mc.MULTIPLE+mc.BY+mc.TABLE_PERSON+"/"+person.getId();
        eMapper.queryAnswerless(url);
    }
}