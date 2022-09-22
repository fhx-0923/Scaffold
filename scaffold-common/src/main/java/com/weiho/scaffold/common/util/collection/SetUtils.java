package com.weiho.scaffold.common.util.collection;

import lombok.experimental.UtilityClass;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Weiho
 * @since 2022/9/22
 */
@UtilityClass
public class SetUtils {

    // 判断两个Set中的元素是否一致
    public <T> boolean isSetEqual(Set<T> set1, Set<T> set2) {

        if (set1 == null && set2 == null) {
            return true; // Both are null
        }

        if (set1 == null || set2 == null || set1.size() != set2.size()
                || set1.size() == 0 || set2.size() == 0) {
            return false;
        }

        Iterator<T> ite1 = set1.iterator();
        Iterator<T> ite2 = set2.iterator();

        boolean isFullEqual = true;

        while (ite2.hasNext()) {
            if (!set1.contains(ite2.next())) {
                isFullEqual = false;
            }
        }
        return isFullEqual;
    }
}
