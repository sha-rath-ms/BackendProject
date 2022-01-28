package com.example.backend2.service;

import com.example.backend2.entity.Sites;
import com.example.backend2.exception.KeyNotFoundException;
import com.example.backend2.exception.ValidationException;
import com.example.backend2.repository.SiteRepository;
import com.example.backend2.repository.UserRepository;
import com.example.backend2.repository.table.SiteTable;
import com.example.backend2.response.ResponseSiteNameAndPwd;
import com.example.backend2.response.ResultInfo;
import com.example.backend2.response.ResultInfoConstants;
import com.example.backend2.sector.Sector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SiteService {

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean insert(long id, Sites sites) throws IOException {
        if (!userRepository.existsById(id)) {
            log.warn("User not found with id:{}", id);
            throw new KeyNotFoundException(ResultInfoConstants.INVALID_USER);
        }
        if (!isUrlValid(sites.getUrl())) {
            log.warn("Url is not valid:{}", sites.getUrl());
            throw new ValidationException(new ResultInfo("Invalid Url"));
        }
        SiteTable newSite = sites.toSiteTable();
        newSite.setUserId(id);
        siteRepository.save(newSite);
        return true;
    }

    public boolean isUrlValid(String siteUrl) throws IOException {
        try {
            URL url = new URL(siteUrl.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                return true;
            }
        } catch (Exception e) {
            log.warn("Invalid URL");
            throw new ValidationException(new ResultInfo(e.getMessage()));
        }
        return false;
    }

    public Collection<ResponseSiteNameAndPwd> getSiteNameAndPwd(long id) {
        if (!userRepository.existsById(id)) {
            log.warn("User not found with id:{}", id);
            throw new KeyNotFoundException(ResultInfoConstants.INVALID_USER);
        }
        return siteRepository
                .getSiteNameAndPwd(id)
                .stream()
                .map(siteTable -> new ResponseSiteNameAndPwd(siteTable.getSiteName(), siteTable.getPassword()))
                .collect(Collectors.toSet());
    }

    public List<ResponseSiteNameAndPwd> getBySector(long id, Sector sector) {
        if (!userRepository.existsById(id)) {
            log.warn("User not found with id:{}", id);
            throw new KeyNotFoundException(ResultInfoConstants.INVALID_USER);
        }
        return siteRepository.getSiteNameAndPwd(id)
                .stream()
                .filter(siteTable -> siteTable.getSector().equals(sector))
                .map(siteTable -> new ResponseSiteNameAndPwd(siteTable.getSiteName(), siteTable.getPassword()))
                .collect(Collectors.toList());
    }

    public Sites getBySiteName(long id, String siteName) {
        if (!userRepository.existsById(id)) {
            log.warn("User not found with id:{}", id);
            throw new KeyNotFoundException(ResultInfoConstants.INVALID_USER);
        }
        Optional<SiteTable> sites = siteRepository.getBySiteName(id, siteName);
        if (!sites.isPresent()) {
            log.warn("Site with siteName:{} is not present", siteName);
            throw new KeyNotFoundException(ResultInfoConstants.SITE_NAME_NOTFOUND);
        }
        return sites.get().toSite();
    }

    public List<ResponseSiteNameAndPwd> search(String siteName, long id) {
        if (!userRepository.existsById(id)) {
            log.warn("User not found with id:{}", id);
            throw new KeyNotFoundException(ResultInfoConstants.INVALID_USER);
        }
        return siteRepository.search(siteName, id)
                .stream()
                .map(siteTable ->
                        new ResponseSiteNameAndPwd(siteTable.getSiteName(), siteTable.getPassword()))
                .collect(Collectors.toList());
    }

    public boolean update(long id, Sites sites) throws IOException{
        if (!userRepository.existsById(id)) {
            log.warn("User not found with id:{}", id);
            throw new KeyNotFoundException(ResultInfoConstants.INVALID_USER);
        }
        Optional<SiteTable> oldSite = siteRepository.findById(sites.getId());
        if (!oldSite.isPresent()) {
            log.warn("Site name not found");
            throw new ValidationException(ResultInfoConstants.SITE_NAME_NOTFOUND);
        }
        if (!isUrlValid(sites.getUrl())) {
            log.warn("Url is not valid:{}", sites.getUrl());
            throw new ValidationException(new ResultInfo("Invalid Url"));
        }
        SiteTable newSite = sites.toSiteTable();
        newSite.setId(oldSite.get().getId());
        newSite.setCreated_at(oldSite.get().getCreated_at());
        newSite.setUserId(id);
        siteRepository.save(newSite);
        return true;
    }
}
