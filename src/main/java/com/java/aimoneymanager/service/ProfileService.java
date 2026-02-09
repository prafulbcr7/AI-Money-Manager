package com.java.aimoneymanager.service;

import com.java.aimoneymanager.dto.AuthDTO;
import com.java.aimoneymanager.dto.ProfileDTO;
import com.java.aimoneymanager.entity.ProfileEntity;
import com.java.aimoneymanager.repo.ProfileRepo;
import com.java.aimoneymanager.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepo profileRepo;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    @Value("${LOCALHOST_URL}")
    private String localHostUrl;

    public ProfileDTO registerProfile(ProfileDTO profileDTO) {

        ProfileEntity newProfile = mapProfileDTOtoProfileEntity(profileDTO);
        newProfile.setActivationToken(UUID.randomUUID().toString());
        newProfile = profileRepo.save(newProfile);

        String activationLink = localHostUrl+"/api/v1.0/activate?token=" + newProfile.getActivationToken();
        String subject = "Activate your AI Money Manager Account.";
        String body = "Click the below following link to activate your account:- "+ activationLink;
        emailService.sendEmail(newProfile.getEmail(), subject, body);

        return mapProfileEntityToProfileDTO(newProfile);
    }

    public ProfileEntity mapProfileDTOtoProfileEntity(ProfileDTO profileDTO) {
        return ProfileEntity.builder()
                .id(profileDTO.getId())
                .fullName(profileDTO.getFullName())
                .email(profileDTO.getEmail())
                .password(passwordEncoder.encode(profileDTO.getPassword()))
                .phoneNumber(profileDTO.getPhoneNumber())
                .profileImageUrl(profileDTO.getProfileImageUrl())
                .createdAt(profileDTO.getCreatedAt())
                .updatedAt(profileDTO.getUpdatedAt())
                .build();
    }

    public ProfileDTO mapProfileEntityToProfileDTO(ProfileEntity profileEntity) {
        return ProfileDTO.builder()
                .id(profileEntity.getId())
                .fullName(profileEntity.getFullName())
                .email(profileEntity.getEmail())
                .phoneNumber(profileEntity.getPhoneNumber())
                .profileImageUrl(profileEntity.getProfileImageUrl())
                .createdAt(profileEntity.getCreatedAt())
                .updatedAt(profileEntity.getUpdatedAt())
                .build();
    }

    public boolean activateProfile(String activateToken) {
        return profileRepo.findByActivationToken(activateToken)
                .map( profile -> {
                    profile.setIsActive(true);
                    profileRepo.save(profile);
                    return true;
                })
                .orElse(false);
    }

    public boolean isUsersAccountActive(String email) {
        return profileRepo.findByEmail(email)
                .map(ProfileEntity::getIsActive)
                .orElse(false);
    }

    public ProfileEntity getCurrentProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return profileRepo.findByEmail(authentication.getName())
                .orElseThrow( () -> new UsernameNotFoundException("Users Profile not found" + authentication.getName()));
    }

    public ProfileDTO getPublicProfile(String email) {
        ProfileEntity currentUser = null;
        if (email == null) {
            currentUser = getCurrentProfile();
        }
        else {
            currentUser = profileRepo.findByEmail(email)
                    .orElseThrow( () -> new UsernameNotFoundException("Users Profile not found" + email));
        }

        return mapProfileEntityToProfileDTO(currentUser);
    }

    public Map<String, Object> authenticateUserAndGenerateToken(AuthDTO authDTO) {
         try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword()));
            // Generate JWT TOken now
             String jwtToken = jwtUtil.generateJwtToken(authDTO.getEmail());
             return Map.of(
                     "token", jwtToken,
                     "user", getPublicProfile(authDTO.getEmail())
             );

         } catch (Exception e) {
             throw new RuntimeException("Invalid Email And Password");
         }
    }

}
