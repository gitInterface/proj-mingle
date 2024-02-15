package com.ispan.mingle.projmingle.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ispan.mingle.projmingle.Service.VolunteerService;
import com.ispan.mingle.projmingle.domain.VolunteerBean;
import com.ispan.mingle.projmingle.repository.VolunteerRepository;

@RestController
@RequestMapping("/api/volunteer")
public class VolunteerController {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private VolunteerService volunteerService;

    /*
     * CREATE
     */
    @PostMapping("/addWorkToKeepList")
    public void addWorkToKeepList(@RequestParam String volunteerId, @RequestParam Integer workId) {
        volunteerService.addWorkToKeepList(volunteerId, workId);
    }

    /*
     * READ
     */
    @GetMapping("/getAllVolunteers")
    public ResponseEntity<Iterable<VolunteerBean>> getAllVolunteers() {
        Iterable<VolunteerBean> volunteers = volunteerService.getAllVolunteers();
        return ResponseEntity.ok(volunteers);
    }

    @GetMapping("/isWorkKeptByVolunteer")
    public ResponseEntity<Boolean> isWorkKeptByVolunteer(
            @RequestParam String volunteerId,
            @RequestParam Integer workId) {
        boolean isKept = volunteerService.isWorkKeptByVolunteer(volunteerId, workId);
        return ResponseEntity.ok(isKept);
    }

    /*
     * UPDATE
     */

    @PutMapping("/updateAdminStatus")
    public VolunteerBean updateAdminStatus(@RequestBody VolunteerBean volunteer) {
        Optional<VolunteerBean> existingVolunteer = volunteerRepository.findById(volunteer.getUserid());

        if (existingVolunteer.isPresent()) {
            VolunteerBean updatedVolunteer = existingVolunteer.get();
            updatedVolunteer.setIsAdmin(volunteer.getIsAdmin());
            return volunteerRepository.save(updatedVolunteer);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Volunteer not found");
        }
    }

    /*
     * DELETE
     */
    @PostMapping("/removeWorkFromKeepList")
    public void removeWorkFromKeepList(@RequestParam String volunteerId, @RequestParam Integer workId) {
        volunteerService.removeWorkFromKeepList(volunteerId, workId);
    }

}