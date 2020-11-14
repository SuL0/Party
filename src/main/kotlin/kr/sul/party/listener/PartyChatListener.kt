package kr.sul.party.listener

import kr.sul.party.partyplayer.PartyPlayerManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

object PartyChatListener: Listener {
    @EventHandler
    fun onChat(e: AsyncPlayerChatEvent) {
        val p = e.player
        val partyPlayer = PartyPlayerManager.getPartyPlayer(p)
        // 파티 채팅
        if (partyPlayer.isInParty() && partyPlayer.partyChat) {

        }
    }
}