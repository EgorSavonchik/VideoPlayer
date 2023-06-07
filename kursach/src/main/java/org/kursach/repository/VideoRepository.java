package org.kursach.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.kursach.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer>
{
    @Query("SELECT v FROM Video v WHERE (:authorId IS NULL OR v.author.id = :authorId)")
    Page<Video> findAllByAuthorId(@Param("authorId") Integer authorId, Pageable pageable);
}
