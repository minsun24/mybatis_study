package com.ohgiraffers.section01.xml;

import java.util.List;
import java.util.Map;

public interface MenuMapper {

    List<MenuDTO> selectMenuByPrice(int maxPrice);

    List<MenuDTO> searchMenu(SearchCriteria searchCriteria);

    List<MenuDTO> searchMenuBySupCategory(SearchCriteria searchCriteria);

    List<MenuDTO> searchByRandomMenuCode(List<Integer> randomList);

    List<MenuDTO> searchMenuByNameOrCategory(Map<String, Object> criteria);

    List<MenuDTO> searchMenuByCodeOrSearchAll(SearchCriteria searchCriteria);

    int updateMenu(Map<String, Object> criteria);
}
