package mapper;

import entity.Person;
import entity.Person_has_prize;
import entity.Prize;

/**
 * Singleton design pattern: map Person entities to-and-from the database.
 * This mapper only returns Person entities. To properly initialize a Person you need
 * the other mappers to fill in each List of Lap, Prize, Achievement, and Training objects.
 * Created on on 01/12/2016.
 * @author Koen Van Kerckhoven
 * @author Giani-Luigi Driesen
 */
public enum PersonHasPrizeMapper {
    UNIQUEMAPPER;

    private EntityMapper eMapper;
    private final MapperConstants mc = MapperConstants.FINALCONSTANTS;
    private Person_has_prize person_has_prize = new Person_has_prize();

    /**
     * Use only one entity mapper for all mappers
     *
     * @param entityMapper Applying the singleton design
     */
    public void setEntityMapper(EntityMapper entityMapper) {
        this.eMapper = entityMapper;
    }

    /**
     * Store a team_has_person in the database
     *
     * @param person The Person object that needs to be stored
     * @param prize   The Team object that needs to be stored
     */
    public void createPersonHasPrize(Person person, Prize prize) {
        String url = mc.BASE_URL + mc.INSERT + mc.TABLE_PERSON_HAS_PRIZE +
                "/" + person.getId() + "/" + prize.getId() + "/" + 0;
        eMapper.queryAnswerless(url);
    }

    /**
     * Delete a person from the database
     *
     * @param person The id of the Person to be deleted
     * @param prize   The Team object that needs to be deleted
     */
    public void deletePersonHasPrize(Person person, Prize prize) {
        String url = mc.BASE_URL + mc.DELETE + mc.TABLE_PERSON_HAS_PRIZE +
                "/" + person.getId() + "/" + prize.getId();
        eMapper.queryAnswerless(url);
    }

    public void getPerson_has_prize() {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_PERSON_HAS_PRIZE+mc.MULTIPLE;
        eMapper.queryEntities(person_has_prize, url);
    }

    public void getPerson_has_prizesByPerson(Person person) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_PERSON_HAS_PRIZE+mc.MULTIPLE+mc.BY+mc.TABLE_PERSON+"/"+person.getId();
        eMapper.queryEntities(person_has_prize, url);
    }

    public void getPerson_has_prizesByPrize(Prize prize) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_PERSON_HAS_PRIZE+mc.MULTIPLE+mc.BY+mc.TABLE_PRIZE+"/"+prize.getId();
        eMapper.queryEntities(person_has_prize, url);
    }

    public void updatePerson_has_prize(Prize prize,Person person, int isRecieved) {
        String url = mc.BASE_URL+mc.UPDATE+mc.TABLE_PERSON_HAS_PRIZE+"/"+isRecieved+"/"+person.getId()+"/"+prize.getId();
        System.out.println(url);
        eMapper.queryEntities(person_has_prize, url);
    }
}
