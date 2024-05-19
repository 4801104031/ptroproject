package vn.hienld.admin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.hienld.admin.dto.BookingDTO;
import vn.hienld.admin.error.BadRequestException;
import vn.hienld.admin.model.Booking;
import vn.hienld.admin.model.Room;
import vn.hienld.admin.repository.BookingRepository;
import vn.hienld.admin.repository.RoomRepository;
import vn.hienld.admin.service.BookingService;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Page<BookingDTO> findAll(BookingDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getSize());
        return bookingRepository.findAll(dto.getCusName(), dto.getRoomId(), dto.getStatus(), dto.getFromDate(), dto.getToDate(), dto.getIsDeleted(), pageable)
                .map(p -> objectMapper.convertValue(p, BookingDTO.class));
    }

    @Override
    public Booking save(BookingDTO dto) {
        if(dto.getId() == null){
            return add(dto);
        }
        return update(dto);
    }

    public Booking add(BookingDTO dto) {
        Booking booking = new Booking();
        return getBooking(dto, booking);
    }

    private Booking getBooking(BookingDTO dto, Booking booking) {
        booking.setDateSet(dto.getDateSet());
        booking.setRentalDeposit(dto.getRentalDeposit());
        if(dto.getStatus() != null){
            booking.setStatus(dto.getStatus());
        }else {
            booking.setStatus(0);
        }
        if(roomRepository.findById(dto.getRoomId()).isEmpty()){
            throw new BadRequestException("Phòng trọ không tồn tại!");
        }
        booking.setRoomId(dto.getRoomId());
        booking.setAfterDays(dto.getAfterDays());
        booking.setRoomName(dto.getRoomName());
        booking.setBuildingId(dto.getBuildingId());
        booking.setBuildingName(dto.getBuildingName());
        booking.setCusName(dto.getCusName());
        Room room = roomRepository.getReferenceById(dto.getRoomId());
        if(booking.getStatus() == 0){
            room.setStatus(2);
        }else {
            room.setStatus(0);
        }
        roomRepository.save(room);

        return bookingRepository.save(booking);
    }

    public Booking update(BookingDTO dto) {
        Booking booking = bookingRepository.getReferenceById(dto.getId());
        return getBooking(dto, booking);
    }

    @Override
    @Transactional
    public void delete(BookingDTO dto) {
        try{
            bookingRepository.delete(dto.getId());
        }catch (Exception ex){
            log.error("Delete booking exception : {}", ex.getMessage());
            throw new BadRequestException("Có lỗi sảy ra trong quá trình thực hiện!");
        }
    }

    @Override
    @Transactional
    public void changeStatus(BookingDTO dto) {
        try{
            bookingRepository.changeStatus(dto.getId());
        }catch (Exception ex){
            log.error("Change status for booking exception : {}", ex.getMessage());
            throw new BadRequestException("Có lỗi sảy ra trong quá trình thực hiện!");
        }
    }
}
