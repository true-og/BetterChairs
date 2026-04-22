# BetterChairs-OG

`BetterChairs-OG` is a forked branch line of
[SpraxDev/BetterChairs](https://github.com/SpraxDev/BetterChairs) maintained for the
TrueOG stack. This branch is version `1.0`.

The plugin display name is now `BetterChairs-OG`, but the command, permission, and
PlaceholderAPI namespace stays `BetterChairs` for compatibility with existing servers.

## What This Branch Is

This branch keeps the classic chair-sitting experience while carrying a narrower,
fork-specific platform target and build setup.

Highlights:
* Project/artifact renamed to `BetterChairs-OG`
* Version reset to `1.0`
* Gradle build with Shadow, Spotless, and bundled wrapper
* Essentials-aware teleports that preserve `/back`
* `/dismount` command and sneak-dismount support
* Active NMS build range limited to Minecraft `1.8` through `1.19.4`
* Updater and bStats code removed from this branch

## Compatibility

This branch currently builds the following NMS modules:
* `1.8_R1`
* `1.8_R2`
* `1.8_R3`
* `1.9_R1`
* `1.9_R2`
* `1.10_R1`
* `1.11_R1`
* `1.12_R1`
* `1.13_R1`
* `1.13_R2`
* `1.14_R1`
* `1.15_R1`
* `1.16_R1`
* `1.16_R2`
* `1.16_R3`
* `1.17_R1`
* `1.18_R1`
* `1.18_R2`
* `1.19_0`
* `1.19_R1`
* `1.19_R2`
* `1.19_R3`

## Commands And Permissions

The compatibility namespace is unchanged.

| Command | Permission | Description |
| --- | --- | --- |
| `/BetterChairs <toggle\|on\|off\|status>` | `BetterChairs.cmd.toggle` | Toggle chair usage for yourself |
| `/BetterChairs reload` | `BetterChairs.cmd.reload` | Reload `config.yml` and `messages.yml` |
| `/BetterChairs reset` | `BetterChairs.cmd.reset` | Eject all players from BetterChairs seats |
| `/toggleChairs` or `/bct` | `BetterChairs.cmd.toggle` | Shortcut for player chair toggling |
| `/sit` | `BetterChairs.cmd.sit` | Sit on solid ground |
| `/dismount` | `BetterChairs.cmd.dismount` | Leave a BetterChairs seat |
| Right-click chairs | `BetterChairs.use` | Sit on supported chair blocks |
| Regeneration while seated | `BetterChairs.regeneration` | Allow configured regeneration effect |

## Building

Build with:

```bash
./gradlew build
```

If your environment provides a local bootstrap repository, set `SELF_MAVEN_LOCAL_REPO`
to that directory before building. Otherwise the build falls back to `~/.m2/repository`
and `mavenLocal()`.

## Differences From Upstream

See [CHANGELOG.md](./CHANGELOG.md) for the branch-specific delta against upstream.

In short, this branch:
* renames the project to `BetterChairs-OG`
* keeps the legacy `BetterChairs` runtime namespace for compatibility
* swaps Maven for the Gradle build used here
* removes updater/metrics code
* narrows the active support matrix to the versions listed above
* carries TrueOG-specific dependency and build adjustments
