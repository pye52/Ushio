# Ushio

又双叒叕一个Bangumi的第三方客户端，由于Bgm38和Bangumi-Android已经不更新了，于是自己也做了一个…至少目前是没有坑的想法。

采取MVP + Retrofit + RxJava2 + Realm实现，大部分代码采用Kotlin编写(终于得到Google官方承认了，yeah~)

## 预览

| 首页   | 详情页  | 进度页  | 每日放送 | 个人页  |
| ---- | ---- | ---- | ---- | ---- |
|      |      |      |      |      |

## 注意

本仓库中并没提交 API 文件，如果要使用本仓库的代码，你需要向 Sai 要 API 文档，然后配合 [Retrofit](https://github.com/square/retrofit) 来添加 API 文件，最后才可以编译。

## TODO

[ ]  每日放送页面提供快速订阅番剧的功能

[ ] 进度页提供类似于一键签到的功能(快速标注当天更新的番剧为"已看") 

[ ] 动画角色、cv的详情页面

## Thanks

[Bgm38](https://github.com/zubinxiong/Bgm38)：部分样式参考

[Bangumi-Android](https://github.com/scarletsky/Bangumi-Android)：图标，部分样式参考