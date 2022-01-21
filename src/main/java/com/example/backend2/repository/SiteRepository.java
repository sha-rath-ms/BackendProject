package com.example.backend2.repository;

import com.example.backend2.repository.table.SiteTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteRepository extends JpaRepository<SiteTable, Long> {

    @Query(value="SELECT * FROM sites s WHERE s.sitename LIKE :sitename% and s.userid=:id",nativeQuery = true)
    List<SiteTable> search(String sitename,long id);
}
