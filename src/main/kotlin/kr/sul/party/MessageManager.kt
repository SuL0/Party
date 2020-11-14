package kr.sul.party

object MessageManager {
    const val PREFIX = "§8[§6PARTY§8]§f"
    const val HEADER = "§8§m             §8 [§6§l파티§8]§m              "
    const val FOOTER = "§8§m                                   "
    // 플레이어 //--

    // 기본
    const val CANNOT_USE_TO_YOURSELF = "$PREFIX 해당 명령어를 자신에게 사용할 수 없습니다."
    const val YOU_ARE_NOT_IN_PARTY = "$PREFIX 당신에게 가입한 파티가 없습니다."
    const val YOU_ARE_NOT_A_LEADER = "$PREFIX 당신은 해당 파티의 §e리더§f가 아닙니다."

    // 초대장 //

    // 보낸 사람
    const val OPPONENT_ALREADY_IN_PARTY = "$PREFIX 상대방은 이미 파티에 가입돼있습니다."
    const val OPPONENT_HAS_YOUR_INVITATION = "$PREFIX 상대방은 당신이 이전에 보낸 초대장을 검토하는 중 입니다."
    const val SENT_INVITE = "$PREFIX §c'{Receiver}' §f에게 초대장을 보냈습니다."
    const val YOUR_INVITATION_HAS_DENIED = "$PREFIX 당신이 §c'{Receiver}' §f에게 보낸 초대가 거절되었습니다."

    // 받는 사람
    const val ACCEPT_RECEIVED_INVITATION = "$PREFIX §c'{Sender}' §f에게서 온 초대를 §a수락§f하였습니다."
    const val DENY_RECEIVED_INVITATION = "$PREFIX §c'{Sender}' §f에게서 온 초대를 §c거절§f하였습니다."
    const val DONT_HAVE_SUCH_INVITATION = "$PREFIX §c'{Arg1}' §f(이)라는 초대가 존재하지 않거나 만료됐습니다."
    const val DENY_ALL_RECEIVED_INVITATIONS = "$PREFIX 처리 대기중인 모든 초대들이 거절되었습니다."
    const val INVITATION_CANNOT_BE_PROCESSED_1 = "$PREFIX 초대를 처리하는 중 문제가 발생하여 취소되었습니다."
    const val INVITATION_CANNOT_BE_PROCESSED_2 = "§7- 사유 : §e{Reason}"
    const val RECEIVED_INVITATION_DENIED_AUTOMATICALLY = "$PREFIX '{Sender}' §f에게서 온 초대가 §c만료§f되었습니다."


    // 채팅
    const val SWITCH_PARTY_CHAT = "$PREFIX §f파티 채팅을 {ON/OFF} §f로 전환하였습니다."

    // 탈퇴
    const val LEAVE_PARTY = "$PREFIX 파티에서 §c탈퇴§f했습니다."

    // 파티 //--
    const val SOMEONE_JOINED_PARTY = "$PREFIX §c'{Opponent}' §f이(가) 파티에 §a가입§f했습니다."




    const val SOMEONE_LEAVED_PARTY = "$PREFIX §c'{Opponent}' §f이(가) 파티에서 §c탈퇴§f했습니다."
    const val RECEIVED_PARTY_LEADER = "$PREFIX 파티장을 §e위임§f받았습니다. 이제 당신이 파티장입니다."

    const val PARTY_DESTROYED = "$PREFIX 파티원 부족으로, 파티가 §4해체§f되었습니다."

}
