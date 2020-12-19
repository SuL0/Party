package kr.sul.party.command.subcommand

import kr.sul.party.MessageManager
import kr.sul.party.command.PartyCommand
import kr.sul.party.partyplayer.PartyPlayer
import org.bukkit.entity.Player

object ChatModeCommand: PartySubCommand {
    override fun onCommand(p: Player, partyPlayer: PartyPlayer, label: String, args: Array<String>) {
        if (args.size != 1) {
            PartyCommand.sendHelpMessageWhenArgIsWrong(p, label, args); return }
        if (!partyPlayer.isInParty()) { p.sendMessage(MessageManager.YOU_ARE_NOT_IN_PARTY); return }

        partyPlayer.partyChat = !partyPlayer.partyChat
        val onOff = run {
            if (partyPlayer.partyChat) "§a활성화"
            else "§c비활성화"
        }
        p.sendMessage(MessageManager.SWITCH_PARTY_CHAT.replace("{ON/OFF}", onOff))
    }
}