# Changelog

## 1.0.22+26.2 — 26.10.2026

**RU**

**fix — флаг с узором не ставился, если в руке был флаг того же цвета без узора (или с другим узором).** Easy Place думал, что нужный флаг уже в руке, и не брал его из рюкзака — клетка оставалась пустой, пока флаг не возьмёшь сам. Чаще всего так бывало с забитым хотбаром. Теперь в руку берётся флаг именно с нужным узором; так же и наоборот — простой флаг, когда в руке узорный.

**fix — Easy Place не ставил нить.** Клетка с нитью молча пропускалась и в выживании, и в творческом. Теперь нить ставится, как обычный блок.

**new — Easy Place зажигает огонь.** Огонь и огонь душ из схемы ставятся огнивом (нет огнива — огненным зарядом): мод щёлкает им по блоку под клеткой, как сделал бы игрок. Огонь душ получается сам, если внизу земля или песок душ.

**EN**

**fix — a patterned banner was not placed while a banner of the same colour without a pattern (or with another one) was in hand.** Easy Place thought the right banner was already in hand and did not take it from the backpack, so the cell stayed empty until you took the banner yourself. It happened mostly with a full hotbar. Now the banner with exactly the right pattern is taken into the hand; the same for a plain banner while a patterned one is in hand.

**fix — Easy Place did not place string.** A string cell was silently skipped in survival and creative. String is placed like a normal block now.

**new — Easy Place lights fire.** Fire and soul fire from the schematic are placed with flint and steel (without one, with a fire charge): the mod clicks the block under the cell with it, as a player would. Soul fire comes by itself when soul soil or soul sand is below.

## 1.0.21+26.2 — порт на Minecraft 26.2 (25.10.2026)

**RU**

Тот же 1.0.21, собранный под 26.2 (Litematica 0.28.8, malilib 0.29.6) — дерево `litematica-easy-place-fix-plus-26.2`.
На 26.2 до этого была только 1.0.6. Код — дерево 26.3 (= выложенная 1.0.21); описание и переводы — из выложенного jar 1.0.21.
Отличия API 26.2 перенесены из Material HUD 26.2 трёхсторонним слиянием по таблице имён (скрипт
`~/projects/_port-1.21.11-26.2/epf262/merge.py`): `Interactive`, `SoilDig`, `SignEditor` + `MixinClientPacketListenerSign`,
`StateFix`, `EntityPlace`, `SchematicEntities`, `ToolTables` (вариант 26.2 — аксессоры `IMixinShovelItem/HoeItem/AxeItem`,
добавлены в `easyplacefixplus.mixins.json`); `aell-ui` берёт замены из `../aell-ui-26.2`.

Проверено: байткод `core` и `mixin/easyplace` против Material HUD 1.0.21+26.2 — отличия «EPF+ против MHUD» те же, что у
выложенных 1.0.21+26.3 (`bytecmp2.py`); харнесс Material HUD 26.2 с этим jar в `run/mods` (копия стенда
`Beta FIX/_port-mhud-stand-26.2`): `h` 89/0, `light` OK, `f` 156/0, `j` 168/0; снимки настроек в трёх стилях (`EPF_SHOTS=1`).

**EN**

The same 1.0.21 built for Minecraft 26.2 (Litematica 0.28.8, malilib 0.29.6). Until now 26.2 only had 1.0.6.

## 1.0.21+26.3 — 24.10.2026

**RU**

**fix — Easy Place не ставил невидимый блок света.** У блока «Свет» нет контура, пока предмет не в руке, и прицел проходил сквозь голограмму — клика не было вовсе. Теперь клетка со светом находится, а в руку берётся «Свет» ровно нужного уровня (раньше вставал уровень 15 вместо 7). Нужного уровня в инвентаре нет — клетка пропускается, а не заполняется чужим уровнем. В творческом нужный уровень берётся сам.

**EN**

**fix — Easy Place did not place the invisible Light block.** A Light block has no outline unless the item is in hand, so the aim went straight through the hologram and no click happened at all. The light cell is now found, and the Light item of exactly the right level is taken (it used to place level 15 instead of 7). With no item of that level in the inventory the cell is skipped instead of getting a wrong level. In creative the right level is picked by itself.

## 1.0.20+26.3 — 22.10.2026

**RU**

**new — оформление: Aell UI, Vanilla или Vanilla+.** Первый раздел настроек — «Стиль интерфейса». Vanilla — окно настроек в виде Minecraft: плитки «Название: значение» в две колонки (правый клик — сбросить), ванильные кнопки, вкладки, подсказки и щелчок кнопки. Vanilla+ — строками, как у Litematica: название слева, справа плитка значения и «Сброс». При «Aell UI» и установленном Material HUD 1.0.19+ берётся его оформление.

**EN**

**new — Look: Aell UI, Vanilla or Vanilla+.** The first settings section is now "Interface style". Vanilla turns the settings window into Minecraft's own look: tiles "Name: value" in two columns (right-click to reset), vanilla buttons, tabs, tooltips and click sound. Vanilla+ shows rows like Litematica's: name on the left, value tile and Reset on the right. With "Aell UI" and Material HUD 1.0.19+ installed, it uses Material HUD's look.

## 1.0.17+26.3 — 10.10.2026

**RU**

**new — тумблер «Бросать клетку после отказов сервера» (по умолчанию вкл).** Раньше после шести подтверждённых отказов сервера клетка бросалась, пока в ней не изменится блок, — а в пустой клетке он не меняется: игрок, перебирающий торги жителя кафедрой, больше не мог поставить её на то же место. Выключенный тумблер отказы не считает: клетку не бросаем, в чат не пишем (`easyPlaceGiveUpRefused`, общий с Material HUD). Сообщение об отказе подсказывает этот тумблер. Проверено харнессом Material HUD `LL_COVERAGE=refuse` с этим jar в `run/mods` — 7/7.

**fix — брошенная клетка не давала поставить блок и руками.** Запрет после отказов относится только к кликам Easy Place: обычный правый клик без Easy Place ставит блок как всегда.

**EN**

**new — "Give up on cells the server refuses" toggle (on by default).** After six confirmed server refusals a cell was given up until its block changed — and an empty cell never changes, so a lectern for rerolling villager trades could not be placed there again. With the toggle off, refusals are not counted and nothing is written to chat (`easyPlaceGiveUpRefused`, shared with Material HUD). The refusal message points at this toggle.

**fix — a given-up cell blocked placing by hand too.** The ban applies only to Easy Place clicks.

## 1.0.16+26.3 — 10.10.2026

**RU**

**new — железные защиты: «Защита от установки мимо схемы» и «Защита от не того блока» (обе по умолчанию выкл).** В отличие от «Только по схеме» (та гасит только клики Easy Place) — для любой установки блока, и руками тоже, и только в пределах схемы: внутри коробок включённых регионов включённых размещений, за границей ставится всё, как обычно. «Мимо схемы» — в пустую клетку схемы не встаёт ничего; «Не тот блок» — в клетку, где схема ждёт блок, встаёт только он (сверка как в «Только по схеме», с горшками, землёй под дёрн и т. п.). Клетка, куда блок ляжет, считается ванильным `BlockPlaceContext`. Клик гасится до отправки на сервер, над хотбаром — «Мимо схемы: …» или «Не тот блок: …». Правый клик без приседа по сундуку, двери, рычагу и прочему, что съедает клик, проходит как обычно; мир схемы в клетке не загружен — защита молчит. Код — `core/StrictGuard`. Проверено харнессом Material HUD `LL_COVERAGE=strict` с этим jar в `run/mods`: четыре прохода (только одна, только другая, обе, обе выкл), сундук с камнем в руке открывается.

