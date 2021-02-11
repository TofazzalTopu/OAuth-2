package com.spring.security.repository;

import com.spring.security.model.RoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleInfoRepository extends JpaRepository<RoleInfo, Long> {
    List<RoleInfo> findAllByUserName(String userName);
}
