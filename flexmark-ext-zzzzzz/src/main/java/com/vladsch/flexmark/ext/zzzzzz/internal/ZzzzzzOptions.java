package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzExtension;
import com.vladsch.flexmark.util.options.DataHolder;

class ZzzzzzOptions {
    public final boolean zzzzzzOption1;
    public final String zzzzzzOption2;
    public final int zzzzzzOption3;

    public ZzzzzzOptions(DataHolder options) {
        this.zzzzzzOption1 = options.get(ZzzzzzExtension.ZZZZZZ_OPTION1);
        this.zzzzzzOption2 = options.get(ZzzzzzExtension.ZZZZZZ_OPTION2);
        this.zzzzzzOption3 = options.get(ZzzzzzExtension.ZZZZZZ_OPTION3);
    }
}
