package kr.sul.party.command.subcommand

import kr.sul.party.command.PartyCommand
import kr.sul.party.partyplayer.PartyPlayer
import org.bukkit.entity.Player

object InvitationCommand : PartySubCommand {
    override fun onCommand(p: Player, partyPlayer: PartyPlayer, label: String, args: Array<String>) {
        if (args.size != 3) { PartyCommand.sendHelpMessageWhenArgIsWrong(p, label, args); return }
        if (args[1] == "수락") {
            partyPlayer.acceptInvitation(args[2])
        } else if (args[1] == "거절") {
            partyPlayer.denyInvitation(args[2])
        }
    }
}