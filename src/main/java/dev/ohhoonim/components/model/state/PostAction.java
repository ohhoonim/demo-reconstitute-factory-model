package dev.ohhoonim.components.model.state;

@FunctionalInterface
public interface PostAction<C> {
   void followup(C context); 
}
