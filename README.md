### 微信登陆逻辑

1. 小程序端调用checkSession检查用户登陆状态
2. 用户如果过期，调用wx.login 重新登陆
3. 使用用户的code 调用后端 /open/wx/login 进行登陆
4. 如果调用微信后发现，用户open_id 在库中存在注册，那么直接签名发放token 存在的依据是 open_id 存在 切状态为 1  
5. 如果没有登陆，则推送302状态码，用户重定向到注册信息页面 预先注册一个 open_id ，状态为0
6. 收集用户信息，将用户信息持久化，持久化完成修改绑定状态，返回用户token 相关信息

### 订单流转逻辑

1. create order use the goods id 
2. pass the order to admin to the wait_dipatch 
3. pass the order to the next dispatching status 
4. complete the order by admin or cancel 