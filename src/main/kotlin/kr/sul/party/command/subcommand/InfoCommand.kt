package kr.sul.party.command.subcommand

import kr.sul.party.MessageManager
import kr.sul.party.command.PartyCommand
import kr.sul.party.party.Party
import kr.sul.party.partyplayer.PartyPlayer
import org.bukkit.entity.Player

object InfoCommand : PartySubCommand {
    override fun onCommand(p: Player, partyPlayer: PartyPlayer, label: String, args: Array<String>) {
        if (args.size != 1) { PartyCommand.sendHelpMessageWhenArgIsWrong(p, label, args); return }
        if (!partyPlayer.isInParty()) { p.sendMessage(MessageManager.YOU_ARE_NOT_IN_PARTY); return }
        val party = partyPlayer.joinedParty!!

        // LongestNameLength 구하기
        var longestNameLength = 0
        for (memberNameLength in party.members.stream().map { it.player.name.length }) {
            if (memberNameLength > longestNameLength) longestNameLength = memberNameLength
        }

        // 파티 정보 출력
        p.sendMessage("")
        p.sendMessage(MessageManager.HEADER)
        for (member in party.members) {
            p.sendMessage(manufactureMemberInfo(party, member, longestNameLength))
        }
        p.sendMessage(MessageManager.FOOTER)
    }

    // (왕관/유저) 닉네임 (체력(하트)) (KDA)
    private fun manufactureMemberInfo(party: Party, member: PartyPlayer, longestMemberNameLength: Int): String {
        val strBuilder = StringBuilder()
        if (member.isLeader()) strBuilder.append("§eK ") else strBuilder.append("§6U ")

        strBuilder.append("§f${member.player.name}")
        for (i in 0 until longestMemberNameLength-(member.player.name.length)) {  // 칸 맞추기
            strBuilder.append(" ")
        }
        strBuilder.append("  ")

        // TODO: 현재 체력

        // TODO: KDA
        val killDeath = party.membersKillDeath[member]!!
        strBuilder.append("§c${killDeath.kill} §7/ §4${killDeath.death}")

        return strBuilder.toString()
    }
}