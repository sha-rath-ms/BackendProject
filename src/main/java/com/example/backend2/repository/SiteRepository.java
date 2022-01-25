package com.example.backend2.repository;

import com.example.backend2.repository.table.SiteTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<SiteTable, Long> {

    @Query(value = "SELECT * FROM sites s WHERE s.sitename LIKE :sitename% and s.userid=:id", nativeQuery = true)
    List<SiteTable> search(String sitename, long id);

    @Query(value = "select * from sites s where s.userid=:id", nativeQuery = true)
    List<SiteTable> getSiteNameAndPwd(long id);

    @Query(value = "select * from sites s where s.sitename = :siteName and s.userid=:id", nativeQuery = true)
    Optional<SiteTable> getBySiteName(long id, String siteName);

    @Query(value = "select * from sites s where s.sitename=:sitename and s.userid=:userid and s.id!=:id", nativeQuery = true)
    Optional<SiteTable> siteNameExistsOrNot(String sitename, long userid, long id);

}
