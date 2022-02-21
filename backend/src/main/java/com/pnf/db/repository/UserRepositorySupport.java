package com.pnf.db.repository;

import com.pnf.db.entity.Expert;
import com.pnf.db.entity.QExpert;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.pnf.db.entity.QUser;
import com.pnf.db.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class UserRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QUser qUser = QUser.user;
    QExpert qExpert = QExpert.expert;

    public User findUserByEmail(String email) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.email.eq(email)).fetchOne();
        return user;
    }

    public Expert findExpertByEmail(String email) {
        Expert expert = jpaQueryFactory.select(qExpert).from(qExpert)
                .where(qExpert.email.eq(email)).fetchOne();
        return expert;
    }

    public User findUserByNickname(String nickname) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.nickname.eq(nickname)).fetchOne();
        return user;
    }

    public Boolean isExpert(String email) {
        Expert expert = jpaQueryFactory.select(qExpert).from(qExpert)
                .where(qExpert.email.eq(email)).fetchOne();

        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.email.eq(email)).fetchOne();

        if (expert == null) {
            if (user == null)
                return null;
            else
                return false;
        }
        else
            return true;
    }


}
