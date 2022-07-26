//
//  CULConfig.h
//
//

#import <Foundation/Foundation.h>
#import "CULHost.h"
#import "CULScheme.h"

/**
 *  Model for <universal-links /> entry, specified in config.xml.
 */
@interface CULConfig : NSObject

/**
 *  List of supported schemes in config.xml.
 */
@property (nonatomic, readonly, strong) NSArray<CULScheme *> *supportedSchemes;

/**
 *  List of supported hosts in config.xml.
 */
@property (nonatomic, readonly, strong) NSArray<CULHost *> *supportedHosts;

/**
 *  Constructor.
 *
 *  @param name   host name
 *  @param scheme scheme; if <code>nil</code> - http will be used
 *  @param event  event name; if <code>nil</code> - didLaunchAppFromLink event name will be used
 *
 *  @return instance of the CULHost
 */
- (instancetype)init;

/**
 *  Add scheme entry to supported schemes list.
 *
 *  @param scheme scheme to add
 */
- (void)addScheme:(CULScheme *)scheme;

/**
 *  Add host entry to supported hosts list.
 *
 *  @param host host to add
 */
- (void)addHost:(CULHost *)host;

@end
