### 使用Groovy定义DSL教程

DSL（Domain Specific Language）官方定义为：针对某一**领域**，具有**受限表达性**的一种计算机程序设计**语言**。

常用于聚焦指定的领域或问题，这就要求 DSL 具备强大的表现力，同时在使用起来要简单。由于其使用简单的特性，DSL 通常不会像 Java，C++等语言将其应用于一般性的编程任务。

对于 Groovy 来说，一个伟大的 DSL 产物就是新一代构建工具——Gradle，接下来让我们看下有哪些特性来支撑Groovy方便的编写DSL：

### 一、原理

#### 1、闭包

官方定义是“**Groovy中的闭包是一个开放，匿名的代码块，可以接受参数，返回值并分配给变量**”

简而言之，他说一个匿名的代码块，可以接受参数，有返回值。在DSL中，一个DSL脚本就是一个闭包。

比如：

```
//执行一句话  
{ printf 'Hello World' }                                   
    
//闭包有默认参数it，且不用申明      
{ println it }                   

//闭包有默认参数it，申明了也无所谓                
{ it -> println it }      
    
// name是自定义的参数名  
{ name -> println name }                 

 //多个参数的闭包
{ String x, int y ->                                
    println "hey ${x} the value is ${y}"    
}
```

每定义的闭包是一个Closure对象，我们可以把一个闭包赋值给一个变量，然后调用变量执行

```
//闭包赋值
def closure = {
    printf("hello")
}
//调用
closure()
```

#### 2、括号语法

当调用的方法需要参数时，Groovy 不要求使用括号，若有多个参数，那么参数之间依然使用逗号分隔；如果不需要参数，那么方法的调用必须显示的使用括号。

```
def add(number) { 1 + number }

//DSL调用
def res = add 1
println res
```

也支持级联调用方式，举例来说，`a b c d` 实际上就等同于 `a(b).c(d)`

```
//定义
total = 0
def a(number) {
    total += number
    return this
}
def b(number) {
    total *= number
    return this
}

//dsl
a 2 b 3
println total
```

#### 3、无参方法调用

我们结合 Groovy 中对属性的访问就是对 getXXX 的访问，将无参数的方法名改成 getXXX 的形式，即可实现“调用无参数的方法不需要括号”的语法！比如：

```
def getTotal() { println "Total" }

//DSL调用
total
```

#### 4、MOP

MOP：元对象协议。由 Groovy 语言中的一种协议。该协议的出现为元编程提供了优雅的解决方案。而 MOP 机制的核心就是 MetaClass。

有点类似于 Java 中的反射，但是在使用上却比 Java 中的反射简单的多。

常用的方法有:

- invokeMethod()
- setProperty()
- hasProperty()
- methodMissing()

以下是一个methodMissing的例子：

```
detailInfo = [:]

def methodMissing(String name, args) {
    detailInfo[name] = args
}

def introduce(closure) {
    closure.delegate = this
    closure()
    detailInfo.each {
        key, value ->
            println "My $key is $value"
    }
}

introduce {
    name "zx"
    age 18
}
```

#### 5、定义和脚本分离

`@BaseScript` 需要在注释在自定义的脚本类型变量上，来指定当前脚本属于哪个Delegate，从而执行相应的脚本命令，也使IDE有自动提示的功能：

```
脚本定义
abstract class DslDelegate extends Script {
	def setName(String name){
        println name
    }
}
```

脚本：

```
import dsl.groovy.SetNameDelegate
import groovy.transform.BaseScript

@BaseScript DslDelegate _

setName("name")
```

#### 6、闭包委托

使用以上介绍的方法，只能在脚本里执行单个命令，如果想在脚本里执行复杂的嵌套关系，比如Gradle中的dependencies，就需要@DelegatesTo支持了，@DelegatesTo执行了脚本里定义的闭包用那个类来解析。

上面提到一个DSL脚本就是一个闭包，这里的DelegatesTo其实定义的是闭包里面的二级闭包的格式，当然如果你乐意，可以无限嵌套定义。

```
//定义二级闭包格式
class Conf{
    String name
    int age

    Conf name(String name) {
        this.name = name
        return this
    }

    Conf age(int age) {
        this.age = age
        return this
    }
}

//定义一级闭包格式，即脚本的格式
String user(@DelegatesTo(Conf.class) Closure<Conf> closure) {
    Conf conf = new Conf()
    DefaultGroovyMethods.with(conf, closure)
    println "my name is ${conf.name} my age is ${conf.age}"
}

//dsl脚本
user{
    name "tom"
    age 12
}
```

#### 7、加载并执行脚本

脚本可以在IDE里直接执行，大多数情况下DSL脚本都是以文本的形式存在数据库或配置中，这时候就需要先加载脚本再执行，加载脚本可以通过以下方式：

```
 CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
 compilerConfiguration.setScriptBaseClass(DslDelegate.class.getName());
 GroovyShell shell = new GroovyShell(GroovyScriptRunner.class.getClassLoader());
 Script script = shell.parse(file);
```

给脚本传参数，并得到返回结果：

```
Binding binding = new Binding();
binding.setProperty("key", anyValue);
Object res = InvokerHelper.createScript(script.getClass(), binding).run()
```

### 二、总结

通过以上的原理，你应该能设计出自己的DSL了，通多DSL可以设计出非常简洁的API给用户，在执行的时候调用DSL内部的复杂功能，这些功能的背后逻辑隐藏在了自己编写的Delegate中。

为了加深印象，我写了个小的开源项目，把上面知识点串起来，构建了一个较完整的流程，如果还有什么不懂的地方，欢迎留言交流。

项目地址：https://github.com/sofn/dsl-groovy

### 三、参考

官方MOP：https://groovy-lang.org/metaprogramming.html

领域专属语言：https://wiki.jikexueyuan.com/project/groovy-introduction/domain-specific-languages.html

实战Groovy系列：https://wizardforcel.gitbooks.io/ibm-j-pg/content/index.html

详细教程：https://lesofn.com

