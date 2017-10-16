package mapper;

import entity.Person;
import entity.Training;

/**
 * Singleton design pattern: map Training entities to-and-from the database.
 * Created on on 04/12/2016.
 * @version 1.1 Updated on 21/12/2016
 * @author Koen Van Kerckhoven
 * @author Giani-Luigi Driesen
 */

public enum TrainingMapper {
    UNIQUEMAPPER;

    private EntityMapper eMapper;
    private Training training = new Training();
    private final MapperConstants mc = MapperConstants.FINALCONSTANTS;

    /**
     * Use only one entity mapper for all mappers
     * @param entityMapper Applying the singleton design
     */
    public void setEntityMapper(EntityMapper entityMapper) { this.eMapper = entityMapper; }

    /**
     * Get a training object by its id
     * @param id The id of the Training to be found
     */
    public void getTraining(int id) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_TRAINING+"/"+id;
        eMapper.queryEntity(training, url);
    }

    /**
     * Get all trainings
     */
    public void getTrainings() {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_TRAINING+mc.MULTIPLE;
        eMapper.queryEntities(training, url);
    }

    /**
     * Get training objects by their runner
     * @param person The person of whom to get trainings
     */
    public void getTrainingsByPerson(Person person) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_TRAINING+mc.MULTIPLE+mc.BY+mc.TABLE_PERSON+"/"+person.getId();
        eMapper.queryEntities(training, url);
    }

    /**
     * Store a training (only non-nullable fields) in the database
     * @param training The Training object that needs to be stored
     */
    public void createTraining(Training training) {
        String url = mc.BASE_URL+mc.INSERT+mc.TABLE_TRAINING+"/"+training.getBeginDateTime()+"/"+training.getEndDateTime();
        eMapper.queryAnswerless(url);
    }

    /**
     * Update all non-nullable columns of a Training in the database
     * @param training The Training object with the new data
     */
    public void updateTraining(Training training) {
        String url = mc.BASE_URL+mc.UPDATE+mc.TABLE_TRAINING+"/"+training.getBeginDateTime()+"/"+training.getEndDateTime()+"/"+training.getId();
        eMapper.queryAnswerless(url);
    }

    /**
     * Update the nullable column "meetingPoint" of a Training in the database
     * @param training The Training object with the new data
     */
    public void updateTrainingLocation(Training training) {
        String url = mc.BASE_URL+mc.UPDATE+mc.TABLE_TRAINING+mc.LOCATION+"/"+training.getMeetingPoint().replace(' ', '_')+"/"+training.getId();
        eMapper.queryAnswerless(url);
    }

    /**
     * Update the nullable column "distance" of a Training in the database
     * @param training The Training object with the new data
     */
    public void updateTrainingDistance(Training training) {
        String url = mc.BASE_URL+mc.UPDATE+mc.TABLE_TRAINING+mc.DISTANCE+"/"+training.getDistance()+"/"+training.getId();
        eMapper.queryAnswerless(url);
    }

    /**
     * Delete a training from the database
     * @param id The id of the Training to be deleted
     */
    public void deleteTraining(int id) {
        String url = mc.BASE_URL+mc.DELETE+mc.TABLE_TRAINING+"/"+id;
        eMapper.queryAnswerless(url);
    }

    /**
     * Delete all trainings done by a certain person
     * ATTENTION: only actually deletes the rows in "person_has_training".
     * @param id The Person of whom to remove all trainings
     */
    public void deleteTrainingsByPerson(int id) {
        String url = mc.BASE_URL+mc.DELETE+mc.TABLE_TRAINING+mc.MULTIPLE+mc.BY+mc.TABLE_PERSON+"/"+id;
        eMapper.queryAnswerless(url);
    }
}
