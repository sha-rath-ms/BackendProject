package com.example.backend2.controller;

import com.example.backend2.entity.Sites;
import com.example.backend2.response.ResponseWrapper;
import com.example.backend2.response.ResultInfoConstants;
import com.example.backend2.sector.Sector;
import com.example.backend2.security.GetUser;
import com.example.backend2.service.SiteService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@Data
public class SiteController {

    private final SiteService siteService;

    private final GetUser getUser;

    @PostMapping("/addsite")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper insert(HttpServletRequest request, @RequestBody @Valid Sites sites) throws IOException {
        siteService.insert(Long.parseLong(getUser.getId(request)), sites);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, null);
    }

    @GetMapping("/home")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper<Sites> getAll(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer pageNo) {
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.getAll(Long.parseLong(getUser.getId(request)), pageNo));
    }

    @GetMapping("/{sector}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper<Sites> getBySector(HttpServletRequest request, @PathVariable Sector sector, @RequestParam(defaultValue = "0") Integer pageNo) {
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.getBySector(Long.parseLong(getUser.getId(request)), sector, pageNo));
    }

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper update(HttpServletRequest request, @RequestBody @Valid Sites sites) throws IOException {
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.update(Long.parseLong(getUser.getId(request)), sites));
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper<Sites> search(HttpServletRequest request, @RequestBody String siteName, @RequestParam(defaultValue = "0") Integer pageNo) {
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.search(siteName, Long.parseLong(getUser.getId(request)), pageNo));
    }
}
