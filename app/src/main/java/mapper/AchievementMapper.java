package mapper;

import entity.Team;
import entity.Person;
import entity.Achievement;
import entity.RunnerAchievement;

/**
 * Singleton design pattern: map Achievement entities to-and-from the database.
 * Created on on 04/12/2016.
 * @author Koen Van Kerckhoven
 * @author Giani-Luigi Driesen
 */

public enum AchievementMapper {
    UNIQUEMAPPER;

    private EntityMapper eMapper;
    private final MapperConstants mc = MapperConstants.FINALCONSTANTS;
    private Achievement achievement = new RunnerAchievement(); // Because we are only testing the "instanceof", it doesn't matter which implementation here.

    /**
     * Use only one entity mapper for all mappers
     * @param entityMapper Applying the singleton design
     */
    public void setEntityMapper(EntityMapper entityMapper) { this.eMapper = entityMapper; }

    /**
     * Get an achievement object by its id
     * @param id The id of the Achievement to be found
     */
    public void getAchievement(int id) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_ACHIEVEMENT+"/"+id;
        eMapper.queryEntity(achievement, url);
    }

    /**
     * Get all achievements
     */
    public void getAchievements() {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_ACHIEVEMENT+mc.MULTIPLE;
        eMapper.queryEntities(achievement, url);
    }

    /**
     * Get achievement implementation(s) by their name (It can be any implementation)
     * @param name The name of the Achievement implementation to be found
     */
    public void getAchievementsByName(String name) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_ACHIEVEMENT+mc.MULTIPLE+mc.BY+mc.NAME+"/"+name.replace(' ', '_');
        eMapper.queryEntities(achievement, url);
    }

    /**
     * Get achievement implementation(s) by the Person that achieved them
     * @param person The Person of whom to find RunnerAchievements
     */
    public void getAchievementsByPerson(Person person) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_ACHIEVEMENT+mc.MULTIPLE+mc.BY+mc.TABLE_PERSON+"/"+person.getId();
        eMapper.queryEntities(achievement, url);
    }

    /**
     * Get achievement implementation(s) by the Team that achieved them
     * @param team The Team of whom to find TeamAchievements
     */
    public void getAchievementsByTeam(Team team) {
        String url = mc.BASE_URL+mc.SELECT+mc.TABLE_ACHIEVEMENT+mc.MULTIPLE+mc.BY+mc.TABLE_TEAM+"/"+team.getId();
        eMapper.queryEntities(achievement, url);
    }

    /**
     * Store an achievement in the database
     * @param achievement The Achievement implementation that needs to be stored
     */
    public void insertAchievement(Achievement achievement) {
        String name = achievement.getName().replace(' ', '_');
        String description = achievement.getDescription().replace(' ', '_');
        String url = mc.BASE_URL+mc.INSERT+mc.TABLE_ACHIEVEMENT+"/"+
                name+"/"+description+"/"+achievement.printType();
        eMapper.queryAnswerless(url);
    }

    /**
     * Update all non-nullable columns of a Achievement in the database
     * @param achievement The Achievement object with the new data
     */
    public void updateAchievement(Achievement achievement) {
        String name = achievement.getName().replace(' ', '_');
        String description = achievement.getDescription().replace(' ', '_');
        String url = mc.BASE_URL+mc.UPDATE+mc.TABLE_ACHIEVEMENT+"/"+name+"/"+description+"/"+achievement.printType()+"/"+achievement.getId();
        eMapper.queryAnswerless(url);
    }

    /**
     * Delete an achievement from the database
     * @param id The id of the Achievement to be deleted
     */
    public void deleteAchievement(int id) {
        String url = mc.BASE_URL+mc.DELETE+mc.TABLE_ACHIEVEMENT+"/"+id;
        eMapper.queryAnswerless(url);
    }

    /**
     * Delete all achievements attained by a certain person
     * ATTENTION: only actually deletes the rows in "person_has_achievement".
     * @param id The Person of whom to remove all achievements
     */
    public void deleteAchievementsByPerson(int id) {
        String url = mc.BASE_URL+mc.DELETE+mc.TABLE_ACHIEVEMENT+mc.MULTIPLE+mc.BY+mc.TABLE_PERSON+"/"+id;
        eMapper.queryAnswerless(url);
    }

    /**
     * Delete all achievements attained by a certain team
     * ATTENTION: only actually deletes the rows in "team_has_achievement".
     * @param team The Team of whom to remove all achievements
     */
    public void deleteAchievementsByTeam(Team team) {
        String url = mc.BASE_URL+mc.DELETE+mc.TABLE_ACHIEVEMENT+mc.MULTIPLE+mc.BY+mc.TABLE_TEAM+"/"+team.getId();
        eMapper.queryAnswerless(url);
    }
}
