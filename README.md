# demoLogin

#springboot 分页及其增删改查
````
delete /update/insert 成功返回一个整数1，失败0
select返回一个结果集

````
#Maybatis 分页插件使用步骤

https://blog.csdn.net/qq_27317475/article/details/81168241

#ajax异步刷新
ajax默认为异步刷新



               $.ajaxSetup({
                                   async:false
                               }) 
                $.post("/add.action", $("#mobileform").serialize(), function (data) {
                    alert(data)
                    if (data === "OK") {
                        alert("客户添加成功");
                        window.location.reload();
                    } else {
                        alert("客户添加失败");
                        window.location.reload();
                    }
                })
            })
     

#不提交表单
````
button属性 type = button

