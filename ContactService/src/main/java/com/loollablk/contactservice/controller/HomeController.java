package com.loollablk.contactservice.controller;

import org.apache.el.stream.Stream;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping ( "/api/v1/" )
public class HomeController {



    @PreAuthorize ("hasAuthority('ADMIN')")
    @GetMapping("/private/ping")
    public String test() {
        try {
            return "Welcome";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping ( "/public/home/{name}" )
    public ResponseEntity <String> getHome ( @PathVariable String name ) {
        return ResponseEntity.ok ( "Welcome " + name );
    }


    @GetMapping ( "/private/home/{name}" )
    public ResponseEntity <String> getHomePrivate ( @PathVariable String name ) {
        return ResponseEntity.ok ( "Welcome private " + name );
    }


    @PostMapping ( "/public/getAllNameList" )
    public ResponseEntity <List <String>> getAllNameList ( ) {

        List <String> nameList = Arrays.asList ( "sunimal", "erandi", "nayana", "saman" );

        return ResponseEntity.ok ( nameList );
    }

    @PostMapping ( "/private/getAllPrivateList" )
    public ResponseEntity <List <String>> getAllPrivateList ( ) {

        List <String> nameList = Arrays.asList ( "sunimal", "erandi", "nayana", "saman" );

        List <String> nameUpperLsit = nameList.stream ( ).map ( ( n ) -> n.toUpperCase ( ) ).collect ( Collectors.toList ( ) );

        return ResponseEntity.ok ( nameUpperLsit );
    }


}
