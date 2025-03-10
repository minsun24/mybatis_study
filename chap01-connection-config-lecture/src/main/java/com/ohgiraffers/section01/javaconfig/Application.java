package com.ohgiraffers.section01.javaconfig;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class Application {
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/jdbcdb";
    private static String user = "root";
    private static String password = "mariadb";


    public static void main(String[] args) {
        
        /* 설명. Environment : 연동할 DB에 대한 설정
            -
            JdbcTransactionFactory : 수동 커밋
        *   ManagedTransactionFactory : 자동 커밋
            -
        *   PooledDataSource : ConnectionPool(=> hikaricp 내장) 사용
                               (Connection 객체를 버퍼(저장공간)에 미리 여러 개 생성해두고 요청이 올 때 할당해주는 방법)
            UnPooledDataSource : ConnectionPool 미사용
        * */
        Environment environment = new Environment(
            "dev",
                new JdbcTransactionFactory(),
                new PooledDataSource(driver, url, user, password)
        );

        /*  필기. 쿼리문 정보
        *       - java의 쿼리는 xml 대신 인터페이스의 추상메서드로 구현한다.
        *         (Mapper.java 참고)
        * */

        /* 필기. 설계도(Configuration) 만들기
        *       - environment
        *       - Mapper
        * */
        /* 목차. 1. java - Configuration */
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(Mapper.class);

        /* 목차. 2. SqlSession */
        /* 설명.
        *   SqlSessionFactory : SqlSession 객체를 생성하기 위한 팩토리 역할의 인터페이스
        *   SqlSessionFactoryBuilder : SqlSessionFactory 인터페이스 타입의 하위 구현 객체를 생성하기 위한 빌드 역할
        *   -
        *   build() : 설정에 대한 정보를 담고 있는 Configuration 타입의 객체 (java방식)
        *             혹은 외부 설정 파일과 연결된 stream을 매개변수로 전달(xml 방식)하면,
        *             SqlSessionFactory 인터페이스 타입의 객체를 반환하는 메소드
        * */
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        // ---<하나의 트랜잭션>------------------------------------------------
        // Builder에게 설계도(Configuration) 전달하고, Factory 생성 받기
        SqlSession sqlSession = sqlSessionFactory.openSession(false); // openSession() : 공장에서 제품을 생산,
        // false 작성해야 수동 커밋 설정 완료
        // SqlSession == Connection 객체

        // 쿼리 불러오기
        Mapper mapper = sqlSession.getMapper(Mapper.class);

        java.util.Date date = mapper.selectNow();
        System.out.println("date = " + date);

        sqlSession.close(); // 커넥션 객체는 사용 후 자원을 무조건 반납해주어야 함.
        // ------------------------------------------------------------

    }

}