**fix — на скорости иногда вставал не тот блок (сервер, 10 блоков/с).** Причина — гонка между установкой и обменом предмета в руку. Блок A поставлен из руки — сервер потом пришлёт «в этом слоте A, на штуку меньше». Пока ответ в пути, Litematica уже меняет в руке A на B кликом по инвентарю (блок лежит в рюкзаке, не в хотбаре) и ставит B. Запоздавший ответ про A перезаписывает у клиента руку: клиент думает, что держит A, сервер — что B; следующий A клиент ставит «без обмена» — а сервер ставит B. Стенд Material HUD `LL_COVERAGE=sync` (с этим jar в `run/mods`) воспроизводит это задержкой пакетов: пары «A, B, A, B» из рюкзака, пинг 150 мс — 5–6 не тех блоков из 24. Теперь обмен в руку (`swapItemToMainHand` malilib) ждёт подтверждения сервера на прошлую установку Easy Place (`ClientboundBlockChangedAckPacket` по номеру пакета; обновление слота руки сервер 26.3 рассылает в тике мира, раньше подтверждения, — к подтверждению ответ про руку уже дошёл); подтверждения нет 1,5 с — идёт как есть. Установка Easy Place ждёт два пинга после чужого клика по инвентарю (автопополнение Inventory Profiles Next и т. п.); свой обмен Litematica и перекладки для полок установку не задерживают. На стенде с защитой — 0 не тех из 24. Стенд при пинге 100 мс и 10 типах блоков: без защиты 6–10 не тех из 24 (10 и 20 блоков/с), с защитой — 0; когда блоки в хотбаре, скорость почти полная (9,4 и 15,6 блоков/с), когда каждый блок идёт обменом из рюкзака — около 6 блоков/с (один пинг на обмен). В одиночной игре — без ожиданий. Если сервер подтверждений не шлёт вовсе (старый сервер за ViaVersion) — обмен ждёт по пингу, как раньше, а не по 1,5 с.

**new — тыква вырезается ножницами по схеме.** В схеме вырезанная тыква, в мире целая — «Доводить блоки инструментом» режет её ножницами, кликом по грани, куда в схеме смотрит лицо (`Conversions.pumpkin`, план с заданной гранью `StateFix.Fix.clickFace` — точка на грани по взгляду). Общий код с Material HUD 1.0.5.

**fix — с Material HUD в режиме «только блоки из инвентаря» не работала доводка.** Material HUD прятал клетку, если предмета блока схемы нет в инвентаре, — горящий костёр для доводки прицела становился пустым местом. Исправлено в Material HUD 1.0.5: клетка, которую можно довести инструментом из инвентаря, не прячется, и мост `EasyPlaceBridge.ghostHidden` отвечает «видна». Здесь — подсказка опции.

**fix — свечи, яйца черепахи, морские огурцы, лепестки, сухие листья и снег не добирались до нужного количества.** Litematica, прежде чем взять нужный предмет в руку, спрашивает «можно ли дополнить клетку» ванильным `canBeReplaced` — по предмету, который в руке СЕЙЧАС. Стопка кончилась или в руке другой блок (свеча другого цвета, андезит) — ответ «нельзя», нужный предмет даже не берётся, и клетка навсегда остаётся недобранной: поставил по свече, отошёл к другим блокам, вернулся — добрать уже не выходит. Теперь эта проверка для счётных клеток идёт с предметом, который клетке нужен; его Litematica и берёт в руку следом. Стенд Material HUD `LL_COVERAGE=build` (с этим jar в `run/mods`) (стройка «Тестовой схемы» Easy Place «как игрок») с мелкими стопками по 3 и пингом 60 мс воспроизводил: свеча 3 из 4, яйца 3 из 4, полевые цветы 2 из 3 — с правкой всё добирается.

**fix — в креативе не доводились блоки, которым нужен инструмент.** Книги в резную полку, костная мука на пещерные лозы и трутовик, огниво, ножницы, топор, лопата, соты, ведро воды: инструмент искался только в инвентаре, а в креативе его там обычно нет — доводка не планировалась вовсе. Теперь в креативе инструмент берётся «из ничего», как блоки берёт pick-block Litematica.

**fix — кольцо поворотных рельсов не ставилось вовсе.** Поворот без соседей встал бы прямым, поэтому решатель (при «Пропускать то, что встанет неверно») ждал соседа; в кольце (2×2 поворота, кольцо вокруг блока редстоуна) все ждали друг друга, и не вставал ни один. Теперь рельс ждёт только прямого соседа — тот встаёт верно сам; поворот ставится сразу, следующие подключаются к нему, и ванильная доводка формы загибает его. Стенд Material HUD `LL_COVERAGE=build`: оба кольца «Тестовой схемы» встали.

**fix — «Наполнять блоки» не наливал котёл.** Под котёл с водой Easy Place ставит пустой котёл — у налитого своего предмета нет, — а доливка умела только добавлять уровень в уже налитый, и пустой оставался пустым навсегда. К тому же вода шла только ведром — сразу уровень 3. Теперь пустой котёл доводится: вода бутылками по уровню (полный можно и ведром), лава и рыхлый снег — своими вёдрами. В креативе бутылка и вёдра берутся «из ничего».

**new — снег слоем с любой стороны.** Ваниль добавляет слой снега, только если кликнуть по верхней грани. Теперь, когда игрок смотрит на снег сбоку или снизу, Easy Place сам кликает по верху этой клетки, а железные защиты и проверка Litematica «можно ли дополнить клетку» такой клик пропускают. Стенд Material HUD `LL_COVERAGE=build` (с этим jar в `run/mods`) с подходом строго сбоку: все 8 клеток снега (1–8 слоёв) «Тестовой схемы» — как в схеме.

**fix — Easy Place не ставил подушку на снег в 5–8 слоёв на сервере («сервер трижды не принял установку»).** Снег у игры «заменяемый», и клик по нему мод уводил в блок под снегом — точка клика выходила дальше блока от середины кликнутого блока, а сервер такие клики молча выбрасывает. В одиночной игре этого не видно: там Litematica снимает с сервера эту проверку. Теперь клик идёт по клетке прямо под подушкой, что бы в ней ни лежало. В котёл, воронку и компостер сервер кладёт подушку туда, куда упирается взгляд игрока: мод кликает по точке дна, которую видно, а если дна не видно — пишет «встаньте выше» и не кликает (раньше подушка вставала на край воронки или не вставала вовсе); подушка на краю вместо дна больше не считается поставленной. Стойка на снегу без соседних блоков теперь тоже ставится. Стенд Material HUD `LL_COVERAGE=cushion` (с этим jar в `run/mods`) с проверкой клика как на сервере: подушки «Тестовой схемы» на снегу 1–8 слоёв, на люке, в клетках забора и калитки, подушки в котле, на его краю, в воронке и компостнице, стойка на снегу — 16 из 16 и ни одной лишней; до правки — 7 промахов и подушка на краю воронки.

**EN**

**new — iron guards: «Block placing off the schematic» and «Block placing the wrong block» (both off by default)**: for every block placement, by hand too, and only within the schematic — the first keeps empty schematic cells empty, the second lets a cell take only the block the schematic wants; outside the schematic everything places as usual; chests, doors, levers still open.

