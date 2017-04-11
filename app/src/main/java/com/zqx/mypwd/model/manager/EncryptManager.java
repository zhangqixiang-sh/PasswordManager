package com.zqx.mypwd.model.manager;

import com.zqx.mypwd.global.C;
import com.zqx.mypwd.model.bean.AccountBean;
import com.zqx.mypwd.util.AESUtil;

/**
 * Created by ZhangQixiang on 2017/4/6.
 */

public class EncryptManager {
    public static AccountBean encryptAccount(AccountBean bean) {
        bean.name = AESUtil.encrypt(C.string.seed, bean.name);
        bean.pwd = AESUtil.encrypt(C.string.seed, bean.pwd);
        return bean;
    }

    public static AccountBean decryptAccount(AccountBean bean) {
        bean.name = AESUtil.decrypt(C.string.seed, bean.name);
        bean.pwd = AESUtil.decrypt(C.string.seed, bean.pwd);
        return bean;
    }
}
