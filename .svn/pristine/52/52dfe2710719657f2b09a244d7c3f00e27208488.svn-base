package mapper;

import java.util.List;

import entity.Person;
import entity.Team;

/**
 * Singleton design pattern: map Person entities to-and-from the database.
 * This mapper only returns Person entities. To properly initialize a Person you need
 * the other mappers to fill in each List of Lap, Prize, Achievement, and Training objects.
 * Created on on 01/12/2016.
 * @author Koen Van Kerckhoven
 * @author Giani-Luigi Driesen
 */
public enum TeamHasPersonMapper {
    UNIQUEMAPPER;

    private EntityMapper eMapper;
    private final MapperConstants mc = MapperConstants.FINALCONSTANTS;
    private List<Person> persons;

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
     * @param team   The Team object that needs to be stored
     */
    public void createTeamHasPerson(Person person, Team team) {
        String url = mc.BASE_URL + mc.INSERT + mc.TABLE_TEAM_HAS_PERSON +
                "/" + person.getId() + "/" + team.getId();
        eMapper.queryAnswerless(url);
    }

    /**
     * Delete a team_has_person entry from the database
     *
     * @param person The id of the Person to be deleted
     * @param team   The Team object that needs to be deleted
     */
    public void deleteTeamHasPerson(Person person, Team team) {
        String url = mc.BASE_URL + mc.DELETE + mc.TABLE_TEAM_HAS_PERSON +
                "/" + person.getId() + "/" + team.getId();
        eMapper.queryAnswerless(url);
    }

    public void getTeam_has_person(Person person) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_TEAM_HAS_PERSON+mc.MULTIPLE+mc.BY+mc.TABLE_PERSON+"/"+person.getId();
        System.out.println(url);
        eMapper.queryEntities(persons,url);
    }
}
