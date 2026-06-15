package com.yourorg.licenserenewalmanager.license.repository;

import com.yourorg.licenserenewalmanager.license.entity.RenewalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RenewalHistoryRepository extends JpaRepository<RenewalHistory, Long> {
}