**fix — fast building on a server sometimes placed the wrong block.** A late server update for the hand slot (from the previous placement) overwrote the item Litematica had just swapped in from the inventory, so the client and server disagreed about the held item. Swapping an item into the hand now waits for the server to confirm the previous Easy Place click; Easy Place clicks wait briefly after other mods' inventory clicks. Blocks in the hotbar keep full speed; singleplayer is unaffected.

**fix — candles, turtle eggs, sea pickles, petals, leaf litter and snow layers did not reach the required count.** Before taking the needed item, Litematica asks the vanilla «can this cell be added to» check using the item currently in hand; with an empty hand or a different block it said no and never took the right item. The check now uses the required item.

**fix — in creative, finishing that needs a tool did nothing** (books into a chiseled bookshelf, bone meal on cave vines, flint and steel, shears, axe…): the tool is now conjured like Litematica's creative pick-block.

**fix — a ring of curved rails was never placed**: every curve waited for its neighbours; rails now wait only for straight neighbours, curves are placed right away and vanilla bends them as neighbours arrive.

**fix — «Fill blocks» never filled a cauldron**: Easy Place places an empty cauldron and the fill step did not handle it; water now goes in bottle by bottle up to the level (a full one may take a bucket), lava and powder snow by bucket.

**new — snow layers from any side**: looking at snow from the side or below, Easy Place clicks the top of that cell, so layers are added without looking down at it.

**fix — on servers Easy Place did not place cushions on snow 5–8 layers deep ("the server rejected it three times").** Snow counts as replaceable, so the click went to the block under it and landed more than a block from that block's centre, which servers silently drop (singleplayer skips that check). The click now goes to the cell right under the cushion. For cauldrons, hoppers and composters the server drops the cushion where your line of sight lands, so Easy Place clicks a visible spot of the bottom or asks you to stand higher, instead of leaving the cushion on the rim. An armor stand on snow with no neighbouring blocks is placed too.

**new — pumpkins are carved with shears to match the schematic**, face on the schematic's side.

**fix — with Material HUD's "only blocks from inventory" mode, block finishing did not run** (fixed in Material HUD 1.0.5: a standing block you can finish with a tool you carry is no longer hidden).

## 1.0.15+26.3 — 09.10.2026

**RU**

**new — «Скрывать блоки без опоры» и «Опора с соседнего слоя».** Те же опции, что в Material HUD 1.0.4, — для тех, кто ставит Easy Place Fix+ без него (раздел «Голограммы и опора» на вкладке Easy Place, обе по умолчанию выключены). Первая не рисует голограммы блоков, которым в мире не на чем держаться (подвесная табличка без потолка, светящиеся ягоды и бледный мох без блока сверху, лиана в воздухе, факел без пола; цепочка держится, если держится её начало), и прицел Easy Place проходит такие клетки насквозь. Вторая в режимах со слоями показывает блок соседнего слоя, на котором держится блок видимого, и даёт поставить его Easy Place, хотя он вне слоя. Код — копия модуля Material HUD (`core/GhostSupport`, `mixin/ghost/*`); с установленным Material HUD копия молчит, а тумблеры здесь показывают, что настройка — в Material HUD.

**fix — с Material HUD в режиме «только блоки из инвентаря» доводка прицела выбирала невидимую голограмму.** Мелкая голограмма (цветок, свеча), которую Material HUD не рисует — предмета нет в инвентаре или блоку не на чем держаться, — перехватывала клик у видимой за ней. Теперь доводка прицела (`AirTarget`) спрашивает Material HUD через мост (`EasyPlaceBridge.ghostHidden`) и проходит такую клетку насквозь. Без Material HUD ничего не меняется.

**fix — баннер с узором не доставался из инвентаря, если у него есть имя или описание.** Litematica ищет предмет ванильным `findSlotMatchingItem` — по совпадению всех компонентов, а мод требует только узор: баннер с тем же узором, но с именем (с сервера, из сундука клана) в руку не вставал, и клик не уходил. Теперь мод отдаёт Litematica копию предмета игрока с нужным узором (`ItemData.owned`, общий с Material HUD) — поиск и выбор руки срабатывают. То же у горшков с черепками и голов. Проверено харнессом Material HUD `LL_COVERAGE=x` с этим jar в `run/mods`.

**EN**

**new — "Hide blocks without support" and "Support from the next layer"**, the same options as in Material HUD 1.0.4, for players using Easy Place Fix+ without it. With Material HUD installed, its own options are used.

**fix — with Material HUD's "only blocks from inventory" mode, aim assist could pick an invisible hologram.** Aim assist now asks Material HUD whether a cell's hologram is hidden and passes through it.

**fix — a patterned banner was not taken from the inventory when it had a name or lore.** Litematica matches all item components; the mod now hands it a copy of the player's banner with the required pattern, so picking and hand selection work. Same for decorated pots and heads.

## 1.0.14+26.3 — 08.10.2026

**RU**

**fix — провод-точка вставал крестом.** Одиночный редстоуновый провод из руки встаёт крестом, а точкой становится только от клика рукой — Easy Place этот клик не делал: разница выглядела как «соединения досчитаются сами», и клетка считалась готовой. Крест запитывает блоки сбоку, точка — нет, поэтому схема с точками работала иначе. Теперь точка из схемы доводится кликом сразу после установки (как задержка повторителя), обратный случай — тоже (`StateFix.wireShape`, общий с Material HUD 1.0.1). Проверено харнессом Material HUD `LL_COVERAGE=r` с этим jar в `run/mods`: редстоун-схема из 44 клеток — 89/89 (1.0.13 — две точки крестом); `LL_COVERAGE=j` — 168/168.

**Внутреннее — переписаны три места, слишком близкие к чужому коду (проверка прав 06.10.2026).** Выбор инструмента
(`ToolPick`) — своя схема «гонки с запасом» вместо структуры, похожей на ToolSwitcher форка EasyPlaceFix; правила формы
рельсов (`Rails`) — своя таблица данных вместо повтора ванильного `RailState`; порядок сторон по взгляду
(`SimPlaceContext`) — свой порядок вместо копии `Direction.orderedByNearest`. Поведение то же: байткод совпадает с
переписанными классами Material HUD 0.0.26, у которых выгрузка харнесса `t` побайтно равна старому коду; харнесс
Material HUD `LL_COVERAGE=j` с этим jar — 84/84, `look.csv` совпадает с 1.0.13.

**fix (26.3) — левый клик при вводе клавиши.** Общая библиотека aell-ui сравнивала кнопку мыши с «0» — в 26.3 (SDL) это
уже не левая кнопка; теперь сравнение идёт с `InputConstants.MOUSE_BUTTON_LEFT`. В библиотеке появилась вкладка-ссылка
(`Model.Tab.opens`) — Easy Place Fix+ её не использует.

**«Призраки» в текстах названы голограммами, как в Litematica.** В русском переводе самой Litematica блоки схемы
называются голограммами («Размещённые голограммы» в её главном меню); так теперь и в подсказке про сущности в Easy Place,
в README, журнале изменений и комментариях кода. Ключи языка и опции не менялись — настройки не сбрасываются.

**EN**

**fix — redstone dust dots were placed as crosses.** Single dust from the hand lands as a cross; a dot needs a hand click, which Easy Place never made, because the difference looked like "connections will settle". A cross powers the blocks beside it and a dot does not. The dot (and the reverse case) is now clicked right after placing (`StateFix.wireShape`, shared with Material HUD 1.0.1). Checked with Material HUD harness `LL_COVERAGE=r` with this jar in `run/mods`: 89/89 on a 44-cell redstone schematic (1.0.13 — two dots as crosses); `LL_COVERAGE=j` — 168/168.

