package xyz.chunshengyuan.mall.mapper

import org.springframework.data.jpa.repository.JpaRepository
import xyz.chunshengyuan.mall.model.UserBo

/**
 * @Title: User
 * @Package xyz.chunshengyuan.mall.mapper
 * @Description:
 * @author leemaster
 * @date 2019-07-07 00:49
 */

interface UserRepository : JpaRepository<UserBo,Long>