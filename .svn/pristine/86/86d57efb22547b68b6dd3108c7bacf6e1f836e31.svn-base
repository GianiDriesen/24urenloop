package mapper;


import entity.Achievement;
import entity.Person;
import entity.Prize;
import entity.Team;
import entity.Training;

/**
 * Singleton design pattern: map Person entities to-and-from the database.
 * This mapper only returns Person entities. To properly initialize a Person you need
 * the other mappers to fill in each List of Lap, Prize, Achievement, and Training objects.
 * Created on on 01/12/2016.
 * @author Koen Van Kerckhoven
 * @author Giani-Luigi Driesen
 */
public enum PersonMapper {
    UNIQUEMAPPER;

    private Person person = new Person();
    private EntityMapper eMapper;
    private final MapperConstants mc = MapperConstants.FINALCONSTANTS;

    /**
     * Use only one entity mapper for all mappers
     * @param entityMapper Applying the singleton design
     */
    public void setEntityMapper(EntityMapper entityMapper) { this.eMapper = entityMapper; }

    /**
     * Get a person object by its id
     * @param id The id of the Person to be found
     */
    public void getPerson(int id) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_PERSON+"/"+id;
        eMapper.queryEntity(person, url);
    }

    /** Get all runner details for people in the Runner queue */
    public void getRunnerQueue() {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_RUNNERQUEUE;
        eMapper.queryRunnerQueue(url);
    }

    /**
     * Add a new row to the Runner queue with this person's ID
     * @param person_id The ID of the person to add to the Runner Queue.
     */
    public void insertRunnerQueue(int person_id) {
        String url = mc.BASE_URL+mc.INSERT+mc.TABLE_RUNNERQUEUE+"/"+person_id;
        eMapper.queryAnswerless(url);
    }

    /**
     * Swaps the order of two runners in the Runner queue; direction of swap is irrelevant
     * Actually performs three queries on the queue ID's in table runnerqueue:
     * 1. Sets row with qid1 = -1
     * 2. Sets row with qid2 = qid1
     * 3. Sets row with -1 = qid2
     * @param qid1 Queue ID of runner "one"
     * @param qid2 Queue ID of runner "two"
     */
    public void swapRunners(int qid1, int qid2) {
        String url = mc.BASE_URL+mc.UPDATE+mc.TABLE_RUNNERQUEUE+mc.SWAP+mc.RUNNERS+"/"+qid1+"/"+qid1+"/"+qid2+"/"+qid2;
        eMapper.queryAnswerless(url);
    }

    /** Delete a row fom the Runner queue */
    public void deleteRunner(int qid) {
        String url = mc.BASE_URL+mc.DELETE+mc.TABLE_RUNNERQUEUE+"/"+qid;
        eMapper.queryAnswerless(url);
    }

    /**
     * Get a person object by its email
     * @param email The email of the Person to be found
     */
    public void getPersonByEmail(String email) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_PERSON+mc.BY+mc.EMAIL+"/"+email;
        eMapper.queryEntity(person, url);
    }

    public void getPersons() {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_PERSON+mc.MULTIPLE;
        eMapper.queryEntities(person, url);
    }

    /**
     * Get person objects by their name (firstName + lastName)
     * ATTENTION: Uses SQL "LIKE" instead of "=".
     * @param firstName The first name of the Person to be found
     * @param lastName The last name of the Person to be found
     */
    public void getPersonsByName(String firstName, String lastName) {
        firstName = firstName.replace(' ', '_');
        lastName = lastName.replace(' ', '_');
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_PERSON+mc.MULTIPLE+mc.BY+mc.NAME+"/"+firstName+"/"+lastName;
        eMapper.queryEntities(person, url);
    }

    public void getPersonByFacebook(String id) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_PERSON+mc.BY+mc.FACEBOOK+"/"+id;
        eMapper.queryEntity(person, url);
    }

    public void getPersonByGoogle(String id) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_PERSON+mc.BY+mc.GOOGLE+"/"+id;
        eMapper.queryEntity(person, url);
    }

    /**
     * Get all persons that have done a certain Training.
     * REQUIRES the correct training_id
     * @param training The training that the Persons have done
     */
    public void getPersonsByTraining(Training training) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_PERSON+mc.MULTIPLE+mc.BY+mc.TABLE_TRAINING+"/"+training.getId();
        eMapper.queryEntities(person, url);
    }

    /**
     * Get all persons that have gotten a certain Achievement.
     * REQUIRES the correct achievement_id
     * @param achievement The achievement that the Persons have achieved
     */
    public void getPersonsByAchievement(Achievement achievement) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_PERSON+mc.MULTIPLE+mc.BY+mc.TABLE_ACHIEVEMENT+"/"+achievement.getId();
        eMapper.queryEntities(person, url);
    }

    /**
     * Get all persons that are a part of a certain Team.
     * REQUIRES the correct team_id
     * @param team The team that the Persons have run for
     */
    public void getPersonsByTeam(Team team) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_PERSON+mc.MULTIPLE+mc.BY+mc.TABLE_TEAM+"/"+team.getId();
        eMapper.queryEntities(person, url);
    }

    /**
     * Get all persons that have received a certain prize
     * @param prize The prize that each Person needs to have received
     */
    public void getPersonsByPrize(Prize prize) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_PERSON+mc.MULTIPLE+mc.BY+mc.TABLE_PRIZE+"/"+prize.getId();
        eMapper.queryEntities(person, url);
    }

    /**
     * Store a person in the database
     * @param person The Person object that needs to be stored
     */
    public void createPerson(Person person) {
        String url = mc.BASE_URL+mc.INSERT+mc.TABLE_PERSON+
                "/"+person.getFirstName().replace(' ', '_')+"/"+person.getLastName().replace(' ', '_')+
                "/"+person.geteMail()+"/"+person.getBirthDate()+"/"+person.getType()+
                "/"+person.getNotificationSetting()+"/"+person.getEmailSetting();
        eMapper.queryAnswerless(url);
    }

    /**
     * Update all non-nullable columns of a Person in the database
     * @param person The Person object with the new data
     */
    public void updatePerson(Person person) {
        String url = mc.BASE_URL+mc.UPDATE+mc.TABLE_PERSON+
                "/"+person.getFirstName().replace(' ', '_')+"/"+person.getLastName().replace(' ', '_')+
                "/"+person.geteMail()+"/"+person.getBirthDate()+"/"+person.getType()+
                "/"+person.getNotificationSetting()+"/"+person.getEmailSetting()+"/"+person.getId();
        System.out.println(url);
        eMapper.queryAnswerless(url);
    }

    /**
     * Update the nullable column "facebookID" of a Person in the database
     * @param person The Person object with the new data
     */
    public void updatePersonFacebook(Person person) {
        String url = mc.BASE_URL+mc.UPDATE+mc.TABLE_PERSON+mc.FACEBOOK+"/"+person.getFacebookId()+"/"+person.getId();
        eMapper.queryAnswerless(url);
    }

    /**
     * Update the nullable column "googleID" of a Person in the database
     * @param person The Person object with the new data
     */
    public void updatePersonGoogle(Person person) {
        String url = mc.BASE_URL+mc.UPDATE+mc.TABLE_PERSON+mc.GOOGLE+"/"+person.getGoogleId()+"/"+person.getId();
        eMapper.queryAnswerless(url);
    }

    /**
     * Update the nullable column "phoneNumber" of a Person in the database
     * @param person The Person object with the new data
     */
    public void updatePersonPhone(Person person) {
        String url = mc.BASE_URL+mc.UPDATE+mc.TABLE_PERSON+mc.PHONE+"/"+person.getPhoneNumber()+"/"+person.getId();
        eMapper.queryAnswerless(url);
    }

    /**
     * Delete a person from the database
     * @param id The id of the Person to be deleted
     */
    public void deletePerson(int id) {
        String url = mc.BASE_URL+mc.DELETE+mc.TABLE_PERSON+"/"+id;
        eMapper.queryAnswerless(url);
        deletePersonRows(id);
    }

    /**
     * Delete a person from the database
     * @param email E-mail of the Person to be deleted
     */
    public void deletePersonByEmail(String email) {
        String url = mc.BASE_URL+mc.DELETE+mc.TABLE_PERSON+mc.BY+mc.EMAIL+"/"+email;
        eMapper.queryAnswerless(url);
    }

    private void deletePersonRows(int id) {
        eMapper.getaMapper().deleteAchievementsByPerson(id);
        eMapper.getPrizeMapper().deletePrizesByPerson(id);
        eMapper.getTrainingMapper().deleteTrainingsByPerson(id);
        String url = mc.BASE_URL+mc.DELETE+mc.TABLE_TEAM_HAS_PERSON+mc.BY+mc.TABLE_PERSON+"/"+id;
        eMapper.queryAnswerless(url);
    }

    /**
     * Delete all rows in team_has_person for this team
     * @param team The team of which to remove all rows in team_has_person
     */
    public void deletePersonsByTeam(Team team) {
        String url = mc.BASE_URL+mc.DELETE+mc.TABLE_PERSON+mc.MULTIPLE+mc.BY+mc.TABLE_TEAM+"/"+team.getId();
        eMapper.queryAnswerless(url);
    }
}
