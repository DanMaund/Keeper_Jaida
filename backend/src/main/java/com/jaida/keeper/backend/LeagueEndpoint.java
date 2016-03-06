package com.jaida.keeper.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.googlecode.objectify.ObjectifyService;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Result;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "keeperApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.backendtest.admin.example.com",
                ownerName = "backend.backendtest.admin.example.com",
                packagePath = ""
        )
)
public class LeagueEndpoint {

    private static final Logger logger = Logger.getLogger(LeagueEndpoint
            .class.getName());

    Gson gson = new Gson();

    static {
// Typically you would register this inside an OfyService wrapper. See:
// https://code.google.com/p/objectify-appengine/wiki/BestPractice
        ObjectifyService.register(KeeperUser.class);
        ObjectifyService.register(LeagueMatch.class);
        ObjectifyService.register(LeagueMatchScore.class);
        ObjectifyService.register(LeagueTeam.class);
        ObjectifyService.register(LeagueGroup.class);
        ObjectifyService.register(LeagueActivity.class);
        ObjectifyService.register(LeagueStake.class);
        ObjectifyService.register(League.class);
    }

    @ApiMethod(name = "makeTestLeague")
    public void makeTestLeague() {

        KeeperUser dan = new KeeperUser("Dan");
        KeeperUser anton = new KeeperUser("Anton");
        KeeperUser jamie = new KeeperUser("Jamie");
        KeeperUser bone = new KeeperUser("Bone");
        KeeperUser matt = new KeeperUser("Matt");
        ofy().save().entities(dan, anton, jamie, bone, matt).now();

        League league = new League("Lukin Fooz");
        ofy().save().entities(league).now();

        LeagueActivity fooz = new LeagueActivity(league.getKey(), "Fooz");
        ofy().save().entity(fooz).now();

        LeagueGroup theBoiz = new LeagueGroup(league.getKey(), "The Boiz");
        ofy().save().entity(theBoiz).now();

        LeagueTeam danton = new LeagueTeam(league.getKey(), "DanTon");
        danton.addUser(dan.getKey());
        danton.addUser(anton.getKey());
        LeagueTeam theMatts = new LeagueTeam(league.getKey(), "TheMatts");
        theMatts.addUser(bone.getKey());
        theMatts.addUser(matt.getKey());
        LeagueTeam dreamTeam = new LeagueTeam(league.getKey(), "DreamTeam");
        dreamTeam.addUser(jamie.getKey());
        dreamTeam.addUser(dan.getKey());
        LeagueTeam janton = new LeagueTeam(league.getKey(), "Janton");
        janton.addUser(jamie.getKey());
        janton.addUser(anton.getKey());
        LeagueTeam soloDan = new LeagueTeam(league.getKey(), "Solo Dan");
        soloDan.addUser(dan.getKey());
        ofy().save().entities(danton, theMatts, dreamTeam, janton, soloDan)
                .now();

        LeagueStake rioStake = new LeagueStake(league.getKey(), "=", 10,
                "Rio");
        LeagueStake fredStake = new LeagueStake(league.getKey(), "=", 9,
                "Freddo");
        ofy().save().entities(rioStake, fredStake);

        LeagueMatch match1 = new LeagueMatch(league.getKey());
        LeagueMatch match2 = new LeagueMatch(league.getKey());
        LeagueMatch match3 = new LeagueMatch(league.getKey());
        LeagueMatch match4 = new LeagueMatch(league.getKey());
        ofy().save().entities(match1, match2, match3, match4).now();

        LeagueMatchScore match1score1 = new LeagueMatchScore(match1.getKey(),
                danton.getKey(), 10);
        LeagueMatchScore match1score2 = new LeagueMatchScore(match1.getKey(),
                theMatts.getKey(), 0);
        LeagueMatchScore match2score1 = new LeagueMatchScore(match2.getKey(),
                theMatts.getKey(), 1);
        LeagueMatchScore match2score2 = new LeagueMatchScore(match2.getKey(),
                dreamTeam.getKey(), 9);
        LeagueMatchScore match3score1 = new LeagueMatchScore(match3.getKey(),
                janton.getKey(), 5);
        LeagueMatchScore match3score2 = new LeagueMatchScore(match3.getKey(),
                soloDan.getKey(), 5);
        LeagueMatchScore match4score1 = new LeagueMatchScore(match4.getKey(),
                soloDan.getKey(), 20);
        ofy().save().entities(match1score1, match1score2, match2score1,
                match2score2, match3score1, match3score2, match4score1).now();

    }

