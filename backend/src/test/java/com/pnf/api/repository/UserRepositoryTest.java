package com.pnf.api.repository;

import com.pnf.db.entity.User;
import com.pnf.db.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Test
    public void 회원정보_반환() {
        String email = "hsk2454@gmail.com";

        User user = repository.findUserByEmail(email);
        System.out.println(user.getEmail());

    }



}
