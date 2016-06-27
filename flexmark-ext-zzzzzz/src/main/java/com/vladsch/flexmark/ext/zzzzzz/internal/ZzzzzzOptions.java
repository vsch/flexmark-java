package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzExtension;
import com.vladsch.flexmark.internal.util.collection.DataHolder;

class ZzzzzzOptions {
    final public boolean zzzzzzOption1;
    final public String zzzzzzOption2;
    final public int zzzzzzOption3;

    public ZzzzzzOptions(DataHolder options) {
        this.zzzzzzOption1 = options.get(ZzzzzzExtension.ZZZZZZ_OPTION1);
        this.zzzzzzOption2 = options.get(ZzzzzzExtension.ZZZZZZ_OPTION2);
        this.zzzzzzOption3 = options.get(ZzzzzzExtension.ZZZZZZ_OPTION3);
    }
}
