package org.kursach.repository;

import org.kursach.model.Comment;
import org.kursach.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>
{
    @Query("SELECT c FROM Comment c WHERE (:ownerUserId IS NULL OR c.ownerUser.id = :ownerUserId)")
    Page<Comment> findAllByUserId(@Param("ownerUserId") Integer ownerVideoId, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE (:ownerVideoId IS NULL OR c.ownerVideo.videoId = :ownerVideoId)")
    Page<Comment> findAllByVideoId(@Param("ownerVideoId") Integer ownerVideoId, Pageable pageable);
}
