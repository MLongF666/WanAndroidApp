## 9月12号

* 完成了Android cookie的接收 以及请求发送定义cookie内容

* 完成了room与Repository的结合使用

* 不足/未掌握的技术 协程 以及liveData函数当中执行room数据库操作失败 没有报错 一直阻塞运行 所以换一种方案：不在liveData函数当中对数据库操作 转而在Repository自定义saveUser方法 拿到dabse引用操作数据库 veiwModel拿到Repository引用来操作 最终由UI判断是否登录成功之后 就通知下面两层去对数据进行保存

## 9月13号

