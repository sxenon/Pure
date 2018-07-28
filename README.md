#Pure
基于RxJava2.x+MVP+...

详细文档会先写在CSDN上，Wiki上也会有。下面只是简要介绍一些关键的类与接口

##1、IRouter与IRouterVisitor

在将Activity,Fragment（及其直接子类）作为MVP中的V的基础上，提取出相似方法（例如startActivity、requestPermissions等等），抽象出IRouter接口，并做二次封装，而对于IRouter对象的通用操作（例如权限请求，返回键，生命周期管理，onActivityResult等等）抽象出IRouterVisitor接口，交由IRouter对象对应的Presenter来实现（见PureRouterVisitorAsPresenter）。由此若需要对IRouter对象定义新的操作，可以集中定义IRouterVisitor上，避免让这些操作“污染”IRouter对象的类。

##2、IViewHolder（**精华**）

指的是同一操作逻辑所涉及到的View组件的组合，与布局样式无关（甚至可以不是具体的View，形式上可以参考Adapter的ViewHolder，但与其itemView无关），是对View组件进行模块化处理，可以像使用一般View组件那样使用，甚至部分情况下可以复用。分为四类:

* submitter，处理数据的提交。

* filler **_(精华中的精华)_**，处理数据的填充，父接口IFillerViewHolder ，第三方刷新容器有很多，但都仅仅处理了容器本身的事件，但有了它可以协助您方便地处理分页、刷新的数据加载，空白页，错误页显示逻辑，特别地，像Fragment下的数据缓存与恢复。最重要的是，它不需要您修改已有的刷新容器的内部逻辑，只需要简单地用一下**适配器模式**就可以了，不明白的可以看pure-viewholder-demo这个module,而且重要的是丝毫不会影响扩展性，如果已有的实现还是不满足您的要求，您可以有自己的实现。

* navigation，导航用，例如**BaseSearchViewHolder**，官方的SearchView写的很辛苦，但实际却很少被使用，即使用了也会被嫌丑，可见做这些复合控件，是多么吃力不讨好，可自定义的属性再多，也满足不了所有人的需求，但操作逻辑是基本一致的。

* show，纯粹展示用的，没什么好谈的。

##3、IAdapterViewHolder、IPureAdapter、IItemViewTypeEntity

相信从此可以告别冗长的Adapter，具体见com.sxenon.pure.core.controller.adapter

##4、IResultDispatcher、IResultHandler

所有对M的请求结果，由IResultDispatcher来进行分发，自动选择合适的IResultHandler来处理(ISubmitResultHandler、IFetchListResultHandler、IFetchSingleResultHandler)，详见pure-result-demo，改写自NoHttp（https://github.com/yanzhenjie/NoHttp ）作者的一个Demo，在此表示感谢。


