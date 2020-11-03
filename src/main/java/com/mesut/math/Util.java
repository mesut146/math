package com.mesut.math;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

import java.util.Iterator;
import java.util.List;

public class Util {
    public static func cast(Object obj) {
        //start from subtype
        if (obj instanceof variable) {
            return (variable) obj;
        }
        else if (obj instanceof String) {
            return func.parse((String) obj);
        }
        else if (obj instanceof Integer) {
            return new cons((Integer) obj);
        }
        else if (obj instanceof func) {
            return (func) obj;
        }
        else {
            throw new RuntimeException("unexpected type: " + obj.getClass() + " , " + obj);
        }
    }

    public static variable var(Object obj) {
        if (obj instanceof variable) {
            return (variable) obj;
        }
        else if (obj instanceof String) {
            return new variable((String) obj);
        }
        else {
            throw new RuntimeException("unexpected type: " + obj.getClass() + " , " + obj);
        }
    }

    /*public static variable[] vars(Object obj) {
        if (obj instanceof variable) {
            return (variable) obj;
        }
        else if (obj instanceof String) {
            return new variable((String) obj);
        }
        else {
            throw new RuntimeException("unexpected type: " + obj.getClass() + " , " + obj);
        }
    }*/

    public static boolean isEq(List<func> l1, List<func> l2) {
        int len = l1.size();
        if (len != l2.size()) {
            return false;
        }
        boolean[] b = new boolean[len];//processed flags for l2
        boolean matchedAny = false;//flag for an element is not matched

        for (func first : l1) {
            for (int j = 0; j < len; j++) {
                if (!b[j] && first.eq(l2.get(j))) {
                    b[j] = true;
                    matchedAny = true;
                    break;
                }
            }
            if (!matchedAny) {
                return false;
            }
        }
        return true;
    }

    public static String join(List list, String sep) {
        StringBuilder sb = new StringBuilder();
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(sep);
            }
        }
        return sb.toString();
    }
}
