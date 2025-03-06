package com.example.crud_react.crudReact.serviceimpl;

import com.example.crud_react.crudReact.entity.Announcement;
import com.example.crud_react.crudReact.repository.AnnouncementRepo;
import com.example.crud_react.crudReact.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepo announcementRepo;

    private static Logger logger = Logger.getLogger(AnnouncementServiceImpl.class.getName());

    @Override
    public List<Announcement> findAllActiveAnnouncement() {
        logger.info("AnnouncementServiceImpl.findAllActiveAnnouncement");

        try {
            return announcementRepo.findAllByIsDeletedFalseAndIsActiveTrueOrderByCreateDateDesc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Announcement> findById(Long id) {
        logger.info("AnnouncementServiceImpl.findById");

        try {
            return announcementRepo.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Announcement save(Announcement announcement) {
        logger.info("AnnouncementServiceImpl.save");

        try {
            return announcementRepo.save(announcement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Announcement update(Announcement announcement, Long id) {
        logger.info("AnnouncementServiceImpl.update");

        try {
            Announcement announcement1 = announcementRepo.findById(id).orElse(null);

            if(announcement1 != null) {
                announcement1.setContent(announcement.getContent());
                announcement1.setCreateDate(announcement.getCreateDate());
                announcement1.setIsPublished(announcement.getIsPublished());
                announcement1.setIsDeleted(announcement.getIsDeleted());
                announcement1.setIsActive(announcement.getIsActive());
                announcement1.setTitle(announcement.getTitle());
                announcement1.setExpireDate(announcement.getExpireDate());
                announcement1.setUpdatedBy(announcement.getUpdatedBy());
                return announcementRepo.save(announcement1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Announcement> findAllByIsDeletedFalseAndIsActiveTrueOrderByCreateDateDesc() {
        logger.info("AnnouncementServiceImpl.findAllByIsDeletedFalseAndIsActiveTrueOrderByCreateDateDesc");

        try {
            return announcementRepo.findAllByIsDeletedFalseAndIsActiveTrueOrderByCreateDateDesc();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return List.of();
    }

    @Override
    public List<Announcement> findAllByIsDeletedFalseOrderByCreateDateDesc() {
        logger.info("AnnouncementServiceImpl.findAllByIsDeletedFalseOrderByCreateDateDesc");

        try {
            return announcementRepo.findAllByIsDeletedFalseOrderByCreateDateDesc();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public List<Announcement> findAllByIsDeletedFalseAndIsActiveTrueAndIsPublishedTrueOrderByCreateDateDesc() {
        logger.info("AnnouncementServiceImpl.findAllByIsDeletedFalseAndIsActiveTrueOrderByCreateDateDesc");

        try {
            return announcementRepo.findAllByIsDeletedFalseAndIsActiveTrueAndIsPublishedTrueOrderByCreateDateDesc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

//    @CacheEvict(value = "announcements", key = "#id")
    @Override
    public void deleteById(Long id) {
        logger.info("AnnouncementServiceImpl.deleteById");

        try {
            Announcement announcement = announcementRepo.findById(id).orElse(null);
            if(announcement != null) {
                announcement.setIsDeleted(true);
                announcementRepo.save(announcement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
