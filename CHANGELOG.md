# Change Log

## Version 1.0
_2026-04-22_

This release defines the `BetterChairs-OG` branch line. Compared to `upstream/main`
from [SpraxDev/BetterChairs](https://github.com/SpraxDev/BetterChairs), this branch differs as follows:

### Added
* `/dismount` command for leaving a BetterChairs seat directly
* Sneak-to-dismount handling
* Essentials-aware teleports that preserve the player's `/back` location when sitting or dismounting
* TrueOG Bootstrap repository support in the Gradle build
* Item-NBT-API imports and integration for this branch's dependency set
* NMS shading and API access from NMS modules in the Gradle build
* Targeting Purpur 1.19.4 supporting 1.8-1.21.11 clients via ViaSuite

### Removed
* Upstream updater integration and update notification permissions
* bStats metrics bootstrap from the plugin startup path
* Maven build files in favor of the Gradle build
* `1.20+` and `1.21+` modules from the active Gradle build
