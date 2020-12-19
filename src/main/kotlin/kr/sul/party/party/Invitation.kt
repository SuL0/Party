package kr.sul.party.party

import kr.sul.party.Main.Companion.plugin
import kr.sul.party.MessageManager
import kr.sul.party.MessageManager.PREFIX
import kr.sul.party.partyplayer.PartyPlayer
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.apache.commons.lang.RandomStringUtils
import org.bukkit.scheduler.BukkitRunnable

class Invitation(val sender: PartyPlayer, val receiver: PartyPlayer) {
    companion object { const val VALIDITY_PERIOD = 40 }
    val uuid: String = "${sender.player.name}-${RandomStringUtils.randomAlphanumeric(6)}"
    val members = listOf {
        if (sender.isInParty()) {
            sender.joinedParty!!.members
        } else {
            sender
        }
    } // 멤버는 초대장에 커서 올리면 파티 정보 띄우게끔
    var processed = false


    fun send() {
        sendInvitationMessage()
        receiver.receivedInvitation.add(this)

        // 초대 자동 거절
        val whenToDenyAutomatically = System.currentTimeMillis() + (VALIDITY_PERIOD * 1000)
        object : BukkitRunnable() {
            override fun run() {
                if (processed) { cancel(); return }
                if (whenToDenyAutomatically <= System.currentTimeMillis()) {
                    deny()
                    cancel(); return
                }
            }
        }.runTaskTimer(plugin, 20*5L, 20*5L)
    }
    private fun sendInvitationMessage() {
        // FIXME: 지금 hoverEvent가 앞쪽으로 밀리는 버그가 있음. (옵티파인 문제)
        val jsonMsg = arrayListOf<TextComponent>().run {
            add(TextComponent("$PREFIX §c'${sender.player.name}' §f이(가) 당신을 파티에 초대했습니다. "))
            add(TextComponent("§8[§a수락하기§8]").run {
                hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent("§a초대 수락하기")))
                clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/파티 초대장 수락 $uuid")
                this
            })
            add(TextComponent(" "))
            add(TextComponent("§8[§c거절하기§8]").run {
                hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent("§c초대 거절하기")))
                clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND , "/파티 초대장 거절 $uuid")
                this
            })
            add(TextComponent("  §7§o< Click"))
            this
        }
        receiver.sendMessage(*jsonMsg.toTypedArray())
        receiver.sendMessage("- §7${VALIDITY_PERIOD}초 뒤에 자동 거절됩니다.")
    }



    // 수락, 거절 //
    fun accept() {
        // 파티 없으면 파티 만들고, receiver 를 파티에 추가하기. 근데 파티 인원수 같은것도 좀 보고
        if (!(sender.player.isOnline)) { sendInvitationCannotBeProcessedMessage("파티 초대자가 오프라인입니다."); return }

        val party = run {
            if (!sender.isInParty()) Party(sender)
            else sender.joinedParty!!
        }
        if (party.isFull()) { sendInvitationCannotBeProcessedMessage("파티 인원이 꽉 찼습니다."); return }
        // 파티 가입
        party.addMember(receiver)

        // 남은 초대장 전부 거절
        receiver.run {
            if (receivedInvitation.isNotEmpty()) player.sendMessage(MessageManager.DENY_ALL_RECEIVED_INVITATIONS)
            receivedInvitation.forEach { it.deny() }
            receivedInvitation.clear()
        }

        processed = true
    }

    fun deny() {
        sender.sendMessage(MessageManager.YOUR_INVITATION_HAS_DENIED.replace("{Receiver}", receiver.player.name))
        receiver.sendMessage(MessageManager.RECEIVED_INVITATION_DENIED_AUTOMATICALLY.replace("{Opponent}", sender.player.name))
        receiver.receivedInvitation.remove(this)
        processed = true
    }
    private fun sendInvitationCannotBeProcessedMessage(reason: String) {
        receiver.sendMessage(MessageManager.INVITATION_CANNOT_BE_PROCESSED_1)
        receiver.sendMessage(MessageManager.INVITATION_CANNOT_BE_PROCESSED_2.replace("{Reason}", reason))
    }

}