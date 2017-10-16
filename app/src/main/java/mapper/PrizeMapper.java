package mapper;

import entity.Person;
import entity.Prize;

/**
 * Singleton design pattern: map Prize entities to-and-from the database.
 * Created on on 04/12/2016.
 * @author Koen Van Kerckhoven
 * @author Giani-Luigi Driesen
 */

public enum PrizeMapper {
    UNIQUEMAPPER;

    private Prize prize = new Prize();
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
     * Get a prize object by its id
     * @param id The id of the Prize to be found
     */
    public void getPrize(int id) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_PRIZE+"/"+id;
        eMapper.queryEntity(prize, url);
    }

    /**
     * Get a prize object by its name and year
     * @param name The prize name
     * @param year The year of the Prize to be found
     */
    public void getPrizeByNameAndYear(String name, int year) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_PRIZE+mc.BY+mc.NAME+mc.AND+mc.YEAR+"/"+name.replace(' ', '_')+"/"+year;
        eMapper.queryEntity(prize, url);
    }

    /**
     * Get all prize objects
     */
    public void getPrizes() {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_PRIZE+mc.MULTIPLE;
        eMapper.queryEntities(prize, url);
    }

    /**
     * Get all prize objects with a certain name
     * @param name The name of the prizes
     */
    public void getPrizesByName(String name) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_PRIZE+mc.MULTIPLE+mc.BY+mc.NAME+"/"+name.replace(' ', '_');
        eMapper.queryEntities(prize, url);
    }

    /**
     * Get all prize objects in a certain year
     * @param year The year of the prizes
     */
    public void getPrizesByYear(int year) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_PRIZE+mc.MULTIPLE+mc.BY+mc.YEAR+"/"+year;
        eMapper.queryEntities(prize, url);
    }

    /**
     * Get all prizes already achieved by a certain person
     * @param person The person for which to get all prizes
     */
    public void getPrizesByPerson(Person person) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_PRIZE+mc.MULTIPLE+mc.BY+mc.TABLE_PERSON+"/"+person.getId();
        eMapper.queryEntities(prize, url);
    }

    /**
     * Store a prize in the database
     * @param prize The Prize object that needs to be stored
     */
    public void insertPrize(Prize prize) {
        String url = mc.BASE_URL+mc.INSERT+mc.TABLE_PRIZE+"/"+prize.getYear()+"/"+prize.getPointCost()+"/"+prize.getName().replace(' ', '_')+"/"+prize.getDescription().replace(' ', '_');
        eMapper.queryAnswerless(url);
    }

    /**
     * Update all columns of a Prize in the database
     * @param prize The Prize object with the new data
     */
    public void updatePrize(Prize prize) {
        String url = mc.BASE_URL+mc.UPDATE+mc.TABLE_PRIZE+"/"+prize.getYear()+"/"+prize.getPointCost()+"/"+prize.getName().replace(' ', '_')+"/"+prize.getDescription().replace(' ', '_')+"/"+prize.getId();
        eMapper.queryAnswerless(url);
    }

    /**
     * Delete a prize from the database
     * @param id The id of the Prize to be deleted
     */
    public void deletePrize(int id) {
        String url = mc.BASE_URL+mc.DELETE+mc.TABLE_PRIZE+"/"+id;
        eMapper.queryAnswerless(url);
    }

    /**
     * Delete all prizes attained by a certain person
     * ATTENTION: only actually deletes the rows in "person_has_prize".
     * @param id The Person of whom to remove all prizes
     */
    public void deletePrizesByPerson(int id) {
        String url = mc.BASE_URL+mc.DELETE+mc.TABLE_PRIZE+mc.MULTIPLE+mc.BY+mc.TABLE_PERSON+"/"+id;
        eMapper.queryAnswerless(url);
    }
}
