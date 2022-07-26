//
//  CULScheme.h
//
//

#import <Foundation/Foundation.h>

/**
 *  Model for <scheme /> entry, specified in config.xml.
 */
@interface CULScheme : NSObject

/**
 * Scheme name.
 * Defined as 'name' attribute.
 */
@property (nonatomic, readonly, strong) NSString *name;

/**
 *  Event name that is sent to JS when user clicks on the link from this host.
 *  Defined as 'event' attribute.
 */
@property (nonatomic, strong) NSString *event;

/**
 *  Constructor.
 *
 *  @param name   scheme name
 *  @param event  event name; if <code>nil</code> - didLaunchAppFromLink event name will be used
 *
 *  @return instance of the CULScheme
 */
- (instancetype)initWithSchemeName:(NSString *)name event:(NSString *)event;

@end
