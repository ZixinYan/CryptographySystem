项目接口文档
一、经典加密算法
Affine字符串加密
● URL：/affine/encrypt
● Method：post
● 需要登录：是
● 需要鉴权：否
请求参数
参数	类型	约束
plaintext	String	无
keyA	int	0-26
keyB	int	0-26
请求示例
可以仅传递部分请求参数。
{
    "plaintext": "Hello",
    "keyA":5,
    "keyB":8
}
!!!禁止空传参数

成功响应
条件：请求参数合法
状态码：200 OK
响应示例：响应会将成功加密的字符传给Affine.ciphertext，然后返回Affine实体：
{
  
    "code": 0,
    "message": "Successful"，
    "data":"[Affine]"
}
错误响应
条件：请求数据非法，密码超出格式
状态码：400 BAD REQUEST
响应示例:
{
    "code": 1,
    "message": "worng message",
    "data": null
}

条件：请求参数合法
状态码：200 OK
响应示例：响应会将成功加密的字符传给Affine.plaintext，然后返回Affine实体：
{
  
    "code": 0,
    "message": "Successful"，
    "data":"[Affine]"
}

Affine字符串加密
● URL：/affine/decrypt
● Method：post
● 需要登录：是
● 需要鉴权：否
请求参数
参数	类型	约束
ciphertext	String	无
keyA	int	0-26
keyB	int	0-26
请求示例
可以仅传递部分请求参数。
{
    "plaintext": "Hello",
    "keyA":5,
    "keyB":8
}
!!!禁止空传参数

成功响应
条件：请求参数合法
状态码：200 OK
响应示例：响应会将成功加密的字符传给Affine.plaintext，然后返回Affine实体：
{
  
    "code": 0,
    "message": "Successful"，
    "data":"[Affine]"
}
错误响应
条件：请求数据非法，密码超出格式
状态码：400 BAD REQUEST
响应示例:
{
    "code": 1,
    "message": "worng message",
    "data": null
}

条件：请求参数合法
状态码：200 OK
响应示例：响应会将成功加密的字符传给Affine.plaintext，然后返回Affine实体：
{
  
    "code": 0,
    "message": "Successful"，
    "data":"[Affine]"
}

Affine文件解密
● URL：/affine/encryptFile
● Method：post
● 需要登录：是
● 需要鉴权：否
请求参数
参数	类型	约束
p	String	@URL
keyA	int	0-26
keyB	int	0-26
请求示例
可以仅传递部分请求参数。
{
    "plainFile": "file path",
    "keyA":5,
    "keyB":8
}
!!!禁止空传参数

成功响应
条件：请求参数合法
状态码：200 OK
响应示例：响应会将一个Affine实体返回，其中ciphertext为加密后的文件下载地址：
{
  
    "code": 0,
    "message": "Successful"，
    "data":"[Affine]"
}
错误响应
条件：请求数据非法，密码超出格式
状态码：400 BAD REQUEST
响应示例:
{
    "code": 1,
    "message": "worng message",
    "data": null
}

Affine文件加密
● URL：/Affine/encryptFile
● Method：Post
● 需要登录：是
● 需要鉴权：否
请求参数
参数	类型	约束
plainFile	String	无
keyA	int	0-26
keyB	int	0-26
请求示例
可以仅传递部分请求参数。
{
    "plaintext": "Hello",
    "keyA":5,
    "keyB":8
}
!!!禁止空传参数

成功响应
条件：请求参数合法
状态码：200 OK
响应示例：响应会将修改后的用户信息数据返回，一个id为 1234 的用户设置他们的姓名后将会返回：
{
  
    "code": 0,
    "message": "Successful"，
    "id": 1234,
    "first_name": "Joe",
    "last_name": "Bloggs",
    "email": "joe25@example.com"
}
错误响应
条件：请求数据非法，密码超出格式
状态码：400 BAD REQUEST
响应示例:
{
    "code": 1,
    "message": "worng message",
    "data": null
}

其他说明
Column Permutation，DES，Autokey,  Caesar, MD5, Playfair, RC4, RSA, Vigenere的接口文档与之类似，只是改了一下名字，然后有的密码参数可能有些差别，在此不多写了


二、DH密钥交换
自动生成密钥
● URL：/dh/generatePrivateKey
● Method：Post
● 需要登录：是
● 需要鉴权：否
请求参数
参数	类型	约束
无	null	无
请求示例
可以仅传递部分请求参数。
{
  
}


