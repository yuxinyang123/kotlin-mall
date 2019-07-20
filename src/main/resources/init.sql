# 用户信息表
drop table if exists mall_user_tbl;
create table mall_user_tbl(
    id bigint unsigned not null ,
    name varchar(255) not null default '-' comment '微信登陆直接使用微信昵称',
    phone varchar(11) not null default  '-' comment '用户手机号',
    mail varchar(255) not null default '-' comment '用户邮箱',
    password varchar(255) not null default '' comment '用户密码，微信用户无需用户密码登陆',
    wx_open_id varchar(255) not null default '-' comment '用户微信账号',
    wx_avatar_url varchar(255) not null default '-' comment '微信头像',
    wx_bind_status int not null default 0 comment '首次登陆绑定微信绑定状态，管理员账号是不需要进行微信绑定',
    add_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp,
    primary key (id),
    key idx_phone (phone) using btree ,
    key idx_wx_open_id (wx_open_id) using btree
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

# 用户额外信息表除了微信之后还有的信息
drop table if exists mall_user_ext_tbl;
create table mall_user_ext_tbl(
    id bigint unsigned not null auto_increment,
    user_status int not null default 0 comment '用户状态',
    user_role varchar(30) not null default 'consumer' comment 'admin consumer pre-consumer 管理员，会员，普通用户',
    add_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp,
    primary key (id),
    key idx_user_id (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

# 用户地址表
drop table if exists mall_address_tbl;
create table mall_address_tbl(
    id bigint unsigned not null auto_increment,
    user_id bigint unsigned not null,
    name varchar(10) not null default '-' comment '收货人姓名',
    province varchar(30) not null default '-' comment '省份',
    is_default int not null default 0 comment '是否默认',
    connect varchar(11) not null default '-' comment '联系电话',
    city varchar(30) not null default '-' comment '城市',
    country varchar(30) not null default '-' comment '下属乡镇，或者省市街道',
    details varchar(255) not null default '-' comment '地址详情',
    remark varchar(255) not null default '-' comment '地址备注',
    add_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp,
    primary key (id),
    key idx_user_id (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

# 商品分类表
drop table if exists mall_category_tbl;
create table mall_category_tbl(
    id bigint unsigned not null auto_increment,
    name varchar(50) not null ,
    icon varchar(100) not null default '-' comment 'TODO 默认的icon 标记 class ',
    color varchar(30) not null default '#000' comment '默认的字体图标颜色',
    parent bigint unsigned not null  default 0,
    level int not null default 1 comment '分类等级，最高到两级 limit2 目前字段无用',
    add_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp,
    primary key (id),
    key idx_parent (parent)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

# 商品表
create table mall_goods_tbl(
    id bigint unsigned not null auto_increment,
    name varchar(75) not null default '',
    price decimal(12,4) not null default 0.0 comment '价格',
    type bigint unsigned not null comment '商品分类',
    introduce varchar(255) not null default '' comment '简介',
    details text not null comment '图文详情',
    avatar varchar(255) not null default '' comment '首页图片',
    inventory int unsigned not null default 0 comment  '库存',
    is_enable int not null default 1,
    is_del int not null default 0,
    location varchar(255) not null default '-' comment '产地',
    add_time datetime not null default current_timestamp,
    update_time datetime not null  default current_timestamp on update current_timestamp,
    primary key (id),
    key idx_name (name),
    key idx_location_price(location,price)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

# 轮播图推荐表
create table mall_banner_tbl(
    id bigint unsigned not null auto_increment,
    goods_id bigint unsigned not null default 0 comment '商品ID',
    name varchar(255) not null default '-',
    url varchar(255) not null default '-',
    link varchar(255) not null default '-',
    serial int not null default 1 comment '轮播图顺序',
    type varchar(30) not null default 'item' comment 'common 纯图 item 商品ID category link为分类ID remote 远程 URL',
    location varchar(30) not null default 'index' comment 'index 首页轮播图，goods 商品轮播图',
    add_time datetime not null default current_timestamp,
    update_time datetime not null  default current_timestamp on update current_timestamp,
    primary key (id),
    key idx_type_goods_id (goods_id,type),
    key idx_location_goods_id (goods_id,location)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

# 首页各种商品推荐配置
create table mall_push_tbl(
  id bigint unsigned not null auto_increment,
  icon varchar(100) not null default  '-',
  cover_url varchar(255) not null default '',
  push_type varchar(30) not null default 'index' comment 'index 首页分类图标 new 新商品 hot 热门商品',
  link varchar(255) not null ,
  add_time datetime not null default current_timestamp,
  update_time datetime not null  default current_timestamp on update current_timestamp,
  primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

# 订单表
create table mall_order_tbl(
  id bigint unsigned not null auto_increment,
  sum decimal(20,4) not null default 0.0,
  status int not null default 0 comment '0 初始化 1 等待支付 2 等待派送 3 派送中 4 订单结束 5 订单取消',
  address_id bigint not null,
  user_id bigint unsigned not null ,
  logistics varchar(255) not null default '-',
  carrier_name varchar(255) not null default '普通物流 中通申通',
  add_time datetime not null default current_timestamp,
  update_time datetime not null  default current_timestamp on update current_timestamp,
  primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

# 订单商品关联表
create table mall_goods_order_tbl(
  id bigint unsigned not null auto_increment,
  order_id bigint unsigned not null ,
  goods_num int not null default 1,
  goods_id bigint unsigned not null ,
  add_time datetime not null default current_timestamp,
  update_time datetime not null  default current_timestamp on update current_timestamp,
  primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

# 收藏商品关联表
create table mall_favorite_tbl(
    id bigint unsigned not null auto_increment,
    user_id bigint unsigned not null ,
    goods_id bigint unsigned not null ,
    add_time datetime not null default current_timestamp,
    update_time datetime not null  default current_timestamp on update current_timestamp,
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

# 购物车商品关联表
create table mall_cart_tbl(
  id bigint unsigned not null auto_increment,
  user_id bigint unsigned not null ,
  goods_id bigint unsigned not null ,
  goods_num int not null default 1,
  add_time datetime not null default current_timestamp,
  update_time datetime not null  default current_timestamp on update current_timestamp,
  primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

# 用户申请表
create table mall_apply_tbl(
    id bigint unsigned not null auto_increment,
    user_id bigint unsigned not null ,
    title varchar(255) not null default '会员申请',
    status varchar(30) not null default 'waiting' comment 'waiting handling reject commit cancel ',
    content varchar(255) not null ,
    phone varchar(11) not null ,
    name varchar(30) not null ,
    remark varchar(30) not null default '正在处理' comment '各种记录',
    add_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp,
    primary key (id),
    key idx_phone (phone),
    key idx_name (name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

# 商城应用表
create table mall_application_tbl(
    id int not null auto_increment,
    name varchar(30) not null ,
    link varchar(255) not null comment '商城插件内部连接，URL',
    is_enable int not null default 0,
    is_init int not null default 0,
    add_time datetime not null default current_timestamp,
    update_time datetime not null  default current_timestamp on update current_timestamp,
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

# 个人中心配置
create table mall_center_button(
   id bigint unsigned not null auto_increment,
   name varchar(30) not null ,
   icon varchar(30) not null default '',
   url varchar(255) not null default '',
   link varchar(255) not null default '',
   type varchar(30) not null default 'fonticon' comment 'fonticon 字体图标 svg url代表图片位置',
   add_time datetime not null default current_timestamp,
   update_time datetime not null  default current_timestamp on update current_timestamp,
   primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

# 用户 权限表 TODO
# 权限角色绑定表 TODO
# 角色继承关系绑定 TODO
# 支付状态
# 优惠券表
# 拼团相关表
# 秒杀相关表
