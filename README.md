# rabbit-study

这里是在线学习网站的后端代码。
后端采用Springboot工程，采用分模块设计，使用gateway网关作为同意访问入口，然后由网关找到对应服务的地址。
使用了nacos作为注册平台，可以进入nacos管理平台查看各个模块的启动与否。

############各个模块说明#####################
common模块里存放了公共的类和工具，
model模块里存放的是实体类，
service里有service_user模块，service_live模块和service_vod模块：
  service_user模块里存放的主要的代码，
  service_live模块存放的是直播模块，里面是直播的模块代码
  service_vod模块是视频模块，但目前已将其内容移送至user模块内
service_gateway模块是网关模块。  
