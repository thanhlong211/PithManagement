package com.swd.ccp.services;

import com.swd.ccp.DTO.request_models.BookingRequest;
import com.swd.ccp.DTO.request_models.UpdateBookingRequest;
import com.swd.ccp.DTO.response_models.ResponseObject;
import com.swd.ccp.models.entity_models.Booking;

import java.util.List;

public interface BookingService {
    ResponseObject createBooking(BookingRequest bookingRequest);
    ResponseObject updateBookingStatus(Integer bookingId, String newStatus);
    List<Booking> getAllBookings();
    List<Booking> getBookingsByCustomerId(Integer customerId);
    List<Booking> getBookingsByShopId(Integer shopId);
    ResponseObject updateBooking(UpdateBookingRequest bookingRequest);
    List<Booking> getDoneBookingsByCustomerId(Integer customerId);
}
