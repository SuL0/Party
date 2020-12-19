package kr.sul.party.partyplayer

import kr.sul.party.MessageManager
import kr.sul.party.party.Invitation
import kr.sul.party.party.Party
import net.md_5.bungee.api.chat.BaseComponent
import org.bukkit.entity.Player

class PartyPlayer(val player: Player) {
    var joinedParty : Party? = null
    val receivedInvitation = arrayListOf<Invitation>()
    var partyChat = false

    fun isInParty(): Boolean { return joinedParty != null }
    fun isLeader(): Boolean { return (joinedParty != null && joinedParty!!.leader == this) }
    private fun alreadyHaveYourInvitation(sender: PartyPlayer): Boolean { return (receivedInvitation.map { it.sender }.contains(sender)) }

    // 초대 //
    fun invite(opponent: PartyPlayer) {
        if (isInParty() && !isLeader()) { player.sendMessage(MessageManager.YOU_ARE_NOT_A_LEADER); return }

        if (opponent == this) { player.sendMessage(MessageManager.CANNOT_USE_TO_YOURSELF); return }
        if (opponent.isInParty()) { player.sendMessage(MessageManager.OPPONENT_ALREADY_IN_PARTY); return }
        if (opponent.alreadyHaveYourInvitation(this)) { player.sendMessage(MessageManager.OPPONENT_HAS_YOUR_INVITATION); return }

        player.sendMessage(MessageManager.SENT_INVITE.replace("{Receiver}", opponent.player.name))
        Invitation(this, opponent).send()
    }

    fun acceptInvitation(arg1uuid: String) {
        if (receivedInvitation.map { it.uuid }.contains(arg1uuid)) {
            val invitation = receivedInvitation.first { it.uuid == arg1uuid }
            this.sendMessage(MessageManager.ACCEPT_RECEIVED_INVITATION.replace("{Sender}", invitation.sender.player.name))
            receivedInvitation.remove(invitation)
            invitation.accept()
        } else {
            this.sendMessage(MessageManager.DONT_HAVE_SUCH_INVITATION.replace("{Arg1}", arg1uuid))
        }
    }
    fun denyInvitation(arg1uuid: String) {
        if (receivedInvitation.map { it.uuid }.contains(arg1uuid)) {
            val invitation = receivedInvitation.first { it.uuid == arg1uuid }
            this.sendMessage(MessageManager.DENY_RECEIVED_INVITATION.replace("{Sender}", invitation.sender.player.name))
            receivedInvitation.remove(invitation)
            invitation.deny()
        } else {
            this.sendMessage(MessageManager.DONT_HAVE_SUCH_INVITATION.replace("{Arg1}", arg1uuid))
        }
    }

    // 탈퇴 //
    fun leaveParty() {
        if (isInParty()) {
            this.sendMessage(MessageManager.LEAVE_PARTY)
            this.joinedParty!!.onPlayerLeave(this)
        } else {
            this.sendMessage(MessageManager.YOU_ARE_NOT_IN_PARTY)
        }
    }

    fun sendMessage(message: String) {
        player.sendMessage(message)
    }
    fun sendMessage(vararg components: BaseComponent) {
        player.sendMessage(*components)
    }
}