**Internal — three places that were too close to other code were rewritten (rights review, 06.10.2026).** Tool
selection (`ToolPick`) uses our own "race with a margin" scheme instead of a structure resembling the EasyPlaceFix
fork's ToolSwitcher; rail shape rules (`Rails`) are our own data table instead of repeating vanilla `RailState`; the
look-based face order (`SimPlaceContext`) is our own ordering instead of a copy of `Direction.orderedByNearest`.
Behaviour is unchanged: the bytecode equals Material HUD 0.0.26's rewritten classes, whose harness `t` dump is
byte-identical to the old code; Material HUD harness `LL_COVERAGE=j` with this jar — 84/84, `look.csv` equal to 1.0.13.

**fix (26.3) — left click while capturing a key.** The shared aell-ui library compared the mouse button with "0", no
longer the left button in 26.3 (SDL); it now compares with `InputConstants.MOUSE_BUTTON_LEFT`. The library also gained
link tabs (`Model.Tab.opens`); Easy Place Fix+ does not use them.

**Russian texts call schematic ghost blocks "голограммы" (holograms), as Litematica does.** Litematica's own Russian
translation names them so ("Размещённые голограммы" in its main menu); the Easy Place entities tooltip, README,
changelog and code comments now match. English texts, language keys and options are unchanged, so settings are kept.

## 1.0.13+26.3

**RU**

**fix — наблюдатели, поршни, раздатчики и бочки вставали по взгляду игрока, а не как в схеме.** Лицо этих блоков сервер считает по повороту головы игрока (`yHeadRot`), а пакет поворота разворачивает только тело: голова догоняет его лишь в следующем серверном тике (`Player.aiStep`). Подставленный угол уходил вместе с кликом, и блок вставал туда, куда игрок смотрел на самом деле. Печей, ступеней, сундуков и всего, что встаёт по грани клика, это не касалось. Теперь мод сверяет клик с тем, как его разберёт сервер с прежним поворотом головы, и если результат другой — сначала отдаёт угол ванильным пакетом движения и кликает, только когда угол пробыл на сервере не меньше 100 мс (два серверных тика). Такая клетка встаёт на пару тиков позже, и только когда игрок смотрит не в ту сторону; следующие блоки с тем же углом — без задержки. Работает и без «Позы на тик вперёд». Перенос из Material HUD 0.0.25 (`PoseAhead`, `SimPlaceContext`, `MixinLocalPlayerPose`, `MixinMultiPlayerGameMode`); проверено харнессом Material HUD `LL_COVERAGE=j`.

**EN**

**fix — observers, pistons, dispensers and barrels faced the way the player looked, not the way the schematic says.** The server takes these blocks' facing from the player's head yaw (`yHeadRot`), while a rotation packet only turns the body; the head catches up on the next server tick (`Player.aiStep`). The spoofed angle went out together with the click, so the block faced wherever the player was really looking. Furnaces, stairs, chests and everything placed by the clicked face were unaffected. The mod now checks how the server would resolve the click with the old head yaw, and when the result differs it sends the angle with the vanilla movement packet first and clicks only after the angle has been on the server for at least 100 ms (two server ticks). Such a cell is placed a couple of ticks later, and only when the player looks the other way; the following blocks with the same angle go without delay. Works without "Pose one tick ahead" too. Ported from Material HUD 0.0.25; checked with the Material HUD `LL_COVERAGE=j` harness.

## 1.0.12+26.3

**RU**

**Экран настроек — общая библиотека aell-ui.** Esc во время ввода клавиши заканчивает ввод тем, что нажато; ничего не нажато — клавиша остаётся «не задано», как в malilib. Прежнее значение возвращает повторный клик по полю, значение по умолчанию — кнопка сброса в строке; подсказка об этом — во всплывающем тексте строки. Панель настроек растёт под ряд вкладок до двух третей окна (было до половины).

**fix — значок поиска в заголовке настроек.** Глиф «⌕» игрового шрифта сидел ниже строки подсказки; теперь лупа рисуется пиксельной маской 7×7 вровень с текстом и золотым цветом.

**fix — подсказка «Поиск настройки» не пропадала при клике по полю.** В фокусе подсказки больше нет, вместо неё мигающий курсор: видно, что поле приняло клик.

**EN**

**Settings screen — shared aell-ui library.** Esc while capturing a key finishes the capture with whatever was pressed; nothing pressed — the key stays "not set", as in malilib. A second click on the field brings the previous value back, the reset button in the row — the default; the row tooltip says so. The settings panel grows to fit its tab row, up to two thirds of the window (was a half).

**fix — search icon in the settings header.** The font glyph "⌕" sat below the hint line; the magnifier is now a 7×7 pixel mask aligned with the text and painted gold.

**fix — the "Search settings" hint stayed after a click on the field.** In focus the hint is gone and a blinking caret shows instead: the field visibly took the click.

## 1.0.11+26.3

**RU.** Перенос из Material HUD 0.0.23 (модуль Easy Place, см. `docs/MODULES.md` Material HUD: M11–M15b, M18, M20):
- Easy Place ставит сущности схемы: стойки для брони, подушки, рамки (с предметом и поворотом), картины (сюжет выбирает игра), кристаллы Энда, вагонетки; лодки не ставятся. Тумблер «Ставить сущности» (`easyPlaceEntities`, вкл.; в общем списке с Material HUD).
- Доводка растений: ножницы — обрезанный кончик лозы, костная мука — ягоды на пещерной лозе и трутовик; лишние ягоды снимаются.
- Запитанная полка заполняется обменом с панелью быстрого доступа; полка без питания — ровно нужное число в слот.
- Тела лоз получают предмет головы в `MaterialCache` Litematica (её список и Easy Place), лиана — по граням.
- Помощь прицелу для лоз в перегрузке `getGenericTrace`, которую зовёт Easy Place; клетка лозы без опоры — тупик.
- Проверено харнессом Material HUD (`f` 111/111, `h` 97/97) с этим модом в сборке — Easy Place вёл он.

**EN.** Ported from Material HUD 0.0.23: Easy Place places schematic entities, finishes plants (shears, bone meal), fills powered shelves, places exact counts on unpowered shelves, counts every vine block, aims at thin vine ghosts.

## 1.0.10+26.3

**26.3.** Сборка под Minecraft 26.3 (Litematica 0.29.0, MaLiLib 0.30.1). Функционально то же, что и 1.0.10+26.2; перенесено на новые API: ввод через SDL вместо GLFW (коды клавиш — `InputConstants`), таблички (`SignTextSlot`), `swing` с анимацией, таблицы превращений лопаты/мотыги/топора читаются из data-driven реестра `block_transformer` (класс `ToolTables`) вместо снесённых `ShovelItem`/`HoeItem`/`AxeItem`.

**26.3.** Build for Minecraft 26.3 (Litematica 0.29.0, MaLiLib 0.30.1). Functionally the same as 1.0.10+26.2; ported to the new APIs: SDL input instead of GLFW (`InputConstants` key codes), signs (`SignTextSlot`), `swing` with an animation, and the shovel/hoe/axe conversion tables are read from the data-driven `block_transformer` registry (`ToolTables`) instead of the removed `ShovelItem`/`HoeItem`/`AxeItem`.

## 1.0.10+26.2

**RU**

