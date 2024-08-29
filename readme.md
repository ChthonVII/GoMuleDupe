# GoMuleDupe

A fork of GoMule for D2R with support for duping items. (Also fixes a serious bug that might lead to data corruption.)

# Why?
Because I have better things to do with my time than endlessly farm for high runes.

Be warned, however, that overuse of the dupe feature may short circuit your enjoyment of the game by depriving you of the feeling that something worthwhile remains unfound and *might* drop for you this game. Use it judiciously.

# How?
New buttons have been added to the button panel for .d2x external stashes and the "Item Control" panel of the main window. These buttons mirror the behavior of the similarly named buttons above them, except that they dupe items rather than moving them. The original item remains in the source location, and a duplicate item is created at the destination location. The duplicate item will be given a new randomized fingerprint.

# Updated for D2R 2.7
As of v4.4.20-beta, GoMuleDupe supports D2R v2.5, 2.6, and 2.7.

Unlike the prior update in v4.4.14-beta from D2R 2.3 to 2.4 compatibility, there is no backward compatibility kludge for external stashes. You will need to use an old version of GoMule or GoMuleDupe to move your items into an old D2R stash file and then use D2R to upgrade that stash to the new format.

# What's This About Potential Data Corruption?
Upstream GoMule's function for writing to an item's binary data is buggy and doesn't always write out what it's supposed to. Fortunately, it seems to (usually?) work OK for changing the very short values used for stuff like stash position, and upstream GoMule generally doesn't change anything more than that. The buggy function is fixed in GoMuleDupe (which needs it working correctly for writing new fingerprint values).

# Building
>./gradlew build

# Other
Also good to know: GoMule uses Swing, so hi-dpi support can be accomplished with one of the following syntaxes:
>java -Dsun.java2d.uiScale=2 -jar GoMule.jar

>java -Dsun.java2d.uiScale=300% -jar GoMule.jar

# Original GoMule
Visit the [Official Site](https://gomule.sourceforge.io/) 
