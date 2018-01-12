<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        a{
            cursor: pointer;
        }
        a:hover{color: blue;}
    </style>
    <script src="/static/jquery-1.11.1.min.js"></script>
</head>
    <body>
        <div  style="text-align: center">
            <table border="1" style="margin: 6% 0 0 6%;width: 90%;">
                <tr>
                    <th>任务名</th>
                    <th>code</th>
                    <th>cronExpression</th>
                    <th colspan="4">编辑</th>
                </tr>
                [#list systemTaskList as systemTask]
                    <tr>
                        <td>${systemTask.name}</td>
                        <td>${systemTask.code}</td>
                        <td>${systemTask.cronExpression}</td>
                        <td><a onclick="runNowTask('${systemTask.id}')">触发一次</a></td>
                        <td>
                            [#if systemTask.status == 1]
                                <a onclick="pauseTask('${systemTask.id}')">暂停</a>
                            [#else ]
                                <a onclick="resumeTask('${systemTask.id}')">恢复</a>
                            [/#if]
                        </td>
                        <td>
                            <a onclick="deleteTask('${systemTask.id}')">删除</a>
                        </td>
                        <td>
                            <a>更新</a>
                        </td>
                    </tr>
                [/#list]
            </table>
        </div>
    </body>
    <script>
            function deleteTask(systemTaskId) {
                if(confirm("确定删除？")){
                    $.ajax({
                        method: 'post',
                        url: "/systemTask/delete",
                        data: {id : systemTaskId},
                        success: function(data){
                            if(!data.errorcode){
                                alert("操作成功")
                                location.reload();
                            }
                        }
                    })
                }
            }

            function pauseTask(systemTaskId) {
                if(confirm("确定暂停？")){
                    $.ajax({
                        method: 'post',
                        url: "/systemTask/pause",
                        data: {id : systemTaskId},
                        success: function(data){
                            if(!data.errorcode){
                                alert("操作成功");
                                location.reload();
                            }
                        }
                    })
                }
            }

            function resumeTask(systemTaskId) {
                if(confirm("确定恢复？")){
                    $.ajax({
                        method: 'post',
                        url: "/systemTask/resume",
                        data: {id : systemTaskId},
                        success: function(data){
                            if(!data.errorcode){
                                alert("操作成功");
                                location.reload();
                            }
                        }
                    })
                }
            }

            function runNowTask(systemTaskId) {
                if(confirm("确定触发一次？")){
                    $.ajax({
                        method: 'post',
                        url: "/systemTask/runNow",
                        data: {id : systemTaskId},
                        success: function(data){
                            if(!data.errorcode){
                                alert("操作成功");
                               location.reload();
                            }
                        }
                    })
                }
            }
    </script>

</html>