package com.baizhi.dto;

import com.baizhi.model.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class PaginationDto<T> {

    private List<T> data;
    private Boolean showPrevious=true;
    private Boolean showFirstPage=true;
    private Boolean showEndPage=true;
    private Boolean showNext=true;
    private Integer page;
    private List<Integer> pages=new ArrayList<>();
    private Integer totalPage;


    public Integer setPagination(Integer totalCount, Integer page, Integer size) {

        Integer totalPage = (totalCount - 1) / size + 1;
        this.totalPage=totalPage;
        if(page>totalPage) page=totalPage;
        if(page<1) page=1;
        this.page=page;
        if(page==totalPage) showNext=false;
        if(page==1) showPrevious=false;
        for(int i=page-3;i<=page+3;i++){
            if(i>=1 && i<=totalPage) pages.add(i);
        }
        if(pages.contains(1)) showFirstPage=false;
        if(pages.contains(totalPage)) showEndPage=false;
        return this.page;
    }
}
