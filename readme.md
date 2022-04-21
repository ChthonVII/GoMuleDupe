# GoMuleDupe

A fork of GoMule for D2R with support for duping items. (Also fixes a serious bug that might lead to data corruption. Also allows (possibly unsafe) opening of .d2x external stash files created with the prior version.)

# Why?
Because I have better things to do with my time than endlessly farm for high runes.

Be warned, however, that overuse of the dupe feature may short circuit your enjoyment of the game by depriving you of the feeling that something worthwhile remains unfound and *might* drop for you this game. Use it judiciously.

# How?
New buttons have been added to the button panel for .d2x external stashes and the "Item Control" panel of the main window. These buttons mirror the behavior of the similarly named buttons above them, except that they dupe items rather than moving them. The original item remains in the source location, and a duplicate item is created at the destination location. The duplicate item will be given a new randomized fingerprint.

# Updated for D2R 2.4
As of v4.4.14-beta, GoMuleDupe supports D2R v2.4. Unlike upstream GoMule, GoMuleDupe retains the ability to open external stash files (.d2x) created with the prior version, subject to a confirmation prompt. However, **be warned** that trying to open an old stash containing items that have been personalized by Anya will not work correctly. If your stash contains personalized items, then you need to use the prior version of GoMule/Dupe to remove those items from the stash file first. (You can still keep those items by using the prior version to move them to a 2.3 character file, then letting D2R update that character file to 2.4.) There are *probably* no other compatibility issues with old stashes, but you should back them up along with a 2.3 character file and old version of GoMule just in case.

# What's This About Potential Data Corruption?
Upstream GoMule's function for writing to an item's binary data is buggy and doesn't always write out what it's supposed to. Fortunately, it seems to (usually?) work OK for changing the very short values used for stuff like stash position, and upstream GoMule generally doesn't change anything more than that. The buggy function is fixed in GoMuleDupe (which needs it working correctly for writing new fingerprint values).

# Building
>./gradlew build

Note that some dependency added to upstream GoMule between v.4.4.13 and v.4.4.14-beta (likely some kotlin thing) requires the Java 8 JDK to build. Newer versions of Java won't work. You can get an unpackaged version of openjdk 8 from [Eclipse](https://adoptium.net/temurin/releases/). Set the JAVA_HOME environment variable to use it. Once the bothersome dependency is compiled and cached, the problem goes away and you can compile using any version of Java.

# Other
Also good to know: GoMule uses Swing, so hi-dpi support can be accomplished with one of the following syntaxes:
>java -Dsun.java2d.uiScale=2 -jar GoMule.jar

>java -Dsun.java2d.uiScale=300% -jar GoMule.jar

# Original GoMule
Visit the [Official Site](https://gomule.sourceforge.io/) 
