package kr.sul.party.command.subcommand

import kr.sul.party.command.PartyCommand
import kr.sul.party.partyplayer.PartyPlayer
import org.bukkit.entity.Player

object LeaveCommand : PartySubCommand {
    override fun onCommand(p: Player, partyPlayer: PartyPlayer, label: String, args: Array<String>) {
        if (args.size != 1) { PartyCommand.sendHelpMessageWhenArgIsWrong(p, label, args); return }
        partyPlayer.leaveParty()
    }
}