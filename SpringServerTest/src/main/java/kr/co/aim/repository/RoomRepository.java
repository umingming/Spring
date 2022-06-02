package kr.co.aim.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.aim.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
