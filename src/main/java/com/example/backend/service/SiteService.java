package com.example.backend.service;

import com.example.backend.entity.Sites;
import com.example.backend.exception.KeyNotFoundException;
import com.example.backend.exception.ValidationException;
import com.example.backend.repository.SiteRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.table.SiteTable;
import com.example.backend.response.ResultInfo;
import com.example.backend.response.ResultInfoConstants;
import com.example.backend.sector.Sector;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.backend.extract.UrlValidOrNot.isUrlValid;

@Slf4j
@Service
@Data
public class SiteService {

    private final SiteRepository siteRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void insert(long id, Sites sites) throws IOException {
        if (!userRepository.existsById(id)) {
            log.warn("User not found with id:{}", id);
            throw new KeyNotFoundException(ResultInfoConstants.INVALID_USER);
        }
        if (!isUrlValid(sites.getUrl())) {
            log.warn("Url is not valid:{}", sites.getUrl());
            throw new ValidationException(new ResultInfo("Invalid Url"));
        }
        SiteTable newSite = sites.toSiteTable(passwordEncoder);
        newSite.setUserId(id);
        siteRepository.save(newSite);
    }

    public List<Sites> getAll(long id, Integer pageNo) {
        if (!userRepository.existsById(id)) {
            log.warn("User not found with id:{}", id);
            throw new KeyNotFoundException(ResultInfoConstants.INVALID_USER);
        }
        Pageable pageable = PageRequest.of(pageNo, 3);
        Page<SiteTable> pagedResult = siteRepository.findByUserId(id, pageable);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent()
                    .stream()
                    .map(SiteTable::toSite)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<Sites>();
        }
    }

    public List<Sites> getBySector(long id, Sector sector, Integer pageNo) {
        if (!userRepository.existsById(id)) {
            log.warn("User not found with id:{}", id);
            throw new KeyNotFoundException(ResultInfoConstants.INVALID_USER);
        }
        Pageable pageable = PageRequest.of(pageNo, 3);
        Page<SiteTable> pagedResult = siteRepository.findBySector(id, sector.ordinal(), pageable);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent()
                    .stream()
                    .map(SiteTable::toSite)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<Sites>();
        }
    }

    public List<Sites> search(String siteName, long id, Integer pageNo) {
        if (!userRepository.existsById(id)) {
            log.warn("User not found with id:{}", id);
            throw new KeyNotFoundException(ResultInfoConstants.INVALID_USER);
        }
        Pageable pageable = PageRequest.of(pageNo, 3);
        Page<SiteTable> pagedResult = siteRepository.search(siteName, id, pageable);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent()
                    .stream()
                    .map(SiteTable::toSite)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<Sites>();
        }
    }

    public boolean update(long id, Sites sites) throws IOException {
        if (!userRepository.existsById(id)) {
            log.warn("User not found with id:{}", id);
            throw new KeyNotFoundException(ResultInfoConstants.INVALID_USER);
        }
        Optional<SiteTable> oldSite = siteRepository.findById(sites.getId());
        if (!oldSite.isPresent()) {
            log.warn("Site name not found");
            throw new ValidationException(ResultInfoConstants.SITE_NAME_NOT_FOUND);
        }
        if (oldSite.get().getUserId() != id) {
            log.warn("site id is not present in this user id");
            throw new ValidationException(ResultInfoConstants.INVALID_SITE_ID);
        }
        if (!isUrlValid(sites.getUrl())) {
            log.warn("Url is not valid:{}", sites.getUrl());
            throw new ValidationException(new ResultInfo("Invalid Url"));
        }
        SiteTable newSite = sites.toSiteTable(passwordEncoder);
        newSite.setCreated_at(oldSite.get().getCreated_at());
        newSite.setUserId(id);
        siteRepository.save(newSite);
        return true;
    }
}
