package xyz.chunshengyuan.mall.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.chunshengyuan.mall.configuration.RequiredRole;
import xyz.chunshengyuan.mall.constrant.PushType;
import xyz.chunshengyuan.mall.constrant.UserRole;
import xyz.chunshengyuan.mall.mapper.BannerPlusMapper;
import xyz.chunshengyuan.mall.mapper.CategoryPlusMapper;
import xyz.chunshengyuan.mall.mapper.PushPlusMapper;
import xyz.chunshengyuan.mall.model.BaseResponse;
import xyz.chunshengyuan.mall.model.po.Banner;
import xyz.chunshengyuan.mall.model.po.Category;
import xyz.chunshengyuan.mall.model.po.Push;
import xyz.chunshengyuan.mall.model.v1.V1IndexTypesVo;
import xyz.chunshengyuan.mall.model.vo.PushListVo;
import xyz.chunshengyuan.mall.service.GoodsService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: V1IndexController
 * @date: 2019-07-11 01:25
 * @author: yuxinyang
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/v1/index")
public class V1IndexController {
    @Resource
    GoodsService goodsService;

    @Resource
    BannerPlusMapper bannerPlusMapper;

    @Resource
    PushPlusMapper pushPlusMapper;

    @Resource
    CategoryPlusMapper categoryPlusMapper;

    @GetMapping("banner")
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse getBannerResource() {
        List<Banner> banners = bannerPlusMapper.selectList(Wrappers.<Banner>query().eq("location", "index").orderByAsc("serial"));
        return BaseResponse.succes(banners);
    }

    @GetMapping("newest")
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse getNewestResource() {
        PushListVo pushListVo = goodsService.listPush(6L, 1L, PushType.NEW.code);
        return BaseResponse.succes(pushListVo);
    }

    @GetMapping("hottest")
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse getHottestResource() {
        PushListVo pushListVo = goodsService.listPush(6L, 1L, PushType.HOT.code);
        return BaseResponse.succes(pushListVo);
    }

    @GetMapping("types")
    @RequiredRole(roles = {UserRole.ADMIN, UserRole.PRECONSUMER, UserRole.CONSUMER})
    public BaseResponse getTypesResource() {
        IPage<Push> push = pushPlusMapper.selectPage(new Page<>(1, 8), Wrappers.<Push>query().eq("push_type", PushType.INDEX.code));
        List<Category> categories = categoryPlusMapper.selectBatchIds(push.getRecords().stream().map(Push::getLink).collect(Collectors.toList()));
        List<V1IndexTypesVo> list = new ArrayList<>();
        push.getRecords().forEach(i -> categories.forEach(t -> {
            if (Long.valueOf(i.getLink()).equals(t.getId())) {
                list.add(new V1IndexTypesVo(i, t));
            }
        }));
        return BaseResponse.succes(list);
    }

}
