package com.ohgiraffers.section01.xmlconfig;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class Template {

    /*  필기. Factory 는 싱글톤 패턴으로 관리   */
    private static SqlSessionFactory sqlSessionFactory;

    /*  필기. lazy singleton 방식으로 세션 Factory 생성하기
            : 메서드 호출할 때 객체 없으면 그때 생성하는 방법
    */
    public static SqlSession getSqlSession() {
        if(sqlSessionFactory == null) {
            String resource = "com/ohgiraffers/section01/xmlconfig/mybatis-config.xml";

            //
            try {
                InputStream inputStream = Resources.getResourceAsStream(resource);
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return sqlSessionFactory.openSession();
    }

}
