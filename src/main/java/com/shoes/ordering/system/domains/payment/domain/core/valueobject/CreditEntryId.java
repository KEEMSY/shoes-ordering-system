package com.shoes.ordering.system.domains.payment.domain.core.valueobject;

import com.shoes.ordering.system.domains.common.valueobject.BaseId;

import java.util.UUID;

public class CreditEntryId extends BaseId<UUID> {
    public CreditEntryId(UUID value) {
        super(value);
    }
}
