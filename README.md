#Pure
做一款开发框架应该做的最少的事

详细文档在路上，先简要介绍一下几个关键类与接口

##1、IRouter与IRouterVisitor

在将Activity,Fragment（及其直接子类）作为MVP中的V的基础上，提取出相似方法（例如startActivity、requestPermissions等等），抽象出IRouter接口，并做二次封装，而对于IRouter对象的通用操作（例如权限请求，键盘弹起通知，返回键，生命周期管理等等）抽象出IRouterVisitor接口，交由IRouter对象对应的Presenter来实现（见PureRouterPresenter）。由此若需要对IRouter对象定义新的操作，可以集中定义IRouterVisitor上，避免让这些操作“污染”IRouter对象的类。

##2、IViewComponentGroup（**精华**）

注意不是ViewGroup，指的是View组件的组合（甚至可以不是具体的View）与布局样式无关，是对View组件进行模块化处理，并且对其通用的部分，再组件化，尽可能多地复用。分为四类:

* submitter,例如**BaseSelectGroup**，处理列表的单选，多选，删除，插入操作。

* filler,例如**FillerGroup** **_(精华中的精华)_** 第三方刷新容器有很多，但都仅仅处理了容器本身的事件，但有了它可以协助您方便地处理分页、刷新的数据加载，空白页，错误页显示逻辑，特别地，像Fragment下的数据缓存与恢复。最重要的是，它不需要您修改已有的刷新容器的内部逻辑，只需要简单地用一下**适配器模式**就可以了，不明白的可以看pure-pull-demo这个module,会影响扩展性吗？我相信不会。

* navigation,例如**BaseSearchView**，官方的SearchView写的很辛苦，但实际却很少被使用，即使用了也会被嫌丑，可见做这些复合控件，是多么吃力不讨好，可自定义的属性再多，也满足不了所有人的需求。

* show，纯粹展示用的，没什么好谈的。

##3、IPureViewHolder、IPureAdapter、IItemViewTypeEntity

相信从此可以告别冗长的Adapter，具体见com.sxenon.pure.core.component.adapter

##4、IResultDispatcher、IResultHandler

所有对M的请求结果，由IResultDispatcher来进行分发，交给合适的IResultHandler来处理(ISubmitResultHandler、IFetchListResultHandler、IFetchSingleResultHandler、IFetchSetResultHandler)

##TODO
1、完善文档，同时优化细节，之后打tag，上传
