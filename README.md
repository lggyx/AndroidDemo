# lgDemo Android 实验项目

Android 基础功能实验集合，共 8 个独立子项目，使用 Java + Gradle 构建。

---

## 项目结构

```
lgDemo/
├── lgDemo01/   HelloWorld + Activity 生命周期
├── lgDemo02/   多 Activity 跳转与数据传递
├── lgDemo03/   GridLayout 计算器
├── lgDemo04/   "我的音乐盒"登录界面
├── lgDemo05/   触摸事件检测
├── lgDemo06/   Toolbar + OverflowMenu + SearchView
├── lgDemo07/   ContentProvider 通讯录增删查
└── lgDemo08/   BroadcastReceiver 无序/有序广播
```

---

## lgDemo01 — HelloWorld + Activity 生命周期

**技术要点：**
- 两个 Activity：`MainActivity`（启动页）和 `ActivityTwo`（第二页）
- `onCreate()` 中 `setContentView()` 加载布局
- 使用 `startActivityForResult()` 启动 `ActivityTwo`，通过 `onActivityResult()` 接收返回数据
- 布局包含 Button，点击后跳转并传回用户名

**关键代码：**
- [MainActivity.java](lgDemo01/src/main/java/com/lggyx/lgdemo/MainActivity.java) — 启动 ActivityTwo 并接收返回
- [activity_main.xml](lgDemo01/src/main/res/layout/activity_main.xml) — 主界面布局
- [activity_two.xml](lgDemo01/src/main/res/layout/activity_two.xml) — 第二页布局

**涉及概念：** Activity 四种启动模式、onCreate / onStart / onResume / onPause / onStop / onDestroy 生命周期回调

---

## lgDemo02 — 多 Activity 跳转与数据传递

**技术要点：**
- `MainActivity` → `ActivityLogin` → `ActivityTwo` 三页跳转
- Intent 传值：`putExtra()` / `getStringExtra()`
- 登录校验逻辑，成功后跳转并回传用户名
- 布局使用 `android:background` 设置背景色

**关键代码：**
- [MainActivity.java](lgDemo02/src/main/java/com/lggyx/lgdemo02/MainActivity.java)
- [ActivityLogin.java](lgDemo02/src/main/java/com/lggyx/lgdemo02/ActivityLogin.java)
- [ActivityTwo.java](lgDemo02/src/main/java/com/lggyx/lgdemo02/ActivityTwo.java)
- [activity_main.xml](lgDemo02/src/main/res/layout/activity_main.xml)

**涉及概念：** Intent 显式/隐式启动、Activity 间数据传递、返回结果（setResult + finish）

---

## lgDemo03 — GridLayout 计算器

**技术要点：**
- GridLayout（4列 × 5行）实现按钮矩阵
- 运算符：+ − × ÷ √ 1/x CE C
- `firstNum` / `secondNum` / `operator` / `result` 状态管理
- 合法性校验：除零、负数开平方、零求倒数、重复小数点
- 支持整数和小数运算

**关键代码：**
- [MainActivity.java](lgDemo03/src/main/java/com/lggyx/lgdemo03/MainActivity.java) — onClick / verify / 四则运算核心逻辑
- [activity_main.xml](lgDemo03/src/main/res/layout/activity_main.xml) — GridLayout 按钮矩阵
- [ic_sqrt.xml](lgDemo03/src/main/res/drawable/ic_sqrt.xml) — 根号图标

**涉及概念：** GridLayout 布局权重、View.OnClickListener、Toast 提示、数学运算逻辑

---

## lgDemo04 — "我的音乐盒"注册登录 + 首页

**技术要点：**
- `MainActivity`：注册登录页（LinearLayout + EditText + Button）
- `HomeActivity`：登录后首页，显示欢迎信息 + 退出按钮
- SharedPreferences 持久化存储用户名密码（`user.xml`）
- 注册：用户名密码非空校验 → `sp.edit().putString()` 存储
- 登录：比对存储的 uid/pwd，成功跳转首页
- 自动登录：已登录状态重新打开应用直接进入首页
- 退出登录：清除 SharedPreferences 返回登录页

**关键代码：**
- [MainActivity.java](lgDemo04/src/main/java/com/lggyx/lgdemo04/MainActivity.java) — register() / login() / goHome() 逻辑
- [HomeActivity.java](lgDemo04/src/main/java/com/lggyx/lgdemo04/HomeActivity.java) — 首页欢迎 + 退出
- [activity_main.xml](lgDemo04/src/main/res/layout/activity_main.xml) — 登录界面布局
- [activity_home.xml](lgDemo04/src/main/res/layout/activity_home.xml) — 首页布局

**涉及概念：** SharedPreferences、Intent 跳转、Activity finish()、Toast 提示、登录状态持久化

---

## lgDemo05 — 触摸事件检测

**技术要点：**
- 重写 `Activity.onTouchEvent(MotionEvent event)`
- 实时显示：事件类型（ACTION_DOWN / ACTION_UP / ACTION_MOVE）、坐标(x,y)、压力(pressure)、触点尺寸(size)
- 上方 touch_area（weight=1，背景 #0F2F67）+ 下方 event_label 信息展示区
- `Display()` 方法拼接信息字符串并通过 `setText()` 更新

