package com.sidav.gdxgame.game.monsters.tokens

import com.sidav.gdxgame.game.monsters.MonsterTrait

class Prowlers: MonsterToken(4, 3, 2)
class Diggers: MonsterToken(3, 3, 2)
class CursedHags: MonsterToken(3, 5, 3, MonsterTrait.POISON)
class WolfRiders: MonsterToken(3, 4, 3, MonsterTrait.SWIFT)
class Ironclads: MonsterToken(4, 3, 4, MonsterTrait.BRUTAL)
class OrcSummoners: MonsterToken(4, 3, 4, MonsterTrait.SUMMONER)
