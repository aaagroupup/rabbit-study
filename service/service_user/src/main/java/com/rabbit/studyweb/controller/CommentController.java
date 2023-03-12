package com.rabbit.studyweb.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbit.model.pojo.User;
import com.rabbit.studyweb.common.Constants;
import com.rabbit.studyweb.result.R;
import com.rabbit.model.pojo.Comment;
import com.rabbit.studyweb.service.ICommentService;
import com.rabbit.studyweb.service.UserService;
import com.rabbit.studyweb.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rabbit
 * @since 2022-10-17
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private ICommentService commentService;

    @Autowired
    private UserService userService;

    @GetMapping("/tree/{articleId}")
    public R<List<Comment>> findCommentDetail(@PathVariable Integer articleId){
        List<Comment> commentList = commentService.findCommentDetail(articleId);//查询出文章下所有的评论

        //查询评论数据（不包括回复）
        List<Comment> originList = commentList.stream().filter(comment -> comment.getOriginId() == null).collect(Collectors.toList());

        //设置评论的子节点（该评论下的所有回复）
        for (Comment origin : originList) {
            //设置当前用户数据
            User user = userService.getById(origin.getUserId());
            origin.setAvatar(Constants.OSS_URL+user.getAvatarUrl());
            //找到所有子评论的集合
            List<Comment> childrenComments = commentList.stream().filter(comment -> origin.getId().equals(comment.getOriginId())).collect(Collectors.toList());

            /*对以下代码的解释：
                  1. 我们要找出当前’回复‘的父级评论的用户id和昵称，目的是指明回复的对象是谁，这里的对象就是父级评论的用户昵称
                  2. 怎么找父级评论的用户id和昵称？
                    2.1 首先遍历所有的回复，分别给每个回复赋与对应的父级评论的用户id和昵称
                    2.2 接着找出每个回复的父级，怎么找呢？ 当然是遍历所有的评论，
                    找出当前的父id和所有评论里的id相等的那个对象，这个对象就是父级 代码对应-> comment.getPid().equals(c.getId()) comment是当前的回复，c是所有的评论
                    2.3 补充说明 findFirst()：返回Stream中的第一个元素。在java8中，
                    查找集合中符合条件的第一个对象，如果可以明确条件只能匹配一个，使用上 findFirst()，性能更好。
                    如果存在一个值，isPresent()将返回true, get()将返回值
                    2.4 找到父级的用户id和昵称后就可以设置给当前的回复，对应的代码：
                        comment.setPUserId(v.getUserId());
                        comment.setPNickname(v.getNickname()
            */
            childrenComments.forEach(comment -> {
                //找到父级评论的用户id和用户昵称，并设置给当前的回复对象
                commentList.stream().filter(c->comment.getPid().equals(c.getId())).findFirst().ifPresent(v->{
                    comment.setPUserId(v.getUserId());
                    comment.setPNickname(v.getNickname());
                });
                User user1 = userService.getById(comment.getUserId());
                comment.setAvatar(Constants.OSS_URL+user1.getAvatarUrl());

            });
            origin.setChildren(childrenComments);
        }
        return R.success(originList);
    }

    //根据课程id查询评论
    @GetMapping("/tree/getComment/{courseId}")
    public R<List<Comment>> findCommentDetailByCourseId(@PathVariable Long courseId){
        List<Comment> commentList = commentService.findCommentDetailByCourseId(courseId);//查询出课程下所有的评论

        //查询评论数据（不包括回复）
        List<Comment> originList = commentList.stream().filter(comment -> comment.getOriginId() == null).collect(Collectors.toList());

        //设置评论的子节点（该评论下的所有回复）
        for (Comment origin : originList) {
            //设置当前用户数据
            User user = userService.getById(origin.getUserId());
            origin.setAvatar(Constants.OSS_URL+user.getAvatarUrl());
            //找到所有子评论的集合
            List<Comment> childrenComments = commentList.stream().filter(comment -> origin.getId().equals(comment.getOriginId())).collect(Collectors.toList());

            /*对以下代码的解释：
                  1. 我们要找出当前’回复‘的父级评论的用户id和昵称，目的是指明回复的对象是谁，这里的对象就是父级评论的用户昵称
                  2. 怎么找父级评论的用户id和昵称？
                    2.1 首先遍历所有的回复，分别给每个回复赋与对应的父级评论的用户id和昵称
                    2.2 接着找出每个回复的父级，怎么找呢？ 当然是遍历所有的评论，
                    找出当前的父id和所有评论里的id相等的那个对象，这个对象就是父级 代码对应-> comment.getPid().equals(c.getId()) comment是当前的回复，c是所有的评论
                    2.3 补充说明 findFirst()：返回Stream中的第一个元素。在java8中，
                    查找集合中符合条件的第一个对象，如果可以明确条件只能匹配一个，使用上 findFirst()，性能更好。
                    如果存在一个值，isPresent()将返回true, get()将返回值
                    2.4 找到父级的用户id和昵称后就可以设置给当前的回复，对应的代码：
                        comment.setPUserId(v.getUserId());
                        comment.setPNickname(v.getNickname()
            */
            childrenComments.forEach(comment -> {
                //找到父级评论的用户id和用户昵称，并设置给当前的回复对象
                commentList.stream().filter(c->comment.getPid().equals(c.getId())).findFirst().ifPresent(v->{
                    comment.setPUserId(v.getUserId());
                    comment.setPNickname(v.getNickname());
                });
                User user1 = userService.getById(comment.getUserId());
                comment.setAvatar(Constants.OSS_URL+user1.getAvatarUrl());
            });
            origin.setChildren(childrenComments);
        }
        return R.success(originList);
    }


     // 新增或者更新
     @PostMapping
     public R<String> save(@RequestBody Comment comment) {
        if(comment.getId()==null){//新增评论
            if(comment.getContent()==null||comment.getContent().length()==0){
                return R.error("请您输入评论");
            }else{
                comment.setUserId(TokenUtils.getCurrentUser().getId());
                comment.setTime(DateUtil.now());

                if(comment.getPid()!=null){//如果新增的评论为回复，则处理
                    Integer pid = comment.getPid();
                    Comment pComment = commentService.getById(pid);//查询出父评论
                    if(pComment.getOriginId()!=null){//如果当前的父级有祖宗，那么就设置相同的祖宗，说明这个回复是第三级以下的回复
                        comment.setOriginId(pComment.getOriginId());
                    }else{//否则就设置父级为当前回复的祖宗，说明这是第一级的回复
                        comment.setOriginId(comment.getPid());
                    }
                }
            }
        }
         boolean flag = commentService.saveOrUpdate(comment);
        if(!flag){
            return R.error("评论失败");
        }
         return R.success("评论成功");
     }

      @DeleteMapping("/{id}")
      public Boolean delete(@PathVariable Integer id) {
          return commentService.removeById(id);
      }

      @GetMapping("/{id}")
      public Comment findOne(@PathVariable Integer id) {
          return commentService.getById(id);
      }

      @GetMapping("/page")
      public Page<Comment> findPage(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize) {
          QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
          queryWrapper.orderByDesc("id");
          return commentService.page(new Page<>(pageNum, pageSize), queryWrapper);
      }

}

