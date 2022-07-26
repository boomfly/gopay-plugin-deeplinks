//
//  CULConfig.m
//
//

#import "CULConfig.h"

@interface CULConfig() {
    NSMutableArray<CULScheme *> *_supportedSchemes;
    NSMutableArray<CULHost *> *_supportedHosts;
}

@end

@implementation CULConfig

- (instancetype)init {
    self = [super init];
    if (self) {
        _supportedSchemes = [[NSMutableArray alloc] init];
        _supportedHosts = [[NSMutableArray alloc] init];
    }
    return self;
}

- (void)addScheme:(CULScheme *)scheme {
    if (scheme) {
        [_supportedSchemes addObject:scheme];
    }
}

- (void)addHost:(CULHost *)host {
    if (host) {
        [_supportedHosts addObject:host];
    }
}

- (NSArray<CULScheme *> *)schemes {
    return _supportedSchemes;
}

- (NSArray<CULHost *> *)hosts {
    return _supportedHosts;
}

@end
