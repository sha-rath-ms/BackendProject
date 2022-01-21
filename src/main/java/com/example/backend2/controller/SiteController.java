package com.example.backend2.controller;

import com.example.backend2.entity.Sites;
import com.example.backend2.response.ResponseSiteNameAndPwd;
import com.example.backend2.response.ResponseWrapper;
import com.example.backend2.response.ResultInfoConstants;
import com.example.backend2.sector.Sector;
import com.example.backend2.service.SiteService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/safepass")
public class SiteController {

    @Autowired
    SiteService siteService;

    @PostMapping("/{id}/addsite")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper insert(@PathVariable long id, @RequestBody @Valid Sites sites) throws IOException {
        siteService.insert(id, sites);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, null);
    }

    @GetMapping("/{id}/home")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper<JSONObject> getAll(@PathVariable long id) {
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.getSiteNameAndPwd(id));
    }

    @GetMapping("/{id}/{sector}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper<JSONObject> getBySector(@PathVariable long id, @PathVariable Sector sector) {
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.getBySector(id, sector));
    }

    @GetMapping("/{id}/view/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper<Sites> getBySiteName(@PathVariable long id, @RequestBody ResponseSiteNameAndPwd siteName) {
        return new ResponseWrapper<>(ResultInfoConstants.SUCCESS, siteService.getBySiteName(id, siteName.getSiteName()));
    }

    @PutMapping("/{id}/edit/{siteName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper update(@PathVariable long id, @PathVariable String siteName, @RequestBody @Valid Sites sites) {
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.update(id, siteName, sites));
    }

    @GetMapping("/{id}/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper<ResponseSiteNameAndPwd> search(@PathVariable long id, @RequestBody String siteName) {
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.search(siteName, id));
    }
}
