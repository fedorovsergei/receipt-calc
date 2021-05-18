package ru.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.models.Position;

@Repository
public interface PositionRepository extends CrudRepository<Position, Integer> {
}
