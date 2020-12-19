package kr.sul.party.party

import kr.sul.party.MessageManager
import kr.sul.party.MessageManager.PREFIX
import kr.sul.party.partyplayer.PartyPlayer
import org.inventivetalent.glow.GlowAPI

class Party(var leader: PartyPlayer) {
    companion object { const val MAX_MEMBERS_NUM = 4 }
    init {
        leader.joinedParty = this
    }
    val members = arrayListOf(leader)
    val membersKillDeath = hashMapOf(leader to MemberKillDeath(leader))

    fun isFull(): Boolean { return (members.size >= MAX_MEMBERS_NUM) }

    fun addMember(memberToJoin: PartyPlayer) {
        if (members.size >= MAX_MEMBERS_NUM) return
        members.forEach {
            it.sendMessage(MessageManager.SOMEONE_JOINED_PARTY.replace("{Opponent}", memberToJoin.player.name))
        }
        members.add(memberToJoin)
        membersKillDeath[memberToJoin] = MemberKillDeath(memberToJoin)
        memberToJoin.joinedParty = this

        // 글로우 효과
        GlowAPI.setGlowing(memberToJoin.player, GlowAPI.Color.GREEN, members.filter { it != memberToJoin }.map { it.player })
        GlowAPI.setGlowing(members.filter { it != memberToJoin }.map { it.player }, GlowAPI.Color.GREEN, memberToJoin.player)
    }

    fun kickMember(executor: PartyPlayer, memberToKick: PartyPlayer) {
        if (!executor.isLeader()) { executor.sendMessage(MessageManager.YOU_ARE_NOT_A_LEADER); return }
        if (!members.contains(memberToKick)) { executor.sendMessage(MessageManager.OPPONENT_IS_NOT_IN_YOUR_PARTY); return }
        if (executor == memberToKick) { executor.sendMessage("$PREFIX 자기 자신을 강퇴할 수 없습니다"); return }

        memberToKick.sendMessage("$PREFIX 파티에서 §c강퇴§f되었습니다.")
        onPlayerLeave(memberToKick)
    }

    fun onPlayerLeave(memberToLeave: PartyPlayer, bPartyDestroyed: Boolean=false) {  // 킥(Party), 탈퇴(Player)
        members.remove(memberToLeave)
        membersKillDeath.remove(memberToLeave)
        memberToLeave.joinedParty = null
        memberToLeave.partyChat = false

        if (!bPartyDestroyed) {
            members.forEach {
                it.sendMessage(MessageManager.SOMEONE_LEAVED_PARTY.replace("{Opponent}", memberToLeave.player.name))
            }
            if (this.members.size < 2) {
                this.destroy(); return   // 파티 해체
            }
            if (leader == memberToLeave) {
                // 파티 리더 위임(랜덤)
                leader = members.random()
                leader.sendMessage(MessageManager.RECEIVED_PARTY_LEADER)
            }
        }
    }

    private fun destroy() {
        val clonedMembers = ArrayList(members)  // onPlayerLeave에서 members를 건드려버려서, members를 clone 해야 함
        clonedMembers.forEach {
            it.sendMessage(MessageManager.PARTY_DESTROYED)
            onPlayerLeave(it, true)
        }
    }
}