package kr.co.aim.manager;

import kr.co.aim.domain.Packet;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class PacketManager {
	private Packet packet;
	
	public PacketManager(Packet packet) {
		this.packet = packet;
	}
}
