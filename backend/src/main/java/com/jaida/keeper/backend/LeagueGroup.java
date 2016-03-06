package com.jaida.keeper.backend;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

import java.util.ArrayList;
import java.util.List;

/**
 * The @Entity tells Objectify about our entity.  We also register it in
 * OfyHelper.java -- very important.
 *
 * This is never actually created, but gives a hint to Objectify about our Ancestor key.
 */
@Entity
public class LeagueGroup {

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    @Parent Key<League> league;

    @Id public Long id;
    public String name;
    public String houserules;

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public List<Key<KeeperUser>> users = new ArrayList<>();

    /*No-parm constructor required for Objectify*/
    public LeagueGroup() {};

    public LeagueGroup(Key<League> league, String name) {
        this.league = league;
        this.name = name;
    }

    public LeagueGroup(Key<League> league, String name, List<Key<KeeperUser>> users) {
        this(league, name);
        this.users = users;
    }

    public void addUser(Key<KeeperUser> user) {
        if (this.users.contains(user)) {
            // Do something maybe
        } else {
            this.users.add(user);
        }
    }

    public void addUsers(List<Key<KeeperUser>> users) {
        for (Key<KeeperUser> user: users) {
            this.addUser(user);
        }
    }

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Key<LeagueGroup> getKey(){
        return Key.create(this.league, this.getClass(), id);
    }
}