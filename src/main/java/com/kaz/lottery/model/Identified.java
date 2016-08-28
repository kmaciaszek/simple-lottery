package com.kaz.lottery.model;

/**
 * All objects which are going to be persisted have to implement that interface. It forces the object to
 * implement accessors for id.
 */
public interface Identified {

    public String getId();

    public void setId(String id);
}
