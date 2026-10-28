# Litematica EasyPlaceFixPlus — changelog

English release notes as published on [Modrinth](https://modrinth.com/mod/easyplacefixplus/changelog), newest first.
The original Russian notes are in [CHANGELOG.ru.md](CHANGELOG.ru.md).

## 1.0.22 — 2026-10-26

Minecraft 26.3, 26.2, 1.21.11

#### New
- **Easy Place lights fire.** Fire and soul fire from the schematic are placed with flint and steel (without one, with a fire charge): the mod clicks the block under the cell with it, as a player would. Soul fire comes by itself when soul soil or soul sand is below.

#### Fixed
- fix — **a patterned banner was not placed while a banner of the same colour without a pattern (or with another pattern) was in hand.** Easy Place thought the right banner was already in hand and did not take it from the backpack, so the cell stayed empty until you took the banner yourself. It happened mostly with a full hotbar. Now the banner with exactly the right pattern is taken into the hand; the same for a plain banner while a patterned one is in hand.
- fix — **Easy Place did not place string.** A string cell was silently skipped in survival and creative. String is placed like a normal block now.

#### 📦 You need

Minecraft 1.21.11 · Fabric Loader · Fabric API · malilib · Litematica · Java 21

## 1.0.21 — 2026-10-24

Minecraft 26.3, 26.2, 1.21.11

#### Fixed
- fix — **Easy Place did not place the invisible Light block.** A Light block has no outline unless the Light item is in your hand, so the aim went straight through the hologram and no click happened at all. Light cells are now found, and Easy Place takes the Light item of **exactly the level the schematic wants** (it used to put level 15 where 7 was needed). No item of that level in the inventory — the cell is skipped instead of getting a wrong level. In creative the right level is picked by itself.

#### 📦 You need

Minecraft 1.21.11 · Fabric Loader · Fabric API · malilib 0.27.20 · Litematica 0.26.16 · Java 21

## 1.0.20 — 2026-10-22

Minecraft 26.3, 1.21.11

#### New
- **Look: Aell UI, Vanilla or Vanilla+.** The first settings section is now *Interface style*. **Vanilla** turns the settings window into Minecraft's own look: tiles **"Name: value"** in two columns (right-click a tile to reset it), vanilla buttons, tabs, tooltips and the click sound. **Vanilla+** shows rows like Litematica's: name on the left, a value tile and **Reset** on the right.
- With **Aell UI** chosen and **Material HUD 1.0.19+** installed, it uses Material HUD's look.

#### 📦 You need

Minecraft 1.21.11 · Fabric Loader · Fabric API · malilib 0.27.20 · Litematica 0.26.16 · Java 21

## 1.0.19 — 2026-10-13

Minecraft 26.3, 1.21.11

The first Easy Place Fix+ for Minecraft 1.21.11: the same mod as the 26.3 version, ported to Minecraft 1.21.11 and Litematica 0.26.

#### What you get
- **Every click of Litematica's Easy Place is corrected before it is sent**: slabs, stairs with their corners, logs and pillars, blocks that face somewhere, buttons and levers, doors and gates, rails, double chests, candles and sea pickles by count, flowers and grass, signs with their text.
- **Finishing with a tool you carry**: lighting candles and campfires, water from a bucket, opening doors, scraping and waxing copper, farmland and paths, stripping logs, filling shelves and bookshelves.
- **Corner stairs connect the right way**, whichever of the two equivalent ways the schematic stored them.
- **Guards**: "Block placing off the schematic" and "Do not dig the wrong thing" with its three parts (outside the schematic, correctly placed, only in Easy Place mode), with toggle keys (not bound by default).
- **Blocks with a name, description or server plugin tag** are found in the inventory and taken into your hand.
- **Give up on cells the server refuses**, a speed slider and two options for strict servers.
- Chat messages are off by default; guard toggle keys answer with a line above the hotbar.

#### 📦 You need

Minecraft 1.21.11 · Fabric Loader · Fabric API · malilib 0.27.20 · Litematica 0.26.16 · Java 21

## 1.0.18 — 2026-10-13

Minecraft 26.3

#### New: corner stairs connect the right way
A corner stair can be saved in a schematic in two ways that look exactly the same. Schematics made with Axiom, WorldEdit or pasted without block updates often use the "other" one, and the stair connected as a straight one or turned the wrong way. Now Easy Place picks the facing that gives the right corner. Every stair position and connection was checked: 712 of 712 stairs come out right (1.0.16: 672). A corner placed either way counts as done.

#### New: toggle keys
Keys for **Block placing off the schematic** and **Do not dig the wrong thing**. No keys by default: set them on the Keys tab. With Material HUD installed, both mods share one key.

#### New: "Do not dig the wrong thing" in parts
Three switches under it, all on by default: **Outside the schematic**, **Correctly placed** and **Only in Easy Place mode** (off — the guard works whenever a schematic is shown). A block that differs from the schematic only in what it adjusts to its neighbours by itself — stair corners, fence and pane connections, leaves — now also counts as correctly placed and is protected. Your own shulker boxes can always be broken, even outside the schematic.

#### Fixes
- fix — **Easy Place did not place some blocks until you took one in your hand or placed one yourself.** Litematica looked for the block in your inventory only with exactly the same item data, so a block with a name, a description or a server plugin tag was not found. Now it is found and taken into your hand like any other.

#### 📦 You need

Minecraft 26.3 · Fabric Loader · Fabric API · malilib · Litematica · Java 25

## 1.0.17 — 2026-10-10

Minecraft 26.3

#### New: "Give up on cells the server refuses"
A toggle on the Easy Place tab, on by default. After six refusals from the server, Easy Place stops clicking that cell until the block in it changes, and says so in chat once.

An empty cell never changes, so a lectern you keep breaking and placing to reroll villager trades could get stuck. Turn the toggle off and refusals are not counted: Easy Place keeps clicking and writes nothing to chat. The refusal message now points at this toggle.

#### Fixes
- fix — **A cell Easy Place gave up on could not be filled by hand either.** The ban now applies only to Easy Place clicks.

#### 📦 You need

Minecraft 26.3 · Fabric Loader · Fabric API · malilib · Litematica · Java 25

## 1.0.16 — 2026-10-10

Minecraft 26.3

#### New: iron guards
Two options on the Easy Place tab, both off by default. They work for every block you place, by hand too, and only inside the schematic:
- "Block placing off the schematic": nothing goes into an empty schematic cell;
- "Block placing the wrong block": a cell takes only the block the schematic wants.

Outside the schematic everything places as usual, and chests, doors and levers still open.

#### New: snow layers from any side
Vanilla adds a snow layer only when you click the top of the snow. Now you can look at the snow from the side or from below, and Easy Place clicks its top for you.

#### New: pumpkins carved to match the schematic
"Finish blocks with tools" carves a pumpkin with shears, with the face on the same side as in the schematic.

#### Fixes
- fix — **On servers, Easy Place did not place cushions on snow 5–8 layers deep** ("the server rejected it three times"). For cauldrons, hoppers and composters Easy Place now clicks a spot of the bottom you can see, or asks you to stand higher, instead of leaving the cushion on the rim. An armor stand on snow with no blocks around is placed too.
- fix — **Fast building on a server sometimes placed the wrong block.** Taking a block from the backpack into the hand now waits for the server to confirm the previous placement. Blocks in the hotbar keep full speed; singleplayer is unaffected.
- fix — **Candles, turtle eggs, sea pickles, petals, leaf litter and snow layers did not reach the required count.**
- fix — **A ring of curved rails was never placed.**
- fix — **"Fill blocks" never filled a cauldron.** Water now goes in bottle by bottle up to the level (a full one may take a bucket), lava and powder snow by bucket.
- fix — **In creative, finishing that needs a tool did nothing** (books into a chiseled bookshelf, bone meal, flint and steel, shears, axe…).
- fix — **With Material HUD's "Only blocks from inventory" mode, block finishing did not run.** Fixed together with Material HUD 1.0.5.

#### 📦 You need

Minecraft 26.3 · Fabric Loader · Fabric API · malilib · Litematica · Java 25

## 1.0.15 — 2026-10-09

Minecraft 26.3

#### New: "Hide blocks without support"
Easy Place tab, "Holograms and support" section, off by default. Holograms of blocks that have nothing to hold on to right now are not drawn: a hanging sign without a ceiling or a wall, glow berries and pale hanging moss with nothing above, a vine in the air, a torch without a floor. A chain counts as held when its first link is. Easy Place aims through these cells, so they never block the hologram behind them. Build the support and the hologram appears by itself.

#### New: "Support from the next layer"
Same section, off by default. When a block of the visible layer hangs on a schematic block in the next layer (a hanging sign on one layer, the ceiling above it on the next), that support block is shown together with the layer and Easy Place can place it, although it is outside the layer.

With Material HUD installed, both options live on its Highlight tab.

#### Fixes
- fix — **A patterned banner with a name or description was not taken from the inventory.** Now any banner with the right pattern is used. The same goes for decorated pots and heads.
- fix — **With Material HUD's "Only blocks from inventory" mode, Easy Place could aim at an invisible hologram** in front of a visible one. Hidden holograms are now passed through.

#### 📦 You need

Minecraft 26.3 · Fabric Loader · Fabric API · malilib · Litematica · Java 25

## 1.0.14 — 2026-10-08

Minecraft 26.3

- fix — **Redstone dust dots.** Easy Place placed single redstone dust as a cross even when the schematic had a dot. A cross powers the blocks around it and a dot does not, so circuits could behave differently. The dot is now made right after placing (and a cross from a dot, if the schematic wants a cross).
- fix — **Left click while setting a key** in the settings screen works again on Minecraft 26.3.

#### 📦 You need

Minecraft 26.3 · Fabric Loader · Fabric API · malilib · Litematica · Java 25

## 1.0.13 — 2026-10-05

Minecraft 26.3

- **Fix: observers, pistons, dispensers, droppers and barrels now face the way the schematic says.** Easy Place turned the player for them, but the server reads these blocks' facing from the head, which catches up with a new angle only on the next server tick — so they were placed facing wherever you were actually looking. The mod now gives the server that tick before clicking. Only cells you look at from the wrong side wait (a couple of ticks); the next blocks with the same angle go without delay. Works with *Pose one tick ahead* on or off.

#### 📦 You need

Minecraft 26.3 · Fabric Loader · Fabric API · malilib · Litematica · Java 25

## 1.0.12 — 2026-10-03

Minecraft 26.3

Settings screen update, shared with the other Aell_ Litematica mods:
- **Esc unbinds a hotkey** while capturing; a second click on the field brings the previous key back, the reset button gives the default.
- The settings panel grows to fit every tab name, up to two thirds of the window.
- **Fix:** the search icon is a gold magnifier aligned with the text, and the hint disappears once you click the field.

#### 📦 You need

## 1.0.11 — 2026-10-01

Minecraft 26.3

#### New: entities from the schematic
- **Easy Place now places armor stands, cushions, item frames, paintings, end crystals and minecarts.** Look at the entity's ghost and press your Easy Place key. Item frames also get their item and rotation.
- Paintings: the game picks the picture by itself, so it may differ from the schematic. Boats are not placed.

#### New: finishing plants
- Easy Place **trims vine tips with shears** so they stop growing, **adds glow berries with bone meal** and **grows shelf mushrooms**. Keep shears and bone meal in your inventory.

#### New: powered shelves
- **Powered shelves** (the solid panel look) are filled from the schematic too: the mod lays out your hotbar and swaps it into the shelves in one click.
- Unpowered shelves now get **exactly one item per slot** instead of your whole stack.

#### Fixes
- Glow berries, weeping/twisting vines and kelp: every block of the column is now placed and counted, not just the tip.
- Easier to aim at thin vine ghosts — build a vine column just by looking at it.

New setting, on by default: **Place entities** (Easy Place tab).

## 1.0.10 — 2026-09-22

Minecraft 26.3

First build for 26.3: input moved from GLFW to SDL, sign editing and tool conversions (shovel / hoe / axe are data-driven `block_transformer` rules now) were ported. Needs Litematica 0.29.0+ and malilib 0.30.1+.

#### Fixes

- **The second half of a slab is placed again.** A cell asking for a double slab with a half standing there was counted as unfixable and skipped by the aim; completing the slab with a second click now works. Checked on 25 cells of seven slab kinds.
- **No more silence when a banner with a specific pattern is needed.** Patterns and pot sherds live in the block entity, so Litematica needs the exact item and otherwise places nothing while the material list shows the cell as covered. The mod now says once per cell which item is actually needed.

#### 📦 You need

Minecraft 26.3 · Fabric Loader · Fabric API · malilib · Litematica · Java 25

## 1.0.6 — 2026-09-16

Minecraft 26.2

_No notes._

## 1.0.5 — 2026-09-16

Minecraft 26.2

#### New

- **"Dirt instead of grass".** When the schematic asks for a grass block and you have none, the mod places plain dirt. Off by default.

## 1.0.4 — 2026-09-13

Minecraft 26.2

- **Easy Place no longer drops a spare block one cell up.** The solver compares against the schematic only on properties a click can set and forgives the ones the surroundings decide: stair shape, `snowy` on grass, `distance` and `persistent` on leaves, fence connections. The "is there work here" test read any difference as work, so an already-placed stair whose neighbour was still missing looked unfinished: the mod lifted Litematica's two-second hold, the click landed on an occupied cell, and vanilla placement pushed it one cell past the face, usually upwards. 110 block types were affected: 64 stairs, 12 kinds of leaves, three soils and odds and ends. A cell whose only difference lies beyond what placement can set now counts as done, and a second guard cancels any automated click into a cell that already holds what the schematic asks for. Candles, sea pickles and snow layers still count as work.
- **The placement rate now covers state repairs too.** Finishing a door, trapdoor, candle, repeater or shelf ran before "blocks per second" and, in a tick where a placement had already gone out, added a second interaction and another pair of rotation packets, exactly what a server-side timer anticheat watches. Repairs and placements now share one budget: one action per interval.

#### 📦 You need

Minecraft 26.2 · Fabric Loader · Fabric API · malilib · Litematica · Java 25

## 1.0.3 — 2026-09-02

Minecraft 26.2

- **Three toggles removed — their behaviour is now always on:** "Open doors, set levers", "Fill shelves", "Cycle by clicking" (was off by default). They were safety switches while the features were new; old config values are simply ignored.

#### Fixed

- **Easy Place no longer places a spare fence on top of the one it just placed** (same for walls, panes, bars and redstone wire). A placed block that differs from the schematic only by its neighbour connections now counts as done, and a click displaced off an occupied cell into a cell the schematic leaves empty is cancelled.

#### 📦 You need

Minecraft 26.2 · Fabric Loader · Fabric API · malilib · Litematica · Java 25

## 1.0.2 — 2026-08-31

Minecraft 26.2

- **Easy Place: a skipped cell is no longer silent.** With "Skip what would go in wrong" enabled, the mod cancels the click when it cannot place the block in the orientation the schematic asks for — most often an orientable block such as a log. Litematica counts the cell as handled either way and holds it for two seconds, so from the outside it looked as if the mod had simply stopped placing there. It now says so in chat, once per cell, and the reason goes to "Easy Place debug".

#### 📦 You need

Minecraft 26.2 · Fabric Loader · Fabric API · malilib · Litematica · Java 25

## 1.0.1 — 2026-08-29

Minecraft 26.2

- **No more bursts of clicks.** Placement speed is paced in client ticks instead of wall-clock milliseconds, so at most one automatic click leaves per tick at any "blocks per second" setting — server timer anticheats stop seeing packet bursts. Multi-step blocks (repeater delay, comparator mode, note block pitch) are stepped one press per pass, so a row of them is finished round by round instead of in one flood.
- **The "EasyPlace+..." button on Litematica's Render Layers screen** no longer overlaps the new "All" tab added in Litematica 0.28.6. The tab row is now measured from the screen itself, so the layout is right on new and older Litematica alike.

#### 📦 You need

Minecraft 26.2 · Fabric Loader · Fabric API · malilib · Litematica · Java 25

## 1.0 — 2026-08-16

Minecraft 26.2

_No notes._

