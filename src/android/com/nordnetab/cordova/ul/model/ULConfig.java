package com.nordnetab.cordova.ul.model;

import java.util.ArrayList;
import java.util.List;

public class ULConfig {
    private final List<ULScheme> supportedSchemes;
    private final List<ULHost> supportedHosts;

    public ULConfig() {
        this.supportedSchemes = new ArrayList<ULScheme>();
        this.supportedHosts = new ArrayList<ULHost>();
    }

    public List<ULScheme> getSupportedSchemes() {
        return this.supportedSchemes;
    }

    public List<ULHost> getSupportedHosts() {
        return this.supportedHosts;
    }
}
