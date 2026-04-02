package com.lurdharry.medicationReminder.emergencycontact;

import com.lurdharry.medicationReminder.emergencycontact.dto.EmergencyContactRequest;
import com.lurdharry.medicationReminder.emergencycontact.dto.EmergencyContactResponse;
import com.lurdharry.medicationReminder.emergencycontact.model.EmergencyContact;
import com.lurdharry.medicationReminder.exception.ResponseException;
import com.lurdharry.medicationReminder.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmergencyContactService {

    private final EmergencyContactRepository repository;
    private final EmergencyContactMapper mapper;

    public EmergencyContactResponse addContact(EmergencyContactRequest request, User user) {
        var contact = mapper.toEntity(request);
        contact.setUser(user);
        repository.save(contact);
        return mapper.toResponse(contact);
    }

    public List<EmergencyContactResponse> getContacts(User user) {
        return repository.findByUserId(user.getId())
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public EmergencyContactResponse updateContact(UUID id, EmergencyContactRequest request, User user) {
        var contact = findByIdAndUser(id, user);
        if (request.name() != null) contact.setName(request.name());
        if (request.relationship() != null) contact.setRelationship(request.relationship());
        if (request.phone() != null) contact.setPhone(request.phone());
        if (request.email() != null) contact.setEmail(request.email());
        if (request.isPrimary() != null) contact.setIsPrimary(request.isPrimary());
        if (request.notifyOnMissedDose() != null) contact.setNotifyOnMissedDose(request.notifyOnMissedDose());
        repository.save(contact);
        return mapper.toResponse(contact);
    }

    public void deleteContact(UUID id, User user) {
        var contact = findByIdAndUser(id, user);
        repository.delete(contact);
    }

    private EmergencyContact findByIdAndUser(UUID id, User user) {
        var contact = repository.findById(id)
                .orElseThrow(() -> new ResponseException("Contact not found", HttpStatus.NOT_FOUND));
        if (!contact.getUser().getId().equals(user.getId())) {
            throw new ResponseException("Not authorized", HttpStatus.FORBIDDEN);
        }
        return contact;
    }
}

