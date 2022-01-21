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
        if (siteRepository.findAll().stream().filter(siteTable -> siteTable.getUserId() == id).map(SiteTable::getSiteName).collect(Collectors.toList()).contains(sites.getSiteName())) {
            log.warn("Site name already exist");
            throw new ValidationException(ResultInfoConstants.SITE_NAME_ALREADYEXISTS);
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
            throw new ValidationException(new ResultInfo(e.getMessage()));
        }
        return false;
    }

    public Collection<ResponseSiteNameAndPwd> getSiteNameAndPwd(long id) {
        return siteRepository.findAll().stream().filter(siteTable -> siteTable.getUserId() == id).map(siteTable -> new ResponseSiteNameAndPwd(siteTable.getSiteName(), siteTable.getPassword())).collect(Collectors.toSet());
    }

    public List<ResponseSiteNameAndPwd> getBySector(long id, Sector sector) {

        return siteRepository.findAll().stream().filter(siteTable -> siteTable.getUserId() == id).filter(siteTable -> siteTable.getSector() == sector).map(siteTable -> new ResponseSiteNameAndPwd(siteTable.getSiteName(), siteTable.getPassword())).collect(Collectors.toList());
    }

    public Sites getBySiteName(long id, String siteName) {
        Optional<Sites> sites = siteRepository.
                findAll()
                .stream()
                .filter(siteTable -> siteTable.getUserId() == id)
                .filter(siteTable -> siteTable.getSiteName().equals(siteName))
                .map(SiteTable::toSite)
                .findAny();
        if (!sites.isPresent()) {
            log.warn("Site with sitename is not present");
            throw new KeyNotFoundException(ResultInfoConstants.SITE_NAME_NOTFOUND);
        }
        return sites.get();
    }

    public List<ResponseSiteNameAndPwd> search(String siteName,long id)
    {
        return siteRepository.search(siteName,id)
                .stream()
                .map(siteTable ->
                        new ResponseSiteNameAndPwd(siteTable.getSiteName(),siteTable.getPassword()))
                .collect(Collectors.toList());
    }

    public boolean update(long id, String siteName, Sites sites) {
        if (!userRepository.existsById(id)) {
            log.warn("User not found with id:{}", id);
            throw new KeyNotFoundException(ResultInfoConstants.INVALID_USER);
        }
        Optional<SiteTable> oldSite = siteRepository.findAll().stream().filter(siteTable -> siteTable.getUserId() == id).filter(siteTable -> siteTable.getSiteName().equals(siteName)).findAny();
        if (!oldSite.isPresent()) {
            log.warn("Site name not found");
            throw new ValidationException(ResultInfoConstants.SITE_NAME_NOTFOUND);
        }
        if (siteRepository.findAll().stream().filter(siteTable -> siteTable.getUserId() == id).filter(siteTable -> siteTable.getUserId() != oldSite.get().getUserId()).filter(siteTable -> siteTable.getSiteName().equals(sites.getSiteName())).findAny().isPresent()) {
            log.warn("Site name already exist");
            throw new ValidationException(ResultInfoConstants.SITE_NAME_ALREADYEXISTS);
        }
        SiteTable newSite = sites.toSiteTable();
        newSite.setId(oldSite.get().getId());
        newSite.setCreated_at(oldSite.get().getCreated_at());
        newSite.setUserId(id);
        siteRepository.save(newSite);
        return true;
    }
}
