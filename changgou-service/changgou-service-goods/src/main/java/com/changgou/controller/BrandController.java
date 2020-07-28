package com.changgou.controller;

import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/brand")
@CrossOrigin
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 条件分页查询实现
     * @param brand
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo<Brand>> findPage(@RequestBody Brand brand,
                                            @PathVariable(value = "page") Integer page,
                                            @PathVariable(value = "size") Integer size){
        //分页查询
        PageInfo<Brand> pageInfo = brandService.findPage(brand,page,size);
        return new Result<PageInfo<Brand>>(true,StatusCode.OK,"分页+条件查询成功",pageInfo);
    }

    /***
     * 分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo<Brand>> findPage(@PathVariable(value = "page") Integer page,
                                     @PathVariable(value = "size") Integer size){
        //分页查询
        PageInfo<Brand> pageInfo = brandService.findPage(page,size);
        return new Result<PageInfo<Brand>>(true,StatusCode.OK,"分页查询成功",pageInfo);
    }

    /**
     * 条件查询
     * @return
     */
    @PostMapping(value = "/search")
    public Result<List<Brand>> findList(@RequestBody Brand brand){
        List<Brand> brands = brandService.findList(brand);
        return new Result<>(true,StatusCode.OK,"条件搜索查询！",brands);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable(value = "id")Integer id){
        //调用service实现删除
        brandService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 品牌修改实现
     */
    @PutMapping("/{id}")
    public Result update(@PathVariable(value = "id")Integer id, @RequestBody Brand brand){
        //将id给brand
        brand.setId(id);
        //调用service实现修改
        brandService.update(brand);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 增加品牌
     */
    @PostMapping
    public Result add(@RequestBody Brand brand){
        //调用service实现操作
        brandService.add(brand);
        return new Result(true,StatusCode.OK,"增加品牌成功");
    }


    /**
     * 根据主键id查询
     */
    @GetMapping("/{id}")
    public Result<Brand> findAll(@PathVariable(value = "id") Integer id){
        //调用service实现查询
        Brand brand = brandService.findById(id);
        //响应数据封装
        return new Result<Brand>(true,StatusCode.OK,"根据id查询Brand成功",brand);
    }

    /**
     * 查询所有品牌
     */
    @GetMapping
    public Result findAll(){
        //查询所有品牌
        List<Brand> brands = brandService.findAll();

        //响应结果封装
        return new Result(true, StatusCode.OK, "查询品牌集合成功", brands);

    }
}
