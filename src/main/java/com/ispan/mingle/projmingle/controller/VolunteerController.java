package com.ispan.mingle.projmingle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.VolunteerService;
import com.ispan.mingle.projmingle.domain.VolunteerBean;

@RestController
@RequestMapping("/api/volunteer")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    
    /*
     * CREATE
     */
    @PostMapping("/addWorkToKeepList")
    public void addWorkToKeepList(@RequestParam String volunteerId, @RequestParam Integer workId) {
        volunteerService.addWorkToKeepList(volunteerId, workId);
    }


    /*O
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
     * DELETE
     */
    @PostMapping("/removeWorkFromKeepList")
    public void removeWorkFromKeepList(@RequestParam String volunteerId, @RequestParam Integer workId) {
        volunteerService.removeWorkFromKeepList(volunteerId, workId);
    }



}