成功响应
条件：请求参数合法
状态码：200 OK
响应示例：响应会返回一个DH实体，然后为privateKey赋值
{
  
    "code": 0,
    "message": "Successful"，
    "data":"[DH]"
}
错误响应
条件：请求数据非法
状态码：502,400
响应示例:
{
    "code": 1,
    "message": "worng message",
    "data": null
}

DH请求发起
● URL：/dh/add
● Method：Post
● 需要登录：是
● 需要鉴权：否
请求参数
参数	类型	约束
privateKey	String	无
targetUser	Integer	无
请求示例
可以仅传递部分请求参数。
{
    "privateKey": "ffffffffff",
    "targetUser":5
}
!!!禁止空传参数

成功响应
条件：请求参数合法
状态码：200 OK
响应示例：后端自动分配一个g和一个p，然后计算公钥A，将A，g，p存入数据库：
{
  
    "code": 0,
    "message": "Successful"，
}
错误响应
条件：请求数据非法，targetUser不存在或者无权限申请
状态码：400 BAD REQUEST
响应示例:
{
    "code": 1,
    "message": "worng message",
    "data": null
}

DH删除
● URL：/dh/delete
● Method：Delete
● 需要登录：是
● 需要鉴权：否
请求参数
参数	类型	约束
id	Integet	无
请求示例
可以仅传递部分请求参数。
{
    "id":"1"
}
!!!禁止空传参数

成功响应
条件：请求参数合法
状态码：200 OK
响应示例：响应在后端数据库删除对应DH信息
{
  
    "code": 0,
    "message": "Successful"，
}
错误响应
条件：请求数据非法，没有权限
状态码：502
响应示例:
{
    "code": 1,
    "message": "worng message",
    "data": null
}

DH第二方响应
● URL：/dh/update
● Method：Put
● 需要登录：是
● 需要鉴权：否
请求参数
参数	类型	约束
privateKey	String	无
createUser	Integer	无
targetUser	Integer	无
id	Integer	无
p	String	无
g	String	无
请求示例
可以仅传递部分请求参数。
{
  "privateKey":"",
  "id":"",
  "createUser":2,
  "targetUser":3,
  "p":"",
  "g":"",
}
!!!禁止空传参数

成功响应
条件：请求参数合法
状态码：200 OK
响应示例：响应会将B的密钥与p与g进行计算，然后得到B的公钥并更新到数据库中
{
  
    "code": 0,
    "message": "Successful"，
}
错误响应
条件：请求数据非法，权限不足
状态码：400，502
响应示例:
{
    "code": 1,
    "message": "worng message",
    "data": null
}

DH密钥生成
● URL：/dh/generateAKey   /dh/generateBKey
● Method：Post
● 需要登录：是
● 需要鉴权：否
请求参数
参数	类型	约束
privateKey	String	无
createUser	Integer	无
targetUser	Integer	无
id	Integer	无
B	String	无
A	String	无
p	String	无
请求示例
可以仅传递部分请求参数。
{
  "privateKey":"",
  "id":"",
  "createUser":2,
  "targetUser":3,
  "p":"",
  "A":"",
  "B":"",
}
!!!禁止空传参数

成功响应
条件：请求参数合法
状态码：200 OK
响应示例：响应会将自己的私钥和对方的公钥与p进行计算，并把结果返回key中
{
  
    "code": 0,
    "message": "Successful"，
    "data":"{
    "key":"",
     …… 
    }"
}
错误响应
条件：请求数据非法，权限不足
状态码：400，502
响应示例:
{
    "code": 1,
    "message": "worng message",
    "data": null
}

DH数据库条数读取
● URL：/dh/list
● Method：Get
● 需要登录：是
● 需要鉴权：否
请求参数
参数	类型	约束
pageNum	Integer	无
pageSize	Integer	无
createUser	Integer	无
targetUser	Integer	无
请求示例
可以仅传递部分请求参数。
{
  "pageNum":"",
  "pageSize":"",
  "createUser":2,
  "targetUser":3,
}
!!!禁止空传参数

成功响应
条件：请求参数合法
状态码：200 OK
响应示例：响应会将自己的私钥和对方的公钥与p进行计算，并把结果返回key中
{
  
    "code": 0,
    "message": "Successful"，
    "data":"{
    "key":"",
     …… 
    }"
}
错误响应
条件：请求数据非法，权限不足
状态码：400，502
响应示例:
{
    "code": 1,
    "message": "worng message",
    "data": null
}

