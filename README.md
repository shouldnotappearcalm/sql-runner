# sql-runner 多数据源 sql 执行服务

## 简介

这是一个支持多数据源的 sql 执行服务，你可以通过这个服务来执行你想执行的 sql，可以调用接口，可以自己部署一个，默认端口 3111，登录 admin，密码 6789@jkl

## 接口使用

POST `api/v1/sql/actions/exec`

```json
{
    "databaseInfoDto": {
        "databaseHost": "172.25.18.14",
        "databaseName": "zentao",
        "databasePassword": "6789@jkl",
        "databasePort": "3306",
        "databaseType": "MySQL",
        "databaseUser": "root"
    },
    "sql": "select * from zt_user limit 10"
}
```

具体可以在运行 sql 页面运行试试，看看接口请求的参数，一些时间和 user 相关参数是没用的，可以不传。就传上面几个参数即可

返回结果主要分为 row 代表返回函数，dataArray 是返回的具体数据

```json
{
    "row": 10,
    "dataArray": [
        // 我注释掉了
    ]
```
