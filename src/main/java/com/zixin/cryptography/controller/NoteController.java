package com.zixin.cryptography.controller;

import com.zixin.cryptography.pojo.Note;
import com.zixin.cryptography.pojo.PageBean;
import com.zixin.cryptography.pojo.Result;
import com.zixin.cryptography.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @PostMapping("/add")
    public Result add(@RequestBody @Validated Note note){
        noteService.add(note);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<PageBean<Note>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer createUser,
            @RequestParam(required = false) Integer targetUser
    ){
       PageBean<Note> pageBean = noteService.list(pageNum, pageSize, createUser, targetUser);
        return Result.success(pageBean);
    }

    @DeleteMapping("/delete")
    public Result delete(Integer id){
        noteService.delete(id);
        return Result.success();
    }
}