三、文件传输
新建信件
● URL：/note/add
● Method：Post
● 需要登录：是
● 需要鉴权：否
请求参数
参数	类型	约束
title	String	<10
content	String	none
algorithm	String	none
signature	String	none
fileUrl	String	none
targetUser	Integer	none
请求示例
可以仅传递部分请求参数。
{
  "title":"",
  "content":"",
  "algorithm":"",
  "signature":"",
  "fileUrl":"",
  "targetUser":""
}


成功响应
条件：请求参数合法
状态码：200 OK
响应示例：响应会successful
{
  
    "code": 0,
    "message": "Successful"，
}
错误响应
条件：请求数据非法
状态码：502,400
响应示例:
{
    "code": 1,
    "message": "worng message",
    "data": null
}

信件删除
● URL：/note/delete
● Method：Delete
● 需要登录：是
● 需要鉴权：否
请求参数
参数	类型	约束
id	Integet	无
请求示例
可以仅传递部分请求参数。
{
    "id":"1"
}
!!!禁止空传参数

成功响应
条件：请求参数合法
状态码：200 OK
响应示例：响应在后端数据库删除对应信息
{
  
    "code": 0,
    "message": "Successful"，
}
错误响应
条件：请求数据非法，没有权限
状态码：502
响应示例:
{
    "code": 1,
    "message": "worng message",
    "data": null
}

信件数据库条数读取
● URL：/note/list
● Method：Get
● 需要登录：是
● 需要鉴权：否
请求参数
参数	类型	约束
pageNum	Integer	无
pageSize	Integer	无
createUser	Integer	无
targetUser	Integer	无
请求示例
可以仅传递部分请求参数。
{
  "pageNum":"",
  "pageSize":"",
  "createUser":2,
  "targetUser":3,
}
!!!禁止空传参数

成功响应
条件：请求参数合法
状态码：200 OK
响应示例：响应会将数据返回
{
  
    "code": 0,
    "message": "Successful"，
    "data":"
  [
    "1":{
      "note":[note]
    },
    "2":{
      "note":[note]
    }
  ]"
}
错误响应
条件：请求数据非法，权限不足
状态码：400，502
响应示例:
{
    "code": 1,
    "message": "worng message",
    "data": null
}




四、用户相关接口
4.1 注册
4.1.1 基本信息
请求路径：/user/register
请求方式：POST
接口描述：该接口用于注册新用户

4.1.2 请求参数
请求参数格式：x-www-form-urlencoded
请求参数说明：
参数名称	说明	类型	是否必须	备注
username	用户名	string	是	5~16位非空字符
password	密码	string	是	5~16位非空字符
请求数据样例：
username=zhangsan&password=123456

4.1.3 响应数据
响应数据类型：application/json
响应参数说明：
名称	类型	是否必须	默认值	备注	其他信息
code	number	必须		响应码, 0-成功,1-失败	
message	string	非必须		提示信息	
data	object	非必须		返回的数据	
响应数据样例：
{
    "code": 0,
    "message": "操作成功",
    "data": null
}

4.2 登录
4.2.1 基本信息
请求路径：/user/login
请求方式：POST
接口描述：该接口用于登录
4.2.2 请求参数
请求参数格式：x-www-form-urlencoded
请求参数说明：
参数名称	说明	类型	是否必须	备注
username	用户名	string	是	5~16位非空字符
password	密码	string	是	5~16位非空字符
请求数据样例：
username=zhangsan&password=123456

4.2.3 响应数据
响应数据类型：application/json
响应参数说明：
名称	类型	是否必须	默认值	备注	其他信息
code	number	必须		响应码, 0-成功,1-失败	
message	string	非必须		提示信息	
data	string	必须		返回的数据,jwt令牌	
响应数据样例：
{
    "code": 0,
    "message": "操作成功",
    "data": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsiaWQiOjUsInVzZXJuYW1lIjoid2FuZ2JhIn0sImV4cCI6MTY5MzcxNTk3OH0.pE_RATcoF7Nm9KEp9eC3CzcBbKWAFOL0IsuMNjnZ95M"
}

4.2.4 备注说明
用户登录成功后，系统会自动下发JWT令牌，然后在后续的每次请求中，浏览器都需要在请求头header中携带到服务端，请求头的名称为 Authorization，值为 登录时下发的JWT令牌。
如果检测到用户未登录，则http响应状态码为401

