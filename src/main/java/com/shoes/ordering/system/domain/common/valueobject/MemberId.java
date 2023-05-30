package com.shoes.ordering.system.domain.common.valueobject;
import java.util.UUID;

public class MemberId extends BaseId<UUID> {
    public MemberId(UUID value) {
        super(value);
    }
}
