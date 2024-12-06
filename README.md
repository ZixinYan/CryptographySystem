# 加解密系统的实现

## 项目简介
本项目实现了一个简单的加解密系统，支持常用经典密码加解密，ECC数据签名，DH密钥交换，用户间文件传输等功能。系统提供了图形化用户界面（UI），用户可以选择加解密算法并输入数据进行加解密操作。

---

## UI 界面展示
下图展示了系统的主要用户界面：

![UI 界面展示]()

---

## 功能模块
### 1. 加解密模块
实现了多种加密算法，包括：
- 对称加密（Affine, Autokey, Caesar, ColumnPermutation, DES, RC4, Playfair, Vigenere）
- 非对称加密（RSA）
- 数据签名(ECC)


界面展示如下：

![加密模块界面](path/to/your/encrypt-module-image.png)

---

### 2. 用户模块
功能信息：
- 登录
- 注册
- 找回密码（邮箱验证码）
- 完善信息（头像，邮箱，昵称）

界面展示如下：

![解密模块界面](path/to/your/decrypt-module-image.png)

---

### 3. 信件系统模块
实现了用户间的信件传递：
- 信件标题
- 接收人（对应的用户名）
- 加密算法（可写可不写）
- 上传文件（采用了aliyun的OSS存储，下载的时候响应速度会快一些）
- 文件内容（可写可不写）

查看文件的时候点击文件标题就可以了

界面展示如下：

![解密模块界面](path/to/your/decrypt-module-image.png)

---

### 4. DH密钥交换模块
实现了DH密钥传输过程：
#### 1.A发起DH交换请求
提供自动生成私钥，也可以自己写，选择交换密钥对象，私钥不会存入数据库所以要自己保存好

#### 2.B响应DH交换请求
B更新自己的私钥，生成公钥

#### 3.计算公共密钥
在信息完善后就可以进行计算了

#### 4.DH公共密钥删除
在任意一方进行删除就会在双方的存储系统中都删除，宣布报废

界面展示如下：

![解密模块界面](path/to/your/decrypt-module-image.png)

---


## 系统架构
### 1. 前端
- 使用 **Vue3**设计用户界面。
- 提供友好的交互体验。

### 2. 后端
- 基于 C++ 的加密算法实现
- 基于SpringBoot的后端框架

### 3.数据库
- MySQL进行用户数据，信件数据，DH公钥存储
- Redis进行登录token存储管理

---

## 使用说明
1. 克隆项目到本地：
   ```bash
   git clone https://github.com/zixin/encryption-system.git

2. 完善AliOSSUtil类，把自己的buket名称和AccessKey填进去
3. 在application.yml配置数据库信息
4. 在vscode文件夹下的是本项目的前端文件，tool文件夹下的本项目用到的加解密程序，fileStorage中存储了加解密中上传的文件，result中存储了加解密的结果文件

## 其他
1. 本系统的接口文档信息在https://www.yuque.com/yuqueyonghuutagdr/fbmk9t/bte5dfbcd2watgc9?singleDoc#
   这个链接有时间限制，如果过期了还需要的话可以发文件找我
3. 系统有一些设计暂时有缺陷，但是大体功能已经完善，其中的素材基本都源于网上，可以替换
