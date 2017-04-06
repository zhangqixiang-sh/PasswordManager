package com.zqx.mypwd.model.manager;

import com.zqx.mypwd.global.Const;
import com.zqx.mypwd.model.bean.AccountBean;
import com.zqx.mypwd.util.AESUtil;

/**
 * Created by ZhangQixiang on 2017/4/6.
 */

public class EncryptManager {
    public static AccountBean encryptAccount(AccountBean bean) {
        bean.name = AESUtil.encrypt(Const.strings.seed, bean.name);
        bean.pwd = AESUtil.encrypt(Const.strings.seed, bean.pwd);
        return bean;
    }

    public static AccountBean decryptAccount(AccountBean bean) {
        bean.name = AESUtil.decrypt(Const.strings.seed, bean.name);
        bean.pwd = AESUtil.decrypt(Const.strings.seed, bean.pwd);
        return bean;
    }
}
