package com.samseung.ceas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.samseung.ceas.model.ImageEntity;
import com.samseung.ceas.model.UserEntity;


@Repository 
public interface  ImageRepository extends JpaRepository<ImageEntity, Long> {


}