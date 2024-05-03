package com.loollablk.contactservice.security;

import com.loollablk.contactservice.model.UserInfo;
import com.loollablk.contactservice.repository.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserInfoRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername( String username) throws UsernameNotFoundException {

        logger.debug("Entering in loadUserByUsername Method...");
        UserInfo user = userRepository.findByUsername(username);
        if(user == null){
            logger.error("Username not found: " + username);
            throw new UsernameNotFoundException("could not found user..!!");
        }
        logger.info("User Authenticated Successfully..!!!");
        return new UserInfoDetail(user);
    }
}