    @ApiMethod(name = "addScores")
    public List<LeagueMatchScore> addScores(@Named("leagueId") Long leagueId,
                                            @Named("matchId") Long matchId,
                                            Map<Long, Integer> teamScores,
                                            @Named("sync") Boolean sync) {

        // Because endpoints can't pass Key or Ref types, we have to rebuild
        // our target parent key here.
        Key<League> leagueKey = Key.create(League.class, leagueId);
        Key<LeagueMatch> matchKey = Key.create(leagueKey, LeagueMatch.class,
                matchId);

        List<LeagueMatchScore> scores = new ArrayList<>();

        Iterator it = teamScores.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            Long teamId = (Long) pair.getKey();
            Integer score = (Integer) pair.getValue();

            Key<LeagueTeam> teamKey = Key.create(leagueKey, LeagueTeam.class,
                    teamId);

            LeagueMatchScore matchScore = new LeagueMatchScore(matchKey, teamKey,
                    score);

            scores.add(matchScore);

            it.remove(); // avoids a ConcurrentModificationException

        }

        Result<Map<Key<LeagueMatchScore>, LeagueMatchScore>> result = ofy()
                .save().entities(scores);
        if (sync) {
            result.now();
        }
        logger.info(gson.toJson(scores));

        return scores;
    }


    @ApiMethod(name = "addScore")
    public LeagueMatchScore addScore(@Named("leagueId") Long leagueId,
                                     @Named("matchId") Long matchId,
                                     @Named("teamId") Long teamId,
                                     @Named("score") Integer score,
                                     @Named("sync") Boolean sync) {

        // Because endpoints can't pass Key or Ref types, we have to rebuild
        // our target parent key here.
        Key<League> leagueKey = Key.create(League.class, leagueId);
        Key<LeagueMatch> matchKey = Key.create(leagueKey, LeagueMatch.class,
                matchId);
        Key<LeagueTeam> teamKey = Key.create(leagueKey, LeagueTeam.class,
                teamId);

        LeagueMatchScore matchScore = new LeagueMatchScore(matchKey, teamKey,
                score);

        Result<Key<LeagueMatchScore>> result = ofy().save().entity(matchScore);
        if (sync) {
            result.now();
        }
        logger.info(gson.toJson(matchScore));

        return matchScore;
    }

    @ApiMethod(name = "addMatch")
    public LeagueMatch addMatch(@Named("leagueId") Long leagueId,
                                @Named("sync") Boolean sync) {

        // Because endpoints can't pass Key or Ref types, we have to rebuild
        // our target parent key here.
        Key<League> league = Key.create(League.class, leagueId);

        LeagueMatch leagueMatch = new LeagueMatch(league);

        Result<Key<LeagueMatch>> result = ofy().save().entity(leagueMatch);
        if (sync) {
            result.now();
        }
        logger.info(gson.toJson(leagueMatch));

        return leagueMatch;
    }

    @ApiMethod(name = "makeKeeperUser")
    public KeeperUser makeKeeperUser(@Named("name") String name,
                                     @Named("sync") Boolean sync) {
        KeeperUser keeperUser = new KeeperUser(name);

        Result<Key<KeeperUser>> result = ofy().save().entity(keeperUser);
        if (sync) {
            result.now();
        }
        logger.info(gson.toJson(keeperUser));

        return keeperUser;
    }

    @ApiMethod(name = "getKeeperUser")
    public KeeperUser getKeeperUser(@Named("id") Long id) {

        KeeperUser keeperUser =  ofy().load().type(KeeperUser.class).id(id).now();
        logger.info(gson.toJson(keeperUser));

        return keeperUser;
    }

    @ApiMethod(name = "makeLeagueTeam")
    public LeagueTeam makeLeagueTeam(@Named("leagueId") Long leagueId,
                                     @Named("name") String name,
                                     @Named("sync") Boolean sync) {
        // Because endpoints can't pass Key or Ref types, we have to rebuild
        // our target parent key here.
        Key<League> league = Key.create(League.class, leagueId);
        LeagueTeam team = new LeagueTeam(league, name);
        Result<Key<LeagueTeam>> result = ofy().save().entity(team);
        if (sync) {
            result.now();
        }
        logger.info(gson.toJson(team));

        return team;
    }

    @ApiMethod(name = "addUserToTeam")
    public void addUserToTeam(@Named("leagueId") Long leagueId,
                              @Named("teamId") Long teamId,
                              @Named("userId") Long userId,
                              @Named("sync") Boolean sync) {

        // Because endpoints can't pass Key or Ref types, we have to rebuild
        // our target parent key here.
        Key<League> leagueKey = Key.create(League.class, leagueId);
        Key<LeagueTeam> teamKey = Key.create(leagueKey, LeagueTeam.class,
                teamId);
        Key<KeeperUser> userKey = Key.create(KeeperUser.class, userId);

        //TODO: Validate user is in LeagueGroup.

        LeagueTeam team = ofy().load().key(teamKey).now();
        logger.info(gson.toJson(team));

        team.addUser(userKey);
        Result<Key<LeagueTeam>> result = ofy().save().entity(team);
        if (sync) {
            result.now();
        }
        logger.info(gson.toJson(team));

    }

    @ApiMethod(name = "addUsersToTeam")
    public void addUsersToTeam(@Named("leagueId") Long leagueId,
                               @Named("teamId") Long teamId,
                               @Named("userIds") List<Long> userIds,
                               @Named("sync") Boolean sync) {

        // Because endpoints can't pass Key or Ref types, we have to rebuild
        // our target parent key here.
        Key<League> leagueKey = Key.create(League.class, leagueId);
        Key<LeagueTeam> teamKey = Key.create(leagueKey, LeagueTeam.class,
                teamId);

        LeagueTeam team = ofy().load().key(teamKey).now();

        for (int i=0; i<userIds.size(); i++)
        {
            Long userId = userIds.get(i);
            Key<KeeperUser> userKey = Key.create(KeeperUser.class, userId);
            //TODO: Validate user is in LeagueGroup.

            team.addUser(userKey);
        }

        Result<Key<LeagueTeam>> result = ofy().save().entity(team);
        if (sync) {
            result.now();
        }

    }

    @ApiMethod(name = "makeLeagueGroup")
    public LeagueGroup makeLeagueGroup(@Named("leagueId") Long leagueId,
                                       @Named("name") String name,
                                       @Named("sync") Boolean sync) {

        // Because endpoints can't pass Key or Ref types, we have to rebuild
        // our target parent key here.
        Key<League> league = Key.create(League.class, leagueId);
        LeagueGroup group = new LeagueGroup(league, name);
        Result<Key<LeagueGroup>> result = ofy().save().entity(group);
        if (sync) {
            result.now();
        }
        logger.info(gson.toJson(group));

        return group;
    }

    @ApiMethod(name = "getLeagueGroup")
    public LeagueGroup getLeagueGroup(@Named("leagueId") Long leagueId){

        Key<League> league = Key.create(League.class, leagueId);
        LeagueGroup group = ofy().load().type(LeagueGroup.class)
                .ancestor(league).first().now();
        logger.info(gson.toJson(group));

        return group;
    }

    @ApiMethod(name = "makeLeagueActivity")
    public LeagueActivity makeLeagueActivity(@Named("leagueId") Long leagueId,
                                             @Named("name") String name,
                                             @Named("sync") Boolean sync) {

        // Because endpoints can't pass Key or Ref types, we have to rebuild
        // our target parent key here.
        Key<League> league = Key.create(League.class, leagueId);
        LeagueActivity activity = new LeagueActivity(league, name);
        Result<Key<LeagueActivity>> result = ofy().save().entity(activity);
        if (sync) {
            result.now();
        }
        logger.info(gson.toJson(activity));

        return activity;
    }

    @ApiMethod(name = "getLeagueActivity")
    public LeagueActivity getLeagueActivity(@Named("leagueId") Long leagueId){

        Key<League> league = Key.create(League.class, leagueId);
        LeagueActivity activity = ofy().load().type(LeagueActivity.class)
                .ancestor(league).first().now();
        logger.info(gson.toJson(activity));

        return activity;
    }

    @ApiMethod(name = "makeLeague")
    public League makeLeague(@Named("name") String name,
                             @Named("sync") Boolean sync) {

        League league = new League(name);
        Result<Key<League>> result = ofy().save().entity(league);
        if (sync) {
            result.now();
        }
        logger.info(gson.toJson(league));

        return league;

    }

    @ApiMethod(name = "getLeague")
    public League getLeague(@Named("id") Long id) {

        League league =  ofy().load().type(League.class).id(id).now();
        logger.info(gson.toJson(league));

        return league;
    }




}