package xyz.chunshengyuan.mall.model

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

/**
 * @Title: BusinessObject
 * @Package xyz.chunshengyuan.mall.model
 * @Description:
 * @author leemaster
 * @date 2019-07-07 00:42
 */

@Entity
@Table(name = "mall_user_tbl")
class UserBo(
    var id: Long,
    var name: String,
    var phone: String,
    var mail: String,
    var password: String,
    @Column(name = "wx_open_id")
    var wxOpenId: String,
    @Column(name = "wx_bind_status")
    var wxBindStatus: String,
    @Column(name = "add_time")
    var addTime: Date,
    @Column(name = "update_time")
    var updateTime: Date
)