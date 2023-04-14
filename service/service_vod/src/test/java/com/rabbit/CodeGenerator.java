package com.rabbit;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * 代码生成器
 */
public class CodeGenerator {

    public static void main(String[] args) {
        generate();
    }

    private static void generate(){
        /*连接信息*/
        String url="jdbc:mysql:///studyweb?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true";
        String username="";
        String password="";

        /*输出路径*/
        String outPath="C:\\Users\\sun\\IdeaProjects\\studyweb_parent\\service\\service_user"+"/src/main/java";
        /*mapper路径*/
        String mapperPath="C:\\Users\\sun\\IdeaProjects\\studyweb_parent\\service\\service_user"+"/src/main/resources/mapper";

        FastAutoGenerator.create(url,username,password )
                .globalConfig(builder -> {
                    builder.author("rabbit") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(outPath); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.rabbit.studyweb") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .entity("pojo")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,mapperPath)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("sys_count_time") // 设置需要生成的表名
                            .addTablePrefix("t_","sys_","tb_"); // 设置过滤表前缀
                })
                //.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
