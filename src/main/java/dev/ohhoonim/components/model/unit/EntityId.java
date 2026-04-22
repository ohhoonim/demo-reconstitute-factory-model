package dev.ohhoonim.components.model.unit;

import com.fasterxml.jackson.annotation.JsonValue;

public non-sealed interface EntityId extends Unit {
    String getRawValue();

    @JsonValue 
    default String toValue() {
        return getRawValue();
    }

    interface Creator<T extends EntityId> {
        T from(String value);
        T generate();
    }
}
