package com.mesut.math;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

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
        else if (obj instanceof func) {
            return (variable) obj;
        }
        else {
            throw new RuntimeException("unexpected type: " + obj.getClass() + " , " + obj);
        }
    }
}
