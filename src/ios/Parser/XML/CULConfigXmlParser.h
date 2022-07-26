//
//  CULConfigXmlParser.h
//
//  Created by Nikolay Demyankov on 15.09.15.
//

#import <Foundation/Foundation.h>
#import "CULConfig.h"

/**
 *  Parser for config.xml. Reads only plugin-specific preferences.
 */
@interface CULConfigXmlParser : NSObject

/**
 *  Parse config.xml
 *
 *  @return list of supported custom schemes and hosts, defined in the config file
 */
+ (CULConfig *)parse;

@end
