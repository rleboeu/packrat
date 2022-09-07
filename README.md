# packrat
*Tired of all that cobblestone clogging your inventory? Packrat has you covered.*
## Description
Packrat is a per-player loot management Bukkit plugin for Minecraft servers. Simply type `/nopickup cobblestone` and mine away!

## Commands
### nopickup
- `/nopickup ITEM_NAME` disables looting for the specified item. Case insensitive, underscores required.
- `/nopickup hand` disables looting for the item in the player's hand.
- `/nopickup all` disables looting for all items.

### pickup
- `/pickup ITEM_NAME` enables looting for the specified item. Case insensitive, underscores required.
- `/pickup hand` enables looting for the item in the player's hand.
- `/pickup all` enables looting for all items.
- `/pickup list` shows a list to the player of what items they CANNOT loot.

## Minecraft Version
Packrat has only been tested on Spigot for MC 1.19, 1.19.1, and 1.19.2. Support for earlier or later versions of Spigot is not guaranteed.<br/>
If Packrat isn't working on your server and you're using one of the aforementioned Spigot versions, try adding `--enable-preview` to your spigot.jar launch options (`java --enable-preview -Xms#G -Xmx#G -jar spigot.jar`).
