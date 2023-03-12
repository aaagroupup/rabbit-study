package com.rabbit.studyweb.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbit.model.pojo.Article;
import com.rabbit.model.pojo.QueryInfo;
import com.rabbit.studyweb.result.R;
import com.rabbit.studyweb.service.IArticleService;
import com.rabbit.studyweb.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rabbit.studyweb.common.Constants.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rabbit
 * @since 2022-10-16
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private IArticleService articleService;

      @DeleteMapping("/{id}")
      public Boolean delete(@PathVariable Integer id) {
          return articleService.removeById(id);
      }

      @PostMapping("/del/batch")
      public boolean deleteBatch(@RequestBody List<Integer> ids) {
          return articleService.removeByIds(ids);
      }

      @GetMapping
      public List<Article> findAll() {
          return articleService.list();
      }

      @GetMapping("/{id}")
      public Article findOne(@PathVariable Integer id) {
          return articleService.getById(id);
      }

      @GetMapping("/page")
      public Page<Article> findPage(@RequestParam String name,
                                    @RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize) {
          LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
          queryWrapper.orderByDesc(Article::getId);
          if(StrUtil.isNotBlank(name)){
              queryWrapper.like(Article::getName,name);
          }
          return articleService.page(new Page<>(pageNum, pageSize), queryWrapper);
      }

    @GetMapping("/articles")
    public R<Object> getArticle(QueryInfo queryInfo){
        int currentPage = queryInfo.getPageSize() * (queryInfo.getCurrentPage() - 1);

        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Article::getName,queryInfo.getQuery());
        int count =(int) articleService.count(wrapper);
        List<Article> allArticle = articleService.getAllArticle(queryInfo.getQuery(), currentPage, queryInfo.getPageSize());

        return R.success(allArticle,count);
    }


    @PostMapping("/addArticle")
    public R<String> addArticle(@RequestBody Article article){
         if(article.getId()==null){//新增
             article.setTime(DateUtil.now());
             article.setHot(0);
             article.setNickname(TokenUtils.getCurrentUser().getNickname());
         }
        boolean flag = articleService.save(article);
        if(!flag){
            return R.error(addErr_MSG);
        }
        return R.success(addSuc_MSG);
    }

    @DeleteMapping("/deleteArticle")
    public R<String> deleteArticle(Integer id){
        boolean flag = articleService.removeById(id);
        if(!flag){
            return R.error(delErr_MSG);
        }
        return R.success(delSuc_MSG);
    }

    @GetMapping("/getUpdate2")
    public R<Article> getUpdateArticle(Integer id){
        Article article = articleService.getById(id);
        return R.success(article);
    }

    @PutMapping("/updateArticle")
    public R<String> updateArticle(@RequestBody Article article){
        boolean flag = articleService.updateById(article);
        if(!flag){
            return R.error(updateErr_MSG);
        }
        return R.success(updateSuc_MSG);
    }

    @PostMapping
    public R<List<Article>> getAllArticle(){
        return R.success(articleService.list());
    }

    @GetMapping("/getArticleByHot")
    public R getArticleByHot(){
        List<Article> articleList=articleService.getArticleByHot();
        return R.success(articleList);
    }
    @GetMapping("/getArticleByTime")
    public R getArticleByTime(){
        List<Article> articleList=articleService.getArticleByTime();
        return R.success(articleList);
    }

    @PutMapping("/addScore/{id}/{score}")
    public R addScore(@PathVariable Integer id,@PathVariable Integer score){
         boolean flag =articleService.addScore(id,score);
        if(!flag){
            return R.error("评分出错了!");
        }
        return R.success("评价成功");
    }

}

