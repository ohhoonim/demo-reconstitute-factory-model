package dev.ohhoonim.components.model.unit;

import java.time.Instant;
public sealed interface Created extends Unit permits BaseEntity {
    Instant getCreatedAt();
    String getCreatedBy();
}
