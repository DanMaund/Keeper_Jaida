package com.jaida.keeper.backend;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

/**
 * The @Entity tells Objectify about our entity.  We also register it in
 * OfyHelper.java -- very important.
 *
 * This is never actually created, but gives a hint to Objectify about our Ancestor key.
 */
@Entity
public class LeagueStake {
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    @Parent Key<League> league;

    @Id public Long id;
    public String condOp;
    public Integer condVal;
    public String reward;

    public LeagueStake(Key<League> league, String op, Integer
            val, String reward) {
        this.league = league;
        this.condOp = op;
        this.condVal = val;
        this.reward = reward;
    }

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Key<LeagueStake> getKey(){
        return Key.create(this.league, this.getClass(), id);
    }
}