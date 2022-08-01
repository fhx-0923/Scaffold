package com.weiho.scaffold.common.util.page;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Redis查询分页工具
 *
 * @author <a href="https://gitee.com/guchengwuyue/yshopmall">参考链接</a>
 */
@UtilityClass
public class PageUtils {
    /**
     * list 分页工具
     *
     * @param page 当前页面(从0开始)
     * @param size 每页显示的数量
     * @param list 传入要分页的数组
     * @param <L>  泛型
     * @return 分页后的list
     */
    public <L> List<L> toPage(int page, int size, List<L> list) {
        int fromIndex = page * size;
        int toIndex = page * size + size;
        if (fromIndex > list.size()) {
            return new ArrayList<>();
        } else if (toIndex >= list.size()) {
            return list.subList(fromIndex, list.size());
        } else {
            return list.subList(fromIndex, toIndex);
        }
    }

    /**
     * 分页包装类
     *
     * @param object        Map的key
     * @param totalElements Map的value
     * @return Map<String, Object>
     */
    public Map<String, Object> toPageContainer(final Object object, final Object totalElements) {
        return new LinkedHashMap<String, Object>(2) {{
            put("content", object);
            put("totalElements", totalElements);
        }};
    }
}
