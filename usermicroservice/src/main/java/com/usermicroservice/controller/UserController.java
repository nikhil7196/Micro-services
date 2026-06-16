package com.usermicroservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usermicroservice.DTO.UserDTO;
import com.usermicroservice.DTO.UserResponseDTO;
import com.usermicroservice.entities.User;
import com.usermicroservice.exception.UserNotFoundException;
import com.usermicroservice.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService us;

    // ✅ ADD USER
    @PostMapping("/insertuserdata")
    public ResponseEntity<UserResponseDTO> addUser(@RequestBody UserDTO userDto) {
        return ResponseEntity.ok(us.addUser(userDto));
    }

    // ✅ USER APPROVAL — explicit PathVariable names
    @PutMapping("/approval/{status}/{id}")
    public ResponseEntity<UserResponseDTO> userApproval(
            @PathVariable("status") String status,
            @PathVariable("id") int id) {
        return ResponseEntity.ok(us.userApproval(status, id));
    }

    // ✅ UPDATE USER — explicit PathVariable name
    @PutMapping("/updateuser/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable("id") int id,
            @RequestBody UserDTO userDto) {
        return ResponseEntity.ok(us.updateUser(id, userDto));
    }

    // ✅ DELETE USER — explicit PathVariable name
    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable("id") int id) throws UserNotFoundException {
        return ResponseEntity.ok(us.deleteUser(id));
    }

    // ✅ GET ALL USERS
    @GetMapping("/fetchallusers")
    public ResponseEntity<List<UserResponseDTO>> getAllUser() {
        return ResponseEntity.ok(us.getAllUser());
    }

    // ✅ GET ALL PAGINATED
    @GetMapping("/fetchAllUsersPaginated")
    public ResponseEntity<Page<User>> getAllUsersPaginated(
            @RequestParam("pgno") int pgno,
            @RequestParam("size") int size,
            @RequestParam("sorting") String sorting,
            @RequestParam("asc") boolean asc) {
        Sort sort = asc ? Sort.by(sorting).ascending() : Sort.by(sorting).descending();
        Pageable pageable = PageRequest.of(pgno, size, sort);
        return ResponseEntity.ok(us.getAllUsersWithPagination(pageable));
    }

    // ✅ FIND BY ID — explicit PathVariable name
    @GetMapping("/findbyid/{id}")
    public ResponseEntity<UserResponseDTO> findById(
            @PathVariable("id") int id) throws UserNotFoundException {
        return ResponseEntity.ok(us.findById(id));
    }

    // ✅ MY PROFILE
    @GetMapping("/myProfile")
    public ResponseEntity<?> getMyProfile(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("User not authenticated");
        }
        String email = authentication.getName();
        UserResponseDTO user = us.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
}