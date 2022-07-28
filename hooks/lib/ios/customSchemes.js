var path = require('path');
var fs = require('fs');
var mkpath = require('mkpath');
var plist = require('plist');
var ConfigXmlHelper = require('../configXmlHelper.js');

var CF_BUNDLE_URL_TYPES = "CFBundleURLTypes";
var CF_BUNDLE_URL_SCHEMES = "CFBundleURLSchemes";

var context;
var projectName;
var projectInfoFilePath;

module.exports = {
    generateCustomUrlSchemes: generateUrlSchemes
}

function generateUrlSchemes(cordovaContext, pluginPreferences) {
    context = cordovaContext;

    var currentProjectInfo = getProjectInfoFileContent();
    var newProjectInfo = injectPreferences(currentProjectInfo, pluginPreferences);

    saveContentToProjectInfoFile(newProjectInfo);
}

function saveContentToProjectInfoFile(content) {
    var plistContent = plist.build(content);
    var filePath = pathToProjectInfoFile();

    mkpath.sync(path.dirname(filePath));
    fs.writeFileSync(filePath, plistContent, "utf8");
}

function getProjectInfoFileContent() {
    var filePath = pathToProjectInfoFile();
    var content;

    try {
        content = fs.readFileSync(filePath, "utf8");
    } catch (err) {
        return {};
    }

    return plist.parse(content);
}

function injectPreferences(currentProjectInfo, pluginPreferences) {
    var newProjectInfo = currentProjectInfo;
    var urlTypes = getUrlTypes(newProjectInfo);
    var urlSchemesIndex = getUrlSchemesIndex(urlTypes);

    var content = generateUrlSchemesDictionary(pluginPreferences);

    if (urlSchemesIndex == -1) {
        urlTypes.push(content);
    } else {
        urlTypes[urlSchemesIndex] = content;
    }

    newProjectInfo[CF_BUNDLE_URL_TYPES] = urlTypes;
    return newProjectInfo;
}

function getUrlTypes(projectInfo) {
    var urlTypes = projectInfo[CF_BUNDLE_URL_TYPES];

    if (!urlTypes) {
        console.log(`${CF_BUNDLE_URL_TYPES} entry not found [adding it]`)
        urlTypes = [];
    }

    return urlTypes;
}

function getUrlSchemesIndex(urlTypes) {
    var foundIndex = -1;

    urlTypes.some(function(entry, index) {
        if (entry.hasOwnProperty(CF_BUNDLE_URL_SCHEMES)) {
            console.log(`${CF_BUNDLE_URL_SCHEMES} dictionary found at index ${index} under ${CF_BUNDLE_URL_TYPES} [replacing it]`)
            foundIndex = index;
            return true;
        }

        console.log(`${CF_BUNDLE_URL_SCHEMES} dictionary not found under ${CF_BUNDLE_URL_TYPES} [adding it]`)
        return false;
    });

    return foundIndex;
}

function generateUrlSchemesDictionary(pluginPreferences) {
    var content = generateUrlSchemesArray(pluginPreferences);

    return ({
        [CF_BUNDLE_URL_SCHEMES]: content
    })
}

function generateUrlSchemesArray(pluginPreferences) {
    var schemesList = [];

    pluginPreferences.schemes.forEach(function(scheme) {
        var entry = entryForCustomScheme(scheme);
        schemesList.push(entry);
    });

    return schemesList;
}

function entryForCustomScheme(scheme) {
    return scheme.name;
}

// region Path helper methods

/**
 * Path to entitlements file.
 *
 * @return {String} absolute path to entitlements file
 */
function pathToProjectInfoFile() {
    if (projectInfoFilePath === undefined) {
        projectInfoFilePath = path.join(getProjectRoot(), 'platforms', 'ios', getProjectName(), getProjectName() + '-Info.plist');
    }

    return projectInfoFilePath;
}

/**
 * Projects root folder path.
 *
 * @return {String} absolute path to the projects root
 */
function getProjectRoot() {
    return context.opts.projectRoot;
}

/**
 * Name of the project from config.xml
 *
 * @return {String} project name
 */
function getProjectName() {
    if (projectName === undefined) {
        var configXmlHelper = new ConfigXmlHelper(context);
        projectName = configXmlHelper.getProjectName();
    }

    return projectName;
}

// endregion
