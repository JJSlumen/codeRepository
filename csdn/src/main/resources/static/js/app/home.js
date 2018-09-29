var app = new Vue({
    el: '#app',
    data: {
        article: []
    },
    methods: {
        toMyArticle: function () {
            window.location.href = "/html/myArticle.html";
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
        }
    },
    mounted: function () {
        this.getArticle();
    }
});