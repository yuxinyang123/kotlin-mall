# 用户信息表
create table mall_user_tbl(
    id bigint unsigned not null auto_increment,
    name varchar(255) not null default '' comment '微信登陆直接使用微信昵称',
    phone varchar(11) not null default  '-' comment '用户手机号',
    mail varchar(255) not null default '-' comment '用户邮箱',
    password varchar(255) not null default '' comment '用户密码，微信用户无需用户密码登陆',
    wx_open_id varchar(255) not null default '-' comment '用户微信账号',
    wx_bind_status int not null default 0 comment '首次登陆绑定微信绑定状态',
    add_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp,
    primary key (id)
);

# 用户额外信息表除了微信之后还有的信息
create table mall_user_ext_tbl(
    id bigint unsigned not null auto_increment,
    user_id bigint unsigned not null ,
    user_role varchar(30) not null default 'consumer' comment '不需要实现完整的权限系统',
    add_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp,
    primary key (id)
);

# 用户 权限表
# 权限角色绑定表
# 角色继承关系绑定

# 用户地址表
create table mall_address_tbl(
    id bigint unsigned not null auto_increment,
    address varchar(255) not null default '-',
    user_id bigint unsigned not null ,
    add_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp,
    primary key (id)
);

# 商品分类表
create table mall_category_tbl(
    id bigint unsigned not null auto_increment,
    name varchar(50) not null ,
    icon varchar(100) not null default  'TODO 默认的icon 标记',
    parent bigint unsigned not null  default 0,
    level int not null default 1 comment '分类等级，最高到两级 limit2',
    add_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp,
    primary key (id)
);

# 商品表
create table mall_goods_tbl(
    id bigint unsigned not null auto_increment,
    name varchar(75) not null default '',
    price decimal(12,4) not null default 0.0,
    introduce varchar(255) not null default '',
    details text not null comment '图文详情',
    inventory int unsigned not null default 0 comment  '库存',
    status int not null default 0 comment '0上架 1下架',
    location varchar(255) not null default '-' comment '产地',
    add_time datetime not null default current_timestamp,
    update_time datetime not null  default current_timestamp on update current_timestamp,
    primary key (id),
    key idx_name (name),
    key idx_location_price(location,price)
);

# 首页轮播推荐表，
create table mall_banner_tbl(
    id bigint unsigned not null auto_increment,
    goods_id bigint unsigned not null comment '商品ID',
    name varchar(255) not null default '-',
    url varchar(255) not null ,
    link varchar(255) not null ,
    type int not null default 0 comment 'item内部商品连接link填写商品ID，extern外部连接填写url',
    location varchar(30) not null default 'index' comment 'index 首页轮播图，goods 商品轮播图',
    add_time datetime not null default current_timestamp,
    update_time datetime not null  default current_timestamp on update current_timestamp,
    primary key (id)
);

# 首页各种推荐配置
create table mall_push_tbl(
  id bigint unsigned not null auto_increment,
  icon varchar(100) not null default  '-',
  cover_url varchar(255) not null default '',
  push_type int not null default 0 comment '0 首页推荐类别ICON，1新品推荐GoodsId 2 热门推荐GoodsId',
  link varchar(255) not null ,
  add_time datetime not null default current_timestamp,
  update_time datetime not null  default current_timestamp on update current_timestamp,
  primary key (id)
);

# 订单表
create table mall_order_tbl(
  id bigint unsigned not null auto_increment,
  sum decimal(20,4) not null default 0.0,
  status int not null default 0 comment '0 init 1 wait_pay 2 wait_express 3 dispatching 4 complete 5 cancel',
  user_id bigint unsigned not null ,
  logistics varchar(255) not null default '-',
  carrier_name varchar(255) not null default '普通物流 中通申通',
  add_time datetime not null default current_timestamp,
  update_time datetime not null  default current_timestamp on update current_timestamp,
  primary key (id)
);

# 订单商品关联表
create table mall_goods_order_tbl(
  id bigint unsigned not null auto_increment,
  order_id bigint unsigned not null ,
  goods_id bigint unsigned not null ,
  add_time datetime not null default current_timestamp,
  update_time datetime not null  default current_timestamp on update current_timestamp,
  primary key (id)
);

# 收藏商品关联表
create table mall_favorite_tbl(
    id bigint unsigned not null auto_increment,
    user_id bigint unsigned not null ,
    goods_id bigint unsigned not null ,
    add_time datetime not null default current_timestamp,
    update_time datetime not null  default current_timestamp on update current_timestamp,
    primary key (id)
);

# 购物车商品关联表
create table mall_favorite_tbl(
  id bigint unsigned not null auto_increment,
  user_id bigint unsigned not null ,
  goods_id bigint unsigned not null ,
  goods_num int not null default 1,
  add_time datetime not null default current_timestamp,
  update_time datetime not null  default current_timestamp on update current_timestamp,
  primary key (id)
);



