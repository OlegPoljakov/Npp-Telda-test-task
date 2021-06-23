package com.example.demo.controller;

import com.example.demo.exception.ApiRequestException;
import com.example.demo.model.Region;
import com.example.demo.mapper.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/region")
public class RegionController {

    private final RegionMapper regionService;

    @Autowired
    public RegionController(RegionMapper regionService){
        this.regionService = regionService;
    }

    @GetMapping(path = "/all")
    public List<Region> getRegions(){
        List<Region> regions = regionService.findAll();
        if (regions.isEmpty()){
            throw new ApiRequestException("No regions in the database");
        }
        else {
            return regions;
        }

    }

    @GetMapping(path = "/new")
    public void newRegion(@RequestParam("region") String region, @RequestParam("shrt") String shrt){
        if (regionService.getByName(region).isPresent()){
            throw new ApiRequestException("This region is already in the database");
        }
        else {
            Region newregion = new Region();
            newregion.setName(region);
            newregion.setShort_name(shrt);
            regionService.insert(newregion);
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteRegion(@PathVariable(value="id") Integer id){
        System.out.println("here");
        Optional<Region> requiredRegion = regionService.getById(id);
        if (requiredRegion.isEmpty()) {
            throw new ApiRequestException("No regions with this id exists in database");
        } else {
            regionService.deleteById(id);
        }
    }

    @GetMapping(path = "/get/{id}")
    public Region getRegion(@PathVariable(value="id") Integer id){
        Optional<Region> requiredRegion = regionService.getById(id);
        if (requiredRegion.isEmpty()) {
            return null;
        } else {
            return requiredRegion.get();
        }
    }
}