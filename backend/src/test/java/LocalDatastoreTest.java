/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// [START LocalDatastoreTest]

import com.jaida.keeper.backend.League;
import com.jaida.keeper.backend.LeagueActivity;
import com.jaida.keeper.backend.LeagueGroup;
import com.jaida.keeper.backend.LeagueMatch;
import com.jaida.keeper.backend.LeagueMatchScore;
import com.jaida.keeper.backend.LeagueStake;
import com.jaida.keeper.backend.LeagueTeam;
import com.jaida.keeper.backend.KeeperUser;

import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;

import static com.googlecode.objectify.ObjectifyService.ofy;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LocalDatastoreTest {

    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    Logger log = Logger.getLogger("LocalDatastoreTest");
    Gson gson = new Gson();

    Closeable session;

    @Before
    public void setUp() {
        helper.setUp();
        session = ObjectifyService.begin();
        ObjectifyService.register(KeeperUser.class);
        ObjectifyService.register(LeagueActivity.class);
        ObjectifyService.register(LeagueGroup.class);
        ObjectifyService.register(LeagueStake.class);
        ObjectifyService.register(LeagueTeam.class);
        ObjectifyService.register(LeagueMatch.class);
        ObjectifyService.register(LeagueMatchScore.class);
        ObjectifyService.register(League.class);
    }

    @After
    public void tearDown() {
        session.close();
        helper.tearDown();
    }

    @Test
    public void megaTest() {


        // Create a league!
        League league = new League("TestLeague");
        Key<League> leagueKey = ofy().save().entity(league).now();
        assertNotNull(leagueKey);

        // Create some users!
        KeeperUser damKeeperUser = new KeeperUser("Dan");
        KeeperUser tonKeeperUser = new KeeperUser("Anton");
        KeeperUser jatKeeperUser = new KeeperUser("Jamie");
        KeeperUser mwaKeeperUser = new KeeperUser("Bone");
        KeeperUser mattKeeperUser = new KeeperUser("Matt");

        List<KeeperUser> keeperUsers = new ArrayList<>();
        keeperUsers.add(damKeeperUser);
        keeperUsers.add(tonKeeperUser);
        keeperUsers.add(jatKeeperUser);
        keeperUsers.add(mwaKeeperUser);
        keeperUsers.add(mattKeeperUser);

        Map<Key<KeeperUser>, KeeperUser> map = ofy().save().entities(keeperUsers).now();

        // Verify put data!
        List<Key<KeeperUser>> keys = new ArrayList<>(map.keySet());
        assertEquals(keys.size(), keeperUsers.size());
        for (int i=0; i< keeperUsers.size(); i++)
        {
            Long id = keeperUsers.get(i).id;
            Long savedid = keys.get(i).getId();
            assertEquals(id, savedid);
        }

        // Fetch and verify fetched data
        Map<Key<KeeperUser>, KeeperUser> fetchedUsers = ofy().load().keys(keys);
        assertEquals(fetchedUsers.size(), keys.size());
        for (KeeperUser keeperUser : keeperUsers)
        {
            KeeperUser fetchedKeeperUser = fetchedUsers.get(Key.create(keeperUser));
            log.info(gson.toJson(fetchedKeeperUser));
            assertEquals(keeperUser.id, fetchedKeeperUser.id);
            assertEquals(keeperUser.name, fetchedKeeperUser.name);
        }



        // Create an activity!
        LeagueActivity activity = new LeagueActivity(leagueKey, "Fooz");
        Key<LeagueActivity> activityKey = ofy().save().entity(activity).now();
        assertEquals(activityKey.getParent(), leagueKey);

        // Create a group!
        LeagueGroup group = new LeagueGroup(leagueKey, "DaBois", keys);
        Key<LeagueGroup> groupKey = ofy().save().entity(group).now();
        assertEquals(groupKey.getParent(), leagueKey);


        // Create some teams!
        LeagueTeam damjatTeam = new LeagueTeam(leagueKey, "DamJat");
        LeagueTeam mwamwTeam = new LeagueTeam(leagueKey, "MwaMw");
        LeagueTeam anjatTeam = new LeagueTeam(leagueKey, "AnJat");

        Key<KeeperUser> damKey = Key.create(KeeperUser.class, damKeeperUser.id);
        Key<KeeperUser> jatKey = Key.create(KeeperUser.class, jatKeeperUser.id);
        Key<KeeperUser> tonKey = Key.create(KeeperUser.class, tonKeeperUser.id);
        Key<KeeperUser> mwaKey = Key.create(KeeperUser.class, mwaKeeperUser.id);
        Key<KeeperUser> mwKey = Key.create(KeeperUser.class, mattKeeperUser.id);

        damjatTeam.addUser(damKey);
        damjatTeam.addUser(jatKey);

        List<Key<KeeperUser>> mattList = new ArrayList<>();
        mattList.add(mwaKey);
        mattList.add(mwKey);
        mwamwTeam.addUsers(mattList);

        anjatTeam.addUser(tonKey);
        anjatTeam.addUser(jatKey);

        List<LeagueTeam> teams = new ArrayList<>();
        teams.add(damjatTeam);
        teams.add(mwamwTeam);
        teams.add(anjatTeam);

        Map<Key<LeagueTeam>, LeagueTeam> teamMap = ofy().save().entities(teams)
                .now();
        Key<LeagueTeam> damjatTeamKey = Key.create(LeagueTeam.class, damjatTeam
                .id);
        Key<LeagueTeam> mwamwTeamKey = Key.create(LeagueTeam.class, mwamwTeam
                .id);
        Key<LeagueTeam> anjatTeamKey = Key.create(LeagueTeam.class, anjatTeam
                .id);

        // Verify put data!
        List<Key<LeagueTeam>> teamKeys = new ArrayList<>(teamMap.keySet());
        assertEquals(teamKeys.size(), teams.size());
        for (int i=0; i<teams.size(); i++)
        {
            Long id = teams.get(i).id;
            Long savedid = teamKeys.get(i).getId();
            assertEquals(id, savedid);
        }

        // Fetch and verify fetched data
        Map<Key<LeagueTeam>, LeagueTeam> fetchedTeams = ofy().load().keys
                (teamKeys);
        assertEquals(fetchedTeams.size(), teamKeys.size());
        for (LeagueTeam team: teams)
        {
            LeagueTeam fetchedTeam = fetchedTeams.get(Key.create(team));
            log.info(gson.toJson(fetchedTeam));
            assertEquals(team.id, fetchedTeam.id);
            assertEquals(team.name, fetchedTeam.name);
            assertEquals(team.users, fetchedTeam.users);
        }

        // Add some stakes!
        LeagueStake stakeOne = new LeagueStake(leagueKey, ">=", 9, "Rio");
        Key<LeagueStake> stakeKey = ofy().save().entity(stakeOne).now();
        assertEquals(stakeKey.getParent(), leagueKey);

        LeagueStake stakeTwo = new LeagueStake(leagueKey, ">=", 8, "Freddo");
        Key<LeagueStake> stakeKeyTwo = ofy().save().entity(stakeTwo).now();
        assertEquals(stakeKeyTwo.getParent(), leagueKey);

        // Keep a list of them for assert later!
        List<LeagueStake> stakes = new ArrayList<>();
        stakes.add(stakeOne);
        stakes.add(stakeTwo);

        // Now lets make some matches!
        LeagueMatch matchOne = new LeagueMatch(leagueKey);
        Key<LeagueMatch> matchOneKey = ofy().save().entity(matchOne).now();
        assertEquals(matchOneKey.getParent(), leagueKey);
        // and some scores...
        LeagueMatchScore damjatScore = new LeagueMatchScore(matchOneKey,
                damjatTeamKey, 10);
        LeagueMatchScore mwamwScore = new LeagueMatchScore(matchOneKey, mwamwTeamKey,
                0);
        Key<LeagueMatchScore> damjatScoreKey = ofy().save().entity
                (damjatScore).now();
        Key<LeagueMatchScore> mwamwScoreKey = ofy().save().entity
                (mwamwScore).now();
        assertEquals(damjatScoreKey.getParent(), matchOneKey);
        assertEquals(mwamwScoreKey.getParent(), matchOneKey);

        // Now add scores to the match, THEN add to datastore!
        LeagueMatch matchTwo = new LeagueMatch(leagueKey);
        Key<LeagueMatch> matchTwoKey = ofy().save().entity(matchTwo).now();

        LeagueMatchScore anjatScore = new LeagueMatchScore(matchTwoKey, 9,
                anjatTeamKey);
        LeagueMatchScore mwamwScoreTwo = new LeagueMatchScore(matchTwoKey, 1,
                mwamwTeamKey);

        List<LeagueMatchScore> scores = new ArrayList<>();
        scores.add(anjatScore);
        scores.add(mwamwScoreTwo);
        Map<Key<LeagueMatchScore>, LeagueMatchScore> scoreMap = ofy().save()
                .entities(scores).now();

        // Keep a list of them for assert later!
        List<LeagueMatch> matches = new ArrayList<>();
        matches.add(matchOne);
        matches.add(matchTwo);

        // Verify put data!
        List<Key<LeagueMatchScore>> scoreKeys = new ArrayList<>(scoreMap.keySet
                ());
        assertEquals(scoreKeys.size(), scores.size());
        for (int i=0; i<scores.size(); i++)
        {
            Long id = scores.get(i).id;
            Long savedid = scoreKeys.get(i).getId();
            assertEquals(id, savedid);
        }

        // Fetch league
        League fetchedLeague = ofy().load().key(leagueKey).now();
        log.info(gson.toJson(fetchedLeague));
        assertEquals(fetchedLeague, league);

        // Fetch activity by league
        LeagueActivity fetchedActivity = ofy().load().type(LeagueActivity.class)
                .ancestor(league).first().now();
        log.info(gson.toJson(fetchedActivity));
        assertEquals(fetchedActivity, activity);

        // Fetch group by league
        LeagueGroup fetchedGroup = ofy().load().type(LeagueGroup.class)
                .ancestor(league).first().now();
        log.info(gson.toJson(fetchedGroup));
        assertEquals(fetchedGroup, group);

        // Fetch teams by league
        List<LeagueTeam> queriedTeams = ofy().load().type(LeagueTeam.class)
                .ancestor(league).list();
        for (LeagueTeam team : queriedTeams) {
            log.info(gson.toJson(team));
        }
        assertEquals(queriedTeams, teams);

        // Fetch stakes by league
        List<LeagueStake> queriedStakes = ofy().load().type(LeagueStake
                .class).ancestor(league).list();
        for (LeagueStake stake : queriedStakes) {
            log.info(gson.toJson(stake));
        }
        assertEquals(queriedStakes, stakes);

        // Fetch matches by league
        List<LeagueMatch> queriedMatches = ofy().load().type(LeagueMatch
                .class).ancestor(league).list();
        for (LeagueMatch match : queriedMatches) {
            log.info(gson.toJson(match));
        }
        assertEquals(queriedMatches, matches);

        // Fetch scores by league!
        List<LeagueMatchScore> queriedScores = ofy().load().type
                (LeagueMatchScore.class).ancestor(league).list();
        for (LeagueMatchScore score : queriedScores) {
            log.info(gson.toJson(score));
        }

        // Fetch scores by match!
        List<LeagueMatchScore> queriedMatchOneScores = ofy().load().type
                (LeagueMatchScore.class).ancestor(matchOne).list();
        for (LeagueMatchScore score : queriedMatchOneScores) {
            log.info(gson.toJson(score));
        }

        List<LeagueMatchScore> queriedMatchTwoScores = ofy().load().type
                (LeagueMatchScore.class).ancestor(matchTwo).list();
        for (LeagueMatchScore score : queriedMatchTwoScores) {
            log.info(gson.toJson(score));
        }
        assertEquals(queriedMatchTwoScores, scores);

        log.info(gson.toJson(fetchedLeague));
    }

}

// [END LocalDatastoreTest]