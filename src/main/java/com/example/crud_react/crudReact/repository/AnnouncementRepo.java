package com.example.crud_react.crudReact.repository;

import com.example.crud_react.crudReact.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementRepo extends JpaRepository<Announcement, Long> {

    List<Announcement> findAllByIsDeletedFalseAndIsActiveTrueOrderByCreateDateDesc();

    List<Announcement> findAllByIsDeletedFalseOrderByCreateDateDesc();

    @Query("SELECT a FROM Announcement a WHERE a.isDeleted = false " +
            "AND a.isActive = true " +
            "AND a.isPublished = true " +
            "AND a.expireDate > CURRENT_TIMESTAMP " +
            "ORDER BY a.createDate DESC")
    List<Announcement> findAllByIsDeletedFalseAndIsActiveTrueAndIsPublishedTrueOrderByCreateDateDesc();

    Optional<Announcement> findById(Long id);
}
