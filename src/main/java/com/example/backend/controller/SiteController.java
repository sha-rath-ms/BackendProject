package com.example.backend.controller;

import com.example.backend.entity.Sites;
import com.example.backend.response.ResponseWrapper;
import com.example.backend.response.ResultInfoConstants;
import com.example.backend.sector.Sector;
import com.example.backend.security.GetUser;
import com.example.backend.security.JwtTokenUtil;
import com.example.backend.service.SiteService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class SiteController {

    private final SiteService siteService;

    private final GetUser getUser;

    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/addsite")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper insert(HttpServletRequest request, @RequestBody @Valid Sites sites) throws IOException {
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.insert(getUser.getId(request), sites));
    }

    @GetMapping("/home")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper<Sites> getAll(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer pageNo) {
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.getAll(getUser.getId(request), pageNo));
    }

    @GetMapping("/{sector}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper<Sites> getBySector(HttpServletRequest request, @PathVariable Sector sector, @RequestParam(defaultValue = "0") Integer pageNo) {
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.getBySector(getUser.getId(request), sector, pageNo));
    }

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper update(HttpServletRequest request, @RequestBody @Valid Sites sites) throws IOException {
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.update(getUser.getId(request), sites));
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper<Sites> search(HttpServletRequest request, @RequestBody String siteName, @RequestParam(defaultValue = "0") Integer pageNo) {
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, siteService.search(siteName, getUser.getId(request), pageNo));
    }
}
