package com.shoes.ordering.system.domain.member.domain.core.valueobject;

import java.util.Objects;
import java.util.UUID;

public class StreetAddress {
    private final UUID id;
    private final String street;
    private final String postalCode;
    private final String city;

    public StreetAddress(UUID id, String street, String postalCode, String city) {
        this.id = id;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
    }

    public UUID getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StreetAddress)) return false;
        StreetAddress that = (StreetAddress) o;
        return Objects.equals(street, that.street) && Objects.equals(postalCode, that.postalCode) && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, postalCode, city);
    }
}