**fix — вторая половина плиты снова встаёт.** Клетка, где схема просит полную плиту, а стоит половина, считалась неисправимой: свойство `type` разошлось, клетка занята — значит «кликом не поправить, надо ломать». Прицел такую клетку бросал и уводил луч дальше, поэтому до кода, который добивает плиту вторым кликом, дело не доходило никогда. Теперь установка на месте (клик по стоящей половине тем же предметом даёт полную плиту) вынесена в общий предикат, и его же спрашивают обе половины мода — «есть ли здесь работа» и сам конвейер. Проверено на схеме с 25 клетками полных плит семи видов: до правки все 25 считались неисправимыми, после — все 25 стали работой.

**fix — мод больше не молчит, когда нужен баннер с конкретным узором.** Узор баннера и черепки горшка лежат не в состоянии блока, а в его сущности, поэтому требуемый предмет мод берёт с данными — иначе в схему встал бы баннер без узора. В выживании Litematica ищет такой предмет точным совпадением компонентов, и если в инвентаре его нет, установка тихо не происходит вовсе: клетка остаётся пустой без единого слова, а список материалов при этом показывает её обеспеченной — он просит просто «баннер», потому что кеш требуемых предметов у Litematica ключуется по состоянию блока. Теперь про такую клетку мод говорит один раз: какой именно предмет нужен и почему список врёт.

**EN**

**fix — the second half of a slab is placed again.** A cell where the schematic asks for a double slab while a half stands there counted as unfixable: `type` differs and the cell is occupied, so "no click can fix this, it has to be broken". The aim dropped such a cell and moved the ray on, so the code that completes the slab with a second click was never reached. Placement that upgrades a block in place (clicking a standing half with the same item gives a double slab) is now a shared predicate, asked by both halves of the mod — "is there work here" and the pipeline itself. Checked on a schematic with 25 double-slab cells of seven kinds: before the fix all 25 counted as unfixable, after it all 25 count as work.

**fix — the mod no longer stays silent when a banner with a specific pattern is needed.** A banner's pattern and a pot's sherds live in the block entity, not in the block state, so the mod takes the required item with its data — otherwise a plain banner would go into the build. In survival Litematica looks that item up by an exact component match, and when it is not in the inventory nothing is placed at all: the cell stays empty without a word, while the material list shows it as covered — it asks for a plain banner, because Litematica's required-item cache is keyed by block state. The mod now says it once per cell: which item is actually needed and why the list disagrees.

## 1.0.5+26.2

**RU**

**new — «Неровный темп».** К паузе между автоматическими кликами случайно добавляется ещё один тик, чтобы интервал не был идеально ровным: рука не кликает как метроном, а часть timer-античитов ловит именно ровный ритм. Немного замедляет стройку, поэтому выключено по умолчанию; включать, если сервер жалуется и на меньшем «Блоков в секунду».

**new — «Не копать лишнего».** Пока в Litematica включён режим Easy Place, левый клик не ломает блоки вне области схемы и блоки, уже стоящие ровно как в схеме: копать можно только лишнее или неверное внутри области. Причина отказа показывается строкой над хотбаром, не чаще раза в секунду. Мгновенная ломка в креативе перехватывается тоже. Выключено по умолчанию.

**new — «Брать инструмент для копания».** Перед тем как блок начнёт ломаться, в руку берётся самый быстрый подходящий инструмент из инвентаря — так же, как Easy Place уже берёт нужный блок для установки: хотбар сменой слота, остальной инвентарь через штатный pick-block Litematica. Инструмент, с которым блок не выпадет, не выигрывает у пустой руки. Только в выживании и только при включённом Easy Place. Выключено по умолчанию.

**new — «Заменять почву».** Земля, дёрн, каменистая земля, тропинка, корневая земля, подзол и мицелий: если в клетке стоит один из них, а схема просит другой, обычный Easy Place упирается в «клетка занята». Теперь мод выкапывает лишний блок теми же вызовами, что и удерж левой кнопки, а следующий проход Easy Place ставит нужный. Где блок доводится на месте мотыгой или лопатой, копать не надо — это по-прежнему делает «Доводить блоки инструментом», и оно идёт первым. Раскопки идут по одной клетке и бросаются, если сервер не отдал блок за четыре секунды. Выключено по умолчанию, потому что ломает блоки.

Четыре новых тумблера — ответ на возможности форка EasyPlaceFix 0.6.5–0.6.6 (placementJitter, Excavation guard, Auto tool switch, Terrain auto-replace). Реализация своя, код форка не заимствован; источник идей указан в NOTICE. Настройка нот и открытие контейнеров, тоже заявленные форком, у нас были и раньше.

**EN**

**new — "Uneven pace".** A random extra tick is added to the pause between automated clicks, so the interval is not perfectly regular: a hand never clicks like a metronome, and some timer anticheats flag exactly that even rhythm. Costs a little speed, so it is off by default; turn it on if the server still complains at a lower "Blocks per second".

**new — "Do not dig the wrong thing".** While Litematica's Easy Place mode is on, a left click does not break blocks outside the schematic area or blocks already standing exactly as the schematic asks: only leftover or wrong material inside the area can be dug. The reason is shown above the hotbar, at most once a second. Creative instant breaking is intercepted too. Off by default.

**new — "Pick the tool for digging".** Before a block starts breaking, the fastest suitable tool from the inventory is taken in hand — the way Easy Place already takes the right block for placing: the hotbar by switching the slot, the rest of the inventory through Litematica's own pick-block. A tool that would not drop the block never beats a bare hand. Survival only, and only while Easy Place mode is on. Off by default.

**new — "Replace soil".** Dirt, grass block, coarse dirt, dirt path, rooted dirt, podzol and mycelium: when the cell holds one of them and the schematic asks for another, plain Easy Place stops at "cell occupied". The mod now digs the wrong block out with the same calls a held left click makes, and the next Easy Place pass puts the right one down. Where a hoe or shovel can convert the block in place, nothing is dug — "Finish blocks with tools" still does that, and it runs first. One cell is dug at a time, and the dig is dropped if the server has not released the block within four seconds. Off by default because it breaks blocks.

The four new toggles answer the features of the EasyPlaceFix fork 0.6.5–0.6.6 (placementJitter, Excavation guard, Auto tool switch, Terrain auto-replace). The implementation is our own and no code was taken from the fork; the source of the ideas is credited in NOTICE. Note block tuning and container opening, also advertised by the fork, were already here.

## 1.0.4+26.2

**RU**

**fix — Easy Place больше не роняет лишний блок на клетку выше.** Решатель сверяется со схемой только по тем свойствам, которые задаёт сам клик, и прощает те, что достаются от окружения: форму ступеней, `snowy` у травы, `distance` и `persistent` у листвы, соединения забора. Проверка «есть ли здесь работа» этого списка не знала и считала работой любое расхождение, кроме четырёх частных случаев. Из-за расхождения двух взглядов уже поставленная ступень, у которой сосед по маршу ещё не встал, выглядела недоделанной: мод снимал с клетки двухсекундную блокировку Litematica, клик уходил в занятую клетку, а ванильная установка уводит такой клик на клетку за гранью — чаще всего вверх. По реестру 26.2 под это попадало 110 видов блоков: 64 ступени, 12 видов листвы, три почвы и мелочь. Теперь клетка, у которой вся разница со схемой лежит вне возможностей установки, считается готовой, а вторая страховка отменяет любой автоматический клик в клетку, где уже стоит то, что просит схема. Свечи, морские огурцы и слои снега работой остаются: их как раз добавляют новые клики.

**fix — темп установки теперь считается и для починки состояний.** Доводка двери, люка, свечи, повторителя и полки стоит в самом начале обработчика Easy Place и шла мимо «Блоков в секунду» целиком: в тике, где уже прошла установка, она добавляла второе взаимодействие и ещё пару пакетов поворота. Серверный timer-античит смотрит ровно на это. Бюджет у починки и у установки теперь общий, то есть за интервал уходит одно действие, а не два.

