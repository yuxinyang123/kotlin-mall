## 用户手机号登陆

POST /api/user/login

### 请求参数

```json
{
  "phone" : "11 bit phone number",
  "password" : ""
}
```

### 返回参数

```json
{
  "data" : {
    "userName" : "leemaster",
    "userToken" : "JWT Token",
    "userRole" : "admin/consumer"
  }
}
```

## 商城用户微信登陆

POST /api/user/wxlogin

1 调用小程序的wx.checkSession 检查用户是否已经登陆
2 非登陆状态 调用wx.login , login 调用完成之后调用本接口，获取open_id ，和 session_key 返回前端
3 后端检查 open_id 是否已经存在在数据库中，如果已经存在，那么返回 code 为302 + 服务自己签发的token 否则 为新用户，返回 200
4 新用户，需要调用wx.getUserInfo 获取用户昵称和头像信息
5 获取信息完成后，调用用户注册，注册完成返回302 + 后端服务自己签发的token  数据库持久化存储

### 请求参数

```json
{
  "code" : "微信登陆的Code",
  "type" : "login/register"
}
```

### 返回参数

```json
{
  "code" : "302/200",
  "data" : {
    "openId" : "200 exists",
    "token" : "302 exists "
  }
}
```

