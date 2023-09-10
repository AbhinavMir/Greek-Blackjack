> **Note:** The server code should maintain a locked instance of the CSV file to monitor movements and other game events, updating after each input for real-time synchronization -  I will be migrating to a proper database soon!

## Files

### Overview
Below is a brief description of each file and its role in the game system:

- **Battle.java:** Manages individual battles, initiated in common grounds. Local variables maintain speed.
- **ManualHandler.java:** Acts as a fallback for ReadHandler, handling potentially corrupted files.
- **Market.java:** Manages market interactions and dynamics.
- **gameData.java:** A local database storing live game data.
- **readHandler.java:** Oversees the read and write operations of data files.
- **Characters.java:** Describes the behavior and attributes of both Heroes and Monsters, extended from this base class.
- **Map.java:** Manages map behavior using a 2D array of tiles, facilitating modularity and extendibility.
- **PrettyPrint.java:** A utility class for beautified printing outputs, enhancing the visual representation.
- **itemClass.java:** Manages item storage and inventory, built with extendibility in mind.

### Detailed Descriptions

#### Maps
Every `Tile` contains essential attributes such as type, and coordinates (x and y). The `isPlayer` boolean flags player presence on a tile. The method `getTileImage()` is responsible for determining the tile representation. Maps are randomized, offering a unique layout with each game instance.

#### Heroes and Monsters
Both heroes and monsters are derived from the `Character` class. Heroes can engage in commerce in markets and have spells stored in the base class.

#### Item Class
Handles the storage of all in-game items, including potions, armors, and weapons.

#### Read and Manual Handlers
Responsible for data reading from `txt` files and storing it in the readHandler class. In case of failure, the `manualHandler` is invoked to rectify issues.

### Additional Modules
- **Market:** Allows trading using the in-game currency, Gold.
- **Battle:** Directs battles with user-defined attacks/spells targeting specific monsters.

## Game Mechanics and Rules
Outlined here are the mathematical expressions and rules governing the gameplay, including formulas for:
- Damage calculations
- HP and MP adjustments during level-ups
- Battle outcomes including experience and gold acquisition
- Item pricing

Refer to the existing documentation for the detailed breakdown of each formula.

## Compilation and Execution
To compile and run the game, follow these steps:

1. Navigate to the `src` directory with the command `cd src`.
2. Compile and execute the game with the following commands:
   ```sh
   javac -d gameUtils *.java
   cd ../gameUtils
   java Main


## Technologies Used

- **Apache Tomcat & Spring tc Server:** These servers are used to host and manage the game environment, ensuring a stable and efficient platform for the game to run on.
- **Solr:** Utilized for advanced search functionalities within the game, facilitating quicker and more precise retrievals of game objects and data.
- **SOAP:** The login system is facilitated through SOAP, ensuring a secure and standardized login process for all users. 

