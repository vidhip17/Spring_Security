package com.example.crud_react.crudReact.controller;

import com.example.crud_react.crudReact.config.JwtTokenProvider;
import com.example.crud_react.crudReact.entity.Announcement;
import com.example.crud_react.crudReact.entity.User;
import com.example.crud_react.crudReact.repository.UserRepo;
import com.example.crud_react.crudReact.service.AnnouncementService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/announcements")
public class AnnouncementController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private UserRepo userRepo;

    @GetMapping(value = "/getAllActiveAnnouncements")
    public List<Announcement> getAllActiveAnnouncements() {
        try {
            return announcementService.findAllActiveAnnouncement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/findAllByIsDeletedFalseOrderByCreateDateDesc")
    public List<Announcement> findAllByIsDeletedFalseOrderByCreateDateDesc() {
        try {
            return announcementService.findAllByIsDeletedFalseOrderByCreateDateDesc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value ="/saveAnnouncement")
    public ResponseEntity<Announcement> saveAnnouncement(@RequestBody Announcement announcement, HttpServletRequest request) {
        try {

            String token = jwtTokenProvider.getTokenFromRequest(request);
            String userName = jwtTokenProvider.extractUsername(token);
            User user = userRepo.findByUserNameOrEmail(userName);

            announcement.setCreatedBy(user);
            announcement = announcementService.save(announcement);

            return new ResponseEntity<>(announcement, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/getAnnouncement/{id}")
    public ResponseEntity<Announcement> getAnnouncement(@PathVariable Long id) {
        try {
            Announcement announcement = announcementService.findById(id).orElse(null);
            if (announcement != null) {
                return new ResponseEntity<>(announcement, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/updateAnnouncement/{id}")
    public ResponseEntity<Announcement> updateAnnouncement(@RequestBody Announcement announcement, @PathVariable Long id) {

        try {
            announcement = announcementService.update(announcement, id);
            return new ResponseEntity<>(announcementService.update(announcement, id), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping(value = "/deleteAnnouncement/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {

        try {
            Announcement announcement = announcementService.findById(id).orElse(null);
            if (announcement != null) {
                announcementService.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/getAllIsActiveAndPublisedAnnouncemnt")
    public List<Announcement> getAllIsActiveAndPublisedAnnouncemnt() {

        try {
            return announcementService.findAllByIsDeletedFalseAndIsActiveTrueAndIsPublishedTrueOrderByCreateDateDesc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/getAllAnnouncemnt")
    public List<Announcement> getAllAnnouncemnt() {

        try {
            return announcementService.findAllByIsDeletedFalseOrderByCreateDateDesc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
