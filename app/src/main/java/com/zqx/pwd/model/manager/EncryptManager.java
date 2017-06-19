package com.zqx.pwd.model.manager;

import com.zqx.pwd.global.C;
import com.zqx.pwd.model.bean.AccountBean;
import com.zqx.pwd.util.AESUtil;

import java.util.Collection;

/**
 * Created by ZhangQixiang on 2017/4/6.
 */

public class EncryptManager {
    public static void encryptAccount(AccountBean bean) {
        bean.name = AESUtil.encrypt(C.string.seed, bean.name);
        bean.pwd = AESUtil.encrypt(C.string.seed, bean.pwd);
    }

    public static void decryptAccount(AccountBean bean) {
        bean.name = AESUtil.decrypt(C.string.seed, bean.name);
        bean.pwd = AESUtil.decrypt(C.string.seed, bean.pwd);
    }

    public static void decryptAccountSet(Collection<AccountBean> set) {
        for (AccountBean bean : set) {
            decryptAccount(bean);
        }
    }
}
