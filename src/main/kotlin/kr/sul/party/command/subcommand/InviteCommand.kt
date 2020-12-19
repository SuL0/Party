package kr.sul.party.command.subcommand

import kr.sul.party.MessageManager
import kr.sul.party.command.PartyCommand
import kr.sul.party.partyplayer.PartyPlayer
import kr.sul.party.partyplayer.PartyPlayerManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object InviteCommand : PartySubCommand {
    override fun onCommand(p: Player, partyPlayer: PartyPlayer, label: String, args: Array<String>) {
        if (args.size != 2) { PartyCommand.sendHelpMessageWhenArgIsWrong(p, label, args); return }
        if (Bukkit.getPlayerExact(args[1]) == null) p.sendMessage("${MessageManager.PREFIX} §f유저 '${args[1]}' 를 찾을 수 없습니다.")

        val opponent = PartyPlayerManager.getPartyPlayer(Bukkit.getPlayerExact(args[1]))
        partyPlayer.invite(opponent)
    }
}