package com.ssafy.cafe.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.cafe.model.dto.Comment;
import com.ssafy.cafe.model.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/rest/comment")
@CrossOrigin("*")
public class CommentRestController {

    @Autowired
    CommentService cService;
    
    @GetMapping("/{productId}")
    @ApiOperation(value="상품에 대한 모든 코멘트를 리턴한다.")
    public List<Comment> select(@PathVariable Integer productId){
        return cService.selectByProduct(productId);
    }
    
    @PostMapping
    @Transactional
    @ApiOperation(value="comment 객체를 추가한다. 성공하면 true를 리턴한다. ", response = Boolean.class)
    public Boolean insert(@RequestBody Comment comment  ) {
        cService.addComment(comment);
        return true;
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ApiOperation(value="{id}에 해당하는 Comment를 삭제한다. 성공하면 true를 리턴한다. ", response = Boolean.class)
    public Boolean delete(@PathVariable Integer id) {
        cService.removeComment(id);
        return true;
    }
    
    @PutMapping
    @Transactional
    @ApiOperation(value="comment 객체를 수정한다. 성공하면 true를 리턴한다. ", response = Boolean.class)
    public Boolean update(@RequestBody Comment comment  ) {
        cService.updateComment(comment);
        return true;
    }
    

}
