package com.changgou.service.impl;

import com.changgou.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 分页+条件搜索
     * @param brand：搜索条件
     * @param page：当前页
     * @param size：每页显示的条数
     * @return
     */
    @Override
    public PageInfo<Brand> findPage(Brand brand, Integer page, Integer size) {
        //分页
        PageHelper.startPage(page,size);

        //搜索数据
        Example example = createExample(brand);
        List<Brand> brands = brandMapper.selectByExample(example);

        //封装PageInfo(Brand)
        return new PageInfo<Brand>(brands);
    }

    /**
     * 分页查询
     * @param page：当前页
     * @param size：每页显示的条数
     * @return
     */
    @Override
    public PageInfo<Brand> findPage(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        //查询集合
        List<Brand> brands = brandMapper.selectAll();
        return new PageInfo<Brand>(brands);
    }

    @Override
    public List<Brand> findList(Brand brand) {
        Example example = createExample(brand);
        return brandMapper.selectByExample(example);
    }

    /**
     * 条件构建
     * @param brand
     * @return
     */
    public Example createExample(Brand brand){
        //自定义条件搜索对象Example
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria(); //条件构造器

        if(brand!=null){
            // 品牌名称
            //brand.name!=null根据名字模糊搜索 where name like '%华%'
            if(!StringUtils.isEmpty(brand.getName())){
                criteria.andLike("name","%"+brand.getName()+"%");
            }
            // 品牌的首字母
            if(!StringUtils.isEmpty(brand.getLetter())){
                criteria.andEqualTo("letter",brand.getLetter());
            }
        }
        return example;
    }

    /**
     * 根据id删除
     * @param id
     */
    @Override
    public void delete(Integer id) {
        //使用通用Mapper.deleteByPrimaryKey实现删除
        brandMapper.deleteByPrimaryKey(id);

    }

    /**
     * 根据id修改品牌
     * @param brand
     */
    @Override
    public void update(Brand brand) {
        /**
         * 使用通用Mapper.updateByPrimaryKeySelective
         */
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    /**
     * 增加品牌
     * @param brand
     */
    @Override
    public void add(Brand brand) {
        /**
         * 使用通用Mapper.insertSelective实现增加
         * 方法中但凡带有Selective,会忽略空值
         */
        brandMapper.insertSelective(brand);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Brand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }
}
