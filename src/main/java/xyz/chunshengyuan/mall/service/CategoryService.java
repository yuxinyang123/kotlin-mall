package xyz.chunshengyuan.mall.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.chunshengyuan.mall.exceptions.ApiOperationException;
import xyz.chunshengyuan.mall.mapper.CategoryPlusMapper;
import xyz.chunshengyuan.mall.model.bo.CategoryNode;
import xyz.chunshengyuan.mall.model.po.Category;
import xyz.chunshengyuan.mall.model.vo.CategoryForm;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author leemaster
 * @Title: CategoryService
 * @Package xyz.chunshengyuan.mall.service
 * @Description:
 * @date 2019-07-1010:54
 */
@Service
public class CategoryService {

    @Resource
    CategoryPlusMapper categoryPlusMapper;

    /**
     * 构建一个类型树
     * @return
     */
    public List<CategoryNode> selectAllCategroyforest(){
        List<Category> categories = categoryPlusMapper.selectList(
                Wrappers.<Category>query()
        );

        List<CategoryNode> tree = categories.stream().parallel()
                .filter(item -> item.getParent() == 0L)
                .map((item) -> {
                    CategoryNode node = handleNode(item);
                    node.setChildren(
                            categories.stream().filter(
                                    child -> child.getParent().equals(item.getId())
                            ).map(child -> {
                                CategoryNode children = handleNode(child);
                                children.setChildren(new ArrayList<>());
                                return children;
                            }).collect(Collectors.toList())
                    );
                    return node;
                }).collect(Collectors.toList());

        return tree;
    }

    private CategoryNode handleNode(Category child) {
        CategoryNode children = new CategoryNode();
        children.setAddTime(child.getAddTime());
        children.setUpdateTime(child.getUpdateTime());
        children.setId(child.getId().toString());
        children.setIcon(child.getIcon());
        children.setColor(child.getColor());
        children.setName(child.getName());
        children.setLevel(child.getLevel());
        children.setParent(child.getParent().toString());
        return children;
    }


    @Transactional
    public void createNewCategory(CategoryForm form) throws ApiOperationException {
        Category category = new Category();
        category.setIcon(form.getIcon());
        category.setColor(form.getColor());
        category.setParent(form.getParent());
        category.setName(form.getName());

        Category parent = categoryPlusMapper.selectById(form.getId());
        if (Objects.isNull(parent)){
            throw  new ApiOperationException("参数非法","没有父节点 ->> "+ category.getParent());
        }else{
            categoryPlusMapper.insert(
                    category
            );
        }
    }

    @Transactional
    public void deleteCategory(Long categoryId){
        Category category = categoryPlusMapper.selectById(categoryId);
        if(category.getParent() == 0){
            List<Long> childs = categoryPlusMapper
                    .selectList(
                            Wrappers.<Category>query()
                            .eq("parent",categoryId)
                    ).stream().map(Category::getParent).collect(Collectors.toList());

            categoryPlusMapper.deleteBatchIds(childs);
            categoryPlusMapper.deleteById(categoryId);
        }else {
            categoryPlusMapper.deleteById(categoryId);
        }
    }

    @Transactional
    public void updateCategory(CategoryForm form) throws ApiOperationException {
        if (Objects.isNull(form.getId()))
            throw  new ApiOperationException("参数非法","没有ID节点 ->> " + form.getName());
        Category category = new Category();
        category.setIcon(form.getIcon());
        category.setColor(form.getColor());
        category.setParent(form.getParent());
        category.setName(form.getName());
        category.setId(form.getId());

        categoryPlusMapper.updateById(category);
    }

    public CategoryNode getCategoryById(Long id) throws ApiOperationException {
        Category category = categoryPlusMapper.selectById(id);
        if (Objects.isNull(category)){
            throw  new ApiOperationException("获取类型数据失败","没有该类型 ->>> " + id);
        }else{
            return handleNode(category);
        }
    }

}
