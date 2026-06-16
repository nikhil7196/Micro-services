
package com.medi360.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.medi360.db.WardRepository;
import com.medi360.entities.Ward;
import com.medi360.exception.WardNotFoundException;

@Service
public class WardService {
	@Autowired
	private  WardRepository wardRepository;
	
	public WardService(WardRepository wardRepository) {
		this.wardRepository=wardRepository;
	}
	
	public Ward createWard(Ward ward) {
		return wardRepository.save(ward);
	}
	public List<Ward> getAllWard(){
		return wardRepository.findAll();
	}
	public Ward getWardById(int wardId) {
		return wardRepository.findById(wardId).orElse(null);
	}
	public Ward updateWard(Ward ward) throws WardNotFoundException {
		if (!wardRepository.existsById(ward.getWardId())){
			throw new WardNotFoundException("ward not found with id " + ward.getWardId());
		}
		return wardRepository.save(ward);
	}
	public void deleteWard(int wardId) throws WardNotFoundException{
		if (!wardRepository.existsById(wardId)) {
			throw new WardNotFoundException("ward not found with id " + wardId);
		}
		wardRepository.deleteById(wardId);
	}
	public Page<Ward>getAllWardsWithPaginated(Pageable pageable){
		return this.wardRepository.findAll(pageable);
	}
	// Add this to your existing WardService.java

	public String getWardOccupancyReport(int wardId) throws WardNotFoundException {
	    Ward ward = wardRepository.findById(wardId)
	            .orElseThrow(() -> new WardNotFoundException("Ward not found with id " + wardId));
	    
	    // Logic for Feature 4.5: Occupancy tracking [cite: 74]
	    long occupiedBeds = ward.getBeds().stream()
	            .filter(b -> "OCCUPIED".equalsIgnoreCase(b.getBedStatus()))
	            .count();
	            
	    return String.format("Ward: %s | Total Capacity: %d | Occupied: %d | Available: %d", 
	        ward.getWardname(), 
	        ward.getWardcapacity(), 
	        occupiedBeds, 
	        (ward.getWardcapacity() - occupiedBeds));
	}

}
