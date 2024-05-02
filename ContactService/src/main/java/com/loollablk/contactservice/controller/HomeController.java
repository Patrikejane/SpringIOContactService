package com.loollablk.contactservice.controller;

import org.apache.el.stream.Stream;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {



    @GetMapping("/public/home")
    public ResponseEntity<String> getHome(@PathVariable String name){
        return ResponseEntity.ok("Welcome " + name);
    }


    @GetMapping("/private/home")
    public ResponseEntity<String> getHomePrivate(@PathVariable String name){
        return ResponseEntity.ok("Welcome private" + name);
    }



    @PostMapping("/public/getAllNameList")
    public ResponseEntity<List<String>> getAllNameList(@PathVariable String name){

        List<String> nameList = Arrays.asList("sunimal","erandi","nayana","saman");

        return ResponseEntity.ok(nameList);
    }

    @PostMapping("/private/getAllPrivateList")
    public ResponseEntity<List<String>> getAllPrivateList(@PathVariable String name){

        List<String> nameList = Arrays.asList("sunimal","erandi","nayana","saman");

        List<String> nameUpperLsit = nameList.stream().map((n)-> n.toUpperCase()).collect(Collectors.toList());

        return ResponseEntity.ok(nameUpperLsit);
    }


}
