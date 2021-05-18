package ru.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.models.Room;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
}
