package kz.dev.triangle.core.repository;

import kz.dev.triangle.core.model.TriangleEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ITriangleRepository extends JpaRepository<TriangleEntity, Integer> {
}
