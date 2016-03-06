package com.jaida.keeper.backend;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.Key;

import java.lang.String;
import java.util.Date;
import java.util.List;

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
public class LeagueMatch {

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    @Parent Key<League> league;

    @Id public Long id;
    @Index public Date date;


    /*No-parm constructor required for Objectify*/
    public LeagueMatch() {
        date = new Date();
    }

    public LeagueMatch(Key<League> league) {
        this();
        this.league = league;
    }

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Key<LeagueMatch> getKey(){
        return Key.create(this.league, this.getClass(), id);
    }

}