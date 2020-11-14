package kr.sul.party.command

import kr.sul.party.MessageManager
import kr.sul.party.MessageManager.PREFIX
import kr.sul.party.command.subcommand.ChatMode
import kr.sul.party.command.subcommand.Info
import kr.sul.party.partyplayer.PartyPlayerManager.getPartyPlayer
import kr.sul.party.tab.TablistGeneratorImpl
import kr.sul.party.tab.TablistUpdaterImpl
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.inventivetalent.glow.GlowAPI
import pl.mnekos.tablist.TablistManager

object PartyCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) return false
        val p = sender as Player
        if (args.isEmpty()) { sendHelpMessage(p); return true }

        val partyPlayer = getPartyPlayer(p)
        when(args[0]) {
            "초대" -> {
                if (args.size != 2) { sendHelpMessageWhenArgIsWrong(p, label, args); return true }
                if (Bukkit.getPlayerExact(args[1]) != null) {
                    val opponent = getPartyPlayer(Bukkit.getPlayerExact(args[1]))
                    partyPlayer.invite(opponent)
                } else {
                    p.sendMessage("$PREFIX §f유저 '${args[1]}' 를 찾을 수 없습니다.")
                }
            }
            "초대장" -> {
                if (args.size != 3) { sendHelpMessageWhenArgIsWrong(p, label, args); return true }
                if (args[1] == "수락") {
                    partyPlayer.acceptInvitation(args[2])
                } else if (args[1] == "거절") {
                    partyPlayer.denyInvitation(args[2])
                }
            }
            "채팅" -> {
                ChatMode.onCommand(p, partyPlayer, label, args)
            }
            "탈퇴" -> {
                if (args.size != 1) { sendHelpMessageWhenArgIsWrong(p, label, args); return true }
                partyPlayer.leaveParty()
            }
            "강퇴" -> {
                if (args.size != 2) { sendHelpMessageWhenArgIsWrong(p, label, args); return true }
            }
            "정보" -> {
                Info.onCommand(p, partyPlayer, label, args)
            }
            "tab" -> {
                val tabListManager = TablistManager(TablistGeneratorImpl, TablistUpdaterImpl)
                tabListManager.createTablist(p)
            }
            else -> sendHelpMessageWhenArgIsWrong(p, label, args)
        }
        return true
    }

    internal fun sendHelpMessageWhenArgIsWrong(p: Player, label: String, args: Array<String>) {
        sendHelpMessage(p)
        val argsCombined = StringBuilder()
        for (arg in args) {
            argsCombined.append(arg).append(" ")
        }
        p.sendMessage("§7- §c입력하신 명령어와 일치하는 명령어가 없습니다.  §7'/$label $argsCombined''")
    }
    private fun sendHelpMessage(p: Player) {
        p.sendMessage("")
        p.sendMessage(MessageManager.HEADER)
        p.sendMessage("§8/§6파티 초대 <이름> §8- §7플레이어를 파티에 초대합니다.")
        p.sendMessage("  §7- 파티를 만들 필요 없이, 상대가 초대를 수락함과 동시에 파티가 생성됩니다.")
        p.sendMessage("§8/§6파티 채팅 §8- §7파티 정보를 확인합니다.")
        p.sendMessage("§8/§6파티 탈퇴 §8- §7파티에서 탈퇴합니다.")
        p.sendMessage("§8/§6파티 강퇴 <이름> §8- §7플레이어를 파티에서 강퇴시킵니다.")
        p.sendMessage("§8/§6파티 정보 §8- §7파티 정보를 확인합니다.")         // 플레이어별 KDA - 스코어보드에 띄우고 명령어 없애자
        p.sendMessage(MessageManager.FOOTER)
    }
}