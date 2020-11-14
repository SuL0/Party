package kr.sul.party.partyplayer

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

object PartyPlayerManager : Listener {
    private val partyPlayersMap = hashMapOf<Player, PartyPlayer>()

    fun getPartyPlayer(p: Player): PartyPlayer {
        if (!partyPlayersMap.containsKey(p)) {
            partyPlayersMap[p] = PartyPlayer(p)
        }
        return partyPlayersMap[p]!!
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onQuit(e: PlayerQuitEvent) {
        val partyPlayer = getPartyPlayer(e.player)
        // 파티 가입 상태일 시 탈퇴
        if (partyPlayer.isInParty()) {
            partyPlayer.leaveParty()
        }
        // 메모리 비우기
        partyPlayersMap.remove(e.player)
    }
}