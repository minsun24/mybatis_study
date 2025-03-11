package com.ohgiraffers.section01.xml;

import java.util.*;

public class Application {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("========== 마이바티스 동적 SQL ==========");
            System.out.println("1. if 확인하기");
            System.out.println("2. choose(when, otherwises) 확인하기");
            System.out.println("3. foreach 확인하기");
            System.out.println("4. trim(where, set) 확인하기"); // where절 관련해서 ... 필요성(?) -찾아보기
            System.out.println("9. 종료하기");
            System.out.print("메뉴를 선택하세요: ");

            int no = sc.nextInt();

            switch(no){
                case 1:
                    ifSubMenu();
                    break;
                case 2:
                    chooseSubMenu();
                    break;
                case 3:
                    foreachSubMenu();
                    break;
                case 4:
                    trimSubMenu();
                    break;
                case 9:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("번호를 잘못 입력하셨습니다.");
            }
        } while (true);

    }

    private static void trimSubMenu() {
        Scanner sc = new Scanner(System.in);
        MenuService menuService = new MenuService();
        do {
            System.out.println("======== trim 서브 메뉴 ======== ");
            System.out.println("1. 검색 조건이 있는 경우 메뉴 코드로 조회, 단 없으면 전체 조회 ");
            System.out.println("2. 메뉴 혹은 카테고리로 검색, 단, 메뉴와 카테고리 둘 다 일치하는 경우도 검색하며 " + 
                    "검색 조건이 없는 경우 전체 조회");
            System.out.println("3. 원하는 메뉴 정보만 수정하기 ");
            System.out.println("9. 이전 메뉴로");
            System.out.print("메뉴 번호를 입력해주세요. : ");
            int no = sc.nextInt();
            switch(no){
                case 1:
                    menuService.searchMenuByCodeOrSearchAll(inputAllOrOne());
                    break;
                case 2:
                    menuService.searchMenuByNameOrCategory(inputSearchCriteriaMap());
                    break;
                case 3:
                    menuService.modifyMenu(inputChangeInfo());
                    break;
                case 9:
                    return;
            }
        } while(true);
    }

    private static Map<String, Object> inputChangeInfo() {
        Scanner sc = new Scanner(System.in);

        System.out.print("변경할 메뉴 코드를 입력하세요: ");
        int menuCode = sc.nextInt();
        sc.nextLine();
        System.out.print("변경할 메뉴 이름을 입력하세요: ");
        String menuName = sc.nextLine();
        System.out.print("변경할 판매 여부를 결정해 주세요(Y/N): ");
        String orderableStatus = sc.nextLine().toUpperCase();

        Map<String, Object> criteria = new HashMap<>();
        criteria.put("menuCode", menuCode);
        criteria.put("menuName", menuName);
        criteria.put("orderableStatus", orderableStatus);

        return criteria;
    }

    // 전체 또는 하나?
    private static SearchCriteria inputAllOrOne() {
        Scanner sc = new Scanner(System.in);
        System.out.print("검색 조건을 입력하시겠습니까? ");

        boolean hasSearchValue = "예".equals(sc.nextLine()) ? true : false;

        SearchCriteria searchCriteria = new SearchCriteria();
        if(hasSearchValue){
            System.out.print("검색할 메뉴 코드를 입력하세요: ");
            String menuCode = sc.nextLine();
            searchCriteria.setCondition("menuCode");
            searchCriteria.setValue(menuCode);
        }
        return searchCriteria;
    }


    private static void foreachSubMenu() {
        Scanner sc = new Scanner(System.in);
        MenuService menuService = new MenuService();
        do {
            System.out.println("======== foreach 서브 메뉴 ======== ");
            System.out.println("1. 랜덤한 메뉴 5개 추출해서 조회하기 ");
            System.out.println("9. 이전 메뉴로");
            System.out.print("메뉴 번호를 입력해주세요. : ");
            int no = sc.nextInt();
            switch(no){
                case 1:
                    menuService.searchByRandomMenuCode(generateRandomMenuCodeList());
                    break;
                case 9:
                    menuService.searchMenuByNameOrCategory(inputSearchCriteriaMap());
                    return;
            }
        } while(true);
    }

    // 검색 조건,
    private static Map<String, Object> inputSearchCriteriaMap() {
        Scanner sc = new Scanner(System.in);
        System.out.print("검색 조건을 입력하세요 (category or name or both or none) : ");

        String condition = sc.nextLine();

        Map<String, Object> searchCriteriaMap = new HashMap<>();

        if("category".equals(condition)){
            System.out.println("검색할 카테고리의 코드를 입력하세요 : ");
            int categoryCode = sc.nextInt();

            searchCriteriaMap.put("categoryCode", categoryCode);

        } else if("name".equals(condition)){
            System.out.println("검색할 이름을 입력하세요 : ");
            String nameValue = sc.nextLine();

            searchCriteriaMap.put("name", nameValue);
         } else if ("both".equals(condition)){
            System.out.println("검색할 이름을 입력하세요: ");
            String nameValue = sc.nextLine();
            System.out.println("검색할 카테고리 코드를 입력하세요.");
            int categoryCode = sc.nextInt();

            searchCriteriaMap.put("name", nameValue);
            searchCriteriaMap.put("categoryCode", categoryCode);
        }
        return searchCriteriaMap;
    }


    /* 설명. 중복되지 않은 21개의 메뉴 5개를 랜덤하게 생성하고 정렬 후 List<Integer> 로 반환하는 메소드  */
    private static List<Integer> generateRandomMenuCodeList() {
        // 중복되지 않는 리스트 => Set 사용
        // 랜덤으로 추출된 메뉴의 번호 리스트를 반환할 것

        Set<Integer> set = new HashSet<>();

        while(set.size() < 5){
            int random = (int) (Math.random() * 21) + 1;
            set.add(random);
        }

        // 설명. HashSet -> ArrayList 변환
        List<Integer> list = new ArrayList<>(set);
        Collections.sort(list);
        System.out.println("생성된 랜덤수: " + list);

        return list;
    }


    private static void chooseSubMenu() {
        Scanner sc = new Scanner(System.in);
        MenuService menuService = new MenuService();
        do {
            System.out.println("======== choose 서브 메뉴 ======== ");
            System.out.println("1. 카테고리 상위 분류별 메뉴 보여주기 (식사, 음료, 디저트)");
            System.out.println("9. 이전 메뉴로");
            System.out.print("메뉴 번호를 입력해주세요. : ");
            int no = sc.nextInt();
            switch(no){
                case 1:
                    menuService.searchMenuBySupCategory(inputSupCategory());
                    break;
                case 9:
                    return;
            }
        } while(true);
    }


    // 상위 카테고리 (식사, 음료, 디저트) 중 선택 받아서 해당 상위 카테고리의 메뉴를 조회하기
    private static SearchCriteria inputSupCategory() {
        Scanner sc = new Scanner(System.in);
        System.out.println("상위 분류를 입력해 주세요. (식사 / 음료 / 디저트) : ");
        String value =sc.nextLine();

        return new SearchCriteria("category", value);
    }


    private static void ifSubMenu() {
        Scanner sc = new Scanner(System.in);
        MenuService menuService = new MenuService();    // Controller 계층 생략해서 바로 Service로 전달

        do {
            System.out.println("======== if 서브 메뉴 ========");
            System.out.println("1. 원하는 금액대에 적합한 추천 메뉴 목록 보여주기");
            System.out.println("2. 메뉴 이름 혹은 카테고리명으로 검색하여 메뉴 목록 보여주기");
            System.out.println("9. 이전 메뉴로");
            System.out.print("메뉴 번호를 입력해주세요.");
            int no = sc.nextInt();

            switch(no){
                case 1:
                    menuService.findMenuByPrice(inputPrice());
                    break;
                case 2:
                    System.out.println("여기로 왔다.");
                    menuService.searchMenu(inputSearchCriteia());
                    break;
                case 9:
                    return;
            }
        } while (true);
    }


    // 사용자 입력값을 파싱해서 서비스에 전달하는 컨트롤러의 작업을 애플리케이션 단에서 수행하고 있음.
    private static SearchCriteria  inputSearchCriteia() {
        Scanner sc = new Scanner(System.in);
        System.out.print("검색 기준을 입력해주세요 (name or category) : ");
        String condition = sc.nextLine();
        System.out.println("검색어를 입력해주세요: ");
        String value = sc.nextLine();

        return new SearchCriteria(condition, value);
    }


    private static int inputPrice() {
        Scanner sc = new Scanner(System.in);
        System.out.print("검색할 가격대의 최대 금액을 입력해주세요 : ");
        int price = sc.nextInt();
        return price;
    }
}