**new — «Поза на тик вперёд»: мод больше не шлёт своих пакетов поворота.** Подмена взгляда стоила двух лишних пакетов на каждый блок: один перед кликом, второй на восстановление угла. Ванильный клиент отправляет ровно один пакет движения за тик, а стоя на месте — один раз в двадцать тиков, и серверная timer-проверка считает именно их; в тике с установкой уходило три вместо одного. Теперь нужный угол применяется ровно на время сборки ванильного пакета движения, то есть его уносит собственный пакет клиента, а клик уходит следующим тиком уже без единого своего пакета. Камера при этом не дёргается: между подстановкой и снятием кадры не рисуются. Плата: клетка, для которой нужен поворот, встаёт на тик позже, а пока угол держится, сервер не видит настоящего взгляда, поэтому удар мышью в это время он посчитает по подставленному углу — удержание снимается, как только игрок перестал строить или нажал атаку. Если пакет движения в тике не уходит вовсе, например игрок верхом, схема на две секунды отключается сама и работает по-старому, чтобы установка не встала. Опция выключена по умолчанию: включайте, если сервер кикает с «timer».

**EN**

**fix — Easy Place no longer drops a spare block one cell up.** The solver compares against the schematic only on properties a click can set, and forgives the ones the surroundings decide: stair shape, `snowy` on grass, `distance` and `persistent` on leaves, fence connections. The "is there work here" test knew nothing of that list and read any difference as work, except for four special cases. The two views disagreed, so an already-placed stair whose neighbour along the flight was still missing looked unfinished: the mod lifted Litematica's two-second hold on the cell, the click landed on an occupied cell, and vanilla placement pushes such a click one cell past the clicked face, usually upwards. By the 26.2 registry that covered 110 block types: 64 stairs, 12 kinds of leaves, three soils and odds and ends. A cell whose only difference from the schematic lies beyond what placement can set now counts as done, and a second guard cancels any automated click into a cell that already holds what the schematic asks for. Candles, sea pickles and snow layers still count as work: those are what extra clicks add.

**fix — the placement rate now covers state repairs too.** Finishing a door, trapdoor, candle, repeater or shelf runs at the very start of the Easy Place handler and bypassed "blocks per second" entirely: in a tick where a placement had already gone out, it added a second interaction and another pair of rotation packets. That is exactly what a server-side timer anticheat watches. Repairs and placements now share one budget, so one action goes out per interval instead of two.

**new — "Aim one tick ahead": the mod no longer sends rotation packets of its own.** Look substitution cost two extra packets per block: one before the click, one to restore the angle after it. A vanilla client sends exactly one movement packet per tick, and only one every twenty ticks while standing still, and a server-side timer check counts exactly those; a tick with a placement carried three instead of one. The needed angle is now applied only for as long as vanilla builds its own movement packet, so that packet carries it, and the click goes out on the next tick without a single packet of ours. The camera does not jerk: no frame is drawn between substituting and restoring. The price: a cell that needs turning is placed one tick later, and while the angle is held the server does not see where you really look, so a left-click in that window is resolved from the substituted angle - the hold is released as soon as you stop building or press attack. If no movement packet goes out in a tick at all, riding a mount for instance, the scheme switches itself off for two seconds and works the old way so placement never stalls. Off by default: turn it on if a server kicks you with "timer".

## 1.0.3+26.2

**RU**

**Три переключателя убраны — их поведение теперь включено всегда.** «Открывать двери, включать рычаги», «Заполнять полки» и «Перебирать кликами». Это были страховки на время обкатки; режимы устоялись, выключать их незачем — строки настроек убраны, а «Перебирать кликами» заодно перестал быть выключенным по умолчанию. Старые значения в конфиге просто игнорируются.

**fix — Easy Place больше не ставит лишний забор поверх только что поставленного.** У забора, ограды, стены, панели, решётки и провода соединения с соседями задаются не кликом, а окружением: пока сосед по линии не построен, стоящий блок отличается от схемы одним-двумя соединениями. Мод считал такую клетку недоделанной и снимал с неё двухсекундную блокировку Litematica — клик уходил в уже занятую клетку, а ванильная установка уводит такой клик на клетку за гранью, чаще всего вверх. Собственная защита Litematica здесь не срабатывает, а наша («Только по схеме») ловила лишь клетки внутри области размещения — над верхним рядом забора она уже кончается. Теперь клетка с расхождением только по соединениям считается готовой, а любой съехавший клик Easy Place в пустую по схеме клетку отменяется, где бы она ни лежала.


**EN**

**Three toggles are gone — their behaviour is now always on.** "Open doors, set levers", "Fill shelves" and "Cycle by clicking". They were safety switches while the features were new; the features have settled and turning them off served no purpose — the settings rows are removed, and "Cycle by clicking" is no longer off by default. Old config values are simply ignored.

**fix — Easy Place no longer stacks a spare fence on top of the one it just placed.** Fences, fence gates, walls, panes, bars and redstone wire get their connections from the surroundings, not from the click: until the neighbour along the line is built, the placed block differs from the schematic by a connection or two. The mod read such a cell as unfinished and lifted Litematica's two-second hold on it — the click then landed on an already occupied cell, and vanilla placement pushes such a click one cell past the clicked face, usually upwards. Litematica's own guard does not catch this, and ours ("Only from the schematic") only covered cells inside the placement box — which ends right above the top row of a fence. A cell that differs only in connections now counts as done, and any displaced Easy Place click into a cell the schematic leaves empty is cancelled, wherever that cell lies.

## 1.0.2+26.2

**RU**

**fix — Easy Place: пропуск клетки больше не молчит.** При включённом «Пропускать то, что встанет неверно» мод отменяет клик, если не может поставить блок в том повороте, который просит схема, — чаще всего это ориентируемый блок вроде бревна. Litematica всё равно считает клетку обработанной и держит её две секунды, поэтому снаружи это выглядело так, будто мод просто перестал ставить вот здесь. Теперь он говорит об этом в чат, один раз на клетку, а причина уходит в «Отладку Easy Place».


**EN**

**fix — Easy Place: a skipped cell is no longer silent.** With "Skip what would go in wrong" enabled, the mod cancels the click when it cannot place the block in the orientation the schematic asks for — most often an orientable block such as a log. Litematica counts the cell as handled either way and holds it for two seconds, so from the outside it looked as if the mod had simply stopped placing there. It now says so in chat, once per cell, and the reason goes to "Easy Place debug".

## 1.0.1+26.2

**RU**

**fix — кнопка мода на экране слоёв Litematica не наезжает на новую вкладку «All».** В
Litematica 0.28.6 ряд вкладок на экране «Слои отрисовки» стал собираться циклом по всем вкладкам
настроек, и в нём появилась седьмая — «All»; прежде шесть кнопок создавались поимённо. Наш
распределитель слотов считал длину ряда по шести вкладкам, поэтому кнопка аддона вставала ровно
на ширину «All» левее и накрывала её. Теперь наличие вкладки спрашивается у самого экрана, а не
задаётся константой: с 0.28.6 ряд считается по семи вкладкам, на прежних версиях — по шести, и
раскладка верна в обоих случаях. Экран настроек Litematica спрашивал экран и раньше — расходился
только экран слоёв. Правка общая для всех наших аддонов Litematica, так что кнопки не наезжают ни
на вкладки Litematica, ни друг на друга.

