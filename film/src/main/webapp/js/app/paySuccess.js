var app = new Vue({
    el: '#app',
    data: {
        message:[]
    },
    methods: {
        toBuyTicket: function (id) {
            window.location.href = '/buyticket.html?id=' + id;
        },
        toChooseSeat: function (showTimeId) {
            window.location.href = '/chooseSeat.html?showTimeId=' + showTimeId;
        }
    },
    mounted: function () {
        $.ajax({
            type: 'post', //默认get
            url: "/api/query/queryTrade.do",
            async: true,   //是否异步（默认true：异步）
            dataType: "json",//定义服务器返回的数据类型
            success: function (data) {//data服务器返回的json字符串
                if (data.success) {
                    app.message = data.data;
                } else {
                    alert(data.errorMsg)
                }

            }
        });
    }
});