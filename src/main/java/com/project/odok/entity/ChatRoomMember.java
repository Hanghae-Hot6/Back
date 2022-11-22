package com.project.odok.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoomMember {
//    private static final long serialVersionUID = 6494678977089006639L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomMemberId;

    @JoinColumn(name = "chatRoomId", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    @JoinColumn(name = "memberId", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "clubId", nullable = false)
    @ManyToOne
    private Club club;

    @Column(nullable = false)
    private Boolean enterStatus;

    @Builder
    public ChatRoomMember(ChatRoom chatRoom, Member member, Club club, Boolean enterStatus) {
        this.chatRoom = chatRoom;
        this.member = member;
        this.club = club;
        this.enterStatus = enterStatus;
    }

    public void updateEnterStatus() {
        this.enterStatus = true;
    }
}
