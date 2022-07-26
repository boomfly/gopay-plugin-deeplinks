package com.nordnetab.cordova.ul.parser;

import android.content.Context;
import android.text.TextUtils;

import com.nordnetab.cordova.ul.model.ULConfig;
import com.nordnetab.cordova.ul.model.ULHost;
import com.nordnetab.cordova.ul.model.ULPath;
import com.nordnetab.cordova.ul.model.ULScheme;

import org.apache.cordova.ConfigXmlParser;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikolay Demyankov on 09.09.15.
 * <p/>
 * Parser for config.xml. Reads only plugin-specific preferences.
 */
public class ULConfigXmlParser extends ConfigXmlParser {

    private final Context context;
    private ULConfig config;

    private boolean isInsideMainTag;
    private boolean didParseMainBlock;
    private boolean isInsideSchemeBlock;
    private boolean isInsideHostBlock;
    private ULScheme processedScheme;
    private ULHost processedHost;

    // region Public API

    /**
     * Constructor
     *
     * @param context application context
     */
    public ULConfigXmlParser(Context context) {
        this.context = context;
    }

    /**
     * Parse config.xml
     *
     * @return list of hosts, defined in the config file
     */
    public ULConfig parse() {
        resetValuesToDefaultState();
        super.parse(context);

        return config;
    }

    // endregion

    // region XML processing

    @Override
    public void handleStartTag(XmlPullParser xml) {
        if (didParseMainBlock) {
            return;
        }

        final String name = xml.getName();
        if (!isInsideMainTag && XmlTags.MAIN_TAG.equals(name)) {
            isInsideMainTag = true;
            return;
        }

        if (!isInsideMainTag) {
            return;
        }

        if (!isInsideSchemeBlock && XmlTags.SCHEME_TAG.equals(name)) {
            isInsideSchemeBlock = true;
            processSchemeBlock(xml);
            return;
        }

        if (!isInsideHostBlock && XmlTags.HOST_TAG.equals(name)) {
            isInsideHostBlock = true;
            processHostBlock(xml);
            return;
        }

        if (isInsideHostBlock && XmlTags.PATH_TAG.equals(name)) {
            processPathBlock(xml);
        }
    }

    @Override
    public void handleEndTag(XmlPullParser xml) {
        if (didParseMainBlock) {
            return;
        }

        final String name = xml.getName();

        if (isInsideSchemeBlock && XmlTags.SCHEME_TAG.equals(name)) {
            isInsideSchemeBlock = false;
            config.getSupportedSchemes().add(processedScheme);
            processedScheme = null;
            return;
        }

        if (isInsideHostBlock && XmlTags.HOST_TAG.equals(name)) {
            isInsideHostBlock = false;
            config.getSupportedHosts().add(processedHost);
            processedHost = null;
            return;
        }

        if (XmlTags.MAIN_TAG.equals(name)) {
            isInsideMainTag = false;
            didParseMainBlock = true;
        }
    }

    /**
     * Parse <scheme />
     */
    private void processSchemeBlock(XmlPullParser xml) {
        final String schemeName = xml.getAttributeValue(null, XmlTags.SCHEME_NAME_ATTRIBUTE);
        final String eventName = xml.getAttributeValue(null, XmlTags.SCHEME_EVENT_ATTRIBUTE);

        processedScheme = new ULScheme(schemeName, eventName);
    }

    /**
     * Parse <host />
     */
    private void processHostBlock(XmlPullParser xml) {
        final String hostName = xml.getAttributeValue(null, XmlTags.HOST_NAME_ATTRIBUTE);
        final String eventName = xml.getAttributeValue(null, XmlTags.HOST_EVENT_ATTRIBUTE);
        final String scheme = xml.getAttributeValue(null, XmlTags.HOST_SCHEME_ATTRIBUTE);

        processedHost = new ULHost(hostName, scheme, eventName);
    }

    /**
     * Parse <path />
     */
    private void processPathBlock(XmlPullParser xml) {
        final String url = xml.getAttributeValue(null, XmlTags.PATH_URL_TAG);
        String event = xml.getAttributeValue(null, XmlTags.PATH_EVENT_TAG);

        // skip wildcard urls
        if ("*".equals(url) || ".*".equals(url)) {
            // but if path has event name - set it to host
            if (!TextUtils.isEmpty(event)) {
                processedHost.setEvent(event);
            }

            return;
        }

        // if event name is empty - use one from the host
        if (TextUtils.isEmpty(event)) {
            event = processedHost.getEvent();
        }

        // create path entry
        ULPath path = new ULPath(url, event);
        processedHost.getPaths().add(path);
    }

    // endregion

    // region Private API

    private void resetValuesToDefaultState() {
        config = new ULConfig();
        isInsideMainTag = false;
        didParseMainBlock = false;
        isInsideSchemeBlock = false;
        isInsideHostBlock = false;
        processedScheme = null;
        processedHost = null;
    }

    // endregion
}