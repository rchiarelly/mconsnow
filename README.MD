# Minecraft on ServiceNow

## THIS IS NOT YET FULLY RELEASED
I have not yet released the ServiceNow Application Files. I plan to release this fully in the near future.


### Minecraft Server Prep
- Obtaining Spigot Server Jar
    - The server will need to have Java 17 or 18, You can use OpenJDK if you prefer. You will need to setup proper environmental variables.
    - Download SpigotBuildTools (https://hub.spigotmc.org/jenkins/job/BuildTools/) Using the latest stable build should be fine, this has been tested with 1.18.2
    - Following instructions at https://www.spigotmc.org/wiki/spigot-installation/ setup the Spigot server as normal.
    - Copy the final **spigot-1.XX.X.jar** file generated from the previous step to the **Server\\** folder. We no longer need the other files created with the BuildTools
- Building Server Directory
    - After acquiring the spigot jar file and moving it to the **Server\\** folder. If you used a version different than 1.18.2 you will need to edit the **Server\runserver.bat** file to reference your version of the spigot jar.
- Generating and Editing Config Files
    - Run the server and let it load fully, this will generate the configuration files for the MCOnSNOW Plugin
    - Once it is fully loaded stop the server (run the **'stop'** command in console)
    - In the **Server\plugins\MCOnSNOW** directory there will be a **config.yml** file, you will need to update the instance and username/password used for the minecraft API account
    - To disable heartbeating to ServiceNow for picking up catalog requests set the configuration option commandHeartbeatSeconds to 0.

### ServiceNow Setup
    I haven't yet released the Application Files for ServiceNow
