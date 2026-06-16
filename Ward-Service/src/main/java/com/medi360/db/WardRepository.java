
package com.medi360.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medi360.entities.Ward;

@Repository
public interface WardRepository extends JpaRepository<Ward, Integer>{

}
