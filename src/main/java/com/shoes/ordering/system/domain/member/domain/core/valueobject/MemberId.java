package com.shoes.ordering.system.domain.member.domain.core.valueobject;
import com.shoes.ordering.system.domain.common.valueobject.BaseId;

import java.util.UUID;

public class MemberId extends BaseId<UUID> {
    public MemberId(UUID value) {
        super(value);
    }
}
