package com.nbs.repository;
import org.springframework.data.repository.CrudRepository;
import com.nbs.model.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

}