**fix — темп Easy Place считается в тиках, а не по часам; сервер больше не видит пачек
кликов.** «Блоков в секунду» раньше мерилось миллисекундами между кликами. Серверный
timer-античит меряет пакеты на свой тик, и по часам два клика легко ложились в один тик:
несколько кадров на тик, догоняющие тики после лага — клиент насчитывал честную паузу, а
сервер получал пачку и кикал с «timer». Теперь пауза считается в тиках клиента (20 — каждый
тик, 10 — через тик, 5 — каждый четвёртый), часы остались лишь страховкой от второго клика
внутри того же тика, и больше одного автоматического клика за тик не уходит ни при каком
значении. Дожимающие клики многошаговых блоков — задержка повторителя, режим компаратора,
нота нотного блока (до 24 шагов) — идут через ту же очередь по одному за проход: после
клика клетка уходит в конец, следующий шаг получает, когда сервер подтвердит предыдущий и
очередь обойдёт остальных, ряд таких блоков дожимается по кругу. Раз включено «Перебирать
кликами», повторитель, компаратор и нотный блок ставятся в очередь дожима сразу при
установке — раньше каждый ждал, пока прицел снова окажется на нём. При смене мира, портале
и дисконнекте очереди дожима и отложенных установок и отсчёт темпа сбрасываются целиком:
координаты прошлого мира и пауза с прошлого сервера сюда не переносятся. Совместимость с
EasyPlaceFix 0.6.5: блокировка взгляда соседа читается через её срок годности
(`isLookLocked`), а не через сырой флаг — зависшая блокировка больше не держит наши
отложенные установки до истечения их лимита. Ни одной новой настройки.

**fix — рельсы вставали не в том положении, что в схеме.** Установкой рельс задаёт только
ось: `BaseRailBlock.getStateForPlacement` смотрит на угол взгляда и кладёт `north_south`
либо `east_west`. Настоящую форму — поворот, подъём, разворот на соседа — блок получает уже
в мире, и делает это сервер (`onPlace` → `RailState.place`); на клиенте эта ветка выходит
первой же строкой по `level.isClientSide`. Поэтому симуляция клика видела только заготовку,
`shape` числился «не задаваемым установкой», перебор угла под рельс не запускался вовсе — и
рельс ложился по той оси, куда игрок случайно смотрел. Ванильный расчёт `RailState` теперь
повторён на чтение (`Rails`, ни одной записи в мир) и досчитывается поверх симуляции: решатель
знает настоящую форму до клика и подбирает под схему угол взгляда. Затронуты все четыре вида —
обычный, энергетический, нажимной, активирующий; повороты и подъёмы обычного тоже. Где форму
целиком решают соседи, а нужного соседа схема ещё не поставила, клетка откладывается до его
появления — но только пока он вообще может появиться: когда соседство достроено и точной формы
уже не выйдет, рельс ставится как есть, чтобы «Пропускать то, что встанет неверно» не оставило
в пути дыру навсегда. Стоящий рельс не той формы больше не собирает бесполезные клики: заменить
его нельзя, а правый клик по нему ушёл бы в клетку над ним — лишним рельсом поверх пути.
Расчёт сверен с ванилью на 40 048 наборах соседства (дифференциальный прогон против самого
`RailState`) — расхождений ноль.

**fix — вторая половина двойного сундука ставится присед-кликом по боку первого.** Пару
из двух независимых кликов не собрать: первый сундук всегда встаёт одиночным, слипание
решает установка второго. Раньше этот клик доверялся перебору решателя, и пара собиралась
через auto-merge, где лицо считается из угла взгляда, — на сервере результат зависел от
того, дошёл ли поворот раньше клика, и вторая половина могла остаться одиночной. Теперь,
как только первая половина стоит одиночной нужным лицом, клик второй половины задаётся
сразу: присед и боковая грань первого сундука, смотрящая в клетку цели. Так пару ставит
ванильный игрок — ветка `secondaryUse` в `ChestBlock.getStateForPlacement` берёт и лицо,
и `type` от партнёра за кликнутой гранью, угол взгляда не участвует вовсе, и с посторонним
сундуком с другой стороны такой клик не слипнется. Работает для всех парных сундуков,
включая все виды медных. Результат по-прежнему сверяется симуляцией до клика.

**new — рычаги переключаются в положение из схемы.** Установка всегда даёт рычаг
выключенным: `powered` не задаётся никаким кликом установки. Рычаг, включённый в схеме,
теперь дожимается отдельным кликом — сразу после установки (та же очередь дожима, что у
дверей) или при наведении на уже стоящий не в том положении. Ровно рычаг, не всё с
`powered`: кнопка отожмётся сама, а у двери и наблюдателя `powered` — внешний редстоун,
кликом его не задать. Живёт под тумблером «Открывать двери и люки», который стал
«Открывать двери, включать рычаги».


**EN**

**fix — the mod's button on Litematica's render layers screen no longer overlaps the new "All"
tab.** In Litematica 0.28.6 the tab row on the render layers screen is built by looping over every
config tab, and a seventh one — "All" — joined it; before that six buttons were created by name.
Our slot allocator measured the row as six tabs, so the addon button landed exactly one "All"
width too far left and covered it. The screen is now asked whether it has that tab instead of the
answer being hardcoded: on 0.28.6 the row counts seven tabs, on earlier versions six, and the
layout is right either way. Litematica's config screen was already asked this way — only the
render layers screen disagreed. The fix is shared by all our Litematica addons, so the buttons
overlap neither Litematica's tabs nor each other.

**fix — Easy Place pacing is counted in ticks, not wall-clock time; the server no longer
sees click bursts.** "Blocks per second" used to measure milliseconds between clicks. A
server's timer anti-cheat counts packets per server tick, and by the clock two clicks easily
landed in one tick — several frames per tick, catch-up ticks after a lag spike — so the client
counted an honest pause while the server received a burst and kicked for "timer". The pause
is now counted in client ticks (20 — every tick, 10 — every other, 5 — every fourth), the
clock remains only as a backstop against a second click inside the same tick, and at most one
automated click per tick goes out at any setting. Follow-up clicks for multi-step blocks —
repeater delay, comparator mode, note block pitch (up to 24 steps) — run through the same
queue, one per pass: after a click the cell goes to the back and gets its next step once the
server has acknowledged the previous one and the queue has visited the others, so a row of such
blocks is finished round-robin. With "Cycle by clicking" on, repeaters, comparators and note
blocks are queued for follow-up right at placement — previously each waited for the crosshair
to return to it. On a world change, portal or disconnect the follow-up and deferred-placement
queues and the pacing counter are reset completely: coordinates from the previous world and a
pause from the previous server do not carry over. EasyPlaceFix 0.6.5 compatibility: the
neighbour's look lock is read through its expiry (`isLookLocked`) rather than the raw flag, so
a stuck lock no longer holds our deferred placements until their own limit runs out. No new
settings.

**fix — rails came out in a different orientation than the schematic.** Placement only sets a
rail's axis: `BaseRailBlock.getStateForPlacement` reads the look angle and writes `north_south`
or `east_west`. The real shape — the turn, the slope, the swing towards a neighbour — is given
to the block once it is in the world, and it is the server that does it (`onPlace` →
`RailState.place`); on the client that branch returns on its first line, at `level.isClientSide`.
So the click simulation only ever saw the blank, `shape` counted as "not set by placement", the
look-angle sweep never ran for a rail at all — and the rail landed on whichever axis the player
happened to face. The vanilla `RailState` calculation is now mirrored read-only (`Rails`, not a
single write to the world) on top of the simulation: the solver knows the real shape before the
click and picks the look angle that matches the schematic. All four kinds are affected — plain,
powered, detector, activator — and the plain rail's turns and slopes as well. Where the shape is
decided by neighbours alone and the schematic has not placed the needed one yet, the cell waits
for it — but only while it can still arrive: once the neighbourhood is complete and the exact
shape is out of reach, the rail goes in as it comes, so that "skip what would be placed wrong"
does not leave a permanent hole in the track. A standing rail with the wrong shape no longer
collects useless clicks: it cannot be replaced, and a right click on it would land in the cell
above it as a stray rail over the track. The calculation is checked against vanilla on 40,048
neighbourhoods (a differential run against `RailState` itself) — zero divergences.

