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
import com.jaida.keeper.backend.LeagueEndpoint;
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

public class LocalEndpointsTest {

    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    Logger log = Logger.getLogger("LocalEndpointsTest");
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
    public void endpointTest() {

        LeagueEndpoint endpoint = new LeagueEndpoint();


    }

}

// [END LocalDatastoreTest]