package com.baizhi.cache;

import com.baizhi.dto.TagDto;
import org.springframework.util.StringUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TagCache {

    public static List<TagDto> get() {
        List<TagDto> tagDtos = new ArrayList<>();
        TagDto program = new TagDto();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("javascript", "php", "css", "html", "java", "node.js", "python", "c++", "go", "ruby", ".net", "lua", "rust", "scala", "c#", "perl", "erlang"));
        TagDto framework = new TagDto();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("larvel", "spring", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts"));
        TagDto server = new TagDto();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "docker", "apache", "ubuntu", "centos", "tomcat", "unix", "hadoop", "windows-server"));
        TagDto database = new TagDto();
        database.setCategoryName("数据库");
        database.setTags(Arrays.asList("mysql", "oracle", "mongodb", "memcached", "redis", "sqlserver", "postgresql", "sqlite"));
        TagDto tool = new TagDto();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git", "github", "visual-studio-code", "vim", "intellij-idea", "eclipse", "maven", "svn"));
        tagDtos.add(tool);
        tagDtos.add(database);
        tagDtos.add(framework);
        tagDtos.add(server);
        tagDtos.add(program);
        return tagDtos;
    }

    public static String filterInvalid(String tags) {
        String[] split = null;
        if(tags.contains(",")) {
             split= StringUtils.split(tags, ",");
        }else{
            split = new String[1];
            split[0]=tags;
        }
        List<TagDto> tagDtos = TagCache.get();
        List<String> tagList = tagDtos.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
