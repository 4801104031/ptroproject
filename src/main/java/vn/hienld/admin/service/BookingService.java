package vn.hienld.admin.service;

import org.springframework.data.domain.Page;
import vn.hienld.admin.dto.BookingDTO;
import vn.hienld.admin.model.Booking;

public interface BookingService {
    Page<BookingDTO> findAll(BookingDTO dto);
    Booking save(BookingDTO dto);
    void delete(BookingDTO dto);

    void changeStatus(BookingDTO dto);
}
