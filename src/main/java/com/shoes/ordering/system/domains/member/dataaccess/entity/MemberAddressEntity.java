package com.shoes.ordering.system.domains.member.dataaccess.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member_address")
@Entity
public class MemberAddressEntity {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID")
    private MemberEntity member;

    private String street;
    private String postalCode;
    private String city;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberAddressEntity)) return false;
        MemberAddressEntity that = (MemberAddressEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
