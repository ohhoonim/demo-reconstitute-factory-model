package dev.ohhoonim.components.annotation;

import java.util.List;
import java.util.Optional;

public interface JpaRepository<E, I> {
   List<E> findAll() ;

   Optional<E> findById(I id);
}
