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
import java.util.List;

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
            list.add(position);
        });
        return list;
    }

    public void addPosition(String secureKey, Position inputTMP) {
        inputTMP.setRoomSecureKey(secureKey);
        positionRepository.save(inputTMP);
    }

    public void deletePosition(int positionId) {
        Position tmp = positionRepository.findById(positionId).get();
        positionRepository.delete(tmp);
    }

    public int getSum(String secureKey){
        return 5000;
    }
}







//
//    public void addUserName(Room room, Position positionTMP) {
//        System.out.println(positionTMP.getPositionNameUser());
//        System.out.println(positionTMP.getPositionId());
//        for (int i = 0; i < room.getPositions().size(); i++) {
//            if (positionTMP.getPositionId() == room.getPositions().get(i).getPositionId()) {
//                room.getPositions().get(i).setPositionNameUser(positionTMP.getPositionNameUser());
//                System.out.println(room.getPositions().get(i).getPositionNameUser());
//            }
//        }
//    }

