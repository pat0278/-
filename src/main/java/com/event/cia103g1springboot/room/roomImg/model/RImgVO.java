package com.event.cia103g1springboot.room.roomImg.model;

import com.event.cia103g1springboot.room.roomtype.model.RTVO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name="RoomImg")
public class RImgVO implements java.io.Serializable{
	
	private Integer roomImgId;
	
	private RTVO rtVO;
	
	private String roomImgName;
	
	private byte[] roomImg;

	@Id
	@Column(name="roomImgId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getRoomImgId() {
		return roomImgId;
	}

	public void setRoomImgId(Integer roomImgId) {
		this.roomImgId = roomImgId;
	}
	
	@ManyToOne
	@JoinColumn(name="roomTypeId")
	public RTVO getRtVO() {
		return rtVO;
	}

	public void setRtVO(RTVO rtVO) {
		this.rtVO = rtVO;
	}

	@Column(name="roomImgName")
	public String getRoomImgName() {
		return roomImgName;
	}

	public void setRoomImgName(String roomImgName) {
		this.roomImgName = roomImgName;
	}

	@Column(name="roomImg")
	public byte[] getRoomImg() {
		return roomImg;
	}

	public void setRoomImg(byte[] roomImg) {
		this.roomImg = roomImg;
	}

	public RImgVO(Integer roomImgId, RTVO rtVO, String roomImgName, byte[] roomImg) {
		super();
		this.roomImgId = roomImgId;
		this.rtVO = rtVO;
		this.roomImgName = roomImgName;
		this.roomImg = roomImg;
	}

	public RImgVO() {
		super();
		// TODO Auto-generated constructor stub
	}
}
