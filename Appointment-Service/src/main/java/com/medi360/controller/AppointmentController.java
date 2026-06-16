package com.medi360.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medi360.DTO.AppointmentDTO;
import com.medi360.DTO.AppointmentResponseDTO;
import com.medi360.DTO.AppointmentWithDetailsDTO;
import com.medi360.entities.Appointment;
import com.medi360.exception.AppointmentNotFoundException;
import com.medi360.exception.DoctorNotFoundException;
import com.medi360.exception.PatientNotFoundException;
import com.medi360.exception.SlotNotAvailableException;
import com.medi360.service.AppointmentService;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

	private final AppointmentService appointmentService;

	public AppointmentController(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}

	// ✅ ADD
	@PostMapping("/add")
	public ResponseEntity<AppointmentResponseDTO> addAppointment(@RequestBody AppointmentDTO appointmentDTO)
			throws PatientNotFoundException, DoctorNotFoundException, SlotNotAvailableException {

		Appointment appointment = appointmentService.addAppointment(appointmentDTO.getAppointment());
		AppointmentResponseDTO dto = new AppointmentResponseDTO();
		dto.setAppointment(appointment);
		dto.setStatusCode(201);

		if ("RESCHEDULED".equalsIgnoreCase(appointment.getStatus())) {
			String start = appointment.getTime().toString();
			String end = appointment.getTime().plusMinutes(appointment.getDurationMinutes()).toString();
			dto.setWasRescheduled(true);
			dto.setMessage("Requested slot was unavailable. Appointment scheduled on " + appointment.getDate()
					+ " from " + start + " to " + end + ".");
		} else {
			dto.setWasRescheduled(false);
			dto.setMessage("Appointment created successfully");
		}

		return ResponseEntity.status(201).body(dto);
	}

	// ✅ UPDATE
	@PutMapping("/update")
	public ResponseEntity<AppointmentResponseDTO> updateAppointment(@RequestBody AppointmentDTO appointmentDTO)
			throws AppointmentNotFoundException, PatientNotFoundException, DoctorNotFoundException,
			SlotNotAvailableException {

		Appointment appointment = appointmentService.updateAppointment(appointmentDTO.getAppointment());
		AppointmentResponseDTO dto = new AppointmentResponseDTO();
		dto.setAppointment(appointment);
		dto.setStatusCode(200);

		if ("RESCHEDULED".equalsIgnoreCase(appointment.getStatus())) {
			String start = appointment.getTime().toString();
			String end = appointment.getTime().plusMinutes(appointment.getDurationMinutes()).toString();
			dto.setWasRescheduled(true);
			dto.setMessage(
					"Appointment rescheduled on " + appointment.getDate() + " from " + start + " to " + end + ".");
		} else {
			dto.setWasRescheduled(false);
			dto.setMessage("Appointment updated successfully");
		}

		return ResponseEntity.ok(dto);
	}

	// ✅ DELETE
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteAppointment(@PathVariable("id") int id) throws AppointmentNotFoundException {
		appointmentService.deleteAppointment(id);
		return ResponseEntity.ok("Appointment deleted successfully");
	}

	// ✅ GET ALL — returns enriched list with patient and doctor names
	@GetMapping("/getAll")
	public ResponseEntity<List<AppointmentWithDetailsDTO>> getAllAppointments() {
		return ResponseEntity.ok(appointmentService.getAllAppointmentsWithDetails());
	}

	// ✅ GET BY ID — returns enriched DTO with patient and doctor names
	@GetMapping("/get/{id}")
	public ResponseEntity<AppointmentResponseDTO> getAppointmentById(@PathVariable("id") int id)
			throws AppointmentNotFoundException {

		AppointmentWithDetailsDTO apptWithDetails = appointmentService.getAppointmentByIdWithDetails(id);

		AppointmentResponseDTO dto = new AppointmentResponseDTO();
		dto.setAppointment(apptWithDetails); // ✅ wrap enriched DTO
		dto.setStatusCode(200);
		dto.setMessage("Appointment fetched successfully");
		return ResponseEntity.ok(dto);
	}

// ✅ PAGINATED — returns enriched page with patient and doctor names
	@GetMapping("/getAllPaginated")
	public ResponseEntity<Page<AppointmentWithDetailsDTO>> getAllAppointmentsWithPagination(
			@RequestParam("pgno") int pgno, @RequestParam("size") int size, @RequestParam("sorting") String sorting,
			@RequestParam("asc") boolean asc) {
		Sort sort = asc ? Sort.by(sorting).ascending() : Sort.by(sorting).descending();
		Pageable pageable = PageRequest.of(pgno, size, sort);
		return ResponseEntity.ok(appointmentService.getAllAppointmentsWithDetailsPaginated(pageable));
	}

	// ✅ BY DOCTOR — returns enriched list with patient and doctor names
	@GetMapping("/doctor/{doctorId}")
	public ResponseEntity<List<AppointmentWithDetailsDTO>> getAppointmentsByDoctor(
			@PathVariable("doctorId") int doctorId) throws DoctorNotFoundException {
		return ResponseEntity.ok(appointmentService.getAppointmentsByDoctorIdWithDetails(doctorId));
	}

	// ✅ BY DOCTOR + DATE
	@GetMapping("/doctor/{doctorId}/date")
	public ResponseEntity<List<Appointment>> getAppointmentsByDoctorAndDate(@PathVariable("doctorId") int doctorId,
			@RequestParam("date") LocalDate date) throws DoctorNotFoundException {
		return ResponseEntity.ok(appointmentService.getAppointmentsByDoctorAndDate(doctorId, date));
	}
}