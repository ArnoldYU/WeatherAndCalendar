

# 项目实训：天气+日历 说明文档







## PS1：所涉及到的非自己代码：

- https://github.com/zx391324751/MoJiDemo（仿墨迹天气折线图）
- http://blog.csdn.net/new_one_object/article/details/51993822（获取天气）

## PS2：所使用的获得天气信息的API

- https://www.heweather.com/documents（和风天气，通过京东万象获得（能得到近七天的天气信息）

## PS3：简答函数实现不在说明范围内

## 功能及涉及到的代码位置说明

### 一、主体架构

- 主体布局xml：activity_main.xml 

  viewpager1用于天气部分，viewpager2用于日历部分，navigation用于底部导航栏，drawer1用于实现侧栏

- 主函数：`com.example.arnold.weather`目录下的MainActivity

  本程序默认有三个固定城市“哈尔滨”，“北京”，“石家庄”，vp1添加viewpager1的碎片（也就是添加的城市），vp2是日历部分。

  侧栏拉出后，addcity点击事件用于添加城市，在`public void bottomwindow(View view)`中实现弹出城市并选择城市功能

### 二、天气部分

- 获得天气信息及存储天气信息实现为`com.example.arnold.weather.weathergetbyinternate`,该包代码引用于PS1中获取天气部分，个人修改部分为`com.example.arnold.weather.weathergetbyinternate.Util.Utility`，该类实现从网上获取“省”，“市”，“县”并存储到数据库以及获取天气的JSON信息并通过SharedPreferences进行存储

- 天气核心包：`com.example.arnold.fragement.weatheralilview`

  - DisplayUtil、HourItem、IndexHorizontalScrollView、Today24HourView用于折现图的绘制。所修改部分为Today24HourView，稍后介绍修改部分

  - weatherview核心类，Today24HourView是自定义view，需要在加载之前将信息传入。主要是通过SharedPreferences将数据存储，在加载的时候直接使用SharedPreferences的数据。

  - ```java
    public static int setcityname = 0;//用于选择城市
    public static int setcitymo = 3;//用于表示当前添加了几个城市，默认城市为3
    public static ArrayList cityname = new ArrayList();//用于存放城市的名称
    ```

  - cityname主要是在weatherview添加城市的时候动态修改，之后Today24HourView通过cityname.get(setcityname)来获得所添加城市的24小时预测城市信息，所获得的信息放入如下变量中

    ```java
    private ArrayList TEMP;//存放温度
    private ArrayList WINDY;//存放风力级别
    private ArrayList HOUR;//存放时间 和风天气只给从当前时间到之后的天气信息
    private ArrayList WEATHER_RES;// 天气的图标
    ```

  - 涉及到的xml：

    - weatherview.xml

### 三、日历部分

- 核心包：`com.example.arnold.fragement.calendaviewall`