4.3 获取用户详细信息
4.3.1 基本信息
请求路径：/user/userInfo
请求方式：GET
接口描述：该接口用于获取当前已登录用户的详细信息
4.3.2 请求参数
无
4.3.3 响应数据
响应数据类型：application/json
响应参数说明：
名称	类型	是否必须	默认值	备注	其他信息
code	number	必须		响应码, 0-成功,1-失败	
message	string	非必须		提示信息	
data	object	必须		返回的数据	
|-id	number	非必须		主键ID	
|-username	srting	非必须		用户名	
|-nickname	string	非必须		昵称	
|-email	string	非必须		邮箱	
|-userPic	string	非必须		头像地址	
|-createTime	string	非必须		创建时间	
|-updateTime	string	非必须		更新时间	
响应数据样例：
{
    "code": 0,
    "message": "操作成功",
    "data": {
        "id": 5,
        "username": "wangba",
        "nickname": "",
        "email": "",
        "userPic": "",
        "createTime": "2023-09-02 22:21:31",
        "updateTime": "2023-09-02 22:21:31"
    }
}

4.4 更新用户基本信息
4.4.1 基本信息
请求路径：/user/update
请求方式：PUT
接口描述：该接口用于更新已登录用户的基本信息(除头像和密码)
4.4.2 请求参数
请求参数格式：application/json
请求参数说明：
参数名称	说明	类型	是否必须	备注
id	主键ID	number	是	
username	用户名	string	否	5~16位非空字符
nickname	昵称	string	是	1~10位非空字符
email	邮箱	string	是	满足邮箱的格式
请求数据样例：
{
    "id":5,
    "username":"wangba",
    "nickname":"wb",
    "email":"wb@itcast.cn"
}
4.4.3 响应数据
响应数据类型：application/json
响应参数说明：
名称	类型	是否必须	默认值	备注	其他信息
code	number	必须		响应码, 0-成功,1-失败	
message	string	非必须		提示信息	
data	object	非必须		返回的数据	
响应数据样例：
{
    "code": 0,
    "message": "操作成功",
    "data": null
}

4.5 更新用户头像
4.5.1 基本信息
请求路径：/user/updateAvatar
请求方式：PATCH
接口描述：该接口用于更新已登录用户的头像
4.5.2 请求参数
请求参数格式：queryString
请求参数说明：
参数名称	说明	类型	是否必须	备注
avatarUrl	头像地址	string	是	url地址
请求数据样例：
avatarUrl=
https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png
4.5.3 响应数据
响应数据类型：application/json
响应参数说明：
名称	类型	是否必须	默认值	备注	其他信息
code	number	必须		响应码, 0-成功,1-失败	
message	string	非必须		提示信息	
data	object	非必须		返回的数据	
响应数据样例：
{
    "code": 0,
    "message": "操作成功",
    "data": null
}

4.6 更新用户密码
4.6.1 基本信息
请求路径：/user/updatePwd
请求方式：PATCH
接口描述：该接口用于更新已登录用户的密码
4.6.2 请求参数
请求参数格式：application/json
请求参数说明：
参数名称	说明	类型	是否必须	备注
old_pwd	原密码	string	是	
new_pwd	新密码	string	是	
re_pwd	确认新密码	string	是	
请求数据样例：
{
    "old_pwd":"123456",
    "new_pwd":"234567",
    "re_pwd":"234567"
}
4.6.3 响应数据
响应数据类型：application/json
响应参数说明：
名称	类型	是否必须	默认值	备注	其他信息
code	number	必须		响应码, 0-成功,1-失败	
message	string	非必须		提示信息	
data	object	非必须		返回的数据	
响应数据样例：
{
    "code": 0,
    "message": "操作成功",
    "data": null
}

五、其他接口
5.1 文件上传
5.1.1 基本信息
请求路径：/upload
请求方式：POST
接口描述：该接口用于上传文件(单文件)
5.1.2 请求参数
请求参数格式：multipart/form-data
请求参数说明：
参数名称	说明	类型	是否必须	备注	
file	表单中文件请求参数的名字	file	是		
请求数据样例：
无
5.1.3 响应数据
响应数据类型：application/json
响应参数说明：
名称	类型	是否必须	默认值	备注	其他信息
code	number	必须		响应码, 0-成功,1-失败	
message	string	非必须		提示信息	
data	string	必须		存贮在阿里云的链接	
响应数据样例：
{
    "code": 0,
    "message": "操作成功",
    "data": "https://xxx."
}

