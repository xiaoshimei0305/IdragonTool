简介

> 让事情变得简单，覆盖九成情况，留给项目一线生机
>
> 如果我们发现90%的情况下要使用同样一个功能，请把代码移步至此。

* 收集常用工具

* 快速分享工具

* 避免重复编码

| 模块  | 描述                             |
| ----- | -------------------------------- |
| base  | 基本工具，如：StringUtils        |
| http  | 网络请求，如：服务器请求获取数据 |
| word  | word文档常用操作                 |
| excel | 解析excel文档获取数据            |

## 快速开始

* 引入工具包

  ```
  <!-- https://mvnrepository.com/artifact/store.idragon.tool/base -->
  <dependency>
      <groupId>store.idragon.tool</groupId>
      <artifactId>base</artifactId>
      <version>0.0.1-RELEASE</version>
  </dependency>
  ```

* 操作示例

  ```
   //直接读取表格数据
   JSONObject data = ExcelReadUtils.getDataByFileName("***.xlsx");
   //读取指定sheet页数据，通过Title映射到对应实体当中
   List<UserInfo> list=ExcelReadUtils.getDataByFileName("user.xlsx","sheet",UserInfo.class);
  ```