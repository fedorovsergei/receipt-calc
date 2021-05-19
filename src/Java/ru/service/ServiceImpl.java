package ru.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.models.Position;
import ru.models.Room;
import ru.repository.PositionRepository;
import ru.repository.RoomRepository;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ServiceImpl {

    private final RoomRepository roomRepository;
    private final PositionRepository positionRepository;

    @Autowired
    public ServiceImpl(RoomRepository roomRepository, PositionRepository positionRepository) {
        this.roomRepository = roomRepository;
        this.positionRepository = positionRepository;
    }

    public List<Room> getRooms() {
        List<Room> list = new ArrayList<>();
        roomRepository.findAll().forEach(list::add);
        return list;
    }

    public Room getRoomForKey(String secureKey) {
        List<Room> list = new ArrayList<>();
        roomRepository.findAll().forEach(room -> {
            if (!room.getSecureKey().equals(secureKey)) return;
            list.add(room);
        });
        return list.stream().findAny().orElse(null);
    }

    public String createRoom(Room room) {
        String secureKey = new BigInteger(130, new SecureRandom()).toString(36);
        room.setSecureKey(secureKey);
        roomRepository.save(room);
        return secureKey;
    }

    public void changeRoomName(Room room, String secureKey) {
        Room tmp = getRoomForKey(secureKey);
        tmp.setName(room.getName());
        roomRepository.save(tmp);
    }

    public List<Position> getPositions(String secureKey) {
        List<Position> list = new ArrayList<>();
        positionRepository.findAll().forEach(position -> {
            if (position.getRoomSecureKey().equals(secureKey))
                list.add(position);
        });
        return list;
    }

    public void addPosition(String secureKey, Position inputTMP) {
        inputTMP.setRoomSecureKey(secureKey);
        positionRepository.save(inputTMP);
    }

    public void deletePosition(int positionId) {
        if (positionRepository.existsById(positionId)) {
            positionRepository.deleteById(positionId);
        }
    }

    public String getSum(String secureKey) {
        List<Position> list = getPositions(secureKey);
        Integer result = list.stream().mapToInt(Position::getPositionPrice).sum();
        return result.toString();
    }


    public void addUserName(String id, String positionNameUser) {
        Position tmp = positionRepository.findById(Integer.parseInt(id)).get();
        tmp.setPositionNameUser(positionNameUser);
        positionRepository.save(tmp);
    }

    public String getKeyPosition(String id) {
        Position tmp = positionRepository.findById(Integer.parseInt(id)).get();
        return tmp.getRoomSecureKey();
    }

    public Map<String, Integer> createMap(String key) {
       List<Position> list = getPositions(key);
        Map<String, Integer> map= new HashMap<>();

        for (Position position : list) {
            map.merge(position.getPositionNameUser(), position.getPositionPrice(), Integer::sum);
        }

        return map;
    }
}



