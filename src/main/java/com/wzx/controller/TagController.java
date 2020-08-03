package com.wzx.controller;

import com.wzx.po.Tag;
import com.wzx.po.Type;
import com.wzx.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping
    public String list(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        Page<Tag> page=tagService.listTag(pageable);
        model.addAttribute("page",page);
        return "admin/tags";
    }

    @GetMapping("{id}/delete")
    public String delete(@PathVariable Long id){
        tagService.deleteById(id);
        return "redirect:/admin/tags";
    }

    @GetMapping("input/{id}")
    public String toInput(@PathVariable Long id,Model model){
        Tag tag=null;
        if(id!=-1){
            tag= tagService.findTagById(id);
        }else {
            tag=new Tag();
        }
        model.addAttribute("tag",tag);
        return "admin/tags-input";
    }

    @PostMapping("input")
    public String input(Tag tag){
        tagService.input(tag);
        return "redirect:/admin/tags";
    }
}
