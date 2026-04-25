package dev.ohhoonim.components.model.factory;

import java.util.List;

/*
A: aggregate root
I: aggregate root id
C: components of ar

*/

public interface ArFactory <A, I, C> {
    A reconsitute(I id, List<Class<? extends C>> requiredVos);
    
}
