##Welcome to Equivalent Exchange Reborn!

[Minecraft Forums page](http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/wip-mods/2174708-wip-equivalent-exchange-reborn)

[Compiling EER](#compiling-equivalent-exchange-reborn) - For those that want the latest unreleased features.

[Contributing](#contributing) - For those that want to help out.

###Compiling Equivalent Exchange Reborn
IMPORTANT: Please report any issues you have, there might be some problems with the documentation!
Also make sure you know EXACTLY what you're doing!  It's not any of our faults if your OS crashes, becomes corrupted, etc.
***
[Setup Java](#setup-java)

[Setup Gradle](#setup-gradle)

[Setup Git](#setup-git)

[Setup EER](#setup-eer)

[Compile EER](#compile-eer)

[Setup Eclipse](#setup-eclipse)

[Running the Server](#running-the-server)

[Updating Your Repository](#updating-your-repository)

####Setup Java
The Java JDK is used to compile EER.

1. Download and install the Java JDK.
	* [Windows/Mac download link](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html).  Scroll down, accept the `Oracle Binary Code License Agreement for Java SE`, and download it (if you have a 64-bit OS, please download the 64-bit version).
	* Linux: Installation methods for certain popular flavors of Linux are listed below.  If your distribution is not listed, follow the instructions specific to your package manager or install it manually [here](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html).
		* Gentoo: `emerge dev-java/oracle-jdk-bin`
		* Archlinux: `pacman -S jdk7-openjdk`
		* Ubuntu/Debian: `apt-get install openjdk-7-jdk`
		* Fedora: `yum install java-1.7.0-openjdk`
2. Windows: Set environment variables for the JDK.
    * Go to `Control Panel\System and Security\System`, and click on `Advanced System Settings` on the left-hand side.
    * Click on `Environment Variables`.
    * Under `System Variables`, click `New`.
    * For `Variable Name`, input `JAVA_HOME`.
    * For `Variable Value`, input something similar to `C:\Program Files\Java\jdk1.7.0_51` exactly as shown (or wherever your Java JDK installation is), and click `Ok`.
    * Scroll down to a variable named `Path`, and double-click on it.
    * Append `;%JAVA_HOME%\bin` EXACTLY AS SHOWN and click `Ok`.  Make sure the location is correct; double-check just to make sure.
3. Open up your command line and run `javac`.  If it spews out a bunch of possible options and the usage, then you're good to go.  If not, either try the steps again or check the [FAQ](https://github.com/pahimar/Equivalent-Exchange-Reborn/wiki/Frequently-Asked-Questions).

####Setup Gradle
Gradle is used to execute the various build tasks when compiling EER.

1. Download and install Gradle.
	* [Windows/Mac download link](http://www.gradle.org/downloads).  You only need the binaries, but choose whatever flavor you want.
		* Unzip the package and put it wherever you want, eg `C:\Gradle`.
	* Linux: Installation methods for certain popular flavors of Linux are listed below.  If your distribution is not listed, follow the instructions specific to your package manager or install it manually [here](http://www.gradle.org/downloads).
		* Gentoo: `emerge dev-java/gradle-bin`
		* Archlinux: You'll have to install it from the [AUR](https://aur.archlinux.org/packages/gradle).
		* Ubuntu/Debian: `apt-get install gradle`
		* Fedora: Install Gradle manually from its website (see above), as Fedora ships a "broken" version of Gradle.  Use `yum install gradle` only if you know what you're doing.
2. Windows: Set environment variables for Gradle.
	* Go back to `Environment Variables` and then create a new system variable.
	* For `Variable Name`, input `GRADLE_HOME`.
	* For `Variable Value`, input something similar to `C:\Gradle-1.11` exactly as shown (or wherever your Gradle installation is), and click `Ok`.
	* Scroll down to `Path` again, and append `;%GRADLE_HOME%\bin` EXACTLY AS SHOWN and click `Ok`.  Once again, double-check the location.
3. Open up your command line and run `gradle`.  If it says "Welcome to Gradle [version].", then you're good to go.  If not, either try the steps again or check the [FAQ](https://github.com/pahimar/Equivalent-Exchange-Reborn/wiki/Frequently-Asked-Questions).

####Setup Git
Git is used to clone EER and update your local copy.

1. Download and install Git [here](http://git-scm.com/download/).
	* *Optional*: Download and install a Git GUI client, such as Github for Windows/Mac, SmartGitHg, TortoiseGit, etc.  A nice list is available [here](http://git-scm.com/downloads/guis).

####Setup EER
This section assumes that you're using the command-line version of Git.

1. Open up your command line.
2. Navigate to a place where you want to download EER's source (eg `C:\Github\Equivalent-Exchange-Reborn\`) by executing `cd [folder location]`.  This location is known as `mcdev` from now on.
3. Execute `https://github.com/multiplemonomials/Equivalent-Exchange-Reborn.git`.  This will download EER's source into `mcdev`.
4. Right now, you should have a directory that looks something like:

***
	mcdev
	\-Equivalent-Exchange-Reborn
		\-EER's files (should have `build.gradle`)
***

####Compile EER
1. Execute `gradle setupDecompWorkspace --refresh-dependencies eclipse`. This sets up Forge and downloads the necessary libraries to build EER.  This might take some time, be patient.
	* You will generally only have to do this once until the Forge version in `build.properties` changes.
2. Execute `gradle build`. If you did everything right, `BUILD SUCCESSFUL` will be displayed after it finishes.  This should be relatively quick.
    * If you see `BUILD FAILED`, check the error output (it should be right around `BUILD FAILED`), fix everything (if possible), and try again.
	* Getting this error:
		`org.gradle.api.ProjectConfigurationException: You must set the Minecraft Version!`?
		You can't use Gradle 1.x (including the version used by gradlew, as of forge #1199) with Java 8.  Upgrade Gradle or downgrade Java.
		And no, I don't know why it fails in this weird way.
3. Navigate to `mcdev\Equivalent-Exchange-Reborn\build\libs`.
    *  You should see a `.jar` file named `EquivalentExchangeReborn-1.7.10-0.1.#.jar`, where # is the `build_number` value in `build.properties`.
		* NOTE: `null` means that you are missing a `build_number` value in `build.properties` or that your decomp environment is set up incorrectly.
4. Copy the jar into your Minecraft mods folder, and you are done!

####Setup Eclipse
This is optional, but if you want to 
(a) have an easier time digging into the Minecraft source code, and
(b) use a code-completion-enabled java editor, get it! It will make your life easier, especially since often the Minecraft/Forge source is the only documentation available

1. Download Eclipse Standard from https://www.eclipse.org/

2. Unzip the folder to... somewhere.  I put it in C:

3. Start eclipse.exe from the install directory.

4. It will ask for a workspace folder.  Again, this is your decision.

5. After it's finished starting, go to `file->import...->General->Existing Projects into Workspace`.
	* Point it to the Equivalent-Exchange-Reborn folder and select the project that comes up.

6. Create a new run configuration for the Equivalent Exchange Reborn project.  In the "Program arguments" box put
***
	--version 1.7 --tweakClass cpw.mods.fml.common.launcher.FMLTweaker --accessToken modstest --username {username}
	--userProperties {} --assetIndex 1.7.10 --assetsDir {path_to_gradle_home}/caches/minecraft/assets
***
Replace `{username}` with your username and `{path_to_gradle_home}` with the full path to your .gradle folder (mine was `C:/Users/Jamie/.gradle`).
In the "VM arguments" box put
***
	-Dfml.ignoreInvalidMinecraftCertificates=true
***

7. Run it and make sure it works.  
	*Getting `java.lang.reflect.InvocationTargetException` `Caused by: java.lang.UnsatisfiedLinkError: no lwjgl in java.library.path`?
	In Eclipse, open the "Equivalent Exchange Reborn" folder and look for the "lwjgl-2.9.1.jar" library (your version may be different).
	Right click on it and hit Properties, and go to the Native Library menu.  Next to the "Location path" box hit the "Workspace..." button
	and select `Equivalent Exchange Reborn > build > natives`. Hit OK on the two dialogs, and see if it works now.

####Running the Server
If you feel the need to test your changes in a client/server setup (please do, it helps prevent bugs that would otherwise escape to users),
run the command `gradle runServer` in the source directory.  It will have you accept the EULA, and while you're at it go into server.properties
and change online-mode to false.  Then, run the command again, and you should be good to go.
 
####Updating Your Repository
In order to get the most up-to-date builds, you'll have to periodically update your local repository.

1. Open up your command line.
2. Navigate to `mcdev` in the console.
3. Make sure you have not made any changes to the local repository, or else there might be issues with Git.
	* If you have, try reverting them to the status that they were when you last updated your repository.
4. Execute `git pull master`.  This pulls all commits from the official repository that do not yet exist on your local repository and updates it.

###Contributing
***
####Submitting a PR
So you found a bug in the code?  Think you can make it more efficient?  Want to help in general?  Great!  No requests turned away!

1. If you haven't already, create a Github account.
2. Click the `Fork` icon located at the top-right of this page (below your username).
3. Make the changes that you want to and commit them.
	* If you're making changes locally, you'll have to execute `git commit -a` and `git push` in your command line.
4. Click `Pull Request` at the right-hand side of the gray bar directly below your fork's name.
5. Click `Click to create a pull request for this comparison`, enter your PR's title, and create a detailed description telling us what you changed.
6. Click `Send pull request`, and wait for feedback!

####Creating an Issue
EER crashes every time?  Have a suggestion?  Found a bug?  Create an issue now!

1. Make sure your issue hasn't already been answered or fixed.  Also think about whether your issue is a valid one before submitting it.
	* Please do not open an issue to ask a question-that is for the forums.
2. Go to [the issues page](http://github.com/multiplemonomials/Equivalent-Exchange-Reborn/issues).
3. Click `New Issue` right below `Star` and `Fork`.
4. Enter your Issue's title (something that summarizes your issue), and then create a detailed description ("Hey, could you add/change xxx?" or "Hey, found $exploit").
	* If you are reporting a bug report from an unofficial version, make sure you include the following:
		* Commit SHA (usually located in a changelog or the jar name itself)
		* ForgeModLoader log
		* Server log if applicable
		* Detailed description of the bug and pictures if applicable
5. Click `Submit new issue`, and wait for feedback!
