package kr.sul.party.command.subcommand

import kr.sul.party.partyplayer.PartyPlayer
import org.bukkit.entity.Player

interface PartySubCommand {
    fun onCommand(p: Player, partyPlayer: PartyPlayer, label: String, args: Array<String>)
}