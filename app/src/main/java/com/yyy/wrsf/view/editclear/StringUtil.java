package com.yyy.wrsf.view.editclear;

import androidx.annotation.NonNull;

public class StringUtil {
    public static String formatTitle(@NonNull String s) {
        switch (s.length()) {
            case 0:
                return new StringBuffer().append(s).insert(0, "\u3000\u3000\u3000\u3000").toString();
            case 1:
                return new StringBuffer().append(s).insert(1, "\u3000\u3000\u3000").toString();
            case 2:
                return new StringBuffer().append(s).insert(1, "\u3000\u3000").toString();
            case 3:
                return new StringBuffer().append(s).insert(1, "\u0020\u0020").insert(4, "\u0020\u0020").toString();
            case 4:
                return s;
            default:
                return s.substring(0, 4);
        }
    }

}
