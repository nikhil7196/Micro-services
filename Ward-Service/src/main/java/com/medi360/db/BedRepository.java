
package com.medi360.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medi360.entities.Bed;

@Repository
public interface BedRepository extends JpaRepository<Bed, Integer> {

	List<Bed> findByWard_WardIdAndBedStatus(int wardId,String status);
	List<Bed> findByWard_WardId(int wardId);

}
