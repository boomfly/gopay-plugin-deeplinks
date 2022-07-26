//
//  CULConfigJsonParser.h
//
//  Created by Nikolay Demyankov on 29.01.17.
//

#import <Foundation/Foundation.h>
#import "CULConfig.h"

/**
 *  JSON parser for plugin's preferences.
 */
@interface CULConfigJsonParser : NSObject

/**
 *  Parse JSON config.
 *
 *  @return list of schemes and hosts, defined in the config file
 */
+ (CULConfig *)parseConfig:(NSString *)pathToJsonConfig;

@end
