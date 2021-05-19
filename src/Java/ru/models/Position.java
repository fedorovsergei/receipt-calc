package ru.models;

import javax.persistence.*;

@Entity
@Table(name = "position")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int positionId;
    private int positionPrice;
    private String positionName;
    private String positionNameUser;
    private String roomSecureKey;

    public Position(int positionId, int positionPrice, String positionName, String positionNameUser, String roomSecureKey) {
        this.positionId = positionId;
        this.positionPrice = positionPrice;
        this.positionName = positionName;
        this.positionNameUser = positionNameUser;
        this.roomSecureKey = roomSecureKey;
    }

    public Position() {
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getPositionPrice() {
        return positionPrice;
    }

    public void setPositionPrice(int positionPrice) {
        this.positionPrice = positionPrice;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionNameUser() {
        return positionNameUser;
    }

    public void setPositionNameUser(String positionNameUser) {
        this.positionNameUser = positionNameUser;
    }

    public String getRoomSecureKey() {
        return roomSecureKey;
    }

    public void setRoomSecureKey(String roomSecureKey) {
        this.roomSecureKey = roomSecureKey;
    }

}
