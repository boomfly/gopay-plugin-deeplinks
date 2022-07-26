package com.nordnetab.cordova.ul.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for <scheme /> entry, specified in config.xml.
 */
public class ULScheme {

    // default event name, that is dispatched to JS if none was set to the scheme
    private static final String DEFAULT_EVENT = "didLaunchAppFromLink";

    private final String name;
    private String event;

    /**
     * Constructor
     *
     * @param name   scheme name
     * @param event  event that corresponds to this scheme
     */
    public ULScheme(final String name, final String event) {
        this.name = name.toLowerCase();
        this.event = (event == null) ? DEFAULT_EVENT : event;
    }

    /**
     * Getter for the event name that is sent to JS when user clicks on the link from this host.
     * Defined as 'event' attribute.
     *
     * @return event name
     */
    public String getEvent() {
        return event;
    }

    /**
     * Setter for event name.
     *
     * @param event event name
     */
    public void setEvent(final String event) {
        this.event = event;
    }

    /**
     * Getter for the scheme name.
     * Defined as 'name' attribute.
     *
     * @return scheme name
     */
    public String getName() {
        return name;
    }
}