**fix — the second half of a double chest is placed with a sneak click on the side of the
first.** A pair can never be built out of two independent clicks: the first chest always
lands single, and it is the second placement that welds them. That click used to be left to
the solver sweep, so the pair assembled through auto-merge, where the facing is computed
from the look angle — on the server the outcome depended on whether the rotation packet
arrived before the click, and the second half could stay single. Now, as soon as the first
half stands single with the right facing, the second half's click is fixed up front: sneak
plus the side face of the first chest looking into the target cell. That is how a vanilla
player builds a pair — the `secondaryUse` branch of `ChestBlock.getStateForPlacement` takes
both the facing and the `type` from the partner behind the clicked face, the look angle
plays no part, and the click cannot weld to a stray chest on the other side. Works for
every pairable chest, all copper variants included. The result is still verified by
simulation before the click.

**new — levers are flipped to match the schematic.** Placement always leaves a lever off:
`powered` cannot be set by any placement click. A lever the schematic wants on now gets its
own click — right after placement (the same follow-up queue doors use) or when aiming at
one already standing in the wrong position. Strictly levers, not everything with `powered`:
a button would pop back out, and on doors and observers `powered` is external redstone no
click can set. Lives under the "Open doors and trapdoors" toggle, now renamed "Open doors,
set levers".

## 0.0.1+26.2

Первая сборка. Модуль Easy Place вынесен из Litematica Material HUD в отдельный мод —
код ядра перенесён как есть, без переписывания логики.

**RU**

- Имя мода — `Litematica EasyPlaceFixPlus`: id `easyplacefixplus`, пакет
  `com.aell.easyplacefixplus`, джар `litematica-easy-place-fix-plus`. Ключ слота
  `easyplacefixplus` прописан в `SLOT_ORDER` всех модов семейства, подпись кнопки
  в ряду вкладок Litematica осталась короткой — `EasyPlace+…`.
- fix — определение чужих модов смотрело на неверный id `easyplaceplus` и класс
  `org.uiop.easyplaceplus.config.easyPlacefixConfig`, поэтому вытеснение EasyPlaceFix
  (`ForeignSuppressor`) и учёт блокировки поворота из tick_ins (`ForeignLookLock`)
  молча не работали. Теперь `easyplacefix` и `org.uiop.easyplacefix`, как в Material HUD.
- fix — сообщение о вытеснении EasyPlaceFix приписывало исправления Material HUD;
  во всех 14 локалях названо имя этого мода.
- **Общие настройки с Material HUD.** Внутри него тот же модуль Easy Place, и теперь
  вкладка Easy Place там не прячется, а правит наши живые значения: связь — рефлексия по
  классу-мосту `compat.EasyPlaceBridge` и общий `SharedOptions` из `aell-ui`, объекты опций
  malilib у обоих модов одного класса, поэтому экран соседа работает с ними напрямую.
  На сохранении значения переносятся в объекты соседа и его файл тоже пишется, так что
  снятие любого из двух модов ничего не сбрасывает. `chatMessages` через мост не ходит:
  имя общее, смысл разный.
- fix — пока стоит этот мод, встроенный модуль Material HUD глушился не до конца: обе точки
  входа поднимали `AutoPlaceState.inHandler()` без проверки `isActive()`, и его миксины
  продолжали править прицел и снимать кэш позиции поверх нашей установки. Теперь гейт стоит
  во входах, в правке прицела и в снятии кэша — заодно свой тумблер тоже отпускает управление
  целиком, а не наполовину.
- Ядро (36 классов) и 11 mixin'ов перенесены из `com.aell.layerlist.easyplace`
  в `com.aell.easyplacefixplus.core` / `.mixin.easyplace`.
- Свой конфиг `config/easyplacefixplus.json` на malilib: 18 опций Easy Place
  плюс `chatMessages`. Значения по умолчанию совпадают с прежними.
- При первом запуске настройки молча забираются из прежнего `config/easyplaceplus.json`
  (вместе с горячими клавишами), а если его нет — из `config/layerlist.json` Material HUD.
  Имена опций те же, так что настроенный темп установки и режимы для сервера не сбрасываются.
  Донорские файлы не изменяются и не удаляются.
- Своя иконка в стиле семейства — ступенька в клетке-голограмме схемы, 512 и 16 px,
  генератор `docs/icons.py`. Та же миниатюра 16 px стоит у Material HUD на вкладке
  Easy Place: один модуль — один знак.
- 14 локалей. Строки опций перенесены из Material HUD дословно, остальное переведено заново.
- Общий UI: подпись переключателя теперь режется по ширине колонки — длинные переводы
  вроде `Desactivado` и `비활성화` больше не наезжают на кнопку сброса.
- Свой экран настроек на `aell-ui` и кнопка в настройках Litematica и в экране слоёв —
  по той же схеме, что у Ghost Alpha.
- Две горячие клавиши, по умолчанию не назначены: открыть настройки и включить/выключить правку.
- `handleBlockChangedAck` продублирован в собственный `MixinClientLevel` — подтверждение
  сервера по номеру пакета нужно ядру и без Material HUD.
- Мёртвый `RotationSpoof` при переносе выброшен.
- Проверка «стоит ли отдельный EasyPlaceFix+» из `ContainerClick` убрана: здесь этот мод и есть.


**EN**

- The mod is **Litematica EasyPlaceFixPlus**: id `easyplacefixplus`, jar
  `litematica-easy-place-fix-plus`; the tab button stays short — `EasyPlace+…`.
- fix — foreign-mod detection pointed at the wrong id and class, so EasyPlaceFix was never
  suppressed and tick_ins' look lock was never read. Both look at `easyplacefix` /
  `org.uiop.easyplacefix` now, the same as Material HUD does.
- **Settings shared with Material HUD.** Its Easy Place tab is no longer hidden when this mod
  is installed: it now edits this mod's live option objects through a reflection bridge, so the
  two mods can never drift apart. Saving writes both config files, so removing either mod keeps
  every setting.
- fix — the built-in module was only half silenced while this mod was installed: both handler
  entries raised `inHandler()` without checking `isActive()`, so Material HUD kept rewriting the
  aim over ours. Gated at the entries, at the aim rewrite and at the position-cache reset.
- The Easy Place engine (36 classes, 11 mixins) moved out of Litematica Material HUD as is.
- Own malilib config `config/easyplacefixplus.json`: the 18 Easy Place options plus chat messages,
  same defaults as before. On the first run settings are picked up from the earlier
  `easyplaceplus.json`, or from Material HUD's `layerlist.json` — neither donor is modified.
- Own settings screen and a button inside Litematica's settings and layer screen,
  built the same way as Ghost Alpha.
- Own icon in the family style — a stair inside the ghost schematic cell; the same 16 px
  thumbnail now marks the Easy Place tab in Material HUD.
- Two keybinds, unbound by default: open settings, toggle the fix.
