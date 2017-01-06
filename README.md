#Pure
做一套开发框架应该做的最少的事

详细文档在路上，先简要介绍一下几个关键类与接口

##1、IRouter

Activity,Fragment及它们的一些子类，有部分共同的方法，例如startActivity，requestPermissions等等，对于与之相关的Presenter和ViewHolder等等，绝大多数情况下是不需要差别对待了（Glide是例外，但很少，也很好处理）。

##2、PureRootPresenter

是否经常遇到这样的情况，在BaseActivity里封装的一些功能，又要在BaseFragment里面写一遍，可怕的是还有BaseDialogFragment等等。交给PureRootPresenter，已经帮您处理好的有权限请求，键盘弹起通知，返回键，IRouter生命周期的管理，订阅的自动取消等等。

##3、IViewComponentGroup（**精华**）

注意不是ViewGroup，指的是View组件的组合，与布局样式无关，是对View组件进行模块化处理，并且对其通用的部分，再组件化，尽可能多地复用。分为四类:

* submitter,例如**ISelectGroup**，处理列表的单选，多选，删除，插入操作。

* filler,例如**FillerGroup**，第三方刷新容器有很多，但都仅仅处理了容器本身的事件，但有了它可以协助您方便地处理分页、刷新的数据加载，空白页，错误页显示逻辑，特别地，像Fragment下的数据缓存与恢复。最重要的是，它不需要您修改已有的刷新容器的内部逻辑，只需要简单地用一下**适配器**模式就可以了，不明白的可以看pure-pull-demo这个module,会影响扩展性吗？我相信不会。

* navigation,例如**ISearchView**，官方的SearchView写的很辛苦，但实际却很少被使用，即使用了也会被嫌丑，可见做这些复合控件，是多么吃力不讨好，可自定义的属性再多，也满足不了所有人的需求。

* show，纯粹展示用的，没什么好谈的。

##4、IPureViewHolder、IPureAdapter、IItemViewTypeEntity

相信从此可以告别冗长的Adapter，具体见com.sxenon.pure.core.component.adapter

##5、IResultDispatcher、IResultHandle

所有对M的请求结果，由IResultDispatcher来进行分发，交给合适的IResultHandler来处理(ISubmitResultHandler、IFetchListResultHandler、IFetchSingleResultHandler、IFetchSetResultHandler)

##TODO
1、完善文档，同时优化细节，之后打tag，上传
