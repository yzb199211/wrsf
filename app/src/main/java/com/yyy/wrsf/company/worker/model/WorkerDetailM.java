package com.yyy.wrsf.company.worker.model;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.yyy.wrsf.R;
import com.yyy.wrsf.base.model.BaseM;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.view.editclear.EditClearView;

public class WorkerDetailM extends BaseM {
    public void Editable(LinearLayout llContent, boolean b, int code) {
        for (int i = 0; i < llContent.getChildCount(); i++) {
            if (code == CodeUtil.MODIFY & i == 1) continue;
            View view = llContent.getChildAt(i);
            if (view instanceof EditClearView) {
                EditClearView v = (EditClearView) view;
                if (v.getId() == R.id.ecv_type || v.getId() == R.id.ecv_sex) {
                    v.setOnItemAble(b);
                } else {
                    if (b) v.setEditable();
                    else v.forbidEdit();
                }
            }
        }
    }

    public String canSave(LinearLayout llContent) {
        for (int i = 0; i < llContent.getChildCount(); i++) {
            if (TextUtils.isEmpty(((EditClearView) llContent.getChildAt(i)).getText())) {
                return ((EditClearView) llContent.getChildAt(i)).getHint();
            }
        }
        return null;
    }
}
