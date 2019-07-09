package xyz.chunshengyuan.mall.controller

import org.springframework.web.bind.annotation.*
import xyz.chunshengyuan.mall.middleware.RequiredRole
import xyz.chunshengyuan.mall.model.BaseResponse
import xyz.chunshengyuan.mall.model.success

/**
 * Author LeeMaster
 *
 */

@RestController
@RequestMapping("/api/recommand")
class UserRecommandController{

    /**
     * index banner
     */
    @GetMapping("/index")
    fun getIndexBanners():BaseResponse<Nothing>{
        return success()
    }

    /**
     * index category
     */
    @GetMapping("/icon/index")
    fun getIndexIconBanners(): BaseResponse<Nothing>{
        return success()
    }

    /**
     * index new items
     */
    @GetMapping("/icon/new")
    fun getIndexNewItemIcons(): BaseResponse<Nothing>{
        return success()
    }

    /**
     * index hot items
     */
    @GetMapping("/icon/hot")
    fun getIndexHotItemIcons(): BaseResponse<Nothing>{
        return success()
    }

    /**
     * add a new icon
     */
    @RequiredRole(role = [RequiredRole.ADMIN_ROLE])
    @PostMapping("/icon")
    fun setIndexIcon(): BaseResponse<Nothing>{
        return success()
    }

    /**
     * update the icon
     */
    @RequiredRole(role = [RequiredRole.ADMIN_ROLE])
    @PutMapping("/icon")
    fun updateIndexIcon(): BaseResponse<Nothing> = success()

    /**
     * delete the icon
     */
    @RequiredRole(role = [RequiredRole.ADMIN_ROLE])
    @DeleteMapping("/icon")
    fun deleteIndexIcon(): BaseResponse<Nothing> = success()

    /**
     * add a new banner
     */
    @RequiredRole(role = [RequiredRole.ADMIN_ROLE])
    @PostMapping("/index")
    fun setIndexBanners(): BaseResponse<Nothing>{
        return success()
    }

    /**
     * update a banner
     */
    @RequiredRole(role = [RequiredRole.ADMIN_ROLE])
    @PutMapping("/index")
    fun updateIndexBanner(): BaseResponse<Nothing>{
        return success()
    }

    /**
     * delete a banner
     */
    @RequiredRole(role = [RequiredRole.ADMIN_ROLE])
    @DeleteMapping("/index")
    fun deleteIndexBanners(): BaseResponse<Nothing>{
        return success()
    }

}