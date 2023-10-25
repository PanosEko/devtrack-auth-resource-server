package com.panoseko.devtrack.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(value = """
      select i from Image i inner join i.task u
      where u.id = :id
      """)
    Optional<Image>  findImageByTask(Long id);

    Optional<Image> findByName(String fileName);
}