package com.loollablk.contactservice.repository;

import com.loollablk.contactservice.model.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo,Long> {
    public UserInfo findByUsername(String username);
}
