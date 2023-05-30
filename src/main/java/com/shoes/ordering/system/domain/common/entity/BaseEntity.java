package com.shoes.ordering.system.domain.common.entity;

import java.util.Objects;

// 각 엔터티 클래스에 대해 기본 엔터티를 확장하여, 일반 ID 를 식별자로 바꾼다.
public abstract class BaseEntity<ID> {
    private  ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;
        BaseEntity<?> that = (BaseEntity<?>) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
