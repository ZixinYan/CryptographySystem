package com.zixin.cryptography.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zixin.cryptography.pojo.Note;
import com.zixin.cryptography.pojo.PageBean;
import com.zixin.cryptography.service.NoteService;
import com.zixin.cryptography.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zixin.cryptography.mapper.NoteMapper;

import java.util.List;
import java.util.Map;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired  // 依赖注入
    NoteMapper noteMapper;

    @Override
    public void add(Note note) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        note.setCreateUser(id);
        noteMapper.add(note);
    }

    @Override
    public PageBean<Note> list(Integer pageNum, Integer pageSize, Integer createUser, Integer targetUser) {
        PageBean<Note> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        System.out.println(createUser);
        List<Note> list = noteMapper.list(id,createUser, targetUser);
        // 强转, 由于PageBean继承自ArrayList, 所以可以强转, 可以获取PageHelper的分页信息
        Page<Note> p = (Page<Note>) list;
        //填充到PageBean
        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Override
    public void delete(Integer id) {
        noteMapper.delete(id);
    }
}
