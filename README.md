Plan
1) Get the code so far working, so we have a character moving around on the map

2) Transfer the code to Java (?)

3) Design the game’s core structure in a UML diagram. Should have support for entities, blocks, and items in the game, as well as services for Graphics, Audio (optional for now, focus on other things), etc. Use the designs at gameprogrammingpatterns.com for organizational patterns, especially components for the entities and items (and some blocks, chests will need inventory components), and service locators for the graphics and audio services. Audio and Graphics should each be on a separate thread.

4a) Add three-dimensional functionality to the game. At this stage, the goals should be to have the following;
	a) The graphics should support voxels drawn to the screen from a chosen camera location. Look at Let’s Make a Voxel Engine for inspiration.
	b) The world system, and related classes, should support loading and unloading chunks of blocks into and out of the game. Again, there are many sources online on how to handle chunk loading.
	c) The character should be able to walk around and the camera should follow them.
	d) The character interacts with the world properly, i.e. the physics component properly handles collisions between the player and the ground, and velocity and position work correctly.

4b) Alternately, keep the game top-down 2D with multiple z-levels, and modify the steps in 4a appropriately. If we have to sacrifice some interesting behavior to save the framerate in the 3D version, at least we will have a 2D version.

5) After this, adding some specific blocks (and modifying the world class to appropriately distinguish between block types, and make appropriate calls to the graphics system to render each of them properly), should be a priority. Introduce a system for clicking on blocks to interact with them, as well as a player inventory menu and a few items. Try chopping down trees with an axe. There should be a generic system in place for describing how blocks and items used on them interact to create new items and blocks.





WORLD GENERATION

1) Create a HEIGHT MAP using modified perlin noise. The world is large, but finite, and has poles, an equator, etc.

2) Modify height map to represent the results of plate tectonics, specifically the structures created by the Wilson cycle (which will determine stone levels, mountain biomes, and ocean biomes) at http://csmres.jmu.edu/geollab/fichter/Wilson/Wilson.html

4) Create overlays that determine environmental factors like heat and rain. They are all per block during world generation, so that the biome generation will be smooth, but after they have served their purpose in making biomes they will be averaged over chunks with one value per chunk. This way, the biomes will look nice and have smooth edges, but we won’t have to waste space keeping track of individual values per block.
	Alternately, and perhaps a better option, is to have the values and even biomes per chunk from the start, and give the illusion of smoothness by labeling the interfaces between two biomes as ‘transition biomes’ which have intermediate properties between the biomes they are adjacent to. This saves a lot of space and doesn’t sacrifice too much aesthetics.

Before we determine climate characteristics, our world is only described by HEIGHT MAP, LATITUDE (y), LONGITUDE(x), ALTITUDE(z), TIME OF DAY, and TIME OF YEAR. From these and perlin noise we can make maps of: 

	AVERAGE HEAT AT SEA LEVEL (in degrees) is determined by perlin noise and latitude. It gets hotter near the equator and colder near the poles.
	AVERAGE WIND is determined by perlin noise and latitude. The values it can take are just north polar, north polar front, north Ferrel, north horse latitude, north Hadley, inter-tropical convergence zone, south Hadley, south horse latitude, south Ferrel, south polar front, and south polar. 
	AVERAGE RAINFALL (in fraction of year it rains) is determined by perlin noise, the height map, and the average wind map. For example, it is heavy in the inter-tropical zone but almost zero in the horse latitudes. It is increased downwind of oceans and upwind of mountains, and decreased downwind of mountains.
	AVERAGE INSOLATION (in energy?) is determined by latitude and average rainfall. It is high near the equator and low near the poles.

	From the above values, we can create a map of BIOMES. The above values and the biomes are characteristics of each block (or chunk) that the game keeps track of and saves. They are immutable throughout the game. However, the CURRENT values of all these characteristics change throughout the game, and are determined when needed by the world system.

	SEASON is determined by the biome and the time of year. It can be either SPRING, SUMMER, FALL, WINTER, WET, DRY, OR NONE. It changes at most four times per year.
	CURRENT HEAT depends on altitude (colder as you go up), the time of day (colder at night), and the season, and changes four times a day (3AM, 9AM, 3PM, 12PM).
	CURRENT WIND depends on the average wind, and randomly changes daily.
	CURRENT RAINFALL depends on the average rainfall, the time of the year and season type, and changes each week. The game knows when it will rain for the next week, and changes cloud cover to match it. This means the clouds increase / become darker as it approaches the time it will rain. It can be either light, heavy, or thunderstorm.
	CURRENT INSOLATION depends on the average, as well as the time of day, the time of year, and the cloud cover.

6) Modify the height map again to artificially flatten plains and desert biomes, as well as to add rivers and lakes through erosion. Around rivers in dry climates, add a river biome around them.

7) Add caves.

8) Add in different layers of stone, dirt, clay, loam, peat, etc. depending on the structures created during the wilson cycle, the biomes, and rivers.

9) Populate the world with trees, plants, etc.

10) Add cities and villages.

11) Add animals and other mobile enemies only when the player first visits the area.


BLOCKS
There should be blocks for trees, plants, and grasses, as well as rocks, ores, water, lava, and other materials. There will also be blocks built by the player or generated in the villages, including wooden planks and walls, log piles, stone walls, bricks, windows, floors, etc. There are also work stations such as campfires, furnaces, bloomeries, etc. Some structures may encompass multiple blocks, such as wagons and boats, and the blocks attached to them will all move together.

The blocks should all be stored in the world map in chunks, and only update under certain conditions. Stone, ore, and furniture etc. never update, but plants grow and die at given rates. Instead of keeping track of individual lifetimes of each crop, each chunk chooses a random plant to age at a random time (with a given distribution). Plants (or other blocks) with shorter lifespans need fewer ‘age points’, meaning the aging process does not need to distinguish between different types of age-able items. And a plant may reach maturity at 7 age points, but not die until 1000 age points, for example.

Blocks within a certain radius of the player may also update (useful for cave-ins, traps, etc.), and they can update when interacted with by a player. For example, a block which is clicked on by the player while holding a certain tool will be replaced with another block, and drop an item.

ENTITIES

