package com.example.crud_react.crudReact.service;

import com.example.crud_react.crudReact.entity.Announcement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AnnouncementService {

    List<Announcement> findAllActiveAnnouncement();

    Optional<Announcement> findById(Long id);

    Announcement save(Announcement announcement);

    Announcement update(Announcement announcement, Long id);

    List<Announcement> findAllByIsDeletedFalseAndIsActiveTrueOrderByCreateDateDesc();

    List<Announcement> findAllByIsDeletedFalseOrderByCreateDateDesc();

    List<Announcement> findAllByIsDeletedFalseAndIsActiveTrueAndIsPublishedTrueOrderByCreateDateDesc();

    void deleteById(Long id);
}
