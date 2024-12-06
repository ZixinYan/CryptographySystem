package com.zixin.cryptography.service;

import com.zixin.cryptography.pojo.Note;
import com.zixin.cryptography.pojo.PageBean;

public interface NoteService {
    void add(Note note);

    PageBean<Note> list(Integer pageNum, Integer pageSize, Integer createUser, Integer targetUser);

    void delete(Integer id);
}
