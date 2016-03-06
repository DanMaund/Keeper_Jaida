package com.jaida.keeper.backend;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.Key;

/**
 * The @Entity tells Objectify about our entity.  We also register it in {@link OfyHelper}
 * Our primary key @Id is set automatically by the Google Datastore for us.
 *
 * We add a @Parent to tell the object about its ancestor. We are doing this to support many
 * guestbooks.  Objectify, unlike the AppEngine library requires that you specify the fields you
 * want to index using @Index.  Only indexing the fields you need can lead to substantial gains in
 * performance -- though if not indexing your data from the start will require indexing it later.
 *
 * NOTE - all the properties are PUBLIC so that can keep the code simple.
 **/
@Entity
public class LeagueMatchScore {

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    @Parent Key<LeagueMatch> match;

    @Id public Long id;

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Key<LeagueTeam> team;
    public Integer score;

    /*No-parm constructor required for Objectify*/
    public LeagueMatchScore() {};

    public LeagueMatchScore(Key<LeagueMatch> match,
                            Key<LeagueTeam> team,
                            Integer score ) {
        this.match = match;
        this.score = score;
        this.team = team;
    }

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Key<LeagueMatchScore> getKey(){
        return Key.create(this.match, this.getClass(), id);
    }

}