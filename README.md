# demoLogin

#springboot 分页及其增删改查
````
delete /update/insert 成功返回一个整数1，失败0
select返回一个结果集

````
#cookie和session的关系

https://www.cnblogs.com/moyand/p/9047978.html

#session
````
1.在控制层直接使用，传给前端页面

               HttpSession session = request.getSession();
               session.setAttribute("telephone",telephone);
               return "/index";

2.清除session(session存放在运行项目的服务器上，登陆时插入数据库的信息也叫写入session，是登陆持久化)
        
        session.invalidate();
        return "/login";

````

3.关于退出系统时，清除session

  清除session对象内容的主要方法如下:

        (1)、removeAttribute()方法。该方法是用来删除session对象中保存的指定属性信息。

        例如：session.setAttribute("name", "iverson");session.removeAttribute("name");

        (2)、invalidate()方法。该方法可以清除session对象中的所有信息。

        例如：session.invalidate().
#cookies

````
借助cookies工具类 

public class CookieUtil {
    //设置Cookie
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String key, String value, int expiry){
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(expiry);
        response.addCookie(cookie);
    }

    //获取所有Cookie
    public static Map<String, String> getCookies(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        Cookie cookies[] = request.getCookies();
        if (cookies != null){
            for(int i = 0; i < cookies.length; i++){
                if(!"token".equals(cookies[i].getName()))
                    map.put(cookies[i].getName(), cookies[i].getValue());
            }
        }
        return map;
    }

    //清空所有的Cookie
    public void clear(HttpServletRequest request, HttpServletResponse response){
        Map<String, String> map = getCookies(request);
        Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, String> me = iter.next();
            Cookie cookie = new Cookie(me.getKey(), "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }
}

````
#Maybatis 分页插件使用步骤

https://blog.csdn.net/qq_27317475/article/details/81168241
#thmeleaf语法

https://blog.csdn.net/qq_31424825/article/details/79052192

#html5分页代码

````
<!--    分页预览数设置-->
<!--1.使用bootsrap nav and ul-->
    <nav aria-label="Page navigation">
        <ul class="pagination" >
            <!-- 首页 -->
            <li> <a th:href="@{/list.action}">首页</a></li>

            <li th:if="${pageInfo.pageNum == 1}"><span>上一页</span></li>
            <li th:if="${pageInfo.pageNum > 1}"><a th:href="@{/list.action(pageNum=${pageInfo.pageNum} -1)}">上一页</a></li>

            <!-- 当前页面小于等于4,展示<1，2，3，4...> -->
            <th:block data-th-if="${(pageInfo.pageNum) le 4}" data-th-each="i : ${#numbers.sequence(1,4)}">
              <li th:if="${pageInfo.pageNum == i}"  class="active"><span th:text="${i}">页码</span></li>
              <li th:if="${pageInfo.pageNum != i}"><a th:href="@{/list.action(pageNum=${i})}" th:text="${i}">页码</a></li>
            </th:block>

            <li data-th-if="${(pageInfo.pageNum) le 4}">
                <a th:href="@{/list.action(pageNum=${pageInfo.pageNum} + 3)}" >
                    <span >...</span>
                </a>
            </li>

            <!-- 最后一页与当前页面之差，小于等于3 展示<...14,15,16,17>-->
            <li data-th-if="${((pageInfo.pages)-(pageInfo.pageNum)) le 3}">
                <a th:href="@{/list.action(pageNum=${pageInfo.pageNum}-3)}" >
                    <span >...</span>
                </a>
            </li>
            <th:block data-th-if="${((pageInfo.pages)-(pageInfo.pageNum)) le 3}" data-th-each="i : ${#numbers.sequence((pageInfo.pages)-3, (pageInfo.pages))}">
                <li th:if="${pageInfo.pageNum == i}"  class="active"><span th:text="${i}">页码</span></li>
                <li th:if="${pageInfo.pageNum != i}"><a th:href="@{/list.action(pageNum=${i})}" th:text="${i}">页码</a></li>
            </th:block>

            <!-- 当前页面大于4，最后一页与当前页面之差大于3 展示<...7，8，9....>-->
            <li data-th-if="${((pageInfo.pageNum) gt 4) && (((pageInfo.pages)-(pageInfo.pageNum)) gt 3 )}">
                <a th:href="@{/list.action(pageNum=${pageInfo.pageNum} - 3)}" >
                    <span >...</span>
                </a>
            </li>
            <th:block  data-th-if="${((pageInfo.pageNum ) gt 4) && (((pageInfo.pages)-(pageInfo.pageNum )) gt 3 )}" data-th-each="i : ${#numbers.sequence(pageInfo.pageNum, (pageInfo.pageNum)+2)}">
                <li th:if="${pageInfo.pageNum == i}"  class="active"><span th:text="${i}">页码</span></li>
                <li th:if="${pageInfo.pageNum != i}"><a th:href="@{/list.action(pageNum=${i})}" th:text="${i}">页码</a></li>
            </th:block>

            <li   data-th-if="${((pageInfo.pageNum) gt 4) && (((pageInfo.pages)-(pageInfo.pageNum)) gt 3 )}">
                <a th:href="@{/list.action(pageNum=${pageInfo.pageNum} + 3)}" >
                    <span >...</span>
                </a>
            </li>

            <li th:if="${pageInfo.pageNum == pageInfo.pages}"><span>下一页</span></li>
            <li th:if="${pageInfo.pageNum < pageInfo.pages}">  <a th:href="@{/list.action(pageNum=${pageInfo.hasNextPage}?${pageInfo.nextPage}:${pageInfo.pages})}">下一页</a></li>
            <li>  <a th:href="@{/list.action(pageNum=${pageInfo.pages})}">尾页</a></li>
        </ul>
    </nav>
````
#ajax异步刷新

https://blog.csdn.net/weixin_42595884/article/details/93662944

````
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
     
````
#不提交表单
````
button属性 type = button

````