**关键代码：**
- [MainActivity.java](lgDemo05/src/main/java/com/lggyx/lgdemo05/MainActivity.java) — onTouchEvent + Display 方法
- [activity_main.xml](lgDemo05/src/main/res/layout/activity_main.xml) — touch_area + event_label 布局

**涉及概念：** MotionEvent API、onTouchEvent 重写、TextView 实时刷新、Gravity 布局

---

## lgDemo06 — Toolbar + OverflowMenu + SearchView

**技术要点：**
- Toolbar 替代系统 ActionBar（`setSupportActionBar()`）
- OverflowMenu 三项：Notifications / Sign in / Set configuration
- SearchView 内嵌 Toolbar，实时过滤下方 ListView
- 实现 `SearchView.OnQueryTextListener` 接口
- ArrayAdapter + String[] 数据绑定

**关键代码：**
- [MainActivity.java](lgDemo06/src/main/java/com/lggyx/lgdemo06/MainActivity.java) — Toolbar 配置 + SearchView 过滤
- [activity_main.xml](lgDemo06/src/main/res/layout/activity_main.xml) — Toolbar + ListView 布局
- [menu_main.xml](lgDemo06/src/main/res/menu/menu_main.xml) — 菜单项（Search + Overflow）
- [styles.xml](lgDemo06/src/main/res/values/styles.xml) — AppTheme.NoActionBar + Toolbar 样式

**涉及概念：** Toolbar 配置、menu XML、actionViewClass、ListView 过滤、popupTheme

---

## lgDemo07 — ContentProvider 通讯录增删查

**技术要点：**
- 动态申请 READ_CONTACTS / WRITE_CONTACTS 权限
- 添加联系人：RawContacts → Data（姓名/电话/email）三步插入
- 查询联系人：Contacts → Phone → Email 三层 Cursor 关联
- AlertDialog + SimpleAdapter + ListView 展示结果
- listitem.xml 定义列表项（姓名蓝色、电话/email 黑色）

**关键代码：**
- [MainActivity.java](lgDemo07/src/main/java/com/lggyx/lgdemo07/MainActivity.java) — 权限检查 + AddPersonClick + LookPresonClick + getData
- [activity_main.xml](lgDemo07/src/main/res/layout/activity_main.xml) — 输入表单布局
- [activity_result.xml](lgDemo07/src/main/res/layout/activity_result.xml) — Dialog 内 ListView
- [listitem.xml](lgDemo07/src/main/res/layout/listitem.xml) — 联系人列表项样式
- [AndroidManifest.xml](lgDemo07/src/main/AndroidManifest.xml) — 通讯录权限声明

**涉及概念：** ContentResolver、ContentValues、Cursor、ContactsContract API、运行时权限申请

---

## lgDemo08 — BroadcastReceiver 无序/有序广播

**技术要点：**
- 自定义广播 action="myaction"，携带消息 Extra
- 无序广播 `sendBroadcast()`：MyBcReceiver1 接收，发 Notification + Toast(TOP)
- 有序广播 `sendOrderedBroadcast()`：
  - MyBcReceiver2（动态注册，priority=99）：Toast(CENTER) + `setResultExtras()` 传递附加消息
  - MyBcReceiver3（静态注册）：Toast(BOTTOM) + `getResultExtras()` 获取上游数据
- NotificationChannel（Android O+ 兼容）
- 动态注册在 onResume/onPause 中管理，防止内存泄漏

**关键代码：**
- [MainActivity.java](lgDemo08/src/main/java/com/lggyx/lgdemo08/MainActivity.java) — 发送广播 + 动态注册 MyBcReceiver2
- [MyBcReceiver1.java](lgDemo08/src/main/java/com/lggyx/lgdemo08/MyBcReceiver1.java) — 无序广播接收 + Notification
- [MyBcReceiver2.java](lgDemo08/src/main/java/com/lggyx/lgdemo08/MyBcReceiver2.java) — 动态注册 + setResultExtras
- [MyBcReceiver3.java](lgDemo08/src/main/java/com/lggyx/lgdemo08/MyBcReceiver3.java) — getResultExtras 获取上游数据
- [menu_main.xml](lgDemo06/src/main/res/menu/menu_main.xml)

**涉及概念：** 静态/动态注册 BroadcastReceiver、有序广播优先级、ResultExtras 数据传递、Notification 通知

---

## 构建与运行

1. 使用 Android Studio 打开项目根目录
2. Sync Gradle
3. 选择对应子模块（lgDemo01 ~ lgDemo08）运行
4. 最低 SDK 要求：lgDemo07/08 为 API 26，其余默认为项目配置

---

## 注意事项

- lgDemo04 需手动放入背景图：`lgDemo04/src/main/res/drawable/background.png`
- lgDemo06 需手动放入图标：`mipmap/gbtn`、`drawable/icon_notifications2` / `icon_sign2` / `icon_setting2`
- lgDemo07/08 运行时需要授予权限（通讯录 / 通知）
- lgDemo08 动态注册的 Receiver 需在 onPause 中注销，避免内存泄漏
