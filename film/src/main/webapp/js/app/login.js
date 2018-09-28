var app = new Vue({
    el: '#app',
    data: {},
    methods: {
        submitForm: function () {
            var cardNo = $('#cardNo').val();
            var password = $('#password').val();
            $.ajax({
                type: 'post', //默认get
                url: "/api/user/login.do",
                data: {
                    cardNo: cardNo,
                    password: password
                },
                async: true,   //是否异步（默认true：异步）
                dataType: "json",//定义服务器返回的数据类型
                success: function (data) {//data服务器返回的json字符串
                    if (data.success) {
                        window.location.href = '/home.html';
                    } else {
                        alert(data.errorMsg)
                    }
                }
            });
        }
    },
    mounted: function () {

    }
});