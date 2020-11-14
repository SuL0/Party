package kr.sul.party.party

import kr.sul.party.partyplayer.PartyPlayer
import kr.sul.party.partyplayer.PartyPlayerManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

data class MemberKillDeath(val partyPlayer: PartyPlayer) {
    var kill = 0
    var death = 0
}

object MemberKillDeathManager : Listener {
    @EventHandler
    fun onPlayerDeath(e: PlayerDeathEvent) {
        val victim = PartyPlayerManager.getPartyPlayer(e.entity)
        if (victim.isInParty()) {
            victim.joinedParty!!.membersKillDeath[victim]!!.death += 1
        }
        if (e.entity.killer != null) {
            val killer = PartyPlayerManager.getPartyPlayer(e.entity.killer)
            if (killer.isInParty()) {
                killer.joinedParty!!.membersKillDeath[killer]!!.kill += 1
            }
        }
    }
}