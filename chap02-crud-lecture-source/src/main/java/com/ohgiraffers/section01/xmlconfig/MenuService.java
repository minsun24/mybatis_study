package com.ohgiraffers.section01.xmlconfig;

import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static com.ohgiraffers.section01.xmlconfig.Template.getSqlSession;

public class MenuService {
    private final MenuDAO menuDAO;

    public MenuService() {
        menuDAO = new MenuDAO();
    }

    public List<MenuDTO> findAllMenus() {
        SqlSession sqlSession = getSqlSession();
        /* 필기. 전역으로 SqlSession 을 빼면 안되는 이유
        *       SqlSession 은 http 요청을 받을 때마다 생성/소멸되어야 하므로,
        *       서비스의 메서드 내에서 생성/소멸되어야 한다.
        * */

        List<MenuDTO> menuList = menuDAO.selectAllMenus(sqlSession);

        sqlSession.close();

        return menuList;
    }

    public MenuDTO findMenuByMenuCode(int menuCode) {
        SqlSession sqlSession = getSqlSession();

        MenuDTO menu = menuDAO.selectMenuByMenuCode(sqlSession, menuCode);

        sqlSession.close();

        return menu;
    }

    // 새로운 메뉴를 추가 => INSERT
    public boolean registMenu(MenuDTO menu) {
        SqlSession sqlSession = getSqlSession();

        // INSERT 작업 요청
        int result = menuDAO.insertMenu(sqlSession, menu);

        /*  설명. 성공 실패에 따라 트랜잭션 처리(COMMIT / ROLLBACK)를 해줘야 함
        *    -> 수동 commit/rollback */
        if (result == 1) {
            // 성공 -> COMMIT
            sqlSession.commit();
        }else{
            // 실패 -> ROLLBACK
            sqlSession.rollback();
        }
        sqlSession.close();

        return result == 1 ? true : false;

    }

    // 기존 메뉴를 수정 => UPDATE
    public boolean modifyMenu(MenuDTO menu) {
        SqlSession sqlSession = getSqlSession();

        // UDPATE 작업 요청
        int result = menuDAO.updateMenu(sqlSession, menu);

        if (result >= 1) {
            // 성공 -> COMMIT
            sqlSession.commit();
        }else{
            // 실패 -> ROLLBACK
            sqlSession.rollback();
        }
        sqlSession.close();

        System.out.println(result);
        return result >= 1 ? true : false;

    }

    public boolean removeMenu(int menuCode) {
        SqlSession sqlSession = getSqlSession();

        // DELETE 작업 요청
        int result = menuDAO.deleteMenu(sqlSession, menuCode);

        if (result == 1) {
            // 성공 -> COMMIT
            sqlSession.commit();
        }else{
            // 실패 -> ROLLBACK
            sqlSession.rollback();
        }
        sqlSession.close();

        return result == 1 ? true : false;

    }
}