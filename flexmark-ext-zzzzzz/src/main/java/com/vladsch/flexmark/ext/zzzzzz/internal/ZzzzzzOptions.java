package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzExtension;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSetter;
import org.jetbrains.annotations.NotNull;

class ZzzzzzOptions implements MutableDataSetter {
    final public boolean zzzzzzOption1;
    final public String zzzzzzOption2;
    final public int zzzzzzOption3;

    public ZzzzzzOptions(DataHolder options) {
        zzzzzzOption1 = ZzzzzzExtension.ZZZZZZ_OPTION1.get(options);
        zzzzzzOption2 = ZzzzzzExtension.ZZZZZZ_OPTION2.get(options);
        zzzzzzOption3 = ZzzzzzExtension.ZZZZZZ_OPTION3.get(options);
    }

    @NotNull
    @Override
    public MutableDataHolder setIn(@NotNull MutableDataHolder dataHolder) {
        dataHolder.set(ZzzzzzExtension.ZZZZZZ_OPTION1, zzzzzzOption1);
        dataHolder.set(ZzzzzzExtension.ZZZZZZ_OPTION2, zzzzzzOption2);
        dataHolder.set(ZzzzzzExtension.ZZZZZZ_OPTION3, zzzzzzOption3);
        return dataHolder;
    }
}
