package com.jaida.keeper.backend;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.beans.Transient;

/**
 * The @Entity tells Objectify about our entity.  We also register it in
 * OfyHelper.java -- very important.
 *
 * This is never actually created, but gives a hint to Objectify about our Ancestor key.
 */
@Entity
public class KeeperUser {
    @Id public Long id;
    @Index public String name;

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Key<LeagueGroup>[] groups;

    /*No-parm constructor required for Objectify*/
    public KeeperUser() {};

    public KeeperUser(String name) {
        this();
        this.name = name;
    }

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Key<KeeperUser> getKey(){
        return Key.create(this.getClass(), id);
    }

}