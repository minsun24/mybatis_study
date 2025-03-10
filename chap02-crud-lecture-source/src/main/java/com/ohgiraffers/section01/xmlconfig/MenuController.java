package com.ohgiraffers.section01.xmlconfig;

import java.util.List;
import java.util.Map;

public class MenuController {
    private final MenuService menuService;
    private final PrintResult printResult;      // 성공/실패 결과 페이지

    public MenuController() { // 생성자 의존성 주입
        menuService = new MenuService();
        printResult = new PrintResult();
    }

    // 전체 메뉴 조회
    public void findAllMenus() {
        List<MenuDTO> menuList = menuService.findAllMenus();
//        System.out.println("===== Controller에서 출력");
//        menuList.forEach(System.out::println);

        if(!menuList.isEmpty()){    // 다중행 조회 성공
            printResult.printMenus(menuList);
        } else {                    // 조회 실패
            printResult.printErrorMessage("전체 메뉴 조회 실패!");
        }

    }

    public void findMenuByMenuCode(Map<String, String> parameter) {

        int menuCode = Integer.parseInt(parameter.get("menuCode"));

        MenuDTO menu = menuService.findMenuByMenuCode(menuCode);

        if(menu != null){
            printResult.printMenu(menu);
        } else {
            printResult.printErrorMessage(menuCode + "번의 메뉴는 없습니다.");
        }
    }


    public void registMenu(Map<String, String> parameter) {
        String menuName = parameter.get("menuName");
        int menuPrice = Integer.parseInt(parameter.get("menuPrice"));
        int categoryCode = Integer.parseInt(parameter.get("categoryCode"));

        MenuDTO menu = new MenuDTO();
        menu.setMenuName(menuName);
        menu.setMenuPrice(menuPrice);
        menu.setCategoryCode(categoryCode);

        // INSERT 작업 - 서비스에서 결과를 boolean 형으로 반환하도록 구현할 것
        if(menuService.registMenu(menu)){
            printResult.printSuccessMessage("regist");
        }else{
            printResult.printErrorMessage("메뉴 추가 실패!");
        }
    }


    // 메뉴 수정 메서드
    public void modifyMenu(Map<String, String> parameter) {
        // String 으로 넘어오는 파라미터 값들 실제 데이터 타입에 맞게 파싱
        int menuCode = Integer.parseInt(parameter.get("menuCode"));
        String menuName = parameter.get("menuName");
        int menuPrice = Integer.parseInt(parameter.get("menuPrice"));

        // MenuDTO 로 묶어주기
        MenuDTO menu = new MenuDTO();
        menu.setMenuName(menuName);
        menu.setMenuPrice(menuPrice);
        menu.setMenuCode(menuCode);

        if(menuService.modifyMenu(menu)){
            printResult.printSuccessMessage("modify");
        }else{
            printResult.printErrorMessage("메뉴 수정 실패!");
        }

    }

    public void deleteMenu(Map<String, String> parameter) {

        int menuCode = Integer.parseInt(parameter.get("menuCode"));

        if (menuService.removeMenu(menuCode)) {
            printResult.printSuccessMessage("remove");
        } else {

        }
    }
}
