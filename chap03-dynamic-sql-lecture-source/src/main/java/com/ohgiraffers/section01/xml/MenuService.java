package com.ohgiraffers.section01.xml;

import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

import static com.ohgiraffers.section01.xml.Template.getSqlSession;

public class MenuService {
    public void findMenuByPrice(int maxPrice) {
        // Template을 통해 SqlSession 생성
        SqlSession sqlSession = getSqlSession();

        MenuMapper mapper = sqlSession.getMapper(MenuMapper.class); // 하위 구현체가 생성됨
        // Mapper를 쿼리로 활용한 것?

        // 서비스 입장에서 - MenuMapper를 DAO 계층으로 인식한 것
        List<MenuDTO> menus = mapper.selectMenuByPrice(maxPrice);
        System.out.println("===== Service Layer");
        menus.forEach(System.out::println);
        sqlSession.close();
    }

    public void searchMenu(SearchCriteria searchCriteria) {
        SqlSession sqlSession = getSqlSession();
        MenuMapper mapper = sqlSession.getMapper(MenuMapper.class);

        List<MenuDTO> menus = mapper.searchMenu(searchCriteria);
        System.out.println("===== Service Layer");
        menus.forEach(System.out::println);

        sqlSession.close();
    }

    //
    public void searchMenuBySupCategory(SearchCriteria searchCriteria) {
        SqlSession sqlSession = getSqlSession();
        MenuMapper mapper = sqlSession.getMapper(MenuMapper.class);

        List<MenuDTO> menus = mapper.searchMenuBySupCategory(searchCriteria);
        System.out.println("===== Service Layer");
        menus.forEach(System.out::println);

        sqlSession.close();
    }

    public void searchByRandomMenuCode(List<Integer> randomList) {
        SqlSession sqlSession = getSqlSession();
        MenuMapper mapper = sqlSession.getMapper(MenuMapper.class);

        List<MenuDTO> menus = mapper.searchByRandomMenuCode(randomList);    // 다중행 조회

        System.out.println("===== Service Layer");
        menus.forEach(System.out::println);

        sqlSession.close();
    }

    public void searchMenuByCodeOrSearchAll(SearchCriteria searchCriteria) {
        SqlSession sqlSession = getSqlSession();
        MenuMapper mapper = sqlSession.getMapper(MenuMapper.class);

        List<MenuDTO> menus = mapper.searchMenuByCodeOrSearchAll(searchCriteria);
        System.out.println("===== Service Layer");
        menus.forEach(System.out::println);

        sqlSession.close();
    }

    public void searchMenuByNameOrCategory(Map<String, Object> criteria) {
        SqlSession sqlSession = getSqlSession();
        MenuMapper mapper = sqlSession.getMapper(MenuMapper.class);

        List<MenuDTO> menus = mapper.searchMenuByNameOrCategory(criteria);    // 다중행 조회

        System.out.println("===== Service Layer");
        menus.forEach(System.out::println);

        sqlSession.close();
    }

    /* 필기. DML 작업  => QUERY 반환값으로 (1/0) int값이 반환됨
    *                => 반환값에 따라 COMMIT/ROLLABCK 처리 필요
    * */
    public void modifyMenu(Map<String, Object> criteria) {
        SqlSession sqlSession = getSqlSession();
        MenuMapper mapper = sqlSession.getMapper(MenuMapper.class);

        int result = mapper.updateMenu(criteria);

        if(result == 1){
            System.out.println("메뉴 정보 변경에 성공하였습니다.");
            sqlSession.commit();
        }else{
            System.out.println("메뉴 정보 변경에 실패하였습니다.");
            sqlSession.rollback();
        }

        sqlSession.close();
        
    }
}
