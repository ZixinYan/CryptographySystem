package com.zixin.cryptography.service;

import com.zixin.cryptography.pojo.DH;
import com.zixin.cryptography.pojo.PageBean;

public interface DHService {
    void delete(Integer id);

    DH generateAKey(DH dh);

    DH generateBKey(DH dh);

    void add(DH dh);

    PageBean<DH> list(Integer pageNum, Integer pageSize, Integer createUser, Integer targetUser);

    void update(DH dh);
    DH generatePrivateKey(DH dh);
}
