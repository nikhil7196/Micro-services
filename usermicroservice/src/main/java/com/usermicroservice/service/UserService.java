package com.usermicroservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder; // ✅ Added
import org.springframework.stereotype.Service;

import com.usermicroservice.DTO.UserDTO;
import com.usermicroservice.DTO.UserResponseDTO;
import com.usermicroservice.db.UserRepository;
import com.usermicroservice.entities.User;
import com.usermicroservice.exception.BadRequestException;
import com.usermicroservice.exception.ResourceNotFoundException;
import com.usermicroservice.exception.UserNotFoundException;
import com.usermicroservice.feign.AuditlogFeign;

@Service
public class UserService {

    @Autowired
    private UserRepository userrepo;

    @Autowired
    private AuditlogFeign auf;

    @Autowired
    private PasswordEncoder passwordEncoder; 

    public UserResponseDTO addUser(UserDTO userDto) {

        if (userrepo.existsByEmail(userDto.getUserEmail())) {
            auf.logFailure("User.CREATE",
                    "Duplicate email: " + userDto.getUserEmail());
            throw new BadRequestException("Email already registered: " + userDto.getUserEmail());
        }

        try {
            User user = mapToEntity(userDto);
            UserResponseDTO result = mapToDTO(userrepo.save(user));

            auf.log("User.CREATE_SUCCESS | UserID: " + result.getUserId()
                    + " | Name: " + result.getUserName()
                    + " | Role: " + result.getRole()
                    + " | Email: " + result.getEmail(), result.getUserId());

            return result;

        } catch (Exception ex) {
            auf.logFailure("User.CREATE", ex.getMessage());
            throw ex;
        }
    }

    public UserResponseDTO userApproval(String status, int id) {

        User user = userrepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with ID: " + id));

        String before = "Name: " + user.getUserName()
                + " | Status: " + user.getStatus();

        try {
            user.setStatus(status);
            UserResponseDTO result = mapToDTO(userrepo.save(user));

            auf.log("User.Approval | UserID: " + id
                    + " | Before: " + before
                    + " | Status: " + status, id);

            return result;

        } catch (Exception ex) {
            auf.logFailure("User.UPDATE", ex.getMessage());
            throw ex;
        }
    }

    public UserResponseDTO updateUser(int id, UserDTO dto) {

        User user = userrepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with ID: " + id));

        String before = "Name: " + user.getUserName()
                + " | Role: " + user.getUserRole()
                + " | Phone: " + user.getUserPhone();

        try {
            user.setUserName(dto.getUserName());
            user.setUserRole(dto.getUserRole());
            user.setUserPhone(dto.getPhonenumber());

            // ✅ Fixed: Password is now hashed before saving (was plain text before)
            if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
            }

            UserResponseDTO result = mapToDTO(userrepo.save(user));

            auf.log("User.UPDATE_SUCCESS | UserID: " + id
                    + " | Before: " + before
                    + " | After: Name: " + dto.getUserName()
                    + " | Role: " + dto.getUserRole()
                    + " | Phone: " + dto.getPhonenumber(), id);

            return result;

        } catch (Exception ex) {
            auf.logFailure("User.UPDATE", ex.getMessage());
            throw ex;
        }
    }

    public String deleteUser(int id) throws UserNotFoundException {

        User user = userrepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with ID: " + id));

        try {
            userrepo.deleteById(id);

            auf.log("User.DELETE_SUCCESS | UserID: " + id
                    + " | Name: " + user.getUserName()
                    + " | Email: " + user.getUserEmail()
                    + " | Role: " + user.getUserRole(), id);

            return "Successfully Deleted";

        } catch (Exception ex) {
            auf.logFailure("User.DELETE", ex.getMessage());
            throw ex;
        }
    }

    public List<UserResponseDTO> getAllUser() {
        return this.userrepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Page<User> getAllUsersWithPagination(Pageable pageable) {
        return this.userrepo.findAll(pageable);
    }

    public UserResponseDTO findById(int id) throws UserNotFoundException {
        User user = userrepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with ID: " + id));
        return mapToDTO(user);
    }

    public UserResponseDTO getUserByEmail(String email) {
        User user = userrepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with email: " + email));
        return mapToDTO(user);
    }

    private UserResponseDTO mapToDTO(User u) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(u.getUserId());
        dto.setUserName(u.getUserName());
        dto.setRole(u.getUserRole());
        dto.setEmail(u.getUserEmail());
        dto.setPhoneNumber(u.getUserPhone());
        dto.setStatus(u.getStatus());
        return dto;
    }

    private User mapToEntity(UserDTO dto) {
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setUserRole(dto.getUserRole());
        user.setUserEmail(dto.getUserEmail());
        user.setUserPhone(dto.getPhonenumber());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return user;
    }
}