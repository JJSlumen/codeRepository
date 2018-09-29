var app = new Vue({
    el: '#app',
    data: {
        article: []
    },
    methods: {
        editText: function () {
            if ($("#edit").html()=='编辑') {
                $("#content").attr("contenteditable",true);
                $("#edit").html('提交');
            }else if ($("#edit").html()=='提交') {
                app.editContent();
                $("#content").removeAttr("contenteditable");
                $("#edit").html('编辑');
            }
        },
        getArticle:function () {
            $.ajax({
                type: 'post', //默认get
                url: "/article/getArticle",
                data: {id: 1},
                async: true,   //是否异步（默认true：异步）
                dataType: "json",//定义服务器返回的数据类型
                success: function (data) {//data服务器返回的json字符串
                    if (data.success) {
                        app.article = data.data;
                    } else {
                        alert(data.errorMsg)
                    }

                }
            });
        },
        editContent:function () {
            var content=$("#content").html();
            $.ajax({
                type: 'post', //默认get
                url: "/article/editContent",
                data: {
                    id:1,
                    content: content
                },
                async: true,   //是否异步（默认true：异步）
                dataType: "json",//定义服务器返回的数据类型
                success: function (data) {//data服务器返回的json字符串
                    if (data.success) {
                        alert("更新成功")
                    } else {
                        alert(data.errorMsg)
                    }

                }
            });
        }
    },
    mounted: function () {
        this.getArticle();
    }
});