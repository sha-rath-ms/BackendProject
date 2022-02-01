package com.example.backend.repository;

import com.example.backend.repository.table.SiteTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<SiteTable, Long> {

    Page<SiteTable> findByUserId(long id, Pageable pageable);

    @Query(value = "select * from sites s where s.id=:siteid and s.userid=:userid", nativeQuery = true)
    Optional<SiteTable> findByIdAndName(long userid, long siteid);

    @Query(value = "select * from sites s where s.sector=:sector and s.userid=:id", nativeQuery = true)
    Page<SiteTable> findBySector(long id, int sector, Pageable pageable);

    @Query(value = "SELECT * FROM sites s WHERE s.sitename LIKE :sitename% and s.userid=:id", nativeQuery = true)
    Page<SiteTable> search(String sitename, long id, Pageable pageable);

}
