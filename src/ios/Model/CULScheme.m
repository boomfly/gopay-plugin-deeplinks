//
//  CULScheme.m
//
//

#import "CULScheme.h"

// default event name
static NSString *const DEFAULT_EVENT = @"didLaunchAppFromLink";

@interface CULScheme() {
}

@end

@implementation CULScheme

- (instancetype)initWithSchemeName:(NSString *)name event:(NSString *)event {
    self = [super init];
    if (self) {
        _event = event ? event : DEFAULT_EVENT;
        _name = name.lowercaseString;
    }
    return self;
}

@end
