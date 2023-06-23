package com.shoes.ordering.system.domains.member.domain.core.valueobject;
import com.shoes.ordering.system.domains.common.valueobject.BaseId;

import java.util.UUID;

public class MemberId extends BaseId<UUID> {
    public MemberId(UUID value) {
        super(value);
    }